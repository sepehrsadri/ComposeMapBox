package com.sadri.mapbox.map.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.sadri.mapbox.location.data.repository.LocationRepository
import com.sadri.mapbox.location.model.LocationActionEntity
import com.sadri.mapbox.navigation.data.repository.NavigationRepository
import com.sadri.mapbox.navigation.model.NavigationActionEntity
import com.sadri.mapbox.navigation.model.NavigationModeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
  private val locationRepository: LocationRepository,
  private val navigationRepository: NavigationRepository
) : ViewModel() {

  private val _viewState: MutableStateFlow<MapViewState> = MutableStateFlow(MapViewState())
  val viewState: StateFlow<MapViewState> = _viewState


  init {
    viewModelScope.launch {
      locationRepository.state.collect { locationState ->
        _viewState.value = viewState.value.copy(
          originPoint = locationState.originPoint,
          destinationPoint = locationState.destinationPoint,
          loading = false
        )
      }
    }
  }

  fun onPermissionAccepted() {
    moveToCurrentLocation()
  }

  fun moveToCurrentLocation() {
    if (locationRepository.state.value.isOriginOnCurrentPoint.not()) {
      _viewState.value = viewState.value.copy(loading = true)
    }
    locationRepository.reduce(LocationActionEntity.MoveToCurrentLocation)
    navigationRepository.reduce(NavigationActionEntity.MoveToCurrentLocation)
  }

  fun onMapLongClick(point: Point) {
    when (navigationRepository.state.value.mode) {
      NavigationModeEntity.SELECT_ORIGIN -> {
        locationRepository.reduce(
          LocationActionEntity.UpdateOrigin(point)
        )
        navigationRepository.reduce(
          NavigationActionEntity.OriginSelected
        )
      }
      NavigationModeEntity.SELECT_DESTINATION -> {
        locationRepository.reduce(
          LocationActionEntity.UpdateDestination(point)
        )
        navigationRepository.reduce(
          NavigationActionEntity.DestinationSelected
        )
      }

      else -> {
        // no-op
      }
    }
  }
}