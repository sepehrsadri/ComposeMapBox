package com.sadri.mapbox.navigation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadri.mapbox.location.data.repository.LocationRepository
import com.sadri.mapbox.navigation.data.repository.NavigationRepository
import com.sadri.mapbox.navigation.model.NavigationActionEntity
import com.sadri.mapbox.navigation.model.NavigationModeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
  private val locationRepository: LocationRepository,
  private val navigationRepository: NavigationRepository
) : ViewModel() {

  private val _viewState = MutableStateFlow(NavigationViewState())
  val viewState: StateFlow<NavigationViewState> = _viewState

  init {
    viewModelScope.launch {
      navigationRepository.state.collect { state ->
        _viewState.value = viewState.value.copy(
          mode = state.mode
        )
        state.navigationRoute
          ?.onSuccess {
            _viewState.value = viewState.value.copy(navigationRoute = it)
          }
          ?.onFailure {
            _viewState.value = viewState.value.copy(failure = true)
          }

        when (state.mode) {
          NavigationModeEntity.ROUTING -> {
            val origin = locationRepository.state.value.originPoint!!
            val destination = locationRepository.state.value.destinationPoint!!
            navigationRepository.reduce(
              NavigationActionEntity.RequestRoute(
                origin = origin,
                destination = destination
              )
            )
          }
          else -> {
            // no-op
          }
        }
      }
    }
  }

}