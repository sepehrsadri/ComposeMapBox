package com.sadri.mapbox.navigation.domain

import com.mapbox.geojson.Point
import com.mapbox.navigation.base.route.NavigationRoute
import kotlinx.coroutines.flow.Flow

interface NavigationProvider {
  fun requestRoute(
    origin: Point,
    destination: Point
  ): Flow<Result<NavigationRoute>>
}