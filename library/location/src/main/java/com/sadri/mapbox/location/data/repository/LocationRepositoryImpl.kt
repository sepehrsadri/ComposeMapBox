package com.sadri.mapbox.location.data.repository

import com.mapbox.geojson.Point
import com.sadri.mapbox.core.dispatcher.DispatcherProvider
import com.sadri.mapbox.location.domain.LocationProvider
import com.sadri.mapbox.location.model.LocationActionEntity
import com.sadri.mapbox.location.model.LocationStateEntity
import com.sadri.mapbox.location.util.toPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
  private val dispatcherProvider: DispatcherProvider,
  private val locationProvider: LocationProvider
) : LocationRepository {

  private val _state = MutableStateFlow(LocationStateEntity())

  override val state = _state

  private var job: Job? = null

  @OptIn(DelicateCoroutinesApi::class)
  override fun startWatchingLocation() {
    locationProvider.start()
    job = GlobalScope.launch(dispatcherProvider.default) {
      locationProvider.locationFlow.collect { location ->
        _state.value = state.value.copy(
          currentPoint = location.toPoint()
        )
      }
    }
  }

  override fun stopWatchingLocation() {
    locationProvider.stop()
    job?.cancel()
  }

  override suspend fun lastLocation(): Point? {
    return locationProvider.getLastLocation()?.toPoint()
  }

  @OptIn(DelicateCoroutinesApi::class)
  override fun reduce(locationActionEntity: LocationActionEntity) {
    when (locationActionEntity) {
      is LocationActionEntity.UpdateDestination -> {
        _state.value = state.value.copy(
          destinationPoint = locationActionEntity.point
        )
      }
      is LocationActionEntity.UpdateOrigin -> {
        _state.value = state.value.copy(
          originPoint = locationActionEntity.point,
          currentPoint = null
        )
      }
      LocationActionEntity.MoveToCurrentLocation -> {
        GlobalScope.launch(dispatcherProvider.default) {
          val lastLocation = locationProvider.getLastLocation() ?: return@launch
          _state.value = state.value.copy(
            currentPoint = lastLocation.toPoint(),
            originPoint = lastLocation.toPoint(),
            destinationPoint = null
          )
        }
      }
    }
  }
}