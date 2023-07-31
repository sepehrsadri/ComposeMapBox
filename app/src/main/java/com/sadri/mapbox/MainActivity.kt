package com.sadri.mapbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadri.composemovie.designsystem.theme.ComposeMapBoxTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      ComposeMapBoxTheme {
      }
    }
  }
}