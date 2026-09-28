package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.WorkspacePremium
import com.example.ui.components.SrsScheduleTrackingSheet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
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
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.StreakOrange
import com.example.ui.theme.WeakOrange
import com.example.ui.viewmodel.KanjiProgressDashboardSummary
import com.example.ui.viewmodel.KanjiTimelinePoint
import com.example.ui.viewmodel.StatsTimeRange
import com.example.ui.viewmodel.StreakDayStatus
import com.example.ui.viewmodel.VocabViewModel
import kotlin.math.roundToInt
import kotlin.math.sqrt

enum class ChartMetricSeries(val title: String, val myanmarLabel: String) {
    CUMULATIVE("Cumulative Learned", "စုစုပေါင်း တိုးတက်မှု"),
    DAILY_VELOCITY("Daily Added", "နေ့စဉ် အသစ်ရရှိမှု"),
    RETENTION_MASTERY("Mastery (Level 3+)", "ကျွမ်းကျင်မှု အဆင့် ၃")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    vocabViewModel: VocabViewModel,
    onNavigateBack: (() -> Unit)? = null,
    onNavigateToStudy: (List<VocabCard>) -> Unit = {},
    onNavigateToKanjiProgress: () -> Unit = {}
) {
    val dashboardSummary by vocabViewModel.kanjiProgressDashboardSummary.collectAsState()
    val allCards by vocabViewModel.allCards.collectAsState()

    var activeMetricSeries by remember { mutableStateOf(ChartMetricSeries.CUMULATIVE) }
    var selectedPointIndex by remember { mutableIntStateOf(-1) }
    var selectedHeatmapDay by remember { mutableStateOf<StreakDayStatus?>(null) }
    var showSrsTrackingSheet by remember { mutableStateOf(false) }

    // If point index is unset, default to the latest (today)
    LaunchedEffect(dashboardSummary.timelinePoints) {
        if (dashboardSummary.timelinePoints.isNotEmpty() && selectedPointIndex == -1) {
            selectedPointIndex = dashboardSummary.timelinePoints.lastIndex
        }
    }

    Scaffold(
        topBar = {
            if (onNavigateBack != null) {
                TopAppBar(
                    title = {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
                                ) {
                                    Text(
                                        text = "図",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = "Analytics Dashboard",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "D3 / Recharts Progress & Streak Metrics",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("dashboard_screen"),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ====================================================================
            // 1. HERO KPI SCOREBOARD: JLPT N3 KANJI PROGRESS & VELOCITY
            // ====================================================================
            item {
                DashboardHeroBanner(
                    summary = dashboardSummary,
                    onNavigateToKanjiProgress = onNavigateToKanjiProgress
                )
            }

            // ====================================================================
            // 2. RECHARTS / D3 INTERACTIVE SPLINE CHART: LEARNED KANJI OVER TIME
            // ====================================================================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("recharts_kanji_timeline_card"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Header & Title
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ShowChart,
                                        contentDescription = null,
                                        tint = JapaneseCrimson,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "Learned Kanji Over Time",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Text(
                                    text = "D3 Monotone Spline Curve (d3.curveMonotoneX) • JLPT N3",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // Time Range Selector (7D | 14D | 30D)
                            Row(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(3.dp),
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                StatsTimeRange.entries.forEach { range ->
                                    val isSelected = dashboardSummary.selectedTimeRange == range
                                    Surface(
                                        shape = RoundedCornerShape(9.dp),
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                        modifier = Modifier.clickable {
                                            vocabViewModel.setStatsTimeRange(range)
                                            selectedPointIndex = -1
                                        }
                                    ) {
                                        Text(
                                            text = when (range) {
                                                StatsTimeRange.DAYS_7 -> "7D"
                                                StatsTimeRange.DAYS_14 -> "14D"
                                                StatsTimeRange.DAYS_30 -> "30D"
                                            },
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Series Toggle Chips (Recharts Legend Bar)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ChartMetricSeries.entries.forEach { series ->
                                val selected = activeMetricSeries == series
                                FilterChip(
                                    selected = selected,
                                    onClick = { activeMetricSeries = series },
                                    label = {
                                        Text(
                                            text = series.title,
                                            fontSize = 11.sp,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    leadingIcon = {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(
                                                    when (series) {
                                                        ChartMetricSeries.CUMULATIVE -> JapaneseCrimson
                                                        ChartMetricSeries.DAILY_VELOCITY -> JapaneseIndigo
                                                        ChartMetricSeries.RETENTION_MASTERY -> MasteredGreen
                                                    },
                                                    CircleShape
                                                )
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                        selectedLabelColor = MaterialTheme.colorScheme.primary
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                )
                            }
                        }

                        // Recharts Interactive Floating Tooltip (Dynamic Inspector)
                        val points = dashboardSummary.timelinePoints
                        val activePoint = if (selectedPointIndex in points.indices) points[selectedPointIndex] else points.lastOrNull()

                        if (activePoint != null) {
                            RechartsInspectorTooltip(
                                point = activePoint,
                                totalTarget = dashboardSummary.totalTargetKanji,
                                activeSeries = activeMetricSeries
                            )
                        }

                        // Pure Jetpack Compose D3 Monotone Spline Curve Canvas
                        D3MonotoneSplineCanvas(
                            points = points,
                            totalTarget = dashboardSummary.totalTargetKanji,
                            activeSeries = activeMetricSeries,
                            selectedIndex = selectedPointIndex,
                            onSelectIndex = { selectedPointIndex = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )

                        // Interactive Instructions Footer
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "👆 ဇယားပေါ်ကို ဖိပြီး ဆွဲ၍ ရက်အလိုက် ကြည့်နိုင်သည် (Drag to inspect)",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "JLPT N3 ပန်းတိုင်: ${dashboardSummary.totalTargetKanji} Kanji",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                fontWeight = FontWeight.Bold,
                                color = JapaneseCrimson
                            )
                        }
                    }
                }
            }

            // ====================================================================
            // 3. CURRENT STREAK STATUS & D3 CALENDAR HEATMAP MATRIX
            // ====================================================================
            item {
                StreakStatusAndHeatmapCard(
                    summary = dashboardSummary,
                    selectedDay = selectedHeatmapDay,
                    onSelectDay = { selectedHeatmapDay = it },
                    onStartReview = {
                        val dueCards = allCards.filter { it.isDue }
                        val studyList = if (dueCards.isNotEmpty()) dueCards else allCards.take(15)
                        onNavigateToStudy(studyList)
                    }
                )
            }

            // ====================================================================
            // 4. KANJI MASTERY DISTRIBUTION DONUT (D3 ARC / SUNBURST LOGIC)
            // ====================================================================
            item {
                KanjiMasteryDistributionCard(
                    summary = dashboardSummary,
                    onNavigateToKanjiProgress = onNavigateToKanjiProgress
                )
            }

            // ====================================================================
            // 5. QUICK ACTIONS & STUDY ACCELERATORS
            // ====================================================================
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Next Study Steps",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    val dueCards = allCards.filter { it.isDue }
                                    val studyList = if (dueCards.isNotEmpty()) dueCards else allCards.take(15)
                                    onNavigateToStudy(studyList)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = JapaneseCrimson)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Text("Kanji လေ့လာမည်", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                }
                            }

                            OutlinedButton(
                                onClick = onNavigateToKanjiProgress,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(14.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.WorkspacePremium, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Text("Kanji Matrix", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                }
                            }
                        }

                        // SM-2 Spaced Repetition Room Database Schedule Tracker
                        OutlinedButton(
                            onClick = { showSrsTrackingSheet = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp))
                                Text("SM-2 Spaced Repetition Schedule & Forecast", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showSrsTrackingSheet) {
        SrsScheduleTrackingSheet(
            vocabViewModel = vocabViewModel,
            onDismiss = { showSrsTrackingSheet = false },
            onStartReview = {
                showSrsTrackingSheet = false
                val dueCards = allCards.filter { it.isDue }
                val studyList = if (dueCards.isNotEmpty()) dueCards else allCards.take(15)
                onNavigateToStudy(studyList)
            }
        )
    }
}

// ====================================================================
// HERO KPI SCOREBOARD
// ====================================================================
@Composable
private fun DashboardHeroBanner(
    summary: KanjiProgressDashboardSummary,
    onNavigateToKanjiProgress: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("dashboard_hero_kpi"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            JapaneseCrimson.copy(alpha = 0.08f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                // Top Row: JLPT N3 Target Badge & Quick Level
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "JLPT N3 KANJI PROGRESS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    letterSpacing = 1.1.sp,
                                    fontSize = 11.sp
                                ),
                                fontWeight = FontWeight.Bold,
                                color = JapaneseCrimson
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = JapaneseCrimson.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "375 စာလုံး ပန်းတိုင်",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    fontWeight = FontWeight.Bold,
                                    color = JapaneseCrimson,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "${summary.currentLearnedKanji} / ${summary.totalTargetKanji} Kanji",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Progress Percentage Ring / Badge
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = JapaneseCrimson.copy(alpha = 0.12f),
                        border = BorderStroke(1.dp, JapaneseCrimson.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                contentDescription = null,
                                tint = JapaneseCrimson,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "${summary.overallProgressPercent.roundToInt()}% Goal",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = JapaneseCrimson
                            )
                        }
                    }
                }

                // Smooth Linear Progress Bar with N3 Goal Marker
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    LinearProgressIndicator(
                        progress = { (summary.overallProgressPercent / 100f).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(9.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = JapaneseCrimson,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ကျန်ရှိသော စာလုံး: ${summary.totalTargetKanji - summary.currentLearnedKanji} Kanji",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "ခန့်မှန်းခြေ ပြီးမြောက်မည့်ရက်: ~${summary.estimatedDaysToGoal} ရက်",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // 3 Mini Metric Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ScoreboardMiniCard(
                        modifier = Modifier.weight(1f),
                        title = "Mastered",
                        subtitle = "ကျွမ်းကျင်",
                        value = "${summary.currentMasteredKanji}",
                        accentColor = MasteredGreen,
                        icon = Icons.Default.CheckCircle
                    )
                    ScoreboardMiniCard(
                        modifier = Modifier.weight(1f),
                        title = "Learning",
                        subtitle = "လေ့လာဆဲ",
                        value = "${summary.currentLearningKanji}",
                        accentColor = ReviewBlue,
                        icon = Icons.Default.MenuBook
                    )
                    ScoreboardMiniCard(
                        modifier = Modifier.weight(1f),
                        title = "Velocity",
                        subtitle = "အပတ်စဉ်နှုန်း",
                        value = "+${summary.weeklyVelocity.roundToInt()}/wk",
                        accentColor = StreakOrange,
                        icon = Icons.Default.Timeline
                    )
                }
            }
        }
    }
}

@Composable
private fun ScoreboardMiniCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    value: String,
    accentColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(13.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

// ====================================================================
// RECHARTS-STYLE TOOLTIP & INSPECTOR
// ====================================================================
@Composable
private fun RechartsInspectorTooltip(
    point: KanjiTimelinePoint,
    totalTarget: Int,
    activeSeries: ChartMetricSeries
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "${point.dayName} (${point.dateLabel})",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (point.isToday) "Today's Status • ယနေ့အခြေအနေ" else "Historical Snapshot",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Learned",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "${point.cumulativeLearned} / $totalTarget",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = JapaneseCrimson
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Added",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "+${point.dailyNewKanji}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = JapaneseIndigo
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Mastered",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "${point.cumulativeMastered}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MasteredGreen
                    )
                }
            }
        }
    }
}

// ====================================================================
// PURE COMPOSE D3 MONOTONE SPLINE CURVE CANVAS (d3.curveMonotoneX)
// ====================================================================
@Composable
private fun D3MonotoneSplineCanvas(
    points: List<KanjiTimelinePoint>,
    totalTarget: Int,
    activeSeries: ChartMetricSeries,
    selectedIndex: Int,
    onSelectIndex: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (points.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("No timeline points available yet", color = MaterialTheme.colorScheme.outline)
        }
        return
    }

    // Animation progress for smooth entry transitions
    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(points.size, activeSeries) {
        animationProgress.snapTo(0f)
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing)
        )
    }

    // Color definitions
    val primaryColor = when (activeSeries) {
        ChartMetricSeries.CUMULATIVE -> JapaneseCrimson
        ChartMetricSeries.DAILY_VELOCITY -> JapaneseIndigo
        ChartMetricSeries.RETENTION_MASTERY -> MasteredGreen
    }
    val gridLineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
    val textLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)

    Canvas(
        modifier = modifier
            .pointerInput(points.size) {
                detectTapGestures { offset ->
                    val paddingLeft = 45.dp.toPx()
                    val paddingRight = 16.dp.toPx()
                    val chartWidth = size.width - paddingLeft - paddingRight
                    if (chartWidth > 0 && points.size > 1) {
                        val stepX = chartWidth / (points.size - 1)
                        val touchX = (offset.x - paddingLeft).coerceIn(0f, chartWidth)
                        val index = (touchX / stepX).roundToInt().coerceIn(0, points.lastIndex)
                        onSelectIndex(index)
                    }
                }
            }
            .pointerInput(points.size) {
                detectDragGestures { change, _ ->
                    change.consume()
                    val paddingLeft = 45.dp.toPx()
                    val paddingRight = 16.dp.toPx()
                    val chartWidth = size.width - paddingLeft - paddingRight
                    if (chartWidth > 0 && points.size > 1) {
                        val stepX = chartWidth / (points.size - 1)
                        val touchX = (change.position.x - paddingLeft).coerceIn(0f, chartWidth)
                        val index = (touchX / stepX).roundToInt().coerceIn(0, points.lastIndex)
                        onSelectIndex(index)
                    }
                }
            }
    ) {
        val width = size.width
        val height = size.height
        val paddingLeft = 45.dp.toPx()
        val paddingRight = 16.dp.toPx()
        val paddingTop = 20.dp.toPx()
        val paddingBottom = 28.dp.toPx()

        val chartWidth = width - paddingLeft - paddingRight
        val chartHeight = height - paddingTop - paddingBottom
        if (chartWidth <= 0 || chartHeight <= 0) return@Canvas

        // 1. Determine Min & Max domain values based on active series
        val values = points.map { pt ->
            when (activeSeries) {
                ChartMetricSeries.CUMULATIVE -> pt.cumulativeLearned.toFloat()
                ChartMetricSeries.DAILY_VELOCITY -> pt.dailyNewKanji.toFloat()
                ChartMetricSeries.RETENTION_MASTERY -> pt.cumulativeMastered.toFloat()
            }
        }

        val rawMax = values.maxOrNull() ?: 100f
        val maxY = when (activeSeries) {
            ChartMetricSeries.CUMULATIVE -> (rawMax * 1.15f).coerceAtLeast(totalTarget.toFloat())
            ChartMetricSeries.DAILY_VELOCITY -> (rawMax * 1.25f).coerceAtLeast(10f)
            ChartMetricSeries.RETENTION_MASTERY -> (rawMax * 1.2f).coerceAtLeast(80f)
        }
        val minY = 0f

        // D3 Linear Scale helpers
        fun scaleX(index: Int): Float = paddingLeft + (index.toFloat() / (points.size - 1).coerceAtLeast(1)) * chartWidth
        fun scaleY(v: Float): Float = (paddingTop + chartHeight) - ((v - minY) / (maxY - minY)) * chartHeight

        // 2. Recharts Cartesian Grid (4 horizontal dashed reference lines)
        val gridSteps = 4
        val dashEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
        for (i in 0..gridSteps) {
            val ratio = i.toFloat() / gridSteps.toFloat()
            val yPos = paddingTop + chartHeight * (1f - ratio)
            val gridVal = (minY + ratio * (maxY - minY)).roundToInt()

            // Dashed Grid Line
            drawLine(
                color = gridLineColor,
                start = Offset(paddingLeft, yPos),
                end = Offset(width - paddingRight, yPos),
                strokeWidth = 1.dp.toPx(),
                pathEffect = dashEffect
            )

            // Y-Axis Tick Label (Native Canvas Text)
            drawContext.canvas.nativeCanvas.drawText(
                "$gridVal",
                paddingLeft - 8.dp.toPx(),
                yPos + 4.dp.toPx(),
                android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 10.dp.toPx()
                    textAlign = android.graphics.Paint.Align.RIGHT
                    isAntiAlias = true
                }
            )
        }

        // 3. Goal Target Reference Line (if Cumulative mode)
        if (activeSeries == ChartMetricSeries.CUMULATIVE && totalTarget <= maxY) {
            val goalY = scaleY(totalTarget.toFloat())
            drawLine(
                color = JapaneseCrimson.copy(alpha = 0.4f),
                start = Offset(paddingLeft, goalY),
                end = Offset(width - paddingRight, goalY),
                strokeWidth = 1.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
            )

            drawContext.canvas.nativeCanvas.drawText(
                "N3 Goal ($totalTarget)",
                width - paddingRight,
                goalY - 4.dp.toPx(),
                android.graphics.Paint().apply {
                    color = android.graphics.Color.parseColor("#FF6B6B")
                    textSize = 9.dp.toPx()
                    textAlign = android.graphics.Paint.Align.RIGHT
                    isFakeBoldText = true
                    isAntiAlias = true
                }
            )
        }

        // 4. Construct Coordinates for D3 Monotone Spline Interpolation
        val coords = points.mapIndexed { idx, _ ->
            val x = scaleX(idx)
            val currentVal = values[idx] * animationProgress.value
            val y = scaleY(currentVal)
            Offset(x, y)
        }

        // 5. Compute Monotone Cubic Hermite Spline (d3.curveMonotoneX)
        val splinePath = buildD3MonotonePath(coords)

        // 6. Draw Gradient Area Below the Curve (Recharts `<Area fill="url(...)"/>`)
        val areaPath = Path().apply {
            addPath(splinePath)
            lineTo(coords.last().x, paddingTop + chartHeight)
            lineTo(coords.first().x, paddingTop + chartHeight)
            close()
        }

        drawPath(
            path = areaPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    primaryColor.copy(alpha = 0.32f),
                    primaryColor.copy(alpha = 0.12f),
                    primaryColor.copy(alpha = 0.01f)
                ),
                startY = paddingTop,
                endY = paddingTop + chartHeight
            ),
            style = Fill
        )

        // 7. Draw the Smooth Stroke Curve
        drawPath(
            path = splinePath,
            color = primaryColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // 8. Draw X-Axis Date Labels along bottom
        val labelStep = if (points.size > 14) 4 else if (points.size > 7) 2 else 1
        points.forEachIndexed { idx, pt ->
            if (idx % labelStep == 0 || idx == points.lastIndex) {
                val x = scaleX(idx)
                val label = if (idx == points.lastIndex) "Today" else pt.shortDateLabel
                drawContext.canvas.nativeCanvas.drawText(
                    label,
                    x,
                    height - 6.dp.toPx(),
                    android.graphics.Paint().apply {
                        color = if (idx == selectedIndex) android.graphics.Color.parseColor("#FF6B6B") else android.graphics.Color.GRAY
                        textSize = 9.dp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                        isFakeBoldText = idx == selectedIndex || idx == points.lastIndex
                        isAntiAlias = true
                    }
                )
            }
        }

        // 9. Interactive Scrubber Crosshair & Active Point Anchor (Recharts `<Cursor />`)
        if (selectedIndex in coords.indices) {
            val activeCoord = coords[selectedIndex]

            // Vertical Crosshair Cursor
            drawLine(
                color = primaryColor.copy(alpha = 0.7f),
                start = Offset(activeCoord.x, paddingTop),
                end = Offset(activeCoord.x, paddingTop + chartHeight),
                strokeWidth = 1.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
            )

            // Outer Glowing Halo
            drawCircle(
                color = primaryColor.copy(alpha = 0.22f),
                radius = 12.dp.toPx(),
                center = activeCoord
            )

            // Active Dot
            drawCircle(
                color = primaryColor,
                radius = 5.dp.toPx(),
                center = activeCoord
            )
            drawCircle(
                color = Color.White,
                radius = 2.5.dp.toPx(),
                center = activeCoord
            )
        }
    }
}

/**
 * Mathematical Implementation of D3 Monotone Cubic Hermite Spline (d3.curveMonotoneX)
 * Ensures smooth, strictly monotonic curves between points without artificial overshoot ripples.
 */
private fun buildD3MonotonePath(points: List<Offset>): Path {
    val path = Path()
    if (points.isEmpty()) return path
    if (points.size == 1) {
        path.moveTo(points[0].x, points[0].y)
        return path
    }

    val n = points.size
    val dx = FloatArray(n - 1)
    val dy = FloatArray(n - 1)
    val slopes = FloatArray(n - 1)

    for (i in 0 until n - 1) {
        dx[i] = (points[i + 1].x - points[i].x).coerceAtLeast(0.0001f)
        dy[i] = points[i + 1].y - points[i].y
        slopes[i] = dy[i] / dx[i]
    }

    // Calculate initial tangents
    val tangents = FloatArray(n)
    tangents[0] = slopes[0]
    tangents[n - 1] = slopes[n - 2]

    for (i in 1 until n - 1) {
        val s0 = slopes[i - 1]
        val s1 = slopes[i]
        if (s0 * s1 <= 0f) {
            tangents[i] = 0f
        } else {
            tangents[i] = (s0 + s1) / 2f
        }
    }

    // Fritsch-Carlson algorithm for monotonicity adjustment
    for (i in 0 until n - 1) {
        val s = slopes[i]
        if (s == 0f) {
            tangents[i] = 0f
            tangents[i + 1] = 0f
        } else {
            val alpha = tangents[i] / s
            val beta = tangents[i + 1] / s
            val dist = alpha * alpha + beta * beta
            if (dist > 9f) {
                val tau = 3f / sqrt(dist)
                tangents[i] = tau * alpha * s
                tangents[i + 1] = tau * beta * s
            }
        }
    }

    // Construct cubic Bezier curve segments
    path.moveTo(points[0].x, points[0].y)
    for (i in 0 until n - 1) {
        val p0 = points[i]
        val p1 = points[i + 1]
        val segmentDx = dx[i]

        val cp1x = p0.x + segmentDx / 3f
        val cp1y = p0.y + tangents[i] * segmentDx / 3f

        val cp2x = p1.x - segmentDx / 3f
        val cp2y = p1.y - tangents[i + 1] * segmentDx / 3f

        path.cubicTo(cp1x, cp1y, cp2x, cp2y, p1.x, p1.y)
    }

    return path
}

// ====================================================================
// CURRENT STREAK STATUS & D3 CALENDAR HEATMAP MATRIX
// ====================================================================
@Composable
private fun StreakStatusAndHeatmapCard(
    summary: KanjiProgressDashboardSummary,
    selectedDay: StreakDayStatus?,
    onSelectDay: (StreakDayStatus) -> Unit,
    onStartReview: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "flame_pulse")
    val flameScale by infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flame_scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("dashboard_streak_heatmap_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: Hero Streak Display
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Glowing Flame Icon
                    Surface(
                        shape = CircleShape,
                        color = StreakOrange.copy(alpha = 0.18f),
                        border = BorderStroke(1.dp, StreakOrange.copy(alpha = 0.35f)),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Streak Flame",
                                tint = StreakOrange,
                                modifier = Modifier
                                    .size(30.dp)
                                    .scale(flameScale)
                            )
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "${summary.currentStreak} Days Streak",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (summary.isStreakActiveToday) {
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = MasteredGreen.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "Active Today ✓",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = MasteredGreen,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = "အကောင်းဆုံး စံချိန်: ${summary.bestStreak} ရက် • စဉ်ဆက်မပြတ်မှု: ${summary.consistencyPercent}%",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Streak Shield / Milestone Badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Shield Active",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))

            // D3 Calendar Activity Heatmap Matrix (GitHub / D3 Contribution Graph style)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daily Activity Matrix (လေ့လာမှု ရက်စွဲပြက္ခဒိန်)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Heatmap Legend
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Less", fontSize = 9.sp, color = MaterialTheme.colorScheme.outline)
                        listOf(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            StreakOrange.copy(alpha = 0.35f),
                            StreakOrange.copy(alpha = 0.7f),
                            StreakOrange
                        ).forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(color, RoundedCornerShape(2.dp))
                            )
                        }
                        Text("More", fontSize = 9.sp, color = MaterialTheme.colorScheme.outline)
                    }
                }

                // Grid of Activity Squares
                val days = summary.calendarHeatmapDays
                if (days.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        days.forEach { day ->
                            val isSelected = selectedDay == day
                            val color = when {
                                day.cardsCount >= 25 -> StreakOrange
                                day.cardsCount >= 15 -> StreakOrange.copy(alpha = 0.75f)
                                day.cardsCount > 0 || day.isCompleted -> StreakOrange.copy(alpha = 0.4f)
                                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.clickable { onSelectDay(day) }
                            ) {
                                Surface(
                                    modifier = Modifier.size(34.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    color = color,
                                    border = BorderStroke(
                                        if (isSelected || day.isToday) 2.dp else 1.dp,
                                        if (day.isToday) JapaneseCrimson else if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                                    )
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${day.dayOfMonth}",
                                            fontSize = 11.sp,
                                            fontWeight = if (day.isToday || day.isCompleted) FontWeight.Bold else FontWeight.Normal,
                                            color = if (day.cardsCount >= 15) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                Text(
                                    text = day.dayName.take(1),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }

                // Selected Day Details Banner
                if (selectedDay != null) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${selectedDay.dayName}, ${selectedDay.dateLabel}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (selectedDay.cardsCount > 0) "${selectedDay.cardsCount} Kanji Reviewed • +${selectedDay.xpEarned} XP" else "No reviews recorded on this day",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            if (selectedDay.isToday && !summary.isStreakActiveToday) {
                                Button(
                                    onClick = onStartReview,
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("Complete Today", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ====================================================================
// KANJI MASTERY DONUT (D3 PIE & ARC GAUGE)
// ====================================================================
@Composable
private fun KanjiMasteryDistributionCard(
    summary: KanjiProgressDashboardSummary,
    onNavigateToKanjiProgress: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("dashboard_mastery_donut_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.22f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                        text = "Kanji Mastery Distribution",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "D3 Radial Arc Breakdown • JLPT N3",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                TextButton(onClick = onNavigateToKanjiProgress) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Matrix", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                    }
                }
            }

            // Donut Chart Canvas & Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Compose Donut Canvas
                Box(
                    modifier = Modifier.size(130.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val mastered = summary.currentMasteredKanji.toFloat()
                    val learning = summary.currentLearningKanji.toFloat()
                    val newKanji = summary.currentNewKanji.toFloat().coerceAtLeast(1f)
                    val total = (mastered + learning + newKanji).coerceAtLeast(1f)

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 14.dp.toPx()
                        val diameter = size.minDimension - strokeWidth
                        val arcSize = Size(diameter, diameter)
                        val topLeft = Offset(strokeWidth / 2f, strokeWidth / 2f)

                        var startAngle = -90f

                        // 1. Mastered Slice (Emerald)
                        val masteredSweep = (mastered / total) * 360f
                        if (masteredSweep > 0) {
                            drawArc(
                                color = MasteredGreen,
                                startAngle = startAngle,
                                sweepAngle = masteredSweep,
                                useCenter = false,
                                topLeft = topLeft,
                                size = arcSize,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                            startAngle += masteredSweep
                        }

                        // 2. Learning Slice (Review Blue)
                        val learningSweep = (learning / total) * 360f
                        if (learningSweep > 0) {
                            drawArc(
                                color = ReviewBlue,
                                startAngle = startAngle,
                                sweepAngle = learningSweep,
                                useCenter = false,
                                topLeft = topLeft,
                                size = arcSize,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                            startAngle += learningSweep
                        }

                        // 3. New Slice (Slate Gray)
                        val newSweep = (newKanji / total) * 360f
                        if (newSweep > 0) {
                            drawArc(
                                color = Color.Gray.copy(alpha = 0.25f),
                                startAngle = startAngle,
                                sweepAngle = newSweep,
                                useCenter = false,
                                topLeft = topLeft,
                                size = arcSize,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                        }
                    }

                    // Center percentage display
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${summary.overallProgressPercent.roundToInt()}%",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Mastered",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                // Legend Column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DonutLegendRow(
                        color = MasteredGreen,
                        label = "Mastered (ကျွမ်းကျင်)",
                        count = "${summary.currentMasteredKanji}"
                    )
                    DonutLegendRow(
                        color = ReviewBlue,
                        label = "Learning (လေ့လာဆဲ)",
                        count = "${summary.currentLearningKanji}"
                    )
                    DonutLegendRow(
                        color = Color.Gray.copy(alpha = 0.45f),
                        label = "Remaining (ကျန်ရှိ)",
                        count = "${summary.currentNewKanji}"
                    )
                }
            }
        }
    }
}

@Composable
private fun DonutLegendRow(
    color: Color,
    label: String,
    count: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color, CircleShape)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = count,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
