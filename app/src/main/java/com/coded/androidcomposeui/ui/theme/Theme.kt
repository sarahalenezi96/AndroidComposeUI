package com.coded.androidcomposeui.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CustomColorScheme = lightColorScheme(
    primary = Color(0xFF4A90E2),
    onPrimary = Color.White,
    secondary = Color(0xFF7BDFF2),
    onSecondary = Color(0xFF003C5E),
    background = Color(0xFFE0F2FF),
    onBackground = Color(0xFF1A1A1A),
    surface = Color.White,
    onSurface = Color(0xFF1A1A1A)
)

@Composable
fun AndroidComposeUITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CustomColorScheme,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
