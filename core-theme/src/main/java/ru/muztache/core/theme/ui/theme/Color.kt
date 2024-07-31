package ru.muztache.core.theme.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class MuztacheColors(
    val primaryText: Color,
    val secondaryText: Color,
    val surface: Color
)

val darkColors = MuztacheColors(
    primaryText = Color(0xFF000000),
    secondaryText = Color(0xFF4A4A4A),
    surface = Color(0xFFFFFFFF)
)

val lightColors = MuztacheColors(
    primaryText = Color(0xFF000000),
    secondaryText = Color(0xFF4A4A4A),
    surface = Color(0xFFFFFFFF)
)

val LocalMuztacheColors = staticCompositionLocalOf<MuztacheColors> {
    error("Colors have not been provided")
}