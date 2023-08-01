package com.sadri.mapbox.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.sadri.mapbox.location.LocationProvider
import com.sadri.mapbox.location.toPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
  private val locationProvider: LocationProvider
) : ViewModel() {

  private val _viewState: MutableStateFlow<MapViewState> = MutableStateFlow(MapViewState())
  val viewState: StateFlow<MapViewState> = _viewState


  fun onPermissionDenied() {

  }

  fun onPermissionAccepted() {
    moveToCurrentLocation()
  }

  fun moveToCurrentLocation() {
    viewModelScope.launch {
      _viewState.value = viewState.value.copy(loading = true)
      val currentLocation = locationProvider.getLastLocation() ?: return@launch
      _viewState.value = viewState.value.copy(
        loading = false,
        point = currentLocation.toPoint(),
        destinationPoint = null,
        mode = NavigationMode.SELECT_ORIGIN
      )
    }
  }

  fun onMapLongClick(point: Point) {
    when (viewState.value.mode) {
      NavigationMode.SELECT_ORIGIN -> {
        _viewState.value = viewState.value.copy(
          point = point,
          mode = NavigationMode.SELECT_DESTINATION
        )
      }
      NavigationMode.SELECT_DESTINATION -> {
        _viewState.value = viewState.value.copy(
          destinationPoint = point,
          mode = NavigationMode.NAVIGATION
        )
      }
      else -> {
        _viewState.value = viewState.value.copy(
          point = point,
          destinationPoint = null,
          mode = NavigationMode.SELECT_DESTINATION
        )
      }
    }

  }

  override fun onCleared() {
    super.onCleared()
  }
}