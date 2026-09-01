package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
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
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.viewmodel.QuizViewModel
import com.example.ui.viewmodel.VocabFilterType
import com.example.ui.viewmodel.VocabViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    vocabViewModel: VocabViewModel = viewModel(),
    quizViewModel: QuizViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    var showAddCustomDialog by remember { mutableStateOf(false) }

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Browse,
        Screen.Stats,
        Screen.Quiz,
        Screen.Profile
    )

    val isFullScreenStudy = currentRoute == Screen.Study.route

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
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left: Avatar + Title/Level
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(androidx.compose.foundation.shape.CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = profile?.name?.take(2)?.uppercase() ?: "KK",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                            Column {
                                Text(
                                    text = when (currentRoute) {
                                        Screen.Home.route -> "KotoKanji"
                                        Screen.Browse.route -> "JLPT N3 Library"
                                        Screen.Stats.route -> "Learning Statistics"
                                        Screen.Quiz.route -> "Quiz Arena"
                                        Screen.Profile.route -> "Student Profile"
                                        else -> "KotoKanji"
                                    },
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Level: ${profile?.targetJlptLevel ?: "Intermediate"}",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Right: Streak Badge & Add Button
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Offline & Streak Pill
                            Surface(
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(50),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "🔥 ${profile?.currentStreak ?: 1}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = com.example.ui.theme.StreakOrange
                                    )
                                    Box(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .height(10.dp)
                                            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                                    )
                                    Text(
                                        text = "Offline",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            if (currentRoute == Screen.Home.route || currentRoute == Screen.Browse.route) {
                                IconButton(
                                    onClick = { showAddCustomDialog = true },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f), androidx.compose.foundation.shape.CircleShape)
                                        .testTag("app_bar_add_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add Personalized Card",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.size(20.dp)
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
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                    ),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 0.dp,
                        modifier = Modifier.testTag("main_bottom_nav")
                    ) {
                        bottomNavItems.forEach { screen ->
                            val selected = currentRoute == screen.route
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
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
        AddCustomCardDialog(
            onDismiss = { showAddCustomDialog = false },
            onConfirm = { kanji, reading, burmese, pos, example, exBurmese, note ->
                vocabViewModel.addCustomFlashcard(
                    kanji = kanji,
                    reading = reading,
                    meaningBurmese = burmese,
                    partOfSpeech = pos,
                    exampleSentence = example,
                    exampleMeaningBurmese = exBurmese,
                    personalNote = note
                )
                showAddCustomDialog = false
            }
        )
    }
}
