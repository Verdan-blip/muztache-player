package ru.muztache.core.theme.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun MuztacheTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            //window.statusBarColor = MaterialTheme.colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MuztacheThemeProvider(darkTheme = darkTheme, content = content)
}

@Composable
fun MuztacheThemeProvider(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColors else lightColors
    val typography = MuztacheTypography()

    CompositionLocalProvider(
        LocalMuztacheColors provides colors,
        LocalMuztacheTypography provides typography,
        content = content
    )
}

object MuztacheTheme {

    val colors: MuztacheColors
        @Composable get() = LocalMuztacheColors.current

    val typography: MuztacheTypography
        @Composable get() = LocalMuztacheTypography.current
}