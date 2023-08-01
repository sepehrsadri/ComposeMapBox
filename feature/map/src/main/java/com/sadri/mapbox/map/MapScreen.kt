package com.sadri.mapbox.map

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mapbox.common.location.GetLocationCallback
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point


@Composable
fun MapScreen() {
  var point: Point? by remember {
    mutableStateOf(null)
  }
  var relaunch by remember {
    mutableStateOf(false)
  }
  val context = LocalContext.current
  val permissionRequest = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions(),
    onResult = { permissions ->
      if (!permissions.values.all { it }) {
        //handle permission denied
      } else {
        relaunch = !relaunch
      }
    }
  )

  Column(
    modifier = Modifier.fillMaxSize(),
  ) {
    MapBoxMap(
      onPointChange = { point = it },
      point = point,
      modifier = Modifier
        .fillMaxSize()
    )
  }

  LaunchedEffect(key1 = relaunch) {
    try {
      val locationService = LocationServiceFactory.locationService()
      locationService.getCurrentLocation(
        null,
        GetLocationCallback {
          val location = it.value ?: return@GetLocationCallback
          point = Point.fromLngLat(location.longitude, location.latitude)
        }
      )
    } catch (e: Exception) {
      permissionRequest.launch(
        arrayOf(
          android.Manifest.permission.ACCESS_FINE_LOCATION,
          android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
      )
    }
  }
}