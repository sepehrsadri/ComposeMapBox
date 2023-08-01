package com.sadri.mapbox.location.util

import android.location.Location
import com.mapbox.geojson.Point

fun Location.isSame(loc: Location): Boolean {
  return this.latitude == loc.latitude &&
     this.longitude == loc.longitude
}

fun Location.toPoint(): Point {
  return Point.fromLngLat(
    this.longitude,
    this.latitude
  )
}

fun Point.isSame(point: Point): Boolean {
  return this.latitude() == point.latitude() &&
     this.longitude() == point.longitude()
}