package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
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
import com.example.ui.theme.PolishOutlineVariant
import com.example.ui.theme.PolishTertiary
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.StreakOrange
import com.example.ui.theme.WeakOrange
import com.example.ui.viewmodel.DailyStreakSummary
import com.example.ui.viewmodel.KanjiCategoryMastery
import com.example.ui.viewmodel.StreakDayStatus
import kotlin.math.cos
import kotlin.math.sin

enum class VisualSummaryTab(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    STREAKS("Streaks", Icons.Default.LocalFireDepartment),
    CATEGORIES("Categories", Icons.Default.Category),
    RADAR("Radar", Icons.Default.Radar)
}

/**
 * Visual Summary Dashboard Component
 * Clean, interactive, multi-metric visual summary of:
 * 1. User's Daily Study Streaks & Activity Timeline
 * 2. Mastery Levels for different Kanji Categories
 * 3. Multi-dimensional Radar Analysis for Kanji Skill Distribution
 */
@Composable
fun VisualSummaryDashboardCard(
    streakSummary: DailyStreakSummary,
    categoryStats: List<KanjiCategoryMastery>,
    allCards: List<VocabCard>,
    onStudyCategory: (List<VocabCard>) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(VisualSummaryTab.STREAKS) }
    var selectedCategoryIndex by remember { mutableStateOf<Int?>(0) }
    var selectedStreakDay by remember { mutableStateOf<StreakDayStatus?>(null) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("visual_summary_dashboard_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Dashboard Header & Tab Navigation Bar
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
                            imageVector = Icons.Default.Timeline,
                            contentDescription = null,
                            tint = JapaneseCrimson,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "VISUAL LEARNING SUMMARY",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                                letterSpacing = 1.1.sp
                            ),
                            fontWeight = FontWeight.Bold,
                            color = JapaneseCrimson
                        )
                    }
                    Text(
                        text = when (selectedTab) {
                            VisualSummaryTab.STREAKS -> "Daily Study Streaks & Activity"
                            VisualSummaryTab.CATEGORIES -> "Kanji Categories Mastery"
                            VisualSummaryTab.RADAR -> "Skill & Category Radar Map"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Tab Selector (Recharts-style Segmented Control)
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    VisualSummaryTab.entries.forEach { tab ->
                        val isSelected = selectedTab == tab
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
                            border = if (isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) else null,
                            shadowElevation = if (isSelected) 1.dp else 0.dp,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedTab = tab }
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = null,
                                    tint = if (isSelected) JapaneseCrimson else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = tab.title,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            // Tab Content Switcher with Animation
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(150))
                },
                label = "VisualSummaryTabContent"
            ) { tab ->
                when (tab) {
                    VisualSummaryTab.STREAKS -> {
                        DailyStreaksVisualView(
                            streakSummary = streakSummary,
                            selectedDay = selectedStreakDay,
                            onSelectDay = { selectedStreakDay = it }
                        )
                    }
                    VisualSummaryTab.CATEGORIES -> {
                        KanjiCategoriesMasteryView(
                            categories = categoryStats,
                            allCards = allCards,
                            selectedIndex = selectedCategoryIndex,
                            onSelectIndex = { selectedCategoryIndex = it },
                            onStudyCategory = onStudyCategory
                        )
                    }
                    VisualSummaryTab.RADAR -> {
                        KanjiCategoryRadarView(
                            categories = categoryStats,
                            selectedIndex = selectedCategoryIndex,
                            onSelectIndex = { selectedCategoryIndex = it }
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------
// 1. DAILY STREAKS VISUAL VIEW
// -------------------------------------------------------------------------------------

@Composable
fun DailyStreaksVisualView(
    streakSummary: DailyStreakSummary,
    selectedDay: StreakDayStatus?,
    onSelectDay: (StreakDayStatus) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Streak Hero Stats Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Current Streak Flame Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = StreakOrange.copy(alpha = 0.12f),
                border = BorderStroke(1.dp, StreakOrange.copy(alpha = 0.4f)),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(StreakOrange),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Streak",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${streakSummary.currentStreak} Days",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp, lineHeight = 18.sp),
                            fontWeight = FontWeight.Bold,
                            color = StreakOrange,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Current Streak",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, lineHeight = 13.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Best Streak Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = PolishTertiary.copy(alpha = 0.12f),
                border = BorderStroke(1.dp, PolishTertiary.copy(alpha = 0.4f)),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PolishTertiary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "Best Streak",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${streakSummary.bestStreak} Days",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp, lineHeight = 18.sp),
                            fontWeight = FontWeight.Bold,
                            color = PolishTertiary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Best Record",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, lineHeight = 13.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // 14-Day Streak Activity Timeline Matrix
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
                    text = "14-Day Streak Calendar",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${streakSummary.activeDaysThisMonth} active days (${streakSummary.streakConsistencyPercent}%)",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = MasteredGreen,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // 14-Day Streak Matrix Grid (7 days per row)
            val chunkedDays = remember(streakSummary.streakDays) {
                streakSummary.streakDays.chunked(7)
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                chunkedDays.forEach { rowDays ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        rowDays.forEach { day ->
                            val isSelected = selectedDay == day
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = when {
                                    day.isCompleted -> StreakOrange.copy(alpha = if (day.isToday) 0.95f else 0.8f)
                                    day.isToday -> MaterialTheme.colorScheme.primaryContainer
                                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                },
                                border = if (isSelected || day.isToday) {
                                    BorderStroke(1.5.dp, if (day.isCompleted) StreakOrange else MaterialTheme.colorScheme.primary)
                                } else null,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(52.dp)
                                    .clickable { onSelectDay(day) }
                            ) {
                                Column(
                                    modifier = Modifier.padding(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = day.dayName.take(2),
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                        fontWeight = FontWeight.Medium,
                                        color = if (day.isCompleted) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.outline
                                    )

                                    if (day.isCompleted) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Completed",
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    } else if (day.isToday) {
                                        Text(
                                            text = "Today",
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    } else {
                                        Text(
                                            text = "${day.dayOfMonth}",
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }

                                    Text(
                                        text = if (day.isCompleted) "+${day.xpEarned}p" else "—",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                                        color = if (day.isCompleted) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Active Day Tooltip / Inspection Detail
        val activeDay = selectedDay ?: streakSummary.streakDays.lastOrNull()
        if (activeDay != null) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (activeDay.isCompleted) StreakOrange else MaterialTheme.colorScheme.outline)
                        )
                        Text(
                            text = "${activeDay.dateLabel} (${activeDay.dayName})",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, lineHeight = 15.sp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "${activeDay.cardsCount} cards",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, lineHeight = 15.sp),
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = PolishTertiary,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "+${activeDay.xpEarned} XP",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, lineHeight = 15.sp),
                                color = PolishTertiary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Next Streak Milestone Progress Card
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
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
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = StreakOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Next Milestone: ${streakSummary.nextMilestoneStreak}-Day Streak",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "${streakSummary.daysUntilMilestone} days left",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = StreakOrange
                    )
                }

                val milestoneFraction = (streakSummary.currentStreak.toFloat() / streakSummary.nextMilestoneStreak.toFloat()).coerceIn(0f, 1f)
                LinearProgressIndicator(
                    progress = { milestoneFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(50)),
                    color = StreakOrange,
                    trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
            }
        }
    }
}

// -------------------------------------------------------------------------------------
// 2. KANJI CATEGORIES MASTERY VIEW (Recharts-Style Stacked Bar Charts)
// -------------------------------------------------------------------------------------

@Composable
fun KanjiCategoriesMasteryView(
    categories: List<KanjiCategoryMastery>,
    allCards: List<VocabCard>,
    selectedIndex: Int?,
    onSelectIndex: (Int) -> Unit,
    onStudyCategory: (List<VocabCard>) -> Unit
) {
    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(categories) {
        animProgress.snapTo(0f)
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Legend for Stacked Bar Distribution
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryLegendDot("Mastered", MasteredGreen)
                CategoryLegendDot("Review", JapaneseIndigo)
                CategoryLegendDot("Learn", ReviewBlue)
                CategoryLegendDot("New", PolishOutlineVariant)
            }
            Text(
                text = "${categories.size} Cats",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, lineHeight = 13.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }

        // Horizontal Category Mastery Stacked Bars
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            categories.forEachIndexed { idx, cat ->
                val isSelected = selectedIndex == idx
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                    border = BorderStroke(
                        if (isSelected) 1.5.dp else 1.dp,
                        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectIndex(idx) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Category Title & Mastery %
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f, fill = false),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = cat.iconEmoji,
                                    fontSize = 14.sp,
                                    lineHeight = 18.sp
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                                    Text(
                                        text = cat.categoryName,
                                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp, lineHeight = 15.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = cat.categoryJapanese,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, lineHeight = 13.sp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "${cat.totalCount} Kanji",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, lineHeight = 14.sp),
                                    color = MaterialTheme.colorScheme.outline
                                )
                                Text(
                                    text = "${cat.masteryPercent.toInt()}%",
                                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp, lineHeight = 15.sp),
                                    fontWeight = FontWeight.Bold,
                                    color = if (cat.masteryPercent >= 80) MasteredGreen else MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        // Multi-Segment Stacked Progress Bar (Mastered / Review / Learn / New)
                        val total = cat.totalCount.toFloat().coerceAtLeast(1f)
                        val mFrac = ((cat.masteredCount / total) * animProgress.value).coerceIn(0f, 1f)
                        val rFrac = ((cat.reviewingCount / total) * animProgress.value).coerceIn(0f, 1f)
                        val lFrac = ((cat.learningCount / total) * animProgress.value).coerceIn(0f, 1f)
                        val nFrac = ((cat.newCount / total) * animProgress.value).coerceIn(0f, 1f)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(PolishOutlineVariant)
                        ) {
                            if (mFrac > 0.001f) {
                                Box(
                                    modifier = Modifier
                                        .weight(mFrac.coerceAtLeast(0.01f))
                                        .height(8.dp)
                                        .background(MasteredGreen)
                                )
                            }
                            if (rFrac > 0.001f) {
                                Box(
                                    modifier = Modifier
                                        .weight(rFrac.coerceAtLeast(0.01f))
                                        .height(8.dp)
                                        .background(JapaneseIndigo)
                                )
                            }
                            if (lFrac > 0.001f) {
                                Box(
                                    modifier = Modifier
                                        .weight(lFrac.coerceAtLeast(0.01f))
                                        .height(8.dp)
                                        .background(ReviewBlue)
                                )
                            }
                            if (nFrac > 0.001f) {
                                Box(
                                    modifier = Modifier
                                        .weight(nFrac.coerceAtLeast(0.01f))
                                        .height(8.dp)
                                        .background(PolishOutlineVariant)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Selected Category Detail Drawer & Direct Action
        if (selectedIndex != null && selectedIndex in categories.indices) {
            val selectedCat = categories[selectedIndex]
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
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
                        Column {
                            Text(
                                text = "${selectedCat.iconEmoji} ${selectedCat.categoryName}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${selectedCat.lessonRange} • Accuracy: ${selectedCat.accuracyPercent}%",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (selectedCat.masteryPercent >= 80) MasteredGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                text = "${selectedCat.masteryPercent.toInt()}% Mastered",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedCat.masteryPercent >= 80) MasteredGreen else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Sample Kanji Chips
                    if (selectedCat.sampleKanji.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sample:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                            selectedCat.sampleKanji.forEach { kanji ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                                ) {
                                    Text(
                                        text = kanji,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Direct Action: Study this category
                    Button(
                        onClick = {
                            val targetLessonNumbers = when (selectedCat.categoryId) {
                                "cat_human" -> listOf(1, 2)
                                "cat_daily" -> listOf(3, 4, 5)
                                "cat_school" -> listOf(6, 7)
                                "cat_work" -> listOf(8, 9)
                                "cat_society" -> listOf(10, 11, 12)
                                "cat_nature" -> listOf(13, 14)
                                "cat_health" -> listOf(15, 16)
                                else -> listOf(17, 18, 19, 20, 21, 22)
                            }
                            val categoryCards = allCards.filter { it.lessonNumber in targetLessonNumbers }
                            if (categoryCards.isNotEmpty()) {
                                onStudyCategory(categoryCards)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = JapaneseCrimson)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Practice ${selectedCat.categoryName} Flashcards",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------------------
// 3. KANJI CATEGORY RADAR / SPIDER VIEW (Recharts-Style Canvas Polygon)
// -------------------------------------------------------------------------------------

@Composable
fun KanjiCategoryRadarView(
    categories: List<KanjiCategoryMastery>,
    selectedIndex: Int?,
    onSelectIndex: (Int) -> Unit
) {
    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(categories) {
        animProgress.snapTo(0f)
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val gridColor = PolishOutlineVariant
    val onSurface = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Multi-Category Mastery Polygon",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "Comparing recall & mastery rates across themes",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Radar Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val count = categories.size.coerceAtLeast(3)
                val center = Offset(size.width / 2f, size.height / 2f)
                val maxRadius = (size.minDimension / 2f) - 34.dp.toPx()
                val angleStep = (2 * Math.PI / count).toFloat()

                // 1. Draw Concentric Polygon Web Rings (25%, 50%, 75%, 100%)
                val ringFractions = listOf(0.25f, 0.50f, 0.75f, 1.00f)
                ringFractions.forEach { fraction ->
                    val ringRadius = maxRadius * fraction
                    val ringPath = Path()
                    for (i in 0 until count) {
                        val angle = (i * angleStep) - (Math.PI / 2).toFloat()
                        val x = center.x + (ringRadius * cos(angle))
                        val y = center.y + (ringRadius * sin(angle))
                        if (i == 0) ringPath.moveTo(x, y) else ringPath.lineTo(x, y)
                    }
                    ringPath.close()
                    drawPath(
                        path = ringPath,
                        color = gridColor.copy(alpha = 0.5f),
                        style = Stroke(width = 1.dp.toPx())
                    )
                }

                // 2. Draw Radial Spokes from center to vertices
                for (i in 0 until count) {
                    val angle = (i * angleStep) - (Math.PI / 2).toFloat()
                    val x = center.x + (maxRadius * cos(angle))
                    val y = center.y + (maxRadius * sin(angle))
                    drawLine(
                        color = gridColor.copy(alpha = 0.6f),
                        start = center,
                        end = Offset(x, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // 3. Compute Data Polygon Path (Mastery Percent * Animation)
                val dataPoints = mutableListOf<Offset>()
                for (i in 0 until count) {
                    val cat = categories[i]
                    val fraction = ((cat.masteryPercent / 100f).coerceIn(0.15f, 1.0f)) * animProgress.value
                    val r = maxRadius * fraction
                    val angle = (i * angleStep) - (Math.PI / 2).toFloat()
                    val x = center.x + (r * cos(angle))
                    val y = center.y + (r * sin(angle))
                    dataPoints.add(Offset(x, y))
                }

                val dataPath = Path().apply {
                    dataPoints.forEachIndexed { index, pt ->
                        if (index == 0) moveTo(pt.x, pt.y) else lineTo(pt.x, pt.y)
                    }
                    close()
                }

                // Draw Semi-transparent gradient fill
                drawPath(
                    path = dataPath,
                    brush = Brush.radialGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = 0.35f),
                            primaryColor.copy(alpha = 0.10f)
                        ),
                        center = center,
                        radius = maxRadius
                    ),
                    style = Fill
                )

                // Draw Polygon Stroke
                drawPath(
                    path = dataPath,
                    color = primaryColor,
                    style = Stroke(width = 2.5.dp.toPx())
                )

                // 4. Draw Vertex Markers
                dataPoints.forEachIndexed { idx, pt ->
                    val isSelected = selectedIndex == idx
                    drawCircle(
                        color = if (isSelected) JapaneseCrimson else primaryColor,
                        radius = if (isSelected) 6.dp.toPx() else 4.dp.toPx(),
                        center = pt
                    )
                    drawCircle(
                        color = Color.White,
                        radius = if (isSelected) 3.dp.toPx() else 2.dp.toPx(),
                        center = pt
                    )
                }
            }
        }

        // Quick Category Selector Chips below the Radar
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(categories.size) { idx ->
                val cat = categories[idx]
                val isSelected = selectedIndex == idx
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) primaryColor else MaterialTheme.colorScheme.surfaceVariant,
                    border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                    modifier = Modifier.clickable { onSelectIndex(idx) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = cat.iconEmoji, fontSize = 12.sp)
                        Text(
                            text = "${cat.categoryName}: ${cat.masteryPercent.toInt()}%",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryLegendDot(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, lineHeight = 13.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}
