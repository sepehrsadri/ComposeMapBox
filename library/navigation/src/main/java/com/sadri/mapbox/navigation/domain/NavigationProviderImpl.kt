package com.sadri.mapbox.navigation.domain

import android.content.Context
import com.mapbox.api.directions.v5.models.RouteOptions
import com.mapbox.geojson.Point
import com.mapbox.navigation.base.extensions.applyDefaultNavigationOptions
import com.mapbox.navigation.base.extensions.applyLanguageAndVoiceUnitOptions
import com.mapbox.navigation.base.options.NavigationOptions
import com.mapbox.navigation.base.route.NavigationRoute
import com.mapbox.navigation.base.route.NavigationRouterCallback
import com.mapbox.navigation.base.route.RouterFailure
import com.mapbox.navigation.base.route.RouterOrigin
import com.mapbox.navigation.core.MapboxNavigation
import com.sadri.mapbox.navigation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class NavigationProviderImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : NavigationProvider {

  private val mapboxNavigation = MapboxNavigation(
    NavigationOptions
      .Builder(context)
      .accessToken(context.resources.getString(R.string.mapbox_access_token))
      .build()
  )

  override fun requestRoute(origin: Point, destination: Point): Flow<Result<NavigationRoute>> =
    callbackFlow {
      val routeOptions = RouteOptions.builder()
        .applyDefaultNavigationOptions()
        .applyLanguageAndVoiceUnitOptions(context)
        .coordinatesList(listOf(origin, destination))
        .build()

      val routeRequestId = mapboxNavigation.requestRoutes(
        routeOptions,
        object : NavigationRouterCallback {
          override fun onCanceled(routeOptions: RouteOptions, routerOrigin: RouterOrigin) {
            trySend(Result.failure(Exception("requestRoutes onCanceled")))
          }

          override fun onFailure(reasons: List<RouterFailure>, routeOptions: RouteOptions) {
            trySend(Result.failure(Exception("requestRoutes onFailure")))
          }

          override fun onRoutesReady(routes: List<NavigationRoute>, routerOrigin: RouterOrigin) {
            trySend(Result.success(routes.first()))
          }
        }
      )
      awaitClose {
        mapboxNavigation.cancelRouteRequest(routeRequestId)
      }
    }
}