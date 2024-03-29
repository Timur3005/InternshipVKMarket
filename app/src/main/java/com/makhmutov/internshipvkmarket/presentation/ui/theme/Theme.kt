package com.makhmutov.internshipvkmarket.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Blue200,
    onPrimary = Blue300,
    surface = Grey200,
    onSurface = Grey100,
    background = Black100,
    onBackground = White200
)

private val LightColorScheme = lightColorScheme(
    primary = Blue100,
    onPrimary = Color.White,
    surface = Grey100,
    onSurface = Grey200,
    background = White100,
    onBackground = Black100
)

@Composable
fun InternshipVKMarketTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}