package com.example.ui.theme

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

private val DarkColorScheme =
  darkColorScheme(
    primary = PolishPrimaryDark,
    onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = PolishPrimaryContainer,
    secondary = PolishSecondaryDark,
    onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458),
    onSecondaryContainer = PolishSecondaryContainer,
    tertiary = PolishTertiaryDark,
    onTertiary = Color(0xFF492532),
    tertiaryContainer = Color(0xFF633B48),
    onTertiaryContainer = Color(0xFFFFD8E4),
    background = PolishDarkBackground,
    onBackground = PolishDarkOnSurface,
    surface = PolishDarkSurface,
    onSurface = PolishDarkOnSurface,
    surfaceVariant = PolishDarkSurfaceVariant,
    onSurfaceVariant = PolishDarkOnSurfaceVariant,
    outline = PolishDarkOutline,
    outlineVariant = Color(0xFF49454F)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = PolishPrimary,
    onPrimary = Color.White,
    primaryContainer = PolishPrimaryContainer,
    onPrimaryContainer = PolishOnPrimaryContainer,
    secondary = PolishSecondary,
    onSecondary = Color.White,
    secondaryContainer = PolishSecondaryContainer,
    onSecondaryContainer = PolishOnSecondaryContainer,
    tertiary = PolishTertiary,
    onTertiary = Color.White,
    background = PolishBackground,
    onBackground = PolishOnSurface,
    surface = PolishSurface,
    onSurface = PolishOnSurface,
    surfaceVariant = PolishSurfaceVariant,
    onSurfaceVariant = PolishOnSurfaceVariant,
    outline = PolishOutline,
    outlineVariant = PolishOutlineVariant
  )

@Composable
fun KanjiKotobaTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  KanjiKotobaTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}


