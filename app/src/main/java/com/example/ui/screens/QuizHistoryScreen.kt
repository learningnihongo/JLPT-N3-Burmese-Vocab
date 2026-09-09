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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizHistory
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.JapaneseIndigo
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.PolishOutlineVariant
import com.example.ui.theme.PolishTertiary
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.StreakOrange
import com.example.ui.theme.WeakOrange
import com.example.ui.viewmodel.QuizType
import com.example.ui.viewmodel.QuizViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizHistoryScreen(
    quizViewModel: QuizViewModel,
    onNavigateBack: () -> Unit,
    onStartQuiz: (QuizType) -> Unit = {}
) {
    val allHistory by quizViewModel.quizHistory.collectAsState()
    val last10Sessions = remember(allHistory) { allHistory.take(10) }
    var selectedFilterMode by remember { mutableStateOf<String?>(null) }

    val filteredList = remember(last10Sessions, selectedFilterMode) {
        if (selectedFilterMode == null) last10Sessions
        else last10Sessions.filter { it.quizType.contains(selectedFilterMode!!, ignoreCase = true) }
    }

    // Performance Calculations for the Last 10 Sessions
    val totalSessions = last10Sessions.size
    val averageScore = remember(last10Sessions) {
        if (last10Sessions.isNotEmpty()) {
            last10Sessions.map { it.accuracyPercentage }.average().toInt()
        } else 0
    }
    val bestScore = remember(last10Sessions) {
        last10Sessions.maxOfOrNull { it.accuracyPercentage } ?: 0
    }
    val totalXpEarned = remember(last10Sessions) {
        last10Sessions.sumOf { it.xpEarned }
    }
    val totalQuestionsAnswered = remember(last10Sessions) {
        last10Sessions.sumOf { it.totalQuestions }
    }
    val totalQuestionsCorrect = remember(last10Sessions) {
        last10Sessions.sumOf { it.score }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Quiz History",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Last 10 test sessions & performance tracking",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("quiz_history_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to previous screen",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (last10Sessions.isEmpty()) {
            EmptyQuizHistoryView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("quiz_history_empty_view"),
                onStartFirstQuiz = {
                    onStartQuiz(QuizType.KANJI_TO_MEANING)
                }
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .testTag("quiz_history_screen"),
                contentPadding = PaddingValues(top = 8.dp, bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Performance Overview Banner for Last 10 Sessions
                item {
                    QuizPerformanceSummaryCard(
                        sessionsCount = totalSessions,
                        averageAccuracy = averageScore,
                        bestAccuracy = bestScore,
                        totalXp = totalXpEarned,
                        totalCorrect = totalQuestionsCorrect,
                        totalQuestions = totalQuestionsAnswered,
                        last10Sessions = last10Sessions
                    )
                }

                // 2. Score Progression Timeline (Visual Trend over the 10 Sessions)
                if (last10Sessions.size >= 2) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("quiz_history_trend_card"),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                            contentDescription = null,
                                            tint = JapaneseCrimson,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "10-Session Score Trend",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (averageScore >= 80) MasteredGreen.copy(alpha = 0.15f) else WeakOrange.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = if (averageScore >= 80) "Strong Retention" else "Needs Consistency",
                                            color = if (averageScore >= 80) MasteredGreen else WeakOrange,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = "Chronological score trajectory from Session #$totalSessions to latest Session #1",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                // Mini interactive score timeline curve
                                QuizSessionsTimelineChart(
                                    sessions = last10Sessions.reversed(), // Oldest to newest
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp)
                                )
                            }
                        }
                    }
                }

                // 3. Filter Options & Section Header
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                                    imageVector = Icons.Default.History,
                                    contentDescription = null,
                                    tint = JapaneseIndigo,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Last $totalSessions Quiz Sessions",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Text(
                                text = "Showing ${filteredList.size} of $totalSessions",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        // Filter Chips
                        val modes = remember(last10Sessions) {
                            last10Sessions.map { it.quizType }.distinct()
                        }

                        if (modes.size > 1) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(vertical = 2.dp)
                            ) {
                                item {
                                    FilterChip(
                                        selected = selectedFilterMode == null,
                                        onClick = { selectedFilterMode = null },
                                        label = { Text("All Modes", fontWeight = FontWeight.Bold) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = JapaneseIndigo,
                                            selectedLabelColor = Color.White
                                        )
                                    )
                                }
                                items(modes) { mode ->
                                    val isSelected = selectedFilterMode == mode
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { selectedFilterMode = if (isSelected) null else mode },
                                        label = { Text(mode, fontWeight = FontWeight.Medium) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = JapaneseCrimson,
                                            selectedLabelColor = Color.White
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // 4. Session Cards List (Showing the last 10 sessions)
                itemsIndexed(filteredList, key = { _, item -> item.id }) { index, session ->
                    QuizSessionCard(
                        session = session,
                        sessionIndex = index + 1,
                        onRetest = {
                            val matchedType = QuizType.values().firstOrNull { it.displayName == session.quizType }
                                ?: QuizType.KANJI_TO_MEANING
                            onStartQuiz(matchedType)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizPerformanceSummaryCard(
    sessionsCount: Int,
    averageAccuracy: Int,
    bestAccuracy: Int,
    totalXp: Int,
    totalCorrect: Int,
    totalQuestions: Int,
    last10Sessions: List<QuizHistory>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("quiz_history_summary_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            JapaneseCrimson.copy(alpha = 0.12f),
                            JapaneseIndigo.copy(alpha = 0.05f)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LAST 10 SESSIONS PERFORMANCE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                                letterSpacing = 1.1.sp
                            ),
                            fontWeight = FontWeight.Bold,
                            color = JapaneseCrimson
                        )
                        Text(
                            text = "$averageAccuracy% Avg Accuracy",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Rank Trophy
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = when {
                            averageAccuracy >= 90 -> MasteredGreen.copy(alpha = 0.18f)
                            averageAccuracy >= 75 -> PolishTertiary.copy(alpha = 0.18f)
                            else -> WeakOrange.copy(alpha = 0.18f)
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = when {
                                    averageAccuracy >= 90 -> MasteredGreen
                                    averageAccuracy >= 75 -> PolishTertiary
                                    else -> WeakOrange
                                },
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = when {
                                    averageAccuracy >= 90 -> "Mastery (S)"
                                    averageAccuracy >= 75 -> "Proficient (A)"
                                    averageAccuracy >= 60 -> "Developing (B)"
                                    else -> "Practicing (C)"
                                },
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    averageAccuracy >= 90 -> MasteredGreen
                                    averageAccuracy >= 75 -> PolishTertiary
                                    else -> WeakOrange
                                }
                            )
                        }
                    }
                }

                // 4-Stat Metric Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HistoryMetricBox(
                        modifier = Modifier.weight(1f),
                        title = "Sessions",
                        value = "$sessionsCount",
                        sub = "Recorded",
                        icon = Icons.Default.Quiz,
                        color = JapaneseIndigo
                    )
                    HistoryMetricBox(
                        modifier = Modifier.weight(1f),
                        title = "Peak Score",
                        value = "$bestAccuracy%",
                        sub = "Best Run",
                        icon = Icons.Default.Star,
                        color = MasteredGreen
                    )
                    HistoryMetricBox(
                        modifier = Modifier.weight(1f),
                        title = "Correct Qs",
                        value = "$totalCorrect/$totalQuestions",
                        sub = "Mastered",
                        icon = Icons.Default.CheckCircle,
                        color = ReviewBlue
                    )
                    HistoryMetricBox(
                        modifier = Modifier.weight(1f),
                        title = "XP Gained",
                        value = "+$totalXp",
                        sub = "Earned",
                        icon = Icons.Default.Bolt,
                        color = StreakOrange
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryMetricBox(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    sub: String,
    icon: ImageVector,
    color: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun QuizSessionCard(
    session: QuizHistory,
    sessionIndex: Int,
    onRetest: () -> Unit
) {
    val percent = session.accuracyPercentage
    val accuracyColor = when {
        percent >= 90 -> MasteredGreen
        percent >= 75 -> ReviewBlue
        percent >= 60 -> WeakOrange
        else -> JapaneseCrimson
    }

    val gradeBadge = when {
        percent == 100 -> "100% Perfect"
        percent >= 90 -> "Grade S"
        percent >= 80 -> "Grade A"
        percent >= 65 -> "Grade B"
        else -> "Grade C"
    }

    val formattedDate = remember(session.timestamp) {
        formatHistoryDate(session.timestamp)
    }

    val icon = getQuizTypeIcon(session.quizType)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("quiz_history_item_${sessionIndex - 1}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header Row: Session Index, Date, Rank Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Session Number Pill
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (sessionIndex == 1) JapaneseCrimson.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = if (sessionIndex == 1) "#1 (Latest)" else "#$sessionIndex",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (sessionIndex == 1) JapaneseCrimson else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    // Date & Time
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Grade Pill
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accuracyColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = gradeBadge,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = accuracyColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Main Info Row: Mode Icon & Name on left, Score & % on right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(accuracyColor.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = session.quizType,
                            tint = accuracyColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = session.quizType,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Scope: ${session.lessonFilter}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Score Display
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${session.score}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = accuracyColor
                        )
                        Text(
                            text = " / ${session.totalQuestions}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "$percent% Accuracy",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = accuracyColor
                    )
                }
            }

            // Accuracy Progress Bar
            LinearProgressIndicator(
                progress = { (percent / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = accuracyColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            // Footer Details Row: Time elapsed, XP Earned, and Quick Retest Action
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
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${session.timeSpentSeconds}s elapsed",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = StreakOrange,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "+${session.xpEarned} XP",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = StreakOrange
                        )
                    }
                }

                // Retest Button
                OutlinedButton(
                    onClick = onRetest,
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Replay,
                        contentDescription = "Retest",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Retest",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizSessionsTimelineChart(
    sessions: List<QuizHistory>,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val gridColor = PolishOutlineVariant
    val animProgress = remember { Animatable(0f) }

    LaunchedEffect(sessions) {
        animProgress.snapTo(0f)
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 750, easing = FastOutSlowInEasing)
        )
    }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val bottomY = height - 20.dp.toPx()
            val topY = 14.dp.toPx()
            val usableHeight = bottomY - topY

            if (sessions.isEmpty()) return@Canvas

            // 1. Grid Lines (0%, 50%, 100%)
            for (step in 0..2) {
                val y = topY + (step * (usableHeight / 2f))
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // 2. Compute Points
            val count = sessions.size
            val stepX = if (count > 1) width / (count - 1).toFloat() else width / 2f

            val points = sessions.mapIndexed { idx, s ->
                val x = idx * stepX
                val frac = (s.accuracyPercentage / 100f).coerceIn(0f, 1f)
                val y = bottomY - (usableHeight * frac * animProgress.value)
                Offset(x, y)
            }

            // 3. Fill Gradient under the curve
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

            // 4. Stroke Line
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

            // 5. Point Markers with Accuracy Colors
            points.forEachIndexed { idx, pt ->
                val accuracy = sessions[idx].accuracyPercentage
                val pointColor = when {
                    accuracy >= 90 -> MasteredGreen
                    accuracy >= 75 -> ReviewBlue
                    accuracy >= 60 -> WeakOrange
                    else -> JapaneseCrimson
                }

                drawCircle(
                    color = pointColor,
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

        // Bottom Session Markers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            sessions.forEachIndexed { idx, s ->
                Text(
                    text = "${s.accuracyPercentage}%",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun EmptyQuizHistoryView(
    modifier: Modifier = Modifier,
    onStartFirstQuiz: () -> Unit
) {
    Box(
        modifier = modifier.padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(JapaneseCrimson.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        tint = JapaneseCrimson,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "No Quiz Sessions Yet",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Complete your first quiz in the Arena to track your score history, speed, and accuracy over time.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = onStartFirstQuiz,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = JapaneseCrimson),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("start_first_quiz_btn")
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start a JLPT N3 Quiz", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// Helpers
private fun formatHistoryDate(timestamp: Long): String {
    val date = Date(timestamp)
    val now = Calendar.getInstance()
    val cal = Calendar.getInstance().apply { timeInMillis = timestamp }

    val isToday = now.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)

    val isYesterday = now.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR) == 1

    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    return when {
        isToday -> "Today, ${timeFormatter.format(date)}"
        isYesterday -> "Yesterday, ${timeFormatter.format(date)}"
        else -> SimpleDateFormat("MMM d, yyyy • hh:mm a", Locale.getDefault()).format(date)
    }
}

private fun getQuizTypeIcon(quizType: String): ImageVector {
    return when {
        quizType.contains("Kanji Mastery", ignoreCase = true) -> Icons.Default.Psychology
        quizType.contains("Meaning", ignoreCase = true) -> Icons.Default.Translate
        quizType.contains("Reading", ignoreCase = true) || quizType.contains("Furigana", ignoreCase = true) -> Icons.Default.MenuBook
        quizType.contains("Homophone", ignoreCase = true) || quizType.contains("ဆင်တူ", ignoreCase = true) || quizType.contains("Look-Alike", ignoreCase = true) -> Icons.Default.SwapHoriz
        quizType.contains("Listening", ignoreCase = true) || quizType.contains("Audio", ignoreCase = true) -> Icons.Default.Headphones
        quizType.contains("True", ignoreCase = true) || quizType.contains("Blitz", ignoreCase = true) -> Icons.Default.Bolt
        quizType.contains("Speed", ignoreCase = true) -> Icons.Default.Speed
        quizType.contains("Starred", ignoreCase = true) || quizType.contains("Bookmark", ignoreCase = true) -> Icons.Default.Star
        else -> Icons.Default.School
    }
}
