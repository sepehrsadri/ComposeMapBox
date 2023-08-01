package com.sadri.mapbox.location.model

import com.mapbox.geojson.Point
import com.sadri.mapbox.location.util.isSame

data class LocationStateEntity(
  val originPoint: Point? = null,
  val destinationPoint: Point? = null,
  val currentPoint: Point? = null
) {
  val isOriginOnCurrentPoint: Boolean
    get() {
      return (
         (originPoint != null && currentPoint != null) &&
            (originPoint.isSame(currentPoint))
         )
    }
}