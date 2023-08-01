package com.sadri.mapbox.navigation.model

import com.mapbox.geojson.Point


sealed class NavigationActionEntity {
  data class RequestRoute(val origin: Point, val destination: Point) : NavigationActionEntity()
  object MoveToCurrentLocation : NavigationActionEntity()
  object OriginSelected : NavigationActionEntity()
  object DestinationSelected : NavigationActionEntity()
  object Drive : NavigationActionEntity()
}