package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Professional Polish Palette (M3 Deep Violet & Elegant Neutrals)
val PolishPrimary = Color(0xFF6750A4)
val PolishPrimaryDark = Color(0xFFD0BCFF)
val PolishPrimaryContainer = Color(0xFFEADDFF)
val PolishOnPrimaryContainer = Color(0xFF21005D)

val PolishSecondary = Color(0xFF625B71)
val PolishSecondaryDark = Color(0xFFCCC2DC)
val PolishSecondaryContainer = Color(0xFFE8DEF8)
val PolishOnSecondaryContainer = Color(0xFF1D192B)

val PolishTertiary = Color(0xFF7D5260)
val PolishTertiaryDark = Color(0xFFEFB8C8)

// Surface & Background Colors
val PolishBackground = Color(0xFFFDF8FD)
val PolishSurface = Color(0xFFFFFFFF)
val PolishSurfaceVariant = Color(0xFFF3EDF7)
val PolishSurfaceContainer = Color(0xFFF7F2FA)
val PolishOutline = Color(0xFF79747E) // Accessible contrast >= 4.5:1 for text/captions
val PolishOutlineVariant = Color(0xFFE7E0EC)
val PolishOnSurface = Color(0xFF1C1B1F)
val PolishOnSurfaceVariant = Color(0xFF49454F)

// Dark Palette Neutrals - High Contrast (WCAG AAA Compliant)
val PolishDarkBackground = Color(0xFF121118)
val PolishDarkSurface = Color(0xFF1C1A24)
val PolishDarkSurfaceVariant = Color(0xFF262330)
val PolishDarkOutline = Color(0xFF94A3B8) // Accessible contrast >= 5.5:1 on dark surfaces
val PolishDarkOutlineVariant = Color(0xFF475569)
val PolishDarkOnSurface = Color(0xFFFFFFFF) // Crisp bright white for primary headers/text
val PolishDarkOnSurfaceVariant = Color(0xFFE2E8F0) // Bright readable silver-white for secondary text

// SRS Rating Specific Pastels & High-Contrast Labels (Matching Design Spec)
val SrsAgainBg = Color(0xFFFFE0E0)
val SrsAgainText = Color(0xFF410002)

val SrsHardBg = Color(0xFFFFF1D6)
val SrsHardText = Color(0xFF291800)

val SrsGoodBg = Color(0xFFD6F5FF)
val SrsGoodText = Color(0xFF001F28)

val SrsEasyBg = Color(0xFFE0FFE0)
val SrsEasyText = Color(0xFF002106)

// Adaptive SRS Colors for Light and Dark themes
fun getSrsAgainBg(isDark: Boolean): Color = if (isDark) Color(0xFF4A1818) else SrsAgainBg
fun getSrsAgainText(isDark: Boolean): Color = if (isDark) Color(0xFFFFB4AB) else SrsAgainText
fun getSrsHardBg(isDark: Boolean): Color = if (isDark) Color(0xFF422606) else SrsHardBg
fun getSrsHardText(isDark: Boolean): Color = if (isDark) Color(0xFFFFD59E) else SrsHardText
fun getSrsGoodBg(isDark: Boolean): Color = if (isDark) Color(0xFF0F325E) else SrsGoodBg
fun getSrsGoodText(isDark: Boolean): Color = if (isDark) Color(0xFFA6CCFF) else SrsGoodText
fun getSrsEasyBg(isDark: Boolean): Color = if (isDark) Color(0xFF133E18) else SrsEasyBg
fun getSrsEasyText(isDark: Boolean): Color = if (isDark) Color(0xFFA8EBB0) else SrsEasyText

// Accent & Status Colors - Vivid & High Contrast on both dark & light backgrounds
val StreakOrange = Color(0xFFFB923C) // Warm vibrant amber (high contrast on dark surfaces)
val StreakOrangeDark = Color(0xFFFB923C)
val MasteredGreen = Color(0xFF22C55E) // Crisp vibrant emerald green (high visibility)
val MasteredGreenDark = Color(0xFF4ADE80)
val ReviewBlue = Color(0xFF38BDF8) // Crisp sky blue (high contrast on dark surfaces)
val ReviewBlueDark = Color(0xFF38BDF8)
val WeakOrange = Color(0xFFFB923C)
val WeakOrangeDark = Color(0xFFFB923C)
val SakuraPinkDark = Color(0xFFF48FB1)

// Adaptive status colors helper
fun getAdaptiveMasteredColor(isDark: Boolean): Color = if (isDark) Color(0xFF4ADE80) else Color(0xFF15803D)
fun getAdaptiveReviewColor(isDark: Boolean): Color = if (isDark) Color(0xFF38BDF8) else Color(0xFF0284C7)
fun getAdaptiveStreakColor(isDark: Boolean): Color = if (isDark) Color(0xFFFB923C) else Color(0xFFEA580C)
fun getAdaptiveWeakColor(isDark: Boolean): Color = if (isDark) Color(0xFFFB923C) else Color(0xFFC2410C)

// Backward Compatibility Aliases & High-Contrast Fallbacks (WCAG AA Compliant)
val JapaneseCrimson = Color(0xFFFF6B6B)
val JapaneseCrimsonDark = Color(0xFFFF8983)
val CrimsonPrimary = Color(0xFFFF6B6B)
val CrimsonPrimaryDark = Color(0xFFFF8983)
val JapaneseIndigo = Color(0xFF60A5FA)
val JapaneseIndigoLight = Color(0xFF60A5FA)
val JapaneseIndigoDark = Color(0xFF90CAF9)
val IndigoDeep = Color(0xFF3B82F6)
val IndigoSurface = PolishSurfaceVariant
val AmberGold = Color(0xFFF59E0B)
val AmberGoldDark = Color(0xFFFBBF24)
val JadeGreen = MasteredGreen
val JadeGreenDark = MasteredGreenDark
val CyanAccent = ReviewBlue
val CyanAccentDark = ReviewBlueDark
val LightBackground = PolishBackground
val LightSurface = PolishSurface
val LightSurfaceVariant = PolishSurfaceVariant
val LightOnSurface = PolishOnSurface
val LightOnSurfaceVariant = PolishOnSurfaceVariant
val DarkBackground = PolishDarkBackground
val DarkSurface = PolishDarkSurface
val DarkSurfaceVariant = PolishDarkSurfaceVariant
val DarkOnSurface = PolishDarkOnSurface
val DarkOnSurfaceVariant = PolishDarkOnSurfaceVariant

