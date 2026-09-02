package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

fun getPaletteLightColorScheme(palette: ThemePalette): ColorScheme {
    return when (palette) {
        ThemePalette.CRIMSON -> lightColorScheme(
            primary = Color(0xFFC62828),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFFEBEE),
            onPrimaryContainer = Color(0xFF5F0000),
            secondary = Color(0xFF8D6E63),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFEFEBE9),
            onSecondaryContainer = Color(0xFF3E2723),
            tertiary = Color(0xFFD81B60),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFFCE4EC),
            onTertiaryContainer = Color(0xFF560027),
            background = PolishBackground,
            onBackground = PolishOnSurface,
            surface = PolishSurface,
            onSurface = PolishOnSurface,
            surfaceVariant = PolishSurfaceVariant,
            onSurfaceVariant = PolishOnSurfaceVariant,
            outline = PolishOutline,
            outlineVariant = PolishOutlineVariant
        )
        ThemePalette.INDIGO -> lightColorScheme(
            primary = Color(0xFF1565C0),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE3F2FD),
            onPrimaryContainer = Color(0xFF002171),
            secondary = Color(0xFF455A64),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFECEFF1),
            onSecondaryContainer = Color(0xFF263238),
            tertiary = Color(0xFF00838F),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFE0F7FA),
            onTertiaryContainer = Color(0xFF004D40),
            background = Color(0xFFF8FAFC),
            onBackground = Color(0xFF0F172A),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF0F172A),
            surfaceVariant = Color(0xFFF1F5F9),
            onSurfaceVariant = Color(0xFF475569),
            outline = Color(0xFFCBD5E1),
            outlineVariant = Color(0xFFE2E8F0)
        )
        ThemePalette.MATCHA -> lightColorScheme(
            primary = Color(0xFF2E7D32),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE8F5E9),
            onPrimaryContainer = Color(0xFF003300),
            secondary = Color(0xFF558B2F),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFF1F8E9),
            onSecondaryContainer = Color(0xFF1B5E20),
            tertiary = Color(0xFF00796B),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFE0F2F1),
            onTertiaryContainer = Color(0xFF004D40),
            background = Color(0xFFF7FAF7),
            onBackground = Color(0xFF142015),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF142015),
            surfaceVariant = Color(0xFFEEF5EE),
            onSurfaceVariant = Color(0xFF3B4D3C),
            outline = Color(0xFFC4D6C4),
            outlineVariant = Color(0xFFE0EBE0)
        )
        ThemePalette.SAKURA -> lightColorScheme(
            primary = Color(0xFFAD1457),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFCE4EC),
            onPrimaryContainer = Color(0xFF560027),
            secondary = Color(0xFF880E4F),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFF8BBD0),
            onSecondaryContainer = Color(0xFF4A0033),
            tertiary = Color(0xFFC2185B),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFFFDDEB),
            onTertiaryContainer = Color(0xFF4A002A),
            background = Color(0xFFFFF7F9),
            onBackground = Color(0xFF26181C),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF26181C),
            surfaceVariant = Color(0xFFFCEBF1),
            onSurfaceVariant = Color(0xFF5C404B),
            outline = Color(0xFFE6CAD4),
            outlineVariant = Color(0xFFF5E0E7)
        )
        ThemePalette.FUJI -> lightColorScheme(
            primary = Color(0xFF6A1B9A),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFF3E5F5),
            onPrimaryContainer = Color(0xFF38006B),
            secondary = Color(0xFF512DA8),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFEDE7F6),
            onSecondaryContainer = Color(0xFF311B92),
            tertiary = Color(0xFF7B1FA2),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFF5E8FA),
            onTertiaryContainer = Color(0xFF4A148C),
            background = Color(0xFFFAF7FC),
            onBackground = Color(0xFF201724),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF201724),
            surfaceVariant = Color(0xFFF4ECF7),
            onSurfaceVariant = Color(0xFF4E4154),
            outline = Color(0xFFD4C8DB),
            outlineVariant = Color(0xFFECE4F1)
        )
        ThemePalette.KOGANE -> lightColorScheme(
            primary = Color(0xFFB45309),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFFEF3C7),
            onPrimaryContainer = Color(0xFF451A03),
            secondary = Color(0xFF92400E),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFFDE68A),
            onSecondaryContainer = Color(0xFF451A03),
            tertiary = Color(0xFFD97706),
            onTertiary = Color.White,
            tertiaryContainer = Color(0xFFFFFBEB),
            onTertiaryContainer = Color(0xFF78350F),
            background = Color(0xFFFFFDF7),
            onBackground = Color(0xFF261E14),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF261E14),
            surfaceVariant = Color(0xFFF9F4E8),
            onSurfaceVariant = Color(0xFF52473A),
            outline = Color(0xFFDDD2C0),
            outlineVariant = Color(0xFFF0E8DA)
        )
    }
}

fun getPaletteDarkColorScheme(palette: ThemePalette, isOled: Boolean = false): ColorScheme {
    val bg = if (isOled) Color(0xFF000000) else PolishDarkBackground
    val surf = if (isOled) Color(0xFF0C0C0E) else PolishDarkSurface
    val surfVar = if (isOled) Color(0xFF1E1E22) else PolishDarkSurfaceVariant

    return when (palette) {
        ThemePalette.CRIMSON -> darkColorScheme(
            primary = Color(0xFFFF8983),
            onPrimary = Color(0xFF680005),
            primaryContainer = Color(0xFF93000A),
            onPrimaryContainer = Color(0xFFFFDAD6),
            secondary = Color(0xFFD7CCC8),
            onSecondary = Color(0xFF3E2723),
            secondaryContainer = Color(0xFF5D4037),
            onSecondaryContainer = Color(0xFFEFEBE9),
            tertiary = Color(0xFFFFB2BE),
            onTertiary = Color(0xFF5E0025),
            background = bg,
            onBackground = PolishDarkOnSurface,
            surface = surf,
            onSurface = PolishDarkOnSurface,
            surfaceVariant = surfVar,
            onSurfaceVariant = PolishDarkOnSurfaceVariant,
            outline = PolishDarkOutline,
            outlineVariant = Color(0xFF534341)
        )
        ThemePalette.INDIGO -> darkColorScheme(
            primary = Color(0xFF90CAF9),
            onPrimary = Color(0xFF003258),
            primaryContainer = Color(0xFF0D47A1),
            onPrimaryContainer = Color(0xFFD1E4FF),
            secondary = Color(0xFFB0BEC5),
            onSecondary = Color(0xFF263238),
            secondaryContainer = Color(0xFF37474F),
            onSecondaryContainer = Color(0xFFECEFF1),
            tertiary = Color(0xFF80DEEA),
            onTertiary = Color(0xFF00363A),
            background = bg,
            onBackground = Color(0xFFE2E8F0),
            surface = surf,
            onSurface = Color(0xFFE2E8F0),
            surfaceVariant = surfVar,
            onSurfaceVariant = Color(0xFF94A3B8),
            outline = Color(0xFF64748B),
            outlineVariant = Color(0xFF334155)
        )
        ThemePalette.MATCHA -> darkColorScheme(
            primary = Color(0xFF81C784),
            onPrimary = Color(0xFF00390A),
            primaryContainer = Color(0xFF1B5E20),
            onPrimaryContainer = Color(0xFFA5D6A7),
            secondary = Color(0xFFAED581),
            onSecondary = Color(0xFF253600),
            secondaryContainer = Color(0xFF33691E),
            onSecondaryContainer = Color(0xFFDCEDC8),
            tertiary = Color(0xFF80CBC4),
            onTertiary = Color(0xFF003731),
            background = bg,
            onBackground = Color(0xFFE2EFE2),
            surface = surf,
            onSurface = Color(0xFFE2EFE2),
            surfaceVariant = surfVar,
            onSurfaceVariant = Color(0xFF90A890),
            outline = Color(0xFF5E755E),
            outlineVariant = Color(0xFF2C3E2C)
        )
        ThemePalette.SAKURA -> darkColorScheme(
            primary = Color(0xFFF48FB1),
            onPrimary = Color(0xFF560027),
            primaryContainer = Color(0xFF880E4F),
            onPrimaryContainer = Color(0xFFFFD9E2),
            secondary = Color(0xFFF8BBD0),
            onSecondary = Color(0xFF4A002A),
            secondaryContainer = Color(0xFF6C1340),
            onSecondaryContainer = Color(0xFFFFE0EB),
            tertiary = Color(0xFFFFB0CD),
            onTertiary = Color(0xFF5E002B),
            background = bg,
            onBackground = Color(0xFFFCEBF1),
            surface = surf,
            onSurface = Color(0xFFFCEBF1),
            surfaceVariant = surfVar,
            onSurfaceVariant = Color(0xFFB593A1),
            outline = Color(0xFF7E606E),
            outlineVariant = Color(0xFF45303A)
        )
        ThemePalette.FUJI -> darkColorScheme(
            primary = Color(0xFFCE93D8),
            onPrimary = Color(0xFF4A148C),
            primaryContainer = Color(0xFF6A1B9A),
            onPrimaryContainer = Color(0xFFF3E5F5),
            secondary = Color(0xFFD1C4E9),
            onSecondary = Color(0xFF311B92),
            secondaryContainer = Color(0xFF512DA8),
            onSecondaryContainer = Color(0xFFEDE7F6),
            tertiary = Color(0xFFE1BEE7),
            onTertiary = Color(0xFF38006B),
            background = bg,
            onBackground = Color(0xFFF3ECF8),
            surface = surf,
            onSurface = Color(0xFFF3ECF8),
            surfaceVariant = surfVar,
            onSurfaceVariant = Color(0xFFA597AE),
            outline = Color(0xFF71637A),
            outlineVariant = Color(0xFF3D3345)
        )
        ThemePalette.KOGANE -> darkColorScheme(
            primary = Color(0xFFFBBF24),
            onPrimary = Color(0xFF451A03),
            primaryContainer = Color(0xFF78350F),
            onPrimaryContainer = Color(0xFFFEF3C7),
            secondary = Color(0xFFFDE68A),
            onSecondary = Color(0xFF451A03),
            secondaryContainer = Color(0xFF92400E),
            onSecondaryContainer = Color(0xFFFEF3C7),
            tertiary = Color(0xFFF59E0B),
            onTertiary = Color(0xFF451A03),
            background = bg,
            onBackground = Color(0xFFF9F3EA),
            surface = surf,
            onSurface = Color(0xFFF9F3EA),
            surfaceVariant = surfVar,
            onSurfaceVariant = Color(0xFFA89984),
            outline = Color(0xFF746857),
            outlineVariant = Color(0xFF40372B)
        )
    }
}

@Composable
fun KanjiKotobaTheme(
    themeSettings: AppThemeSettings = AppThemeSettings(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val systemDark = isSystemInDarkTheme()
    val isDark = when (themeSettings.themeMode) {
        ThemeMode.SYSTEM -> systemDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        isDark -> getPaletteDarkColorScheme(themeSettings.themePalette, themeSettings.oledBlack)
        else -> getPaletteLightColorScheme(themeSettings.themePalette)
    }

    CompositionLocalProvider(LocalAppThemeSettings provides themeSettings) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val mode = if (darkTheme) ThemeMode.DARK else ThemeMode.LIGHT
    KanjiKotobaTheme(
        themeSettings = AppThemeSettings(themeMode = mode),
        dynamicColor = dynamicColor,
        content = content
    )
}
