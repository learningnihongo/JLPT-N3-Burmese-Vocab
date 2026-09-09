package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.example.ui.viewmodel.VocabViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

/**
 * Target Goal Mode:
 * - STANDARD_N3_KANJI: Standard JLPT N3 official target of ~650 Kanji characters.
 * - TOTAL_APP_VOCAB: All 880 JLPT N3 vocabulary/kanji items in the curriculum.
 * - CUSTOM_GOAL: User customized milestone target.
 */
enum class KanjiGoalType(val title: String, val defaultGoal: Int, val description: String) {
    STANDARD_N3_KANJI("Standard N3 Kanji", 650, "JLPT N3 စံသတ်မှတ်ချက် (၆၅၀ လုံး)"),
    TOTAL_APP_VOCAB("All N3 Vocabulary", 880, "သင်ရိုးရှိ စကားလုံးအားလုံး (၈၈၀ လုံး)"),
    CUSTOM_GOAL("Custom Target", 500, "စိတ်ကြိုက်ပန်းတိုင် သတ်မှတ်ချက်")
}

/**
 * Chart slice representation for Material Design Donut Arc rendering.
 */
data class DonutSlice(
    val label: String,
    val myanmarLabel: String,
    val count: Int,
    val color: Color,
    val percentage: Float,
    val startAngle: Float,
    val sweepAngle: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanjiProgressScreen(
    vocabViewModel: VocabViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToStudy: (List<VocabCard>) -> Unit,
    modifier: Modifier = Modifier
) {
    val allCards by vocabViewModel.allCards.collectAsState()
    val profile by vocabViewModel.userProfile.collectAsState()
    val kanjiCategories by vocabViewModel.kanjiCategoryStats.collectAsState()

    var selectedGoalType by remember { mutableStateOf(KanjiGoalType.STANDARD_N3_KANJI) }
    var customGoalCount by remember { mutableIntStateOf(500) }
    var showGoalEditDialog by remember { mutableStateOf(false) }

    val targetGoal = when (selectedGoalType) {
        KanjiGoalType.STANDARD_N3_KANJI -> KanjiGoalType.STANDARD_N3_KANJI.defaultGoal
        KanjiGoalType.TOTAL_APP_VOCAB -> allCards.size.coerceAtLeast(KanjiGoalType.TOTAL_APP_VOCAB.defaultGoal)
        KanjiGoalType.CUSTOM_GOAL -> customGoalCount
    }

    // Classification of cards
    val masteredCards = remember(allCards) { allCards.filter { it.masteryLevel >= 3 } }
    val reviewCards = remember(allCards) { allCards.filter { it.masteryLevel == 2 } }
    val learningCards = remember(allCards) { allCards.filter { it.masteryLevel == 1 } }
    val learnedCards = remember(allCards) { allCards.filter { it.masteryLevel >= 1 } }
    val remainingCards = remember(allCards) { allCards.filter { it.masteryLevel == 0 } }

    val learnedCount = learnedCards.size
    val remainingCount = (targetGoal - learnedCount).coerceAtLeast(0)
    val progressPercent = if (targetGoal > 0) {
        ((learnedCount.toFloat() / targetGoal.toFloat()) * 100f).coerceIn(0f, 100f)
    } else 0f

    // Daily pace & velocity calculation
    var dailyPace by remember(profile?.dailyGoal) { mutableIntStateOf(profile?.dailyGoal?.coerceAtLeast(5) ?: 15) }
    val daysRemaining = if (dailyPace > 0) ceil(remainingCount.toDouble() / dailyPace.toDouble()).toInt() else 0

    val estimatedCompletionDateStr = remember(daysRemaining) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysRemaining)
        val format = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        format.format(calendar.time)
    }

    // Kanji filter in matrix
    var matrixFilter by remember { mutableStateOf("ALL") } // ALL, LEARNED, REMAINING
    var searchQuery by remember { mutableStateOf("") }

    val filteredMatrixCards = remember(allCards, matrixFilter, searchQuery) {
        val base = when (matrixFilter) {
            "LEARNED" -> learnedCards
            "REMAINING" -> remainingCards
            else -> allCards
        }
        if (searchQuery.isBlank()) {
            base.take(60)
        } else {
            base.filter {
                it.kanji.contains(searchQuery, ignoreCase = true) ||
                it.reading.contains(searchQuery, ignoreCase = true) ||
                it.meaningBurmese.contains(searchQuery, ignoreCase = true)
            }.take(60)
        }
    }

    // Interactive chart slice highlight
    var activeSliceInfo by remember { mutableStateOf<DonutSlice?>(null) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("kanji_progress_screen"),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Kanji Goal Progress",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "JLPT N3 ပန်းတိုင် တိုးတက်မှုဇယား",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .testTag("kanji_progress_back_btn")
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showGoalEditDialog = true },
                        modifier = Modifier
                            .testTag("edit_kanji_goal_btn")
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Goal",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Goal Selector Tabs
            item {
                GoalSelectorTabs(
                    selectedGoalType = selectedGoalType,
                    onSelectGoalType = { selectedGoalType = it },
                    targetGoal = targetGoal
                )
            }

            // 2. Main Hero: Material Design Donut Arc Progress Chart
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("kanji_donut_chart_card"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
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
                                    Icon(
                                        imageVector = Icons.Default.PieChart,
                                        contentDescription = null,
                                        tint = JapaneseCrimson,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "LEARNED VS REMAINING",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 11.sp,
                                            letterSpacing = 1.sp
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        color = JapaneseCrimson
                                    )
                                }
                                Text(
                                    text = "JLPT N3 Completion Chart",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                            ) {
                                Text(
                                    text = "Goal: $targetGoal Kanji",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }

                        // Donut Chart Canvas
                        MaterialDesignDonutChart(
                            masteredCount = masteredCards.size,
                            reviewCount = reviewCards.size,
                            learningCount = learningCards.size,
                            remainingCount = remainingCount,
                            targetGoal = targetGoal,
                            progressPercent = progressPercent,
                            onSliceSelected = { slice -> activeSliceInfo = slice }
                        )

                        // Interactive Slice Info Tooltip
                        AnimatedVisibility(
                            visible = activeSliceInfo != null,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            activeSliceInfo?.let { slice ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = slice.color.copy(alpha = 0.12f),
                                    border = BorderStroke(1.dp, slice.color.copy(alpha = 0.35f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
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
                                                    .background(slice.color)
                                            )
                                            Text(
                                                text = "${slice.label} (${slice.myanmarLabel})",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.SemiBold,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        Text(
                                            text = "${slice.count} cards (${slice.percentage.toInt()}%)",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = slice.color
                                        )
                                    }
                                }
                            }
                        }

                        // Chart Legend
                        DonutChartLegend(
                            masteredCount = masteredCards.size,
                            reviewCount = reviewCards.size,
                            learningCount = learningCards.size,
                            remainingCount = remainingCount,
                            targetGoal = targetGoal
                        )
                    }
                }
            }

            // 3. Side-by-Side KPI Cards: Learned vs. Remaining
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Learned KPI
                    LearnedKpiCard(
                        learnedCount = learnedCount,
                        targetGoal = targetGoal,
                        percent = progressPercent,
                        mastered = masteredCards.size,
                        review = reviewCards.size,
                        learning = learningCards.size,
                        modifier = Modifier.weight(1f)
                    )

                    // Remaining KPI
                    RemainingKpiCard(
                        remainingCount = remainingCount,
                        targetGoal = targetGoal,
                        percent = (100f - progressPercent).coerceAtLeast(0f),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 4. Goal Pacing & Velocity Estimator
            item {
                VelocityEstimatorCard(
                    remainingCount = remainingCount,
                    dailyPace = dailyPace,
                    daysRemaining = daysRemaining,
                    completionDate = estimatedCompletionDateStr,
                    onSelectPace = { dailyPace = it }
                )
            }

            // 5. Action Buttons: Study Remaining vs Review Learned
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (remainingCards.isNotEmpty()) {
                                onNavigateToStudy(remainingCards)
                            } else {
                                onNavigateToStudy(allCards)
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = JapaneseCrimson
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("study_remaining_btn")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Study Remaining (${remainingCount})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            if (learnedCards.isNotEmpty()) {
                                onNavigateToStudy(learnedCards)
                            } else {
                                onNavigateToStudy(allCards)
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("review_learned_btn")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Review Learned (${learnedCount})",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            // 6. Category Progress Breakdown (Bar Charts)
            if (kanjiCategories.isNotEmpty()) {
                item {
                    CategoryBreakdownSection(
                        categories = kanjiCategories,
                        onCategoryClick = { category ->
                            val matched = allCards.filter { card ->
                                category.sampleKanji.contains(card.kanji) ||
                                (card.lessonTitle.isNotBlank() && category.categoryName.contains(card.lessonTitle, ignoreCase = true))
                            }.ifEmpty { allCards.take(15) }
                            if (matched.isNotEmpty()) {
                                onNavigateToStudy(matched)
                            }
                        }
                    )
                }
            }

            // 7. Interactive Kanji Matrix Explorer
            item {
                KanjiMatrixExplorer(
                    totalCount = allCards.size,
                    learnedCount = learnedCount,
                    remainingCount = remainingCount,
                    selectedFilter = matrixFilter,
                    onFilterChange = { matrixFilter = it },
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    cards = filteredMatrixCards,
                    onCardClick = { card ->
                        onNavigateToStudy(listOf(card))
                    }
                )
            }
        }
    }

    // Custom Goal Edit Dialog
    if (showGoalEditDialog) {
        CustomGoalDialog(
            currentGoal = customGoalCount,
            onDismiss = { showGoalEditDialog = false },
            onConfirm = { newGoal ->
                customGoalCount = newGoal
                selectedGoalType = KanjiGoalType.CUSTOM_GOAL
                showGoalEditDialog = false
            }
        )
    }
}

/**
 * Goal Selector Tabs (Standard N3 650 Kanji vs All 880 Vocab vs Custom)
 */
@Composable
private fun GoalSelectorTabs(
    selectedGoalType: KanjiGoalType,
    onSelectGoalType: (KanjiGoalType) -> Unit,
    targetGoal: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TARGET MILESTONE (ပန်းတိုင်ရွေးချယ်ရန်)",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${targetGoal} Words Goal",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                KanjiGoalType.entries.forEach { type ->
                    val isSelected = selectedGoalType == type
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSelectGoalType(type) },
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = when (type) {
                                    KanjiGoalType.STANDARD_N3_KANJI -> "N3 650 Kanji"
                                    KanjiGoalType.TOTAL_APP_VOCAB -> "All 880 Vocab"
                                    KanjiGoalType.CUSTOM_GOAL -> "Custom"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Native Material Design 3 Donut Chart with animated sweep arcs,
 * center percentage, and touch slice detection.
 */
@Composable
private fun MaterialDesignDonutChart(
    masteredCount: Int,
    reviewCount: Int,
    learningCount: Int,
    remainingCount: Int,
    targetGoal: Int,
    progressPercent: Float,
    onSliceSelected: (DonutSlice?) -> Unit,
    modifier: Modifier = Modifier
) {
    val total = targetGoal.coerceAtLeast(1).toFloat()

    // Smooth entry animation
    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(targetGoal, masteredCount, reviewCount, learningCount) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
    }

    val masteredAngle = (masteredCount / total) * 360f
    val reviewAngle = (reviewCount / total) * 360f
    val learningAngle = (learningCount / total) * 360f
    val remainingAngle = (remainingCount / total) * 360f

    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)

    Box(
        modifier = modifier
            .size(240.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val dx = offset.x - center.x
                        val dy = offset.y - center.y
                        var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
                        if (angle < 0) angle += 360f
                        // standard sweep starts at -90 (top)
                        var normalized = (angle + 90f) % 360f

                        val mSweep = masteredAngle
                        val rSweep = reviewAngle
                        val lSweep = learningAngle

                        when {
                            normalized < mSweep -> {
                                onSliceSelected(
                                    DonutSlice("Mastered", "ကျွမ်းကျင်ပြီး", masteredCount, MasteredGreen, (masteredCount / total) * 100f, -90f, mSweep)
                                )
                            }
                            normalized < (mSweep + rSweep) -> {
                                onSliceSelected(
                                    DonutSlice("Review", "ပြန်လည်သုံးသပ်ဆဲ", reviewCount, ReviewBlue, (reviewCount / total) * 100f, -90f + mSweep, rSweep)
                                )
                            }
                            normalized < (mSweep + rSweep + lSweep) -> {
                                onSliceSelected(
                                    DonutSlice("Learning", "စတင်သင်ယူဆဲ", learningCount, StreakOrange, (learningCount / total) * 100f, -90f + mSweep + rSweep, lSweep)
                                )
                            }
                            else -> {
                                onSliceSelected(
                                    DonutSlice("Remaining", "ကျန်ရှိနေသေးသော", remainingCount, surfaceVariant, (remainingCount / total) * 100f, -90f + mSweep + rSweep + lSweep, remainingAngle)
                                )
                            }
                        }
                    }
                }
        ) {
            val strokeWidth = 28.dp.toPx()
            val diameter = size.minDimension - strokeWidth
            val topLeft = Offset(
                (size.width - diameter) / 2f,
                (size.height - diameter) / 2f
            )
            val arcSize = Size(diameter, diameter)

            // Base Background Ring (Remaining / Unseen)
            drawArc(
                color = surfaceVariant.copy(alpha = 0.5f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            val anim = animatedProgress.value
            var currentAngle = -90f

            // 1. Mastered Arc (Green)
            if (masteredAngle > 0f) {
                val sweep = masteredAngle * anim
                drawArc(
                    color = MasteredGreen,
                    startAngle = currentAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                currentAngle += sweep
            }

            // 2. Review Arc (Blue)
            if (reviewAngle > 0f) {
                val sweep = reviewAngle * anim
                drawArc(
                    color = ReviewBlue,
                    startAngle = currentAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                currentAngle += sweep
            }

            // 3. Learning Arc (Orange)
            if (learningAngle > 0f) {
                val sweep = learningAngle * anim
                drawArc(
                    color = StreakOrange,
                    startAngle = currentAngle,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
            }
        }

        // Center Content of Donut
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${progressPercent.toInt()}%",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${(masteredCount + reviewCount + learningCount)} / $targetGoal",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Learned (သင်ယူပြီး)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Donut Chart Legend Pills
 */
@Composable
private fun DonutChartLegend(
    masteredCount: Int,
    reviewCount: Int,
    learningCount: Int,
    remainingCount: Int,
    targetGoal: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LegendItem(
                label = "Mastered (ကျွမ်းကျင်)",
                count = masteredCount,
                color = MasteredGreen,
                modifier = Modifier.weight(1f)
            )
            LegendItem(
                label = "Review (ပြန်လည်သုံးသပ်)",
                count = reviewCount,
                color = ReviewBlue,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LegendItem(
                label = "Learning (သင်ယူဆဲ)",
                count = learningCount,
                color = StreakOrange,
                modifier = Modifier.weight(1f)
            )
            LegendItem(
                label = "Remaining (ကျန်ရှိ)",
                count = remainingCount,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun LegendItem(
    label: String,
    count: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$count",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Learned KPI Card
 */
@Composable
private fun LearnedKpiCard(
    learnedCount: Int,
    targetGoal: Int,
    percent: Float,
    mastered: Int,
    review: Int,
    learning: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MasteredGreen.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MasteredGreen.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "LEARNED (သင်ယူပြီး)",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MasteredGreen,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MasteredGreen,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = "$learnedCount",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            LinearProgressIndicator(
                progress = { (percent / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MasteredGreen,
                trackColor = MasteredGreen.copy(alpha = 0.15f)
            )

            Text(
                text = "${percent.toInt()}% of $targetGoal target",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Mastered", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$mastered", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MasteredGreen)
                }
                Column {
                    Text("Review", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$review", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = ReviewBlue)
                }
                Column {
                    Text("Learning", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("$learning", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = StreakOrange)
                }
            }
        }
    }
}

/**
 * Remaining KPI Card
 */
@Composable
private fun RemainingKpiCard(
    remainingCount: Int,
    targetGoal: Int,
    percent: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "REMAINING (ကျန်ရှိ)",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = "$remainingCount",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            LinearProgressIndicator(
                progress = { (percent / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Text(
                text = "${percent.toInt()}% left to complete",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Goal Target",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$targetGoal Kanji",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Pacing & Velocity Estimator Card: Shows estimated days to finish JLPT N3 goal.
 */
@Composable
private fun VelocityEstimatorCard(
    remainingCount: Int,
    dailyPace: Int,
    daysRemaining: Int,
    completionDate: String,
    onSelectPace: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("velocity_estimator_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
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
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = null,
                        tint = JapaneseIndigo,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "GOAL PACING & FORECAST",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = JapaneseIndigo
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = JapaneseIndigo.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "ခန့်မှန်းပြီးမြောက်မည့်ရက်",
                        style = MaterialTheme.typography.labelSmall,
                        color = JapaneseIndigo,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Projected Time Highlight
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(JapaneseIndigo.copy(alpha = 0.06f))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = if (daysRemaining > 0) "~$daysRemaining Days Remaining" else "Goal Completed! 🎉",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = JapaneseIndigo
                    )
                    Text(
                        text = "Estimated Finish: $completionDate",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = JapaneseIndigo,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Daily Speed Selector
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Adjust Daily Learning Pace (တစ်နေ့လျှင် လေ့လာမည့်နှုန်း):",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(5, 10, 15, 20, 30).forEach { pace ->
                        val isSelected = dailyPace == pace
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) JapaneseIndigo else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onSelectPace(pace) }
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "$pace",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "/day",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Category Breakdown Progress Bars (Material Design bar progress)
 */
@Composable
private fun CategoryBreakdownSection(
    categories: List<com.example.ui.viewmodel.KanjiCategoryMastery>,
    onCategoryClick: (com.example.ui.viewmodel.KanjiCategoryMastery) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("category_breakdown_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CATEGORY PROGRESS BREAKDOWN",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    ),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${categories.size} Modules",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            categories.take(6).forEach { category ->
                val learnedInCat = category.masteredCount + category.learningCount
                val catPercent = if (category.totalCount > 0) {
                    (learnedInCat.toFloat() / category.totalCount.toFloat())
                } else 0f

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategoryClick(category) }
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
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
                                Text(category.iconEmoji, fontSize = 16.sp)
                                Text(
                                    text = category.categoryName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Text(
                                text = "$learnedInCat / ${category.totalCount} (${(catPercent * 100).toInt()}%)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        LinearProgressIndicator(
                            progress = { catPercent.coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = if (catPercent >= 1f) MasteredGreen else MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Interactive Kanji Matrix Explorer: Explore learned vs remaining kanji items.
 */
@Composable
private fun KanjiMatrixExplorer(
    totalCount: Int,
    learnedCount: Int,
    remainingCount: Int,
    selectedFilter: String,
    onFilterChange: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    cards: List<VocabCard>,
    onCardClick: (VocabCard) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("kanji_matrix_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "EXPLORE KANJI & VOCABULARY",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    letterSpacing = 1.sp
                ),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("kanji_matrix_search"),
                placeholder = { Text("Search Kanji, Reading or Meaning...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(18.dp)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // Filter Chips (All, Learned, Remaining)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == "ALL",
                    onClick = { onFilterChange("ALL") },
                    label = { Text("All ($totalCount)", fontSize = 12.sp) },
                    shape = RoundedCornerShape(10.dp)
                )
                FilterChip(
                    selected = selectedFilter == "LEARNED",
                    onClick = { onFilterChange("LEARNED") },
                    label = { Text("Learned ($learnedCount)", fontSize = 12.sp) },
                    shape = RoundedCornerShape(10.dp)
                )
                FilterChip(
                    selected = selectedFilter == "REMAINING",
                    onClick = { onFilterChange("REMAINING") },
                    label = { Text("Remaining ($remainingCount)", fontSize = 12.sp) },
                    shape = RoundedCornerShape(10.dp)
                )
            }

            // Cards Preview
            if (cards.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No cards found matching current criteria",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    cards.take(15).forEach { card ->
                        val isLearned = card.masteryLevel >= 1
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isLearned) MasteredGreen.copy(alpha = 0.06f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                            border = BorderStroke(
                                1.dp,
                                if (isLearned) MasteredGreen.copy(alpha = 0.25f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCardClick(card) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = card.kanji,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Column {
                                        Text(
                                            text = card.reading,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = card.meaningBurmese,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (isLearned) MasteredGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = if (isLearned) "Learned" else "Remaining",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = if (isLearned) MasteredGreen else MaterialTheme.colorScheme.outline,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Custom Goal Setting Dialog
 */
@Composable
private fun CustomGoalDialog(
    currentGoal: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var textValue by remember { mutableStateOf(currentGoal.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set Custom JLPT N3 Target") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "သတ်မှတ်လိုသော JLPT N3 Kanji/စကားလုံး အရေအတွက် ပန်းတိုင်ကို ထည့်သွင်းပါ:",
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            textValue = newValue
                        }
                    },
                    label = { Text("Target Goal Count") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val count = textValue.toIntOrNull()?.coerceIn(10, 2000) ?: currentGoal
                    onConfirm(count)
                }
            ) {
                Text("Save Goal")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
