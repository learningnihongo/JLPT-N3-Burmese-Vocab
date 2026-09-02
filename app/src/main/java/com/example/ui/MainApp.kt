package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.AddCustomCardDialog
import com.example.ui.navigation.Screen
import com.example.ui.screens.FlashcardStudyScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.StatisticsScreen
import com.example.ui.screens.VocabBrowseScreen
import com.example.ui.theme.IconThemeStyle
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.KanjiKotobaTheme
import com.example.ui.theme.ThemeMode
import com.example.ui.viewmodel.QuizViewModel
import com.example.ui.viewmodel.VocabFilterType
import androidx.compose.runtime.LaunchedEffect
import com.example.ui.viewmodel.VocabViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    initialDestination: String? = null,
    vocabViewModel: VocabViewModel = viewModel(),
    quizViewModel: QuizViewModel = viewModel()
) {
    val themeSettings by vocabViewModel.themeSettings.collectAsState()

    KanjiKotobaTheme(themeSettings = themeSettings) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

        LaunchedEffect(initialDestination) {
            if (initialDestination == "study") {
                vocabViewModel.startDueCardsStudySession {
                    navController.navigate(Screen.Study.route)
                }
            }
        }

        var showAddCustomDialog by remember { mutableStateOf(false) }

        val bottomNavItems = listOf(
            Screen.Home,
            Screen.Browse,
            Screen.Stats,
            Screen.Quiz,
            Screen.Profile
        )

        val isFullScreenStudy = currentRoute == Screen.Study.route
        val systemIsDark = isSystemInDarkTheme()
        val isCurrentlyDark = when (themeSettings.themeMode) {
            ThemeMode.SYSTEM -> systemIsDark
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
        }

        Scaffold(
            topBar = {
                if (!isFullScreenStudy) {
                    val profile by vocabViewModel.userProfile.collectAsState()
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Left: Clean Title & JLPT Level
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    text = when (currentRoute) {
                                        Screen.Home.route -> "KotoKanji こと漢字"
                                        Screen.Browse.route -> "N3 Word Library"
                                        Screen.Stats.route -> "Statistics"
                                        Screen.Quiz.route -> "Quiz Arena"
                                        Screen.Profile.route -> "Profile"
                                        else -> "KotoKanji"
                                    },
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "JLPT N3 Myanmar • Offline Study",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            // Right: Theme Mode Quick Toggle & Add Button
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Quick Dark / Light Mode Toggle Button
                                IconButton(
                                    onClick = {
                                        vocabViewModel.toggleDarkMode(isCurrentlyDark)
                                    },
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                            CircleShape
                                        )
                                        .testTag("app_bar_dark_mode_toggle_btn")
                                ) {
                                    Crossfade(targetState = isCurrentlyDark, label = "theme_icon_crossfade") { dark ->
                                        Icon(
                                            imageVector = if (dark) Icons.Default.LightMode else Icons.Default.DarkMode,
                                            contentDescription = if (dark) "Switch to Light Mode" else "Switch to Dark Mode",
                                            tint = if (dark) Color(0xFFFFD54F) else MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                if (currentRoute == Screen.Home.route || currentRoute == Screen.Browse.route) {
                                    IconButton(
                                        onClick = { showAddCustomDialog = true },
                                        modifier = Modifier
                                            .size(38.dp)
                                            .background(
                                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                                CircleShape
                                            )
                                            .testTag("app_bar_add_btn")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add Card",
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = !isFullScreenStudy,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                ) {
                    Surface(
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
                        ),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 0.dp,
                            modifier = Modifier.testTag("main_bottom_nav")
                        ) {
                            bottomNavItems.forEach { screen ->
                                val selected = currentRoute == screen.route
                                val iconVector = when (themeSettings.iconThemeStyle) {
                                    IconThemeStyle.MODERN_OUTLINE -> screen.unselectedIcon
                                    IconThemeStyle.CLASSIC_DYNAMIC -> if (selected) screen.selectedIcon else screen.unselectedIcon
                                    IconThemeStyle.DUOTONE_GLOW -> screen.selectedIcon
                                }

                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            imageVector = iconVector,
                                            contentDescription = screen.title
                                        )
                                    },
                                    label = {
                                        Text(
                                            screen.title,
                                            fontSize = 11.sp,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    },
                                    selected = selected,
                                    onClick = {
                                        if (currentRoute != screen.route) {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            vocabViewModel = vocabViewModel,
                            onStartStudy = { cards ->
                                vocabViewModel.startStudySession(cards)
                                navController.navigate(Screen.Study.route)
                            },
                            onNavigateToBrowse = { filter ->
                                vocabViewModel.setFilter(filter)
                                navController.navigate(Screen.Browse.route)
                            },
                            onNavigateToQuiz = {
                                navController.navigate(Screen.Quiz.route)
                            },
                            onOpenAddCustomCard = {
                                showAddCustomDialog = true
                            },
                            onNavigateToStats = {
                                navController.navigate(Screen.Stats.route)
                            }
                        )
                    }

                    composable(Screen.Browse.route) {
                        VocabBrowseScreen(
                            vocabViewModel = vocabViewModel,
                            onStartStudy = { cards ->
                                vocabViewModel.startStudySession(cards)
                                navController.navigate(Screen.Study.route)
                            }
                        )
                    }

                    composable(Screen.Stats.route) {
                        StatisticsScreen(
                            vocabViewModel = vocabViewModel,
                            quizViewModel = quizViewModel,
                            onNavigateToStudy = { cards ->
                                vocabViewModel.startStudySession(cards)
                                navController.navigate(Screen.Study.route)
                            },
                            onNavigateToBrowse = { filter ->
                                vocabViewModel.setFilter(filter)
                                navController.navigate(Screen.Browse.route)
                            }
                        )
                    }

                    composable(Screen.Quiz.route) {
                        QuizScreen(quizViewModel = quizViewModel)
                    }

                    composable(Screen.Profile.route) {
                        ProfileScreen(vocabViewModel = vocabViewModel)
                    }

                    composable(Screen.Study.route) {
                        FlashcardStudyScreen(
                            vocabViewModel = vocabViewModel,
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }

        if (showAddCustomDialog) {
            val allCustomTags by vocabViewModel.allCustomTags.collectAsState()
            AddCustomCardDialog(
                onDismiss = { showAddCustomDialog = false },
                availableTags = allCustomTags,
                onConfirm = { kanji, reading, burmese, pos, example, exBurmese, note, tags ->
                    vocabViewModel.addCustomFlashcard(
                        kanji = kanji,
                        reading = reading,
                        meaningBurmese = burmese,
                        partOfSpeech = pos,
                        exampleSentence = example,
                        exampleMeaningBurmese = exBurmese,
                        personalNote = note,
                        tags = tags
                    )
                    showAddCustomDialog = false
                }
            )
        }
    }
}
