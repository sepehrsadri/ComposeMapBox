package com.sadri.mapbox.navigation.data.repository

import com.sadri.mapbox.core.dispatcher.DispatcherProvider
import com.sadri.mapbox.navigation.domain.NavigationProvider
import com.sadri.mapbox.navigation.model.NavigationActionEntity
import com.sadri.mapbox.navigation.model.NavigationModeEntity
import com.sadri.mapbox.navigation.model.NavigationStateEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NavigationRepositoryImpl @Inject constructor(
  private val dispatcherProvider: DispatcherProvider,
  private val navigationProvider: NavigationProvider
) : NavigationRepository {
  private val _state = MutableStateFlow(NavigationStateEntity())

  override val state = _state

  @OptIn(DelicateCoroutinesApi::class)
  override fun reduce(action: NavigationActionEntity) {
    return when (action) {
      NavigationActionEntity.DestinationSelected -> {
        _state.value = state.value.copy(
          mode = NavigationModeEntity.ROUTING
        )
      }
      NavigationActionEntity.Drive -> {
        _state.value = state.value.copy(
          mode = NavigationModeEntity.DRIVE
        )
      }
      NavigationActionEntity.MoveToCurrentLocation -> {
        _state.value = state.value.copy(
          mode = NavigationModeEntity.SELECT_ORIGIN,
          navigationRoute = null
        )
      }
      NavigationActionEntity.OriginSelected -> {
        _state.value = state.value.copy(
          mode = NavigationModeEntity.SELECT_DESTINATION
        )
      }
      is NavigationActionEntity.RequestRoute -> {
        GlobalScope.launch(dispatcherProvider.io) {
          navigationProvider.requestRoute(
            origin = action.origin,
            destination = action.destination
          ).collect { result ->
            _state.value = state.value.copy(
              navigationRoute = result,
              mode = NavigationModeEntity.NAVIGATION
            )
          }
        }
        Unit
      }
    }
  }
}