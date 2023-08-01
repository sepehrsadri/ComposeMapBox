package com.sadri.mapbox.map

import androidx.compose.runtime.Immutable
import com.mapbox.geojson.Point

@Immutable
data class MapViewState(
  val point: Point? = null,
  val destinationPoint: Point? = null,
  val loading: Boolean = true,
  val mode: NavigationMode = NavigationMode.SELECT_ORIGIN
)

@Immutable
enum class NavigationMode {
  SELECT_ORIGIN, SELECT_DESTINATION, NAVIGATION, DRIVE
}