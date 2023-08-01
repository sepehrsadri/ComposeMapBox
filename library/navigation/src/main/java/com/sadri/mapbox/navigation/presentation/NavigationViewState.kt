package com.sadri.mapbox.navigation.presentation

import com.mapbox.navigation.base.route.NavigationRoute
import com.sadri.mapbox.navigation.model.NavigationModeEntity

data class NavigationViewState(
  val navigationRoute: NavigationRoute? = null,
  val failure: Boolean = false,
  val mode: NavigationModeEntity = NavigationModeEntity.SELECT_ORIGIN
)