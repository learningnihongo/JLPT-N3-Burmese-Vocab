package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Home : Screen("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    data object Browse : Screen("browse", "Library", Icons.Filled.MenuBook, Icons.Outlined.MenuBook)
    data object Stats : Screen("stats", "Stats", Icons.Filled.BarChart, Icons.Outlined.BarChart)
    data object Quiz : Screen("quiz", "Quiz", Icons.Filled.Quiz, Icons.Outlined.Quiz)
    data object Profile : Screen("profile", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
    data object Study : Screen("study", "Study", Icons.Filled.AutoAwesome, Icons.Filled.AutoAwesome)
    data object Flashcard : Screen("flashcard", "Flashcard", Icons.Filled.AutoAwesome, Icons.Filled.AutoAwesome)
    data object KanjiProgress : Screen("kanji_progress", "Kanji Progress", Icons.Filled.BarChart, Icons.Outlined.BarChart)
    data object QuizHistory : Screen("quiz_history", "Quiz History", Icons.Filled.Quiz, Icons.Outlined.Quiz)
}

