package com.android.objectmanagerapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6D8E8F),     // Soft Teal
    secondary = Color(0xFFB0A98F),   // Warm Beige
    background = Color(0xFFF1F3F4),  // Light Gray
    surface = Color(0xFFFFFFFF),     // White
    onPrimary = Color(0xFFFFFFFF),   // White
    onSecondary = Color(0xFF2E2E2E), // Slightly lighter Dark Gray
    onBackground = Color(0xFF1C1C1C),// Very Dark Gray
    onSurface = Color(0xFF1C1C1C),   // Very Dark Gray
    error = Color(0xFFB71C1C),       // Dark Red
    onError = Color(0xFFFFFFFF)      // White
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4A6D6F),     // Darker Teal
    secondary = Color(0xFF8C7D5F),   // Darker Beige
    background = Color(0xFF121212),  // Dark Gray
    surface = Color(0xFF1E1E1E),     // Darker Gray
    onPrimary = Color(0xFFFFFFFF),   // White
    onSecondary = Color(0xFFE0E0E0), // Light Gray
    onBackground = Color(0xFFE0E0E0),// Light Gray
    onSurface = Color(0xFFE0E0E0),   // Light Gray
    error = Color(0xFFB71C1C),           // Light Red
    onError = Color(0xFF000000)      // Black
)







@Composable
fun ObjectManagerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}