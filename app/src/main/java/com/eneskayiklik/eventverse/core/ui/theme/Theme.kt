package com.eneskayiklik.eventverse.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
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
)

@Composable
fun EventverseTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme || isSystemInDarkTheme()) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}