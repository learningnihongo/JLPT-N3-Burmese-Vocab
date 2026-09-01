package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Badge
import com.example.data.model.LessonProgress
import com.example.data.model.VocabCard
import com.example.ui.components.BadgeDetailDialog
import com.example.ui.components.MilestoneHighlightBanner
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.JapaneseIndigo
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.SakuraPinkDark
import com.example.ui.theme.WeakOrange
import com.example.ui.viewmodel.VocabFilterType
import com.example.ui.viewmodel.VocabViewModel

@Composable
fun HomeScreen(
    vocabViewModel: VocabViewModel,
    onStartStudy: (List<VocabCard>) -> Unit,
    onNavigateToBrowse: (VocabFilterType) -> Unit,
    onNavigateToQuiz: () -> Unit,
    onOpenAddCustomCard: () -> Unit,
    onNavigateToStats: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val profile by vocabViewModel.userProfile.collectAsState()
    val totalCount by vocabViewModel.totalCardCount.collectAsState()
    val masteredCount by vocabViewModel.masteredCount.collectAsState()
    val dueCount by vocabViewModel.dueCount.collectAsState()
    val lessonProgressList by vocabViewModel.lessonProgressList.collectAsState()
    val allCards by vocabViewModel.filteredCards.collectAsState()
    val badges by vocabViewModel.allBadges.collectAsState()

    var selectedBadgeForDetail by remember { mutableStateOf<Badge?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 90.dp, top = 8.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Today's Goal Progress Card (Matching Professional Polish Design)
        item {
            val dailyGoal = profile?.dailyGoal ?: 50
            val todayReviewed = (profile?.totalXp ?: 42) % dailyGoal // Or progress count
            val goalCompleted = (masteredCount).coerceAtMost(dailyGoal)
            val percent = if (dailyGoal > 0) ((goalCompleted.toFloat() / dailyGoal.toFloat()) * 100).toInt() else 84

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("today_goal_card")
                    .clickable { onNavigateToStats() },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "TODAY'S GOAL: $goalCompleted/$dailyGoal",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    letterSpacing = 1.2.sp
                                ),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "View Statistics",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "$percent% Complete",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Rounded Goal Progress Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(50))
                            .background(com.example.ui.theme.PolishOutlineVariant)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction = (percent / 100f).coerceIn(0.05f, 1f))
                                .height(8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        }

        // Gamified Profile & Streak Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_banner_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Avatar badge
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = profile?.name?.take(2)?.uppercase() ?: "KK",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }

                            Column {
                                Text(
                                    text = profile?.name ?: "JLPT N3 Scholar",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Target: ${profile?.targetJlptLevel ?: "N3"} • Level ${profile?.level ?: 1}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // Streak Badge
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "🔥 ${profile?.currentStreak ?: 1} Days",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = com.example.ui.theme.StreakOrange
                                )
                            }
                        }
                    }

                    // XP Progress Bar to Next Level
                    val xp = profile?.totalXp ?: 0
                    val xpToNext = (profile?.level ?: 1) * 200
                    val progressFraction = (xp % 200).toFloat() / 200f

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total XP: $xp",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${xp % 200} / 200 XP",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(50)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = com.example.ui.theme.PolishOutlineVariant
                        )
                    }
                }
            }
        }

        // Digital Trophy & Milestone Highlight Banner
        item {
            MilestoneHighlightBanner(
                badges = badges,
                onViewAllTrophies = onNavigateToStats,
                onBadgeClick = { badge ->
                    selectedBadgeForDetail = badge
                }
            )
        }

        // Spaced Repetition Due Card Action Callout
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("srs_callout_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (dueCount > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = "SRS Engine",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = if (dueCount > 0) "Spaced Repetition Due" else "All Caught Up!",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = if (dueCount > 0) "$dueCount cards ready for review" else "SuperMemo-2 retention schedule",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }

                        // Badge
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color.White.copy(alpha = 0.22f)
                        ) {
                            Text(
                                text = "$dueCount Due",
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                vocabViewModel.setFilter(VocabFilterType.DUE_REVIEWS)
                                onStartStudy(allCards)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = if (dueCount > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("start_srs_study_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (dueCount > 0) "Review Due ($dueCount)" else "Study All Cards",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        FilledTonalButton(
                            onClick = onNavigateToQuiz,
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color.White.copy(alpha = 0.2f),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .height(46.dp)
                                .testTag("quick_quiz_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Quiz Mode", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // Quick Feature Shortcuts (Mastered, Bookmarks, Custom Flashcards)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickStatCard(
                    title = "Mastered",
                    count = "$masteredCount",
                    sub = "Retained",
                    color = MasteredGreen,
                    icon = Icons.Default.CheckCircle,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToBrowse(VocabFilterType.MASTERED) }
                )

                QuickStatCard(
                    title = "Saved",
                    count = "Stars",
                    sub = "Bookmarks",
                    color = SakuraPinkDark,
                    icon = Icons.Default.Bookmark,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToBrowse(VocabFilterType.BOOKMARKED) }
                )

                QuickStatCard(
                    title = "Custom",
                    count = "+ Add",
                    sub = "My Flashcards",
                    color = ReviewBlue,
                    icon = Icons.Default.Add,
                    modifier = Modifier.weight(1f),
                    onClick = onOpenAddCustomCard
                )
            }
        }

        // Lesson Directory Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "JLPT N3 Vocabulary Lessons",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "$totalCount Words Total",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        // Lesson list items
        items(lessonProgressList) { lesson ->
            LessonProgressCard(
                lesson = lesson,
                onStudyLessonClick = {
                    vocabViewModel.selectLesson(lesson.lessonNumber)
                    onStartStudy(allCards)
                },
                onViewCardsClick = {
                    vocabViewModel.selectLesson(lesson.lessonNumber)
                    onNavigateToBrowse(VocabFilterType.ALL)
                }
            )
        }
    }

    selectedBadgeForDetail?.let { badge ->
        BadgeDetailDialog(
            badge = badge,
            onDismiss = { selectedBadgeForDetail = null }
        )
    }
}

@Composable
private fun QuickStatCard(
    title: String,
    count: String,
    sub: String,
    color: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = count,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun LessonProgressCard(
    lesson: LessonProgress,
    onStudyLessonClick: () -> Unit,
    onViewCardsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (lesson.totalCards > 0) lesson.masteredCards.toFloat() / lesson.totalCards else 0f

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onViewCardsClick() }
            .testTag("lesson_card_${lesson.lessonNumber}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (lesson.lessonNumber == 999) "Personalized Cards" else lesson.lessonTitle,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${lesson.totalCards} Words • ${lesson.masteredCards} Mastered",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                FilledTonalButton(
                    onClick = onStudyLessonClick,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Study", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (progress >= 0.8f) MasteredGreen else JapaneseCrimson,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}
