package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VocabCard
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.JapaneseIndigo
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.PolishOutline
import com.example.ui.theme.PolishOutlineVariant
import com.example.ui.theme.PolishTertiary
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.StreakOrange
import com.example.ui.viewmodel.DailyStudyPoint
import com.example.ui.viewmodel.MasteryBreakdown
import com.example.ui.viewmodel.QuizTrendPoint
import com.example.ui.viewmodel.QuizViewModel
import com.example.ui.viewmodel.StatsTimeRange
import com.example.ui.viewmodel.VocabFilterType
import com.example.ui.viewmodel.VocabViewModel

enum class StatsFilter(
    val title: String,
    val myanmarSubtitle: String,
    val icon: ImageVector
) {
    ALL("All Stats", "အားလုံး", Icons.Default.BarChart),
    MASTERY("Study & Mastery", "လေ့လာမှု", Icons.Default.MenuBook),
    QUIZ("Quiz Performance", "စစ်ဆေးမှု", Icons.Default.Quiz)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    vocabViewModel: VocabViewModel,
    quizViewModel: QuizViewModel? = null,
    onNavigateToStudy: (List<VocabCard>) -> Unit = {},
    onNavigateToBrowse: (VocabFilterType) -> Unit = {},
    onNavigateToQuizHistory: () -> Unit = {}
) {
    val profile by vocabViewModel.userProfile.collectAsState()
    val masteryBreakdown by vocabViewModel.masteryBreakdown.collectAsState()
    val dailyStats by vocabViewModel.dailyStudyStats.collectAsState()
    val accuracySummary by vocabViewModel.accuracySummary.collectAsState()
    val quizTrend by vocabViewModel.quizTrendStats.collectAsState()
    val selectedTimeRange by vocabViewModel.statsTimeRange.collectAsState()

    var selectedFilter by remember { mutableStateOf(StatsFilter.ALL) }
    var selectedDayIndex by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("statistics_screen"),
        contentPadding = PaddingValues(bottom = 96.dp, top = 8.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Hero KPI Banner: Clean, High-Contrast & Beautifully Spaced
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("stats_overview_banner"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    JapaneseCrimson.copy(alpha = 0.08f),
                                    JapaneseIndigo.copy(alpha = 0.03f)
                                )
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "JLPT N3 PROGRESS",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 11.sp,
                                            letterSpacing = 1.1.sp
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        color = JapaneseCrimson
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = JapaneseCrimson.copy(alpha = 0.12f)
                                    ) {
                                        Text(
                                            text = "လေ့လာမှုအခြေအနေ",
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                            fontWeight = FontWeight.Bold,
                                            color = JapaneseCrimson,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "${masteryBreakdown.masteryPercent.toInt()}% Mastered",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Target Level Badge
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = JapaneseIndigo.copy(alpha = 0.12f),
                                border = BorderStroke(1.dp, JapaneseIndigo.copy(alpha = 0.25f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.WorkspacePremium,
                                        contentDescription = null,
                                        tint = JapaneseIndigo,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Target: JLPT ${profile?.targetJlptLevel ?: "N3"}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = JapaneseIndigo
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
                                title = "Vocab",
                                sub = "စုစုပေါင်း",
                                value = "${masteryBreakdown.totalCards}",
                                icon = Icons.Default.MenuBook,
                                accentColor = JapaneseIndigo
                            )
                            MetricCard(
                                modifier = Modifier.weight(1f),
                                title = "Mastered",
                                sub = "ကျွမ်းကျင်",
                                value = "${masteryBreakdown.masteredCount}",
                                icon = Icons.Default.CheckCircle,
                                accentColor = MasteredGreen
                            )
                            MetricCard(
                                modifier = Modifier.weight(1f),
                                title = "Accuracy",
                                sub = "မှန်ကန်မှု",
                                value = "${accuracySummary.accuracyPercent}%",
                                icon = Icons.AutoMirrored.Filled.TrendingUp,
                                accentColor = ReviewBlue
                            )
                            MetricCard(
                                modifier = Modifier.weight(1f),
                                title = "Streak",
                                sub = "ရက်ဆက်",
                                value = "${profile?.currentStreak ?: 1}d",
                                icon = Icons.Default.LocalFireDepartment,
                                accentColor = StreakOrange
                            )
                        }
                    }
                }
            }
        }

        // 2. Clean Segmented Category Filter (All Stats, Study & Mastery, Quiz Performance)
        item {
            PrimaryTabRow(
                selectedTabIndex = selectedFilter.ordinal,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .testTag("stats_filter_tab_row"),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = JapaneseCrimson,
                divider = {}
            ) {
                StatsFilter.entries.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Tab(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        text = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = filter.icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = if (isSelected) JapaneseCrimson else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = filter.title,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) JapaneseCrimson else MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 12.sp
                                    )
                                }
                                Text(
                                    text = filter.myanmarSubtitle,
                                    fontSize = 10.sp,
                                    color = if (isSelected) JapaneseCrimson.copy(alpha = 0.85f) else MaterialTheme.colorScheme.outline
                                )
                            }
                        },
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // 3. Section: JLPT N3 Mastery Donut Breakdown (Shown for ALL or MASTERY)
        if (selectedFilter == StatsFilter.ALL || selectedFilter == StatsFilter.MASTERY) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("mastery_donut_card"),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "JLPT N3 Mastery Breakdown",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "ဝေါဟာရ အဆင့်အလိုက် ခွဲခြားလေ့လာမှု",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MasteredGreen.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${masteryBreakdown.masteredCount}/${masteryBreakdown.totalCards} Words",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MasteredGreen,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Canvas Donut Chart
                            Box(
                                modifier = Modifier
                                    .size(136.dp)
                                    .testTag("donut_chart_canvas"),
                                contentAlignment = Alignment.Center
                            ) {
                                MasteryDonutChart(
                                    breakdown = masteryBreakdown,
                                    modifier = Modifier.fillMaxSize()
                                )

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
                                    label = "Mastered (ကျွမ်းကျင်)",
                                    count = masteryBreakdown.masteredCount,
                                    total = masteryBreakdown.totalCards,
                                    color = MasteredGreen
                                )
                                MasteryLegendItem(
                                    label = "Reviewing (ပြန်လည်လေ့ကျင့်)",
                                    count = masteryBreakdown.reviewingCount,
                                    total = masteryBreakdown.totalCards,
                                    color = JapaneseIndigo
                                )
                                MasteryLegendItem(
                                    label = "Learning (စတင်လေ့လာ)",
                                    count = masteryBreakdown.learningCount,
                                    total = masteryBreakdown.totalCards,
                                    color = ReviewBlue
                                )
                                MasteryLegendItem(
                                    label = "New / Unseen (မလေ့လာရသေး)",
                                    count = masteryBreakdown.newCount,
                                    total = masteryBreakdown.totalCards,
                                    color = PolishOutline
                                )
                            }
                        }
                    }
                }
            }

            // 4. Section: Daily Learning Activity Bar Chart (Shown for ALL or MASTERY)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("daily_progress_chart_card"),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
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
                            Column {
                                Text(
                                    text = "Daily Study Activity",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "နေ့စဉ် လေ့လာပြီးသော ကတ်အရေအတွက်နှင့် XP",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // Time Range Selector
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                            ) {
                                Row(modifier = Modifier.padding(2.dp)) {
                                    StatsTimeRange.entries.forEach { range ->
                                        val isSelected = selectedTimeRange == range
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(50))
                                                .background(
                                                    if (isSelected) JapaneseCrimson else Color.Transparent
                                                )
                                                .clickable { vocabViewModel.setStatsTimeRange(range) }
                                                .padding(horizontal = 9.dp, vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = range.label,
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
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
                                shape = RoundedCornerShape(12.dp),
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
                                            color = JapaneseIndigo
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
                                .height(180.dp)
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
                                            .background(JapaneseCrimson)
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
                                        text = "Daily Goal (${profile?.dailyGoal ?: 15})",
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
        }

        // 5. Section: Quiz Accuracy Trend & History (Shown for ALL or QUIZ)
        if (selectedFilter == StatsFilter.ALL || selectedFilter == StatsFilter.QUIZ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quiz_accuracy_trend_card"),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
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
                            Column {
                                Text(
                                    text = "Quiz Accuracy Trend",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "စစ်ဆေးမှုများတစ်လျှောက် တိကျမှုနှုန်း ပြောင်းလဲပုံ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(50),
                                color = JapaneseIndigo.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = "Avg: ${quizTrend.map { it.scorePercent }.average().let { if (it.isNaN()) 85 else it.toInt() }}%",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = JapaneseIndigo,
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

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                            thickness = 1.dp
                        )

                        // Direct Action Button to View Last 10 Sessions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Last 10 sessions recorded",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Button(
                                onClick = onNavigateToQuizHistory,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = JapaneseCrimson),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                                modifier = Modifier.testTag("stats_view_quiz_history_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "View Quiz History",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
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
    val primaryColor = JapaneseCrimson
    val primaryContainer = JapaneseIndigo.copy(alpha = 0.25f)
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
    val reviewingColor = JapaneseIndigo
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
    val primaryColor = JapaneseCrimson
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
    sub: String,
    value: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = sub,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                color = MaterialTheme.colorScheme.outline,
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

