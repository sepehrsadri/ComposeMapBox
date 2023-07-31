package com.sadri.composemovie.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
  small = RoundedCornerShape(4.dp),
  medium = RoundedCornerShape(8.dp),
  large = RoundedCornerShape(16.dp),
  extraLarge = RoundedCornerShape(32.dp)
)

val LocalShapes = staticCompositionLocalOf { Shapes }

val MaterialTheme.shapes: Shapes
  @Composable
  @ReadOnlyComposable
  get() = LocalShapes.current