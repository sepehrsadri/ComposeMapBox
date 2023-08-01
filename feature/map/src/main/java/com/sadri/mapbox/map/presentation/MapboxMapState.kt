package com.sadri.mapbox.map.presentation

import androidx.compose.runtime.Immutable
import com.mapbox.geojson.Point
import com.mapbox.navigation.base.route.NavigationRoute

@Immutable
data class MapboxMapState(
  val originPoint: Point?,
  val destinationPoint: Point?,
  val navigationRoute: NavigationRoute?
)