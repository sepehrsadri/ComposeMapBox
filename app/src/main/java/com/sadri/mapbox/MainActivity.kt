package com.sadri.mapbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadri.mapbox.designsystem.theme.ComposeMapBoxTheme
import com.sadri.mapbox.map.presentation.MapScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ComposeMapBoxTheme {
        MapScreen()
      }
    }
  }
}