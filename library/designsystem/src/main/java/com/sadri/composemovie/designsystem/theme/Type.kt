package com.sadri.composemovie.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

@Suppress("DEPRECATION")
val DefaultTextStyle = TextStyle(
  platformStyle = PlatformTextStyle(
    includeFontPadding = false
  ),
  lineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None
  )
)

val Typography = Typography(
  displayLarge = DefaultTextStyle.copy(
    fontSize = 57.sp, lineHeight = 64.sp, letterSpacing = (-0.25).sp
  ),
  displayMedium = DefaultTextStyle.copy(
    fontSize = 45.sp, lineHeight = 52.sp, letterSpacing = 0.sp
  ),
  displaySmall = DefaultTextStyle.copy(
    fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = 0.sp
  ),
  headlineLarge = DefaultTextStyle.copy(
    fontSize = 24.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp,
    fontWeight = FontWeight.Medium
  ),
  headlineMedium = DefaultTextStyle.copy(
    fontSize = 20.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.sp,
    fontWeight = FontWeight.Medium
  ),
  headlineSmall = DefaultTextStyle.copy(
    fontSize = 18.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp,
    fontWeight = FontWeight.Medium
  ),
  titleLarge = DefaultTextStyle.copy(
    fontSize = 20.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp,
    fontWeight = FontWeight.Normal
  ),
  titleMedium = DefaultTextStyle.copy(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp,
    fontWeight = FontWeight.Normal
  ),
  titleSmall = DefaultTextStyle.copy(
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp,
    fontWeight = FontWeight.Medium
  ),
  labelLarge = DefaultTextStyle.copy(
    fontSize = 16.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp,
    fontWeight = FontWeight.Medium
  ),
  labelMedium = DefaultTextStyle.copy(
    fontSize = 14.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    fontWeight = FontWeight.Medium
  ),
  labelSmall = DefaultTextStyle.copy(
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    fontWeight = FontWeight.Medium
  ),

  bodyLarge = DefaultTextStyle.copy(
    fontSize = 18.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp
  ),
  bodyMedium = DefaultTextStyle.copy(
    fontSize = 16.sp, lineHeight = 20.sp, letterSpacing = 0.25.sp
  ),
  bodySmall = DefaultTextStyle.copy(
    fontSize = 14.sp, lineHeight = 16.sp, letterSpacing = 0.4.sp
  ),
)