package com.sadri.mapbox.navigation.model

import androidx.compose.runtime.Immutable
import com.mapbox.navigation.base.route.NavigationRoute

data class NavigationStateEntity(
  val mode: NavigationModeEntity = NavigationModeEntity.SELECT_ORIGIN,
  val navigationRoute: Result<NavigationRoute>? = null
)

@Immutable
enum class NavigationModeEntity {
  SELECT_ORIGIN, SELECT_DESTINATION, ROUTING, NAVIGATION, DRIVE
}