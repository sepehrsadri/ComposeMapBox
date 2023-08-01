package com.sadri.mapbox.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.sadri.mapbox.designsystem.component.ContentDescription
import com.sadri.mapbox.designsystem.theme.space


@Composable
fun MapScreen(
  viewModel: MapViewModel = hiltViewModel()
) {
  val state = viewModel.viewState.collectAsState().value

  val onPermissionDenied = remember {
    { viewModel.onPermissionDenied() }
  }
  val onPermissionAccepted = remember {
    { viewModel.onPermissionAccepted() }
  }

  val permissionRequest = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions(),
    onResult = { permissions ->
      if (!permissions.values.all { it }) {
        onPermissionDenied.invoke()
      } else {
        onPermissionAccepted.invoke()
      }
    }
  )

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        modifier = Modifier.size(MaterialTheme.space.xxLarge),
        onClick = viewModel::moveToCurrentLocation
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
      MapBoxMap(
        point = state.point,
        modifier = Modifier.fillMaxSize()
      )
      when (state) {
        MapViewState.Loading -> {
          Loading()
        }
        MapViewState.Permission -> {
          SideEffect {
            permissionRequest.launch(
              arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
              )
            )
          }
        }
        is MapViewState.CurrentLocation -> {

        }
        is MapViewState.Navigation -> {

        }
      }
    }
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