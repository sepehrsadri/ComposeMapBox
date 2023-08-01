package com.sadri.mapbox.navigation.data.repository

import com.sadri.mapbox.navigation.model.NavigationActionEntity
import com.sadri.mapbox.navigation.model.NavigationModeEntity
import com.sadri.mapbox.navigation.model.NavigationStateEntity
import kotlinx.coroutines.flow.StateFlow

interface NavigationRepository {
  val state: StateFlow<NavigationStateEntity>

  fun reduce(action: NavigationActionEntity)
}