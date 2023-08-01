package com.sadri.mapbox.map.presentation

import androidx.compose.runtime.Immutable
import com.mapbox.geojson.Point
import com.mapbox.navigation.base.route.NavigationRoute
import com.sadri.mapbox.navigation.model.NavigationModeEntity

@Immutable
data class MapViewState(
  val loading: Boolean = true,
  val originPoint: Point? = null,
  val destinationPoint: Point? = null,
  val navigationRoute: NavigationRoute? = null,
  val routeFailure: Boolean = false,
  val mode: NavigationModeEntity = NavigationModeEntity.SELECT_ORIGIN
)