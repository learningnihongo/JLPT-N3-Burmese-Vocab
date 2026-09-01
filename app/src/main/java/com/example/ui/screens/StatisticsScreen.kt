package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Badge
import com.example.data.model.VocabCard
import com.example.ui.components.BadgeDetailDialog
import com.example.ui.components.TrophyShowcaseCard
import com.example.ui.components.VisualSummaryDashboardCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.DailyStudyPoint
import com.example.ui.viewmodel.LessonMasteryDetail
import com.example.ui.viewmodel.MasteryBreakdown
import com.example.ui.viewmodel.QuizTrendPoint
import com.example.ui.viewmodel.QuizViewModel
import com.example.ui.viewmodel.StatsTimeRange
import com.example.ui.viewmodel.VocabFilterType
import com.example.ui.viewmodel.VocabViewModel
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    vocabViewModel: VocabViewModel,
    quizViewModel: QuizViewModel? = null,
    onNavigateToStudy: (List<VocabCard>) -> Unit = {},
    onNavigateToBrowse: (VocabFilterType) -> Unit = {}
) {
    val profile by vocabViewModel.userProfile.collectAsState()
    val masteryBreakdown by vocabViewModel.masteryBreakdown.collectAsState()
    val dailyStats by vocabViewModel.dailyStudyStats.collectAsState()
    val lessonStats by vocabViewModel.lessonMasteryStats.collectAsState()
    val accuracySummary by vocabViewModel.accuracySummary.collectAsState()
    val quizTrend by vocabViewModel.quizTrendStats.collectAsState()
    val selectedTimeRange by vocabViewModel.statsTimeRange.collectAsState()
    val allCards by vocabViewModel.filteredCards.collectAsState()
    val dueCount by vocabViewModel.dueCount.collectAsState()
    val badges by vocabViewModel.allBadges.collectAsState()
    val categoryStats by vocabViewModel.kanjiCategoryStats.collectAsState()
    val streakSummary by vocabViewModel.dailyStreakSummary.collectAsState()

    var selectedDayIndex by remember { mutableStateOf<Int?>(null) }
    var selectedLessonFilter by remember { mutableStateOf("") }
    var selectedBadgeForDetail by remember { mutableStateOf<Badge?>(null) }

    val filteredLessons = remember(lessonStats, selectedLessonFilter) {
        if (selectedLessonFilter.isBlank()) lessonStats
        else lessonStats.filter {
            it.lessonTitle.contains(selectedLessonFilter, ignoreCase = true) ||
                    it.lessonNumber.toString().contains(selectedLessonFilter)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("statistics_screen"),
        contentPadding = PaddingValues(bottom = 96.dp, top = 8.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Top Summary Banner: Overall JLPT N3 Progress & Key Metrics
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("stats_overview_banner"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "JLPT N3 MASTERY OVERVIEW",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    letterSpacing = 1.2.sp
                                ),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${masteryBreakdown.masteryPercent.toInt()}% Mastered",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Target Level Pill
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WorkspacePremium,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Target: JLPT ${profile?.targetJlptLevel ?: "N3"}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    // 4-Column Key Metrics Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Total N3",
                            value = "${masteryBreakdown.totalCards}",
                            icon = Icons.Default.MenuBook,
                            accentColor = MaterialTheme.colorScheme.primary
                        )
                        MetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Mastered",
                            value = "${masteryBreakdown.masteredCount}",
                            icon = Icons.Default.CheckCircle,
                            accentColor = MasteredGreen
                        )
                        MetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Accuracy",
                            value = "${accuracySummary.accuracyPercent}%",
                            icon = Icons.AutoMirrored.Filled.TrendingUp,
                            accentColor = ReviewBlue
                        )
                        MetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Streak",
                            value = "${profile?.currentStreak ?: 1}d",
                            icon = Icons.Default.LocalFireDepartment,
                            accentColor = StreakOrange
                        )
                    }
                }
            }
        }

        // 2. Interactive Visual Summary Dashboard (Daily Study Streaks & Kanji Category Mastery)
        item {
            VisualSummaryDashboardCard(
                streakSummary = streakSummary,
                categoryStats = categoryStats,
                allCards = allCards,
                onStudyCategory = { categoryCards ->
                    onNavigateToStudy(categoryCards)
                }
            )
        }

        // 3. Daily Learning Progress Chart (Compose Canvas Bar Chart)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("daily_progress_chart_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Header & Range Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Daily Learning Activity",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Cards studied & XP earned per day",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Time Range Toggle
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                        ) {
                            Row(modifier = Modifier.padding(2.dp)) {
                                StatsTimeRange.entries.forEach { range ->
                                    val isSelected = selectedTimeRange == range
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50))
                                            .background(
                                                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                                            )
                                            .clickable { vocabViewModel.setStatsTimeRange(range) }
                                            .padding(horizontal = 10.dp, vertical = 5.dp)
                                    ) {
                                        Text(
                                            text = range.label,
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Selected Day Detail Box
                    val activeIndex = selectedDayIndex ?: (dailyStats.size - 1).coerceAtLeast(0)
                    if (dailyStats.isNotEmpty() && activeIndex in dailyStats.indices) {
                        val activePoint = dailyStats[activeIndex]
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${activePoint.dateLabel} (${activePoint.dayName})",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text(
                                        text = "📚 ${activePoint.cardsCount} Cards",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "⭐ +${activePoint.xpCount} XP",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = PolishTertiary
                                    )
                                }
                            }
                        }
                    }

                    // Compose Canvas Bar Chart
                    DailyStudyBarChart(
                        dailyPoints = dailyStats,
                        selectedIndex = selectedDayIndex,
                        onSelectIndex = { selectedDayIndex = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                    )

                    // Chart Legend & Daily Goal Note
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                )
                                Text(
                                    text = "Cards Studied",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(12.dp)
                                        .height(2.dp)
                                        .background(StreakOrange)
                                )
                                Text(
                                    text = "Daily Target (${profile?.dailyGoal ?: 15})",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Text(
                            text = "Tap bar for details",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        // 3. JLPT N3 Mastery Donut Breakdown Chart (Compose Canvas)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mastery_donut_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "JLPT N3 Mastery Breakdown",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Canvas Donut Chart
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .testTag("donut_chart_canvas"),
                            contentAlignment = Alignment.Center
                        ) {
                            MasteryDonutChart(
                                breakdown = masteryBreakdown,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Donut Center Content
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${masteryBreakdown.masteryPercent.toInt()}%",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Mastered",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Donut Legend & Stats
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            MasteryLegendItem(
                                label = "Mastered (Lvl 3+)",
                                count = masteryBreakdown.masteredCount,
                                total = masteryBreakdown.totalCards,
                                color = MasteredGreen
                            )
                            MasteryLegendItem(
                                label = "Reviewing (Lvl 2)",
                                count = masteryBreakdown.reviewingCount,
                                total = masteryBreakdown.totalCards,
                                color = MaterialTheme.colorScheme.primary
                            )
                            MasteryLegendItem(
                                label = "Learning (Lvl 1)",
                                count = masteryBreakdown.learningCount,
                                total = masteryBreakdown.totalCards,
                                color = ReviewBlue
                            )
                            MasteryLegendItem(
                                label = "New / Unseen",
                                count = masteryBreakdown.newCount,
                                total = masteryBreakdown.totalCards,
                                color = PolishOutline
                            )
                        }
                    }

                    // Quick action to practice
                    Button(
                        onClick = { onNavigateToBrowse(VocabFilterType.DUE_REVIEWS) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("stats_review_due_btn"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Review Due Flashcards ($dueCount Due)", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // 4. Quiz Performance & Accuracy Trend Chart
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quiz_accuracy_trend_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                        Column {
                            Text(
                                text = "Quiz Performance Trend",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Accuracy trajectory over recent test sessions",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = "Avg: ${quizTrend.map { it.scorePercent }.average().let { if (it.isNaN()) 85 else it.toInt() }}%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Line/Curve Accuracy Chart
                    QuizAccuracyCurveChart(
                        quizPoints = quizTrend,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
            }
        }

        // 5. Digital Trophies & Milestones Showcase
        item {
            TrophyShowcaseCard(
                badges = badges,
                onBadgeClick = { badge ->
                    selectedBadgeForDetail = badge
                }
            )
        }

        // 6. Lesson-by-Lesson JLPT N3 Mastery Matrix Header
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Lesson Mastery Progress",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "${lessonStats.size} Lessons",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Search / Filter Field for Lessons
                OutlinedTextField(
                    value = selectedLessonFilter,
                    onValueChange = { selectedLessonFilter = it },
                    placeholder = { Text("Filter lessons (e.g. Lesson 1, Society, Nature)...", fontSize = 13.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("lesson_stats_filter_input"),
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    ),
                    singleLine = true
                )
            }
        }

        // Filtered Lesson Items
        items(filteredLessons, key = { it.lessonNumber }) { lesson ->
            LessonMasteryCard(
                lesson = lesson,
                onStudyLesson = {
                    val lessonCards = allCards.filter { it.lessonNumber == lesson.lessonNumber }
                    if (lessonCards.isNotEmpty()) {
                        onNavigateToStudy(lessonCards)
                    }
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

// -------------------------------------------------------------
// COMPOSE CHARTS: Daily Learning Bar Chart (Compose Canvas)
// -------------------------------------------------------------

@Composable
fun DailyStudyBarChart(
    dailyPoints: List<DailyStudyPoint>,
    selectedIndex: Int?,
    onSelectIndex: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val outlineVariant = PolishOutlineVariant
    val goalColor = StreakOrange
    val textColor = MaterialTheme.colorScheme.onSurfaceVariant

    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(dailyPoints) {
        animationProgress.snapTo(0f)
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
    }

    val maxCards = remember(dailyPoints) {
        (dailyPoints.maxOfOrNull { it.cardsCount } ?: 20).coerceAtLeast(25)
    }

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clickable { /* Taps handled below */ }
        ) {
            val width = size.width
            val height = size.height
            val bottomAxisY = height - 28.dp.toPx()
            val chartTopY = 16.dp.toPx()
            val usableHeight = bottomAxisY - chartTopY

            if (dailyPoints.isEmpty()) return@Canvas

            // 1. Draw 3 Horizontal Grid lines (0, 50%, 100% max)
            val gridStep = usableHeight / 3
            for (g in 0..3) {
                val lineY = chartTopY + (g * gridStep)
                drawLine(
                    color = outlineVariant,
                    start = Offset(0f, lineY),
                    end = Offset(width, lineY),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // 2. Draw Daily Goal Dashed Line
            val goalY = bottomAxisY - (usableHeight * (15f / maxCards.toFloat())).coerceIn(0f, usableHeight)
            drawLine(
                color = goalColor.copy(alpha = 0.6f),
                start = Offset(0f, goalY),
                end = Offset(width, goalY),
                strokeWidth = 1.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            // 3. Draw Bars for Each Day
            val pointCount = dailyPoints.size
            val barSpacing = width / pointCount.toFloat()
            val barWidth = (barSpacing * 0.55f).coerceAtMost(28.dp.toPx())

            dailyPoints.forEachIndexed { index, point ->
                val centerX = (index * barSpacing) + (barSpacing / 2f)
                val targetFraction = (point.cardsCount.toFloat() / maxCards.toFloat()).coerceIn(0.04f, 1f)
                val currentFraction = targetFraction * animationProgress.value
                val barHeight = usableHeight * currentFraction
                val barTopY = bottomAxisY - barHeight

                val isSelected = selectedIndex == index
                val isToday = point.isToday

                val barColor = when {
                    isSelected -> primaryColor
                    isToday -> primaryColor
                    point.cardsCount >= point.goalCount -> MasteredGreen
                    else -> primaryContainer
                }

                // Draw Bar with Rounded Top Corners
                drawRoundRect(
                    color = barColor,
                    topLeft = Offset(centerX - (barWidth / 2f), barTopY),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                )

                // Highlight border if selected
                if (isSelected) {
                    drawRoundRect(
                        color = Color.Black.copy(alpha = 0.2f),
                        topLeft = Offset(centerX - (barWidth / 2f), barTopY),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            }
        }

        // Bottom Labels Row (Day names)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(24.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            dailyPoints.forEachIndexed { index, point ->
                val isSelected = selectedIndex == index
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelectIndex(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = point.dayName,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        fontWeight = if (isSelected || point.isToday) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected || point.isToday) primaryColor else textColor,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// COMPOSE CHARTS: JLPT N3 Mastery Donut Chart (Compose Canvas)
// -------------------------------------------------------------

@Composable
fun MasteryDonutChart(
    breakdown: MasteryBreakdown,
    modifier: Modifier = Modifier
) {
    val masteredColor = MasteredGreen
    val reviewingColor = MaterialTheme.colorScheme.primary
    val learningColor = ReviewBlue
    val newColor = PolishOutlineVariant

    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(breakdown) {
        animProgress.snapTo(0f)
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
    }

    Canvas(modifier = modifier) {
        val strokeWidthPx = 16.dp.toPx()
        val diameter = size.minDimension - strokeWidthPx
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = diameter / 2f

        val total = breakdown.totalCards.toFloat().coerceAtLeast(1f)
        val mAngle = (breakdown.masteredCount / total) * 360f
        val rAngle = (breakdown.reviewingCount / total) * 360f
        val lAngle = (breakdown.learningCount / total) * 360f
        val nAngle = 360f - (mAngle + rAngle + lAngle)

        var currentStart = -90f

        // Draw Mastered Arc
        if (mAngle > 0) {
            drawArc(
                color = masteredColor,
                startAngle = currentStart,
                sweepAngle = mAngle * animProgress.value,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidthPx)
            )
            currentStart += mAngle * animProgress.value
        }

        // Draw Reviewing Arc
        if (rAngle > 0) {
            drawArc(
                color = reviewingColor,
                startAngle = currentStart,
                sweepAngle = rAngle * animProgress.value,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidthPx)
            )
            currentStart += rAngle * animProgress.value
        }

        // Draw Learning Arc
        if (lAngle > 0) {
            drawArc(
                color = learningColor,
                startAngle = currentStart,
                sweepAngle = lAngle * animProgress.value,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidthPx)
            )
            currentStart += lAngle * animProgress.value
        }

        // Draw New/Unseen Arc
        if (nAngle > 0) {
            drawArc(
                color = newColor,
                startAngle = currentStart,
                sweepAngle = (360f - (currentStart + 90f)).coerceAtLeast(0f),
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidthPx)
            )
        }
    }
}

// -------------------------------------------------------------
// COMPOSE CHARTS: Quiz Accuracy Curve Line Chart (Compose Canvas)
// -------------------------------------------------------------

@Composable
fun QuizAccuracyCurveChart(
    quizPoints: List<QuizTrendPoint>,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val tertiaryColor = PolishTertiary
    val outlineVariant = PolishOutlineVariant
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(quizPoints) {
        animProgress.snapTo(0f)
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 850, easing = FastOutSlowInEasing)
        )
    }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val bottomY = height - 24.dp.toPx()
            val topY = 16.dp.toPx()
            val usableHeight = bottomY - topY

            if (quizPoints.isEmpty()) return@Canvas

            // 1. Draw Grid lines (0%, 50%, 100%)
            for (step in 0..2) {
                val y = topY + (step * (usableHeight / 2f))
                drawLine(
                    color = outlineVariant,
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // 2. Compute Points
            val count = quizPoints.size
            val stepX = if (count > 1) width / (count - 1).toFloat() else width / 2f

            val points = quizPoints.mapIndexed { idx, q ->
                val x = idx * stepX
                val scoreFraction = (q.scorePercent / 100f).coerceIn(0f, 1f)
                val y = bottomY - (usableHeight * scoreFraction * animProgress.value)
                Offset(x, y)
            }

            // 3. Draw Gradient Area under the curve
            val fillPath = Path().apply {
                if (points.isNotEmpty()) {
                    moveTo(points.first().x, bottomY)
                    lineTo(points.first().x, points.first().y)
                    for (i in 0 until points.size - 1) {
                        val p0 = points[i]
                        val p1 = points[i + 1]
                        val cx = (p0.x + p1.x) / 2f
                        cubicTo(cx, p0.y, cx, p1.y, p1.x, p1.y)
                    }
                    lineTo(points.last().x, bottomY)
                    close()
                }
            }

            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        primaryColor.copy(alpha = 0.25f),
                        primaryColor.copy(alpha = 0.02f)
                    )
                )
            )

            // 4. Draw Stroke Curve Line
            val strokePath = Path().apply {
                if (points.isNotEmpty()) {
                    moveTo(points.first().x, points.first().y)
                    for (i in 0 until points.size - 1) {
                        val p0 = points[i]
                        val p1 = points[i + 1]
                        val cx = (p0.x + p1.x) / 2f
                        cubicTo(cx, p0.y, cx, p1.y, p1.x, p1.y)
                    }
                }
            }

            drawPath(
                path = strokePath,
                color = primaryColor,
                style = Stroke(width = 3.dp.toPx())
            )

            // 5. Draw Point Markers with White Fill & Primary Border
            points.forEach { pt ->
                drawCircle(
                    color = primaryColor,
                    radius = 5.dp.toPx(),
                    center = pt
                )
                drawCircle(
                    color = Color.White,
                    radius = 2.5.dp.toPx(),
                    center = pt
                )
            }
        }

        // Bottom Date Labels Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            quizPoints.forEach { q ->
                Text(
                    text = q.dateLabel,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                    color = onSurfaceVariant
                )
            }
        }
    }
}

// -------------------------------------------------------------
// COMPONENT: Metric Card (Overview 4-grid)
// -------------------------------------------------------------

@Composable
fun MetricCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// -------------------------------------------------------------
// COMPONENT: Mastery Legend Row Item
// -------------------------------------------------------------

@Composable
fun MasteryLegendItem(
    label: String,
    count: Int,
    total: Int,
    color: Color
) {
    val percent = if (total > 0) ((count.toFloat() / total.toFloat()) * 100).toInt() else 0

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = "$count ($percent%)",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// -------------------------------------------------------------
// COMPONENT: Lesson Mastery Progress Card
// -------------------------------------------------------------

@Composable
fun LessonMasteryCard(
    lesson: LessonMasteryDetail,
    onStudyLesson: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("lesson_mastery_card_${lesson.lessonNumber}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (lesson.lessonNumber == 999) "★" else "L${lesson.lessonNumber}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Column {
                        Text(
                            text = lesson.lessonTitle,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${lesson.totalCount} Cards • ${lesson.masteredCount} Mastered • ${lesson.dueCount} Due",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Study Lesson Button
                IconButton(
                    onClick = onStudyLesson,
                    modifier = Modifier
                        .size(36.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Study Lesson",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(PolishOutlineVariant)
                ) {
                    val frac = (lesson.masteryPercent / 100f).coerceIn(0f, 1f)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = frac.coerceAtLeast(0.02f))
                            .height(8.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (lesson.masteryPercent >= 80) MasteredGreen else MaterialTheme.colorScheme.primary
                            )
                    )
                }

                Text(
                    text = "${lesson.masteryPercent.toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (lesson.masteryPercent >= 80) MasteredGreen else MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
