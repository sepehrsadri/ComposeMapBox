package com.sadri.mapbox.location.data.repository

import com.mapbox.geojson.Point
import com.sadri.mapbox.location.model.LocationActionEntity
import com.sadri.mapbox.location.model.LocationStateEntity
import kotlinx.coroutines.flow.StateFlow

interface LocationRepository {
  val state: StateFlow<LocationStateEntity>

  fun startWatchingLocation()
  fun stopWatchingLocation()
  suspend fun lastLocation(): Point?
  fun reduce(locationActionEntity: LocationActionEntity)
}