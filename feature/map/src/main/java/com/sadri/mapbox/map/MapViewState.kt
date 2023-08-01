package com.sadri.mapbox.map

import com.mapbox.geojson.Point

sealed class MapViewState(val point: Point?) {
  object Permission : MapViewState(null)
  object Loading : MapViewState(null)
   class CurrentLocation( point: Point) : MapViewState(point)
  data class Navigation(val mode: NavigationMode) : MapViewState(null)
}

enum class NavigationMode {
  SELECT_ORIGIN, SELECT_DESTINATION, NAVIGATION, DRIVE
}