package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.JapaneseIndigo
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.StreakOrange
import com.example.ui.viewmodel.WeeklyKanjiProgress
import com.example.ui.viewmodel.WeeklyMasteryVsReviewedSummary

enum class WeeklyChartType(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    GROUPED_BARS("Dual Bars", Icons.Default.BarChart),
    STACKED_BARS("Stacked", Icons.Default.StackedBarChart),
    TREND_AREA("Trend Spline", Icons.Default.ShowChart)
}

/**
 * Weekly Mastery vs. Reviewed Summary Visualization
 * High-craft D3 / Recharts-style interactive visualization for the user's profile section.
 * Tracks daily Kanji Mastered vs. Reviewed across the current week.
 */
@Composable
fun WeeklyMasteryProgressChartCard(
    weeklySummary: WeeklyMasteryVsReviewedSummary,
    modifier: Modifier = Modifier
) {
    var selectedChartType by remember { mutableStateOf(WeeklyChartType.GROUPED_BARS) }
    var selectedDayIndex by remember {
        mutableIntStateOf(
            if (weeklySummary.dailyProgressList.isNotEmpty()) weeklySummary.dailyProgressList.size - 1 else 0
        )
    }

    val days = weeklySummary.dailyProgressList
    val selectedDay = days.getOrNull(selectedDayIndex)

    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(weeklySummary, selectedChartType) {
        animProgress.snapTo(0f)
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing)
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("weekly_mastery_progress_chart_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Header & Chart Type Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = JapaneseCrimson,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "WEEKLY PROGRESS SUMMARY",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                                letterSpacing = 1.1.sp
                            ),
                            fontWeight = FontWeight.Bold,
                            color = JapaneseCrimson
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Mastered vs. Reviewed Kanji",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Chart Type Mode Switcher Chips
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                ) {
                    Row(
                        modifier = Modifier.padding(3.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        WeeklyChartType.values().forEach { chartType ->
                            val isSelected = selectedChartType == chartType
                            Surface(
                                shape = RoundedCornerShape(9.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
                                shadowElevation = if (isSelected) 1.dp else 0.dp,
                                modifier = Modifier
                                    .clickable { selectedChartType = chartType }
                                    .testTag("chart_type_${chartType.name.lowercase()}")
                            ) {
                                Icon(
                                    imageVector = chartType.icon,
                                    contentDescription = chartType.label,
                                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 2. Weekly Key Metric Stats Row (D3 Summary Cards)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Reviewed KPI
                WeeklyStatMiniCard(
                    title = "Reviewed",
                    value = "${weeklySummary.totalReviewedWeek}",
                    subtitle = "Avg ${weeklySummary.avgDailyReviewed}/day",
                    icon = Icons.Default.Psychology,
                    color = ReviewBlue,
                    modifier = Modifier.weight(1f)
                )

                // Mastered KPI
                WeeklyStatMiniCard(
                    title = "Mastered",
                    value = "${weeklySummary.totalMasteredWeek}",
                    subtitle = "${weeklySummary.weeklyMasteryConversionRate}% Yield",
                    icon = Icons.Default.CheckCircle,
                    color = MasteredGreen,
                    modifier = Modifier.weight(1f)
                )

                // Goal Hit Rate KPI
                WeeklyStatMiniCard(
                    title = "Goal Met",
                    value = "${weeklySummary.goalReachedDaysCount}/7",
                    subtitle = "Best: ${weeklySummary.bestDayName}",
                    icon = Icons.Default.LocalFireDepartment,
                    color = StreakOrange,
                    modifier = Modifier.weight(1f)
                )
            }

            // 3. Interactive D3 / Recharts-style Canvas Chart
            if (days.isNotEmpty()) {
                val primaryColor = MaterialTheme.colorScheme.primary
                val outlineColor = MaterialTheme.colorScheme.outline
                val onSurfaceColor = MaterialTheme.colorScheme.onSurface
                val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant

                // Find max scale for Y-axis
                val maxVal = remember(days) {
                    val highest = days.maxOfOrNull { maxOf(it.kanjiReviewed, it.kanjiMastered, it.goalCount) } ?: 30
                    ((highest / 10) + 1) * 10
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(days) {
                                detectTapGestures { offset ->
                                    val dayWidth = size.width / days.size
                                    val index = (offset.x / dayWidth).toInt().coerceIn(0, days.size - 1)
                                    selectedDayIndex = index
                                }
                            }
                    ) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        val bottomLabelHeight = 24.dp.toPx()
                        val chartHeight = canvasHeight - bottomLabelHeight
                        val stepX = canvasWidth / days.size

                        // Draw Horizontal Y-Grid lines and values
                        val yGridCount = 4
                        for (i in 0..yGridCount) {
                            val ratio = i.toFloat() / yGridCount
                            val y = chartHeight - (ratio * chartHeight)
                            val gridVal = (ratio * maxVal).toInt()

                            drawLine(
                                color = outlineColor.copy(alpha = 0.12f),
                                start = Offset(0f, y),
                                end = Offset(canvasWidth, y),
                                strokeWidth = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
                            )

                            // Y-axis label text
                            drawContext.canvas.nativeCanvas.drawText(
                                gridVal.toString(),
                                8.dp.toPx(),
                                y - 3.dp.toPx(),
                                android.graphics.Paint().apply {
                                    color = outlineColor.copy(alpha = 0.5f).hashCode()
                                    textSize = 9.sp.toPx()
                                    isAntiAlias = true
                                }
                            )
                        }

                        // Draw Daily Goal Reference Line
                        val goalVal = days.firstOrNull()?.goalCount ?: 15
                        val goalY = chartHeight - ((goalVal.toFloat() / maxVal.toFloat()) * chartHeight)
                        drawLine(
                            color = StreakOrange.copy(alpha = 0.6f),
                            start = Offset(0f, goalY),
                            end = Offset(canvasWidth, goalY),
                            strokeWidth = 1.5.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f)
                        )

                        when (selectedChartType) {
                            WeeklyChartType.GROUPED_BARS -> {
                                // Draw Grouped Dual Bars (Reviewed & Mastered)
                                days.forEachIndexed { index, day ->
                                    val groupCenterX = (index * stepX) + (stepX / 2f)
                                    val isSelected = index == selectedDayIndex
                                    val barWidth = 10.dp.toPx()
                                    val spacing = 3.dp.toPx()

                                    // Highlight column background for selected day
                                    if (isSelected) {
                                        drawRoundRect(
                                            color = primaryColor.copy(alpha = 0.1f),
                                            topLeft = Offset(index * stepX + 2.dp.toPx(), 0f),
                                            size = Size(stepX - 4.dp.toPx(), chartHeight + 2.dp.toPx()),
                                            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                                        )
                                    }

                                    // 1. Reviewed Bar (Blue)
                                    val reviewedHeight = ((day.kanjiReviewed.toFloat() / maxVal.toFloat()) * chartHeight * animProgress.value).coerceAtLeast(2.dp.toPx())
                                    val reviewedX = groupCenterX - barWidth - (spacing / 2f)
                                    val reviewedY = chartHeight - reviewedHeight

                                    drawRoundRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                ReviewBlue,
                                                ReviewBlue.copy(alpha = 0.7f)
                                            ),
                                            startY = reviewedY,
                                            endY = chartHeight
                                        ),
                                        topLeft = Offset(reviewedX, reviewedY),
                                        size = Size(barWidth, reviewedHeight),
                                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                                    )

                                    // 2. Mastered Bar (Green)
                                    val masteredHeight = ((day.kanjiMastered.toFloat() / maxVal.toFloat()) * chartHeight * animProgress.value).coerceAtLeast(2.dp.toPx())
                                    val masteredX = groupCenterX + (spacing / 2f)
                                    val masteredY = chartHeight - masteredHeight

                                    drawRoundRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                MasteredGreen,
                                                MasteredGreen.copy(alpha = 0.7f)
                                            ),
                                            startY = masteredY,
                                            endY = chartHeight
                                        ),
                                        topLeft = Offset(masteredX, masteredY),
                                        size = Size(barWidth, masteredHeight),
                                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                                    )

                                    // Selection Top Indicator
                                    if (isSelected) {
                                        drawCircle(
                                            color = primaryColor,
                                            radius = 3.dp.toPx(),
                                            center = Offset(groupCenterX, minOf(reviewedY, masteredY) - 6.dp.toPx())
                                        )
                                    }
                                }
                            }

                            WeeklyChartType.STACKED_BARS -> {
                                // Draw Stacked Bars (Mastered portion + Reviewed remaining portion)
                                days.forEachIndexed { index, day ->
                                    val groupCenterX = (index * stepX) + (stepX / 2f)
                                    val isSelected = index == selectedDayIndex
                                    val barWidth = 18.dp.toPx()

                                    if (isSelected) {
                                        drawRoundRect(
                                            color = primaryColor.copy(alpha = 0.1f),
                                            topLeft = Offset(index * stepX + 2.dp.toPx(), 0f),
                                            size = Size(stepX - 4.dp.toPx(), chartHeight + 2.dp.toPx()),
                                            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                                        )
                                    }

                                    val totalReviewed = day.kanjiReviewed
                                    val mastered = day.kanjiMastered.coerceAtMost(totalReviewed)
                                    val remainingReviewed = (totalReviewed - mastered).coerceAtLeast(0)

                                    val masteredHeight = ((mastered.toFloat() / maxVal.toFloat()) * chartHeight * animProgress.value).coerceAtLeast(2.dp.toPx())
                                    val reviewedHeight = ((remainingReviewed.toFloat() / maxVal.toFloat()) * chartHeight * animProgress.value)

                                    val barX = groupCenterX - (barWidth / 2f)
                                    val masteredY = chartHeight - masteredHeight
                                    val reviewedY = masteredY - reviewedHeight

                                    // Mastered segment (bottom)
                                    drawRoundRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(MasteredGreen, MasteredGreen.copy(alpha = 0.75f)),
                                            startY = masteredY,
                                            endY = chartHeight
                                        ),
                                        topLeft = Offset(barX, masteredY),
                                        size = Size(barWidth, masteredHeight),
                                        cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
                                    )

                                    // Remaining reviewed segment (top)
                                    if (reviewedHeight > 1.dp.toPx()) {
                                        drawRoundRect(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(ReviewBlue, ReviewBlue.copy(alpha = 0.75f)),
                                                startY = reviewedY,
                                                endY = masteredY
                                            ),
                                            topLeft = Offset(barX, reviewedY),
                                            size = Size(barWidth, reviewedHeight),
                                            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                                        )
                                    }
                                }
                            }

                            WeeklyChartType.TREND_AREA -> {
                                // Draw Spline Curves & Gradient Area for Reviewed and Mastered
                                drawSmoothTrendArea(
                                    days = days,
                                    maxVal = maxVal,
                                    chartHeight = chartHeight,
                                    canvasWidth = canvasWidth,
                                    animProgress = animProgress.value,
                                    getValue = { it.kanjiReviewed },
                                    lineColor = ReviewBlue,
                                    areaBrush = Brush.verticalGradient(
                                        colors = listOf(ReviewBlue.copy(alpha = 0.35f), Color.Transparent),
                                        startY = 0f,
                                        endY = chartHeight
                                    )
                                )

                                drawSmoothTrendArea(
                                    days = days,
                                    maxVal = maxVal,
                                    chartHeight = chartHeight,
                                    canvasWidth = canvasWidth,
                                    animProgress = animProgress.value,
                                    getValue = { it.kanjiMastered },
                                    lineColor = MasteredGreen,
                                    areaBrush = Brush.verticalGradient(
                                        colors = listOf(MasteredGreen.copy(alpha = 0.45f), Color.Transparent),
                                        startY = 0f,
                                        endY = chartHeight
                                    )
                                )

                                // Draw anchor dots on selected day
                                selectedDay?.let { day ->
                                    val groupCenterX = (selectedDayIndex * stepX) + (stepX / 2f)
                                    val revY = chartHeight - ((day.kanjiReviewed.toFloat() / maxVal.toFloat()) * chartHeight * animProgress.value)
                                    val masY = chartHeight - ((day.kanjiMastered.toFloat() / maxVal.toFloat()) * chartHeight * animProgress.value)

                                    drawCircle(ReviewBlue, radius = 5.dp.toPx(), center = Offset(groupCenterX, revY))
                                    drawCircle(Color.White, radius = 2.5.dp.toPx(), center = Offset(groupCenterX, revY))

                                    drawCircle(MasteredGreen, radius = 5.dp.toPx(), center = Offset(groupCenterX, masY))
                                    drawCircle(Color.White, radius = 2.5.dp.toPx(), center = Offset(groupCenterX, masY))
                                }
                            }
                        }

                        // X-Axis Day Labels
                        days.forEachIndexed { index, day ->
                            val groupCenterX = (index * stepX) + (stepX / 2f)
                            val isSelected = index == selectedDayIndex

                            drawContext.canvas.nativeCanvas.drawText(
                                day.dayName.take(3),
                                groupCenterX,
                                canvasHeight - 6.dp.toPx(),
                                android.graphics.Paint().apply {
                                    color = if (isSelected) primaryColor.hashCode() else onSurfaceColor.copy(alpha = 0.65f).hashCode()
                                    textSize = 10.sp.toPx()
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    isFakeBoldText = isSelected || day.isToday
                                    isAntiAlias = true
                                }
                            )
                        }
                    }
                }
            }

            // 4. Interactive Day Selector Chips & Legend
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Legend Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ChartLegendItem(color = ReviewBlue, label = "Reviewed")
                        ChartLegendItem(color = MasteredGreen, label = "Mastered")
                        ChartLegendItem(color = StreakOrange, label = "Daily Goal", isDashed = true)
                    }

                    Text(
                        text = "Tap day to inspect",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                // Interactive Day Selector Row
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    itemsIndexed(days) { index, day ->
                        val isSelected = index == selectedDayIndex
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            border = if (isSelected) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)),
                            modifier = Modifier
                                .clickable { selectedDayIndex = index }
                                .testTag("day_chip_${day.dayName.lowercase()}")
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = if (day.isToday) "Today" else day.dayName.take(3),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${day.dayOfMonth}",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // 5. Selected Day Detail Inspector Card (D3-style Tooltip Breakdown)
            selectedDay?.let { day ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("day_progress_inspector_card")
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(if (day.kanjiReviewed >= day.goalCount) MasteredGreen else StreakOrange)
                                )
                                Text(
                                    text = day.fullDateLabel,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(50),
                                color = if (day.kanjiReviewed >= day.goalCount) MasteredGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
                                border = BorderStroke(1.dp, if (day.kanjiReviewed >= day.goalCount) MasteredGreen.copy(alpha = 0.3f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            ) {
                                Text(
                                    text = if (day.kanjiReviewed >= day.goalCount) "Goal Met (${day.kanjiReviewed}/${day.goalCount})" else "${day.kanjiReviewed}/${day.goalCount} words",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    fontWeight = FontWeight.Bold,
                                    color = if (day.kanjiReviewed >= day.goalCount) MasteredGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Breakdown metrics row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            DayMetricTile(
                                label = "Kanji Reviewed",
                                value = "${day.kanjiReviewed}",
                                color = ReviewBlue,
                                modifier = Modifier.weight(1f)
                            )
                            DayMetricTile(
                                label = "Kanji Mastered",
                                value = "${day.kanjiMastered}",
                                color = MasteredGreen,
                                modifier = Modifier.weight(1f)
                            )
                            DayMetricTile(
                                label = "Mastery Yield",
                                value = "${day.masteryRatePercent}%",
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // 6. Smart Pedagogical Weekly Insight
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "Weekly Study Insight",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (weeklySummary.weeklyMasteryConversionRate >= 25) {
                                "Strong retention! ${weeklySummary.weeklyMasteryConversionRate}% of reviewed Kanji advanced to mastery. Keep reviewing regularly."
                            } else {
                                "Consistent reviews build long-term memory. Continue your daily streak to turn reviewed Kanji into permanent mastery."
                            },
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklyStatMiniCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp),
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun ChartLegendItem(
    color: Color,
    label: String,
    isDashed: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if (isDashed) {
            Box(
                modifier = Modifier
                    .width(12.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DayMetricTile(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.08f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

/**
 * Helper to draw a smooth cubic spline line and filled gradient area for trend mode
 */
private fun DrawScope.drawSmoothTrendArea(
    days: List<WeeklyKanjiProgress>,
    maxVal: Int,
    chartHeight: Float,
    canvasWidth: Float,
    animProgress: Float,
    getValue: (WeeklyKanjiProgress) -> Int,
    lineColor: Color,
    areaBrush: Brush
) {
    if (days.size < 2) return
    val stepX = canvasWidth / days.size

    val points = days.mapIndexed { index, day ->
        val x = (index * stepX) + (stepX / 2f)
        val y = chartHeight - ((getValue(day).toFloat() / maxVal.toFloat()) * chartHeight * animProgress)
        Offset(x, y)
    }

    val path = Path()
    path.moveTo(points.first().x, points.first().y)

    for (i in 0 until points.size - 1) {
        val p0 = points[i]
        val p1 = points[i + 1]
        val cx = (p0.x + p1.x) / 2f
        path.cubicTo(cx, p0.y, cx, p1.y, p1.x, p1.y)
    }

    // Draw line stroke
    drawPath(
        path = path,
        color = lineColor,
        style = Stroke(width = 2.5.dp.toPx())
    )

    // Close path to draw filled gradient area under curve
    val areaPath = Path().apply {
        addPath(path)
        lineTo(points.last().x, chartHeight)
        lineTo(points.first().x, chartHeight)
        close()
    }

    drawPath(
        path = areaPath,
        brush = areaBrush,
        style = Fill
    )
}
