package com.example.medtrack.ui.util

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

@Immutable
data class CustomColorsPalette(
    val surfaceContainer: Color = Color.Unspecified,
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

val LightCustomColorsPalette = CustomColorsPalette(
    surfaceContainer = Color(color = 0xFFEDEEF0),
)

val DarkCustomColorsPalette = CustomColorsPalette(
    surfaceContainer = Color(color = 0xFF1D2022),
)