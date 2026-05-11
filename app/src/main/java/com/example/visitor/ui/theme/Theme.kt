@file:Suppress("DEPRECATION")

package com.example.visitor.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val Brand      = Color(0xFF1565C0)
private val BrandLight = Color(0xFF5E92F3)
private val Accent     = Color(0xFF00897B)
private val AccentLight= Color(0xFF4EBAAA)

private val DarkColorScheme = darkColorScheme(
    primary   = BrandLight,
    secondary = AccentLight,
    tertiary  = Color(0xFFCE93D8)
)

private val LightColorScheme = lightColorScheme(
    primary   = Brand,
    secondary = Accent,
    tertiary  = Color(0xFF7B1FA2)
)

@Composable
fun VisitorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,   // ← false = utilise notre palette ci-dessus
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    // Status bar couleur
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}