package com.sadri.mapbox.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

  private val _viewState: MutableStateFlow<MapViewState> = MutableStateFlow(MapViewState.Permission)
  val viewState: StateFlow<MapViewState> = _viewState


  fun onPermissionDenied() {

  }

  fun onPermissionAccepted() {
    moveToCurrentLocation()
  }

  fun moveToCurrentLocation() {
    viewModelScope.launch {
      _viewState.value = MapViewState.Loading
      val currentLocation = locationProvider.getLastLocation() ?: return@launch
      _viewState.value = MapViewState.CurrentLocation(currentLocation.toPoint())
    }
  }

  override fun onCleared() {
    super.onCleared()
  }
}