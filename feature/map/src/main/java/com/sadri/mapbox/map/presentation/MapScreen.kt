package com.sadri.mapbox.map.presentation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mapbox.geojson.Point
import com.sadri.mapbox.designsystem.component.ContentDescription
import com.sadri.mapbox.designsystem.theme.space
import com.sadri.mapbox.map.R
import com.sadri.mapbox.navigation.model.NavigationModeEntity
import com.sadri.mapbox.navigation.presentation.NavigationViewModel
import timber.log.Timber


@Composable
fun MapScreen(
  mapViewModel: MapViewModel = hiltViewModel(),
  navigationViewModel: NavigationViewModel = hiltViewModel()
) {
  val mapViewState = mapViewModel.viewState.collectAsState().value
  val navigationViewState = navigationViewModel.viewState.collectAsState().value

  var permission by remember {
    mutableStateOf(false)
  }

  val onPermissionAccepted = remember {
    { mapViewModel.onPermissionAccepted() }
  }
  val onMapLongClick: (Point) -> Unit = remember {
    { point ->
      mapViewModel.onMapLongClick(point)
    }
  }

  val permissionRequest = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions(),
    onResult = { permissions ->
      if (permission) return@rememberLauncherForActivityResult
      if (!permissions.values.all { it }) {
        // no-op
      } else {
        permission = true
        onPermissionAccepted.invoke()
      }
    }
  )

  LaunchedEffect(permission) {
    permissionRequest.launch(
      arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
      )
    )
  }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        modifier = Modifier.size(MaterialTheme.space.xxLarge),
        backgroundColor = MaterialTheme.colorScheme.onSurface,
        onClick = mapViewModel::moveToCurrentLocation
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_current_location),
          contentDescription = ContentDescription.CURRENT_LOCATION
        )
      }
    }
  ) { padding ->
    val modifier = Modifier.padding(padding)
    Box(
      modifier = modifier.fillMaxSize(),
    ) {
      MapboxMap(
        modifier = Modifier.fillMaxSize(),
        state = MapboxMapState(
          originPoint = mapViewState.originPoint,
          destinationPoint = mapViewState.destinationPoint,
          navigationRoute = navigationViewState.navigationRoute
        )
      ) { longClickedPoint ->
        onMapLongClick.invoke(longClickedPoint)
      }

      if (mapViewState.loading) {
        Loading()
      }

      Timber.d("WTF : navigation mode : ${navigationViewState.mode.name}")

      when (navigationViewState.mode) {
        NavigationModeEntity.SELECT_ORIGIN -> {
          ShowGuideText(text = R.string.select_origin)
        }
        NavigationModeEntity.SELECT_DESTINATION -> {
          ShowGuideText(text = R.string.select_destination)
        }
        NavigationModeEntity.NAVIGATION -> {
          ShowGuideText(text = R.string.start_navigation)
        }
        NavigationModeEntity.ROUTING -> {
          Loading()
          ShowGuideText(text = R.string.routing)
        }
        NavigationModeEntity.DRIVE -> {
          // no-op
        }
      }
    }
  }

}

@Composable
fun BoxScope.ShowGuideText(
  @StringRes text: Int
) {
  Card(
    shape = MaterialTheme.shapes.extraLarge,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface),
    modifier = Modifier.fillMaxWidth()
  ) {
    Text(
      text = stringResource(id = text),
      style = MaterialTheme.typography.titleLarge,
      modifier = Modifier
        .padding(MaterialTheme.space.small)
        .align(Alignment.CenterHorizontally)
        .fillMaxWidth()
    )
  }
}

@Composable
fun BoxScope.Loading() {
  CircularProgressIndicator(
    modifier = Modifier
      .size(MaterialTheme.space.xxLarge)
      .align(Alignment.Center),
    color = MaterialTheme.colorScheme.primary
  )
}