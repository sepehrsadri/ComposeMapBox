package com.sadri.mapbox.location.model

import com.mapbox.geojson.Point

sealed class LocationActionEntity {
  object MoveToCurrentLocation : LocationActionEntity()
  data class UpdateOrigin(val point: Point) : LocationActionEntity()
  data class UpdateDestination(val point: Point) : LocationActionEntity()
}