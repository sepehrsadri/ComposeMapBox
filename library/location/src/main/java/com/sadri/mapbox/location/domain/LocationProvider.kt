package com.sadri.mapbox.location.domain

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.sadri.mapbox.core.dispatcher.DispatcherProvider
import com.sadri.mapbox.location.util.isSame
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

@Singleton
class LocationProvider @Inject constructor(
  @ApplicationContext private val context: Context,
  private val dispatcher: DispatcherProvider
) : LocationListener {
  private val locationManager: LocationManager =
    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

  private val _locationFlow = MutableSharedFlow<Location>()
  val locationFlow: SharedFlow<Location>
    get() = _locationFlow

  @Volatile
  private var lastLocation: Location? = null

  @Volatile
  private var isRunning: Boolean = false

  private suspend fun updateLocation(location: Location) {
    if (lastLocation != null && lastLocation!!.isSame(location)) {
      return
    }

    _locationFlow.emit(location)
    lastLocation = location
  }

  @Synchronized
  fun start() {
    if (isRunning) {
      return
    }

    isRunning = true

    updateLastLocation()

    try {
      this.locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        MINIMUM_LOCATION_TIME_INTERVAL,
        MINIMUM_LOCATION_METER_INTERVAL,
        this
      )
    } catch (
      securityException: SecurityException
    ) {
      Timber.e("Location Permission is not permitted ! ")
      isRunning = false
    }
  }

  @Synchronized
  fun stop() {
    if (!isRunning) {
      return
    }
    isRunning = false

    locationManager.removeUpdates(this)
  }

  @OptIn(DelicateCoroutinesApi::class)
  private fun updateLastLocation() = GlobalScope.launch(dispatcher.ui) {
    lastLocation = getLastLocation()
  }

   suspend fun getLastLocation(): Location? =
    withContext(dispatcher.default) {
      var locationGPS: Location? = null
      var locationNet: Location? = null

      try {
        locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
      } catch (securityException: SecurityException) {
        Timber.e("Location Permission is not permitted ! ")
      }
      var gpsLocationTime: Long = 0
      if (locationGPS != null) {
        gpsLocationTime = locationGPS.time
      }
      var netLocationTime: Long = 0
      if (locationNet != null) {
        netLocationTime = locationNet.time
      }
      return@withContext if (0 < gpsLocationTime - netLocationTime) {
        locationGPS
      } else {
        locationNet
      }
    }

  @Synchronized
  fun getCurrentLocation() = lastLocation
  private suspend fun isBetterLocation(
    location: Location,
    currentBestLocation: Location
  ): Boolean =
    withContext(dispatcher.default) {
      // new Location would not process if update was too fast.
      if (
        abs(location.time - currentBestLocation.time) <
        LOCATION_UPDATE_MIN_DELTA_TIME
      ) {
        return@withContext false
      }
      // Check whether the new location fix is newer or older
      val timeDelta = location.time - currentBestLocation.time
      val isSignificantlyNewer: Boolean = timeDelta > LOCATION_FRESHNESS_TIMEOUT
      val isSignificantlyOlder: Boolean = timeDelta < -LOCATION_FRESHNESS_TIMEOUT
      val isNewer = timeDelta > 0
      // If it's been more than two minutes since the current location
      // , use the new location
      // because the user has likely moved
      if (isSignificantlyNewer) {
        return@withContext true
        // If the new location is more than two minutes older, it must be worse
      } else if (isSignificantlyOlder) {
        return@withContext false
      }
      // Check whether the new location fix is more or less accurate
      val accuracyDelta = (location.accuracy - currentBestLocation.accuracy).toInt()
      val isLessAccurate = accuracyDelta > 0
      val isMoreAccurate = accuracyDelta < 0
      val isSignificantlyLessAccurate = accuracyDelta > LOCATION_LESS_ACCURATE_DELTA
      // Check if the old and new location are from the same provider
      val isFromSameProvider: Boolean = isSameProvider(
        location.provider,
        currentBestLocation.provider
      )
      // Determine location quality using a combination of timeliness and accuracy
      (
         isMoreAccurate ||
            isNewer &&
            !isLessAccurate ||
            isNewer &&
            !isSignificantlyLessAccurate &&
            isFromSameProvider
         )
    }

  private fun isSameProvider(
    provider1: String?,
    provider2: String?
  ): Boolean {
    return if (provider1 == null) {
      provider2 == null
    } else provider1 == provider2
  }

  @OptIn(DelicateCoroutinesApi::class)
  override fun onLocationChanged(location: Location) {
    GlobalScope.launch(dispatcher.ui) {
      when {
        lastLocation == null -> {
          updateLocation(location)
        }
        lastLocation!!.distanceTo(location) >= MINIMUM_LOCATION_METER_INTERVAL &&
           isBetterLocation(
             location,
             lastLocation!!
           ) -> {
          updateLocation(location)
        }
        else -> {
          Timber.d("Can't fetch any new location !")
        }
      }
    }
  }

  override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    // Ignore
  }

  override fun onProviderEnabled(provider: String) {
    // Ignore
  }

  override fun onProviderDisabled(provider: String) {
    Timber.e("Gps provider is disabled !")
  }

  companion object {
    private const val MINIMUM_LOCATION_TIME_INTERVAL: Long = 1000L
    private const val MINIMUM_LOCATION_METER_INTERVAL: Float = 10.0f
    private const val LOCATION_UPDATE_MIN_DELTA_TIME: Int = 50
    private const val LOCATION_FRESHNESS_TIMEOUT: Int = 500
    private const val LOCATION_LESS_ACCURATE_DELTA = 200
  }
}