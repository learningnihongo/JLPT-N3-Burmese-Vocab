package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SrsDayForecast
import com.example.data.model.SrsReviewLog
import com.example.data.model.SrsScheduleSummary
import com.example.ui.viewmodel.FlashcardStudyMode
import com.example.ui.viewmodel.VocabViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SrsScheduleTrackingSheet(
    vocabViewModel: VocabViewModel,
    onDismiss: () -> Unit,
    onStartReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    val srsSummary by vocabViewModel.srsScheduleSummary.collectAsState()
    val recentLogs by vocabViewModel.recentSrsLogs.collectAsState()
    val totalReviews by vocabViewModel.totalSrsReviewsCount.collectAsState()
    val retentionRate by vocabViewModel.srsRetentionRate.collectAsState()

    var showFormulaDetails by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = modifier.testTag("srs_schedule_sheet")
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .navigationBarsPadding(),
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Schedule,
                                        contentDescription = "SRS Icon",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Spaced Repetition (SM-2)",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Room Database Scheduled Reviews & Memory Retention Tracking",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }
            }

            // Quick Stats Banner
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatItem(
                            label = "Due Now",
                            value = "${srsSummary.dueNowCount}",
                            highlightColor = if (srsSummary.dueNowCount > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                        DividerVertical()
                        StatItem(
                            label = "Avg Ease",
                            value = String.format(Locale.US, "%.2f", srsSummary.averageEaseFactor),
                            highlightColor = MaterialTheme.colorScheme.secondary
                        )
                        DividerVertical()
                        StatItem(
                            label = "Retention",
                            value = if (srsSummary.totalReviewsLogged > 0) {
                                "${srsSummary.overallRetentionRate.toInt()}%"
                            } else {
                                "100%"
                            },
                            highlightColor = MaterialTheme.colorScheme.tertiary
                        )
                        DividerVertical()
                        StatItem(
                            label = "Reviews",
                            value = "$totalReviews",
                            highlightColor = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Call to Action: Start Due Reviews
            item {
                if (srsSummary.dueNowCount > 0) {
                    Button(
                        onClick = {
                            vocabViewModel.startDueCardsStudySession(
                                mode = FlashcardStudyMode.JP_TO_MY,
                                onSuccess = {
                                    onDismiss()
                                    onStartReview()
                                }
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("start_srs_due_review_btn")
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Start Due Review (${srsSummary.dueNowCount} Cards Ready)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            vocabViewModel.startDueCardsStudySession(
                                mode = FlashcardStudyMode.JP_TO_MY,
                                onSuccess = {
                                    onDismiss()
                                    onStartReview()
                                }
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("All Due Reviews Completed! Practice Ahead (15 Cards)")
                    }
                }
            }

            // 7-Day Schedule Forecast Bar Chart
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Upcoming 7-Day Review Forecast",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Next 7 Days: ${srsSummary.dueNext7DaysCount}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Forecast Bar Chart
                        SrsForecastBarChart(
                            forecastList = srsSummary.dailyScheduleForecast,
                            primaryColor = MaterialTheme.colorScheme.primary,
                            todayColor = MaterialTheme.colorScheme.error,
                            onSurfaceColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Memory Stages Breakdown Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "SM-2 Memory Stages Distribution",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        StageProgressRow(
                            label = "Mastered (Interval ≥ 14d)",
                            count = srsSummary.masteredCardsCount,
                            total = srsSummary.totalCards,
                            color = Color(0xFF2E7D32)
                        )
                        StageProgressRow(
                            label = "Reviewing (Reps ≥ 2)",
                            count = srsSummary.reviewingCardsCount,
                            total = srsSummary.totalCards,
                            color = Color(0xFF1976D2)
                        )
                        StageProgressRow(
                            label = "Learning (Reps = 1)",
                            count = srsSummary.learningCardsCount,
                            total = srsSummary.totalCards,
                            color = Color(0xFFF57C00)
                        )
                        StageProgressRow(
                            label = "New Cards (Unreviewed)",
                            count = srsSummary.newCardsCount,
                            total = srsSummary.totalCards,
                            color = Color(0xFF757575)
                        )
                    }
                }
            }

            // SM-2 Algorithm Explanation Accordion
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showFormulaDetails = !showFormulaDetails }
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
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
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "How SM-2 Spaced Repetition Works",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Icon(
                                imageVector = if (showFormulaDetails) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null
                            )
                        }

                        AnimatedVisibility(visible = showFormulaDetails) {
                            Column(
                                modifier = Modifier.padding(top = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "The SuperMemo-2 (SM-2) algorithm schedules card appearances according to the human forgetting curve:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "• Again (q=1): Resets repetitions to 0, interval = 1 day, Ease Factor drops by 0.20.\n" +
                                           "• Hard (q=2): Conservative interval expansion (×1.2), preserves habit, EF drops by 0.15.\n" +
                                           "• Good (q=4): Standard interval: I(1)=1d, I(2)=6d, I(n)=I(n-1)×EF.\n" +
                                           "• Easy (q=5): Multiplied by EF × 1.3 bonus, EF increases by 0.15.\n" +
                                           "• Ease Factor: EF' = EF + (0.1 - (5-q) × (0.08 + (5-q) × 0.02)), EF ≥ 1.30.\n" +
                                           "• Room Database: Every single review event is logged into `srs_review_logs` table.",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.5.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            // Recent Room Database Review Logs
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Room Database Review Logs",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${recentLogs.size} logs",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            if (recentLogs.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No reviews logged yet. Complete your first flashcard study session to start tracking!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(recentLogs.take(15), key = { it.id }) { log ->
                    SrsReviewLogItem(log = log)
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    highlightColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = highlightColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DividerVertical() {
    Divider(
        modifier = Modifier
            .height(28.dp)
            .width(1.dp),
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    )
}

@Composable
private fun StageProgressRow(
    label: String,
    count: Int,
    total: Int,
    color: Color
) {
    val fraction = if (total > 0) (count.toFloat() / total.toFloat()).coerceIn(0f, 1f) else 0f

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = "$count (${(fraction * 100).toInt()}%)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        LinearProgressIndicator(
            progress = { fraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.15f)
        )
    }
}

@Composable
private fun SrsForecastBarChart(
    forecastList: List<SrsDayForecast>,
    primaryColor: Color,
    todayColor: Color,
    onSurfaceColor: Color
) {
    if (forecastList.isEmpty()) return

    val maxCount = max(5, forecastList.maxOfOrNull { it.scheduledCardsCount } ?: 5)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        forecastList.forEach { day ->
            val barFraction = (day.scheduledCardsCount.toFloat() / maxCount.toFloat()).coerceIn(0.08f, 1f)
            val barColor = if (day.isToday) todayColor else primaryColor

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // Count Label
                Text(
                    text = "${day.scheduledCardsCount}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (day.scheduledCardsCount > 0) barColor else onSurfaceColor.copy(alpha = 0.5f),
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .fillMaxHeight(0.70f * barFraction)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .background(barColor)
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Day Label
                Text(
                    text = day.dayLabel,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal,
                    color = if (day.isToday) todayColor else onSurfaceColor,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun SrsReviewLogItem(log: SrsReviewLog) {
    val ratingColor = when (log.rating.uppercase()) {
        "EASY" -> Color(0xFF2E7D32)
        "GOOD" -> Color(0xFF1976D2)
        "HARD" -> Color(0xFFF57C00)
        else -> MaterialTheme.colorScheme.error
    }

    val timeFormatted = remember(log.reviewTimestamp) {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        sdf.format(Date(log.reviewTimestamp))
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = log.kanji,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = log.reading,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Text(
                    text = log.meaningBurmese,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Interval: ${log.intervalDaysBefore}d → ${log.intervalDaysAfter}d • EF: ${String.format(Locale.US, "%.2f", log.easeFactorAfter)}",
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ratingColor.copy(alpha = 0.15f),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(ratingColor))
                ) {
                    Text(
                        text = log.rating,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = ratingColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = timeFormatted,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                    fontSize = 10.sp
                )
            }
        }
    }
}
