package com.eneskayiklik.eventverse.core.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.eneskayiklik.eventverse.data.model.profile.DarkMode

/*private val DarkColorPalette = darkColors(
    primary = Purple200,
    secondary = DarkSecondary,
    onSecondary = OnDarkSecondary,
    background = Black100,
    onBackground = White,
    surface = Black300,
    onSurface = OnSurface,
)

private val LightColorPalette = lightColors(
    primary = Purple200,
    secondary = Secondary,
    onSecondary = OnSecondary,
    background = White,
    onBackground = Black300,
    surface = BlueBackground,
    onSurface = OnSurface

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)*/

@Composable
fun EventverseTheme(theme: DarkMode = DarkMode.FOLLOW_SYSTEM, content: @Composable () -> Unit) {
    val darkTheme = when (theme) {
        DarkMode.LIGHT -> false
        DarkMode.DARK -> true
        else -> isSystemInDarkTheme()
    }

    // Animate colors on theme change
    val colors = Colors(
        primary = Purple200,
        onPrimary = White,
        secondary = animateColorAsState(targetValue = if (darkTheme) DarkSecondary else Secondary).value,
        onSecondary = animateColorAsState(targetValue = if (darkTheme) OnDarkSecondary else OnSecondary).value,
        background = animateColorAsState(targetValue = if (darkTheme) Black100 else White).value,
        onBackground = animateColorAsState(targetValue = if (darkTheme) White else Black300).value,
        surface = animateColorAsState(targetValue = if (darkTheme) Black300 else BlueBackground).value,
        onSurface = OnSurface,
        primaryVariant = Color(0xFF3700B3),
        secondaryVariant = Color(0xFF018786),
        error = if (darkTheme) Color(0xFFCF6679) else Color(0xFFB00020),
        onError = White,
        isLight = darkTheme.not()
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}