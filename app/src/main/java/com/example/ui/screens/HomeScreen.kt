package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Badge
import com.example.data.model.LessonProgress
import com.example.data.model.VocabCard
import com.example.ui.components.BadgeDetailDialog
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.PolishOutlineVariant
import com.example.ui.theme.PolishPrimary
import com.example.ui.theme.PolishPrimaryContainer
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.SakuraPinkDark
import com.example.ui.theme.StreakOrange
import com.example.ui.theme.WeakOrange
import com.example.ui.viewmodel.VocabFilterType
import com.example.ui.viewmodel.VocabViewModel
import java.util.Calendar

private data class DailyProverb(
    val japanese: String,
    val reading: String,
    val meaningBurmese: String,
    val meaningEnglish: String
)

private val DAILY_PROVERBS = listOf(
    DailyProverb(
        japanese = "継続は力なり",
        reading = "けいぞくはちからなり",
        meaningBurmese = "စဉ်ဆက်မပြတ် ကြိုးစားခြင်းသည် စွမ်းအားဖြစ်သည်",
        meaningEnglish = "Perseverance pays off"
    ),
    DailyProverb(
        japanese = "七転び八起き",
        reading = "ななころびやおき",
        meaningBurmese = "၇ ကြိမ်လဲလျှင် ၈ ကြိမ်မြောက် ပြန်ထလော့",
        meaningEnglish = "Fall down 7 times, stand up 8"
    ),
    DailyProverb(
        japanese = "一期一会",
        reading = "いちごいちえ",
        meaningBurmese = "ဘဝတွင် တစ်ကြိမ်သာ ဆုံတွေ့ရမည့် တန်ဖိုးရှိသော အခွင့်အရေး",
        meaningEnglish = "Treasure every unrepeatable encounter"
    ),
    DailyProverb(
        japanese = "千里の行も足下に始まる",
        reading = "せんりのこうもあしもとにはじまる",
        meaningBurmese = "မိုင်ပေါင်းတစ်ထောင် ခရီးရှည်သည်လည်း ခြေတစ်လှမ်းမှ စတင်သည်",
        meaningEnglish = "A journey of a thousand miles begins with a single step"
    )
)

private enum class LessonFilterTab(val title: String) {
    ALL("All Lessons"),
    IN_PROGRESS("In Progress"),
    MASTERED("Mastered")
}

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
    var lessonSearchQuery by remember { mutableStateOf("") }
    var selectedLessonTab by remember { mutableStateOf(LessonFilterTab.ALL) }

    val currentHour = remember { Calendar.getInstance().get(Calendar.HOUR_OF_DAY) }
    val timeGreeting = remember(currentHour) {
        when (currentHour) {
            in 5..11 -> Pair("おはようございます", "Good Morning • မင်္ဂလာနံနက်ခင်းပါ")
            in 12..17 -> Pair("こんにちは", "Good Afternoon • မင်္ဂလာနေ့လယ်ခင်းပါ")
            else -> Pair("こんばんは", "Good Evening • မင်္ဂလာညနေခင်းပါ")
        }
    }

    val dayOfYear = remember { Calendar.getInstance().get(Calendar.DAY_OF_YEAR) }
    val todayProverb = remember(dayOfYear) {
        DAILY_PROVERBS[dayOfYear % DAILY_PROVERBS.size]
    }

    val wordOfTheDay = remember(allCards, dayOfYear) {
        if (allCards.isNotEmpty()) {
            val nonCustom = allCards.filter { !it.isCustom }
            if (nonCustom.isNotEmpty()) nonCustom[dayOfYear % nonCustom.size]
            else allCards.first()
        } else null
    }

    val filteredLessons = remember(lessonProgressList, lessonSearchQuery, selectedLessonTab) {
        lessonProgressList.filter { lesson ->
            val matchesSearch = lessonSearchQuery.isBlank() ||
                lesson.lessonTitle.contains(lessonSearchQuery, ignoreCase = true) ||
                "Lesson ${lesson.lessonNumber}".contains(lessonSearchQuery, ignoreCase = true)

            val isComplete = lesson.totalCards > 0 && lesson.masteredCards >= lesson.totalCards
            val matchesTab = when (selectedLessonTab) {
                LessonFilterTab.ALL -> true
                LessonFilterTab.IN_PROGRESS -> !isComplete && (lesson.masteredCards > 0 || lesson.lessonNumber == 1)
                LessonFilterTab.MASTERED -> isComplete
            }

            matchesSearch && matchesTab
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 96.dp, top = 20.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. MINIMALIST HERO DASHBOARD CARD
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_greeting_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    // Header: Greeting + Streak
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = timeGreeting.first,
                                style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${profile?.name ?: "Learner"} • Level ${profile?.level ?: 1} (${profile?.targetJlptLevel ?: "JLPT N3"})",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = StreakOrange.copy(alpha = 0.1f),
                            border = BorderStroke(1.dp, StreakOrange.copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "🔥 ${profile?.currentStreak ?: 1} Days",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = StreakOrange
                                )
                            }
                        }
                    }

                    // SRS / Study Primary Call to Action
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (dueCount > 0) JapaneseCrimson else PolishPrimary
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = if (dueCount > 0) "Spaced Repetition Review" else "Ready to Learn",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = if (dueCount > 0) "$dueCount cards scheduled for review" else "$masteredCount of $totalCount cards mastered",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Button(
                                onClick = {
                                    if (dueCount > 0) {
                                        vocabViewModel.setFilter(VocabFilterType.DUE_REVIEWS)
                                    } else {
                                        vocabViewModel.setFilter(VocabFilterType.ALL)
                                    }
                                    onStartStudy(allCards)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = if (dueCount > 0) JapaneseCrimson else PolishPrimary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                                modifier = Modifier.testTag("start_srs_study_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (dueCount > 0) "Review ($dueCount)" else "Study Now",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    // Progress Overview Bar
                    val dailyGoal = profile?.dailyGoal ?: 30
                    val goalCompleted = masteredCount.coerceAtMost(dailyGoal)
                    val percent = if (dailyGoal > 0) ((goalCompleted.toFloat() / dailyGoal.toFloat()) * 100).toInt() else 100

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Daily Goal: $goalCompleted / $dailyGoal words",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$percent%",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = if (percent >= 100) MasteredGreen else MaterialTheme.colorScheme.primary
                            )
                        }
                        LinearProgressIndicator(
                            progress = { (percent / 100f).coerceIn(0.05f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(50)),
                            color = if (percent >= 100) MasteredGreen else MaterialTheme.colorScheme.primary,
                            trackColor = PolishOutlineVariant
                        )
                    }
                }
            }
        }

        // 2. QUICK ACTION SHORTCUTS (Minimalist Clean Cards)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickStatCard(
                    title = "Saved",
                    count = "Starred",
                    color = SakuraPinkDark,
                    icon = Icons.Default.Bookmark,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToBrowse(VocabFilterType.BOOKMARKED) }
                )

                QuickStatCard(
                    title = "Review",
                    count = "Weak",
                    color = WeakOrange,
                    icon = Icons.Default.Warning,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigateToBrowse(VocabFilterType.WEAK_CARDS) }
                )

                QuickStatCard(
                    title = "Quiz",
                    count = "Arena",
                    color = ReviewBlue,
                    icon = Icons.Default.Timer,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToQuiz
                )

                QuickStatCard(
                    title = "Add",
                    count = "Custom",
                    color = PolishPrimary,
                    icon = Icons.Default.Add,
                    modifier = Modifier.weight(1f),
                    onClick = onOpenAddCustomCard
                )
            }
        }

        // 3. WORD OF THE DAY SPOTLIGHT CARD (Minimalist & Roomy)
        wordOfTheDay?.let { card ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("word_of_the_day_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
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
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = PolishPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Word of the Day • 今日の一言",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = PolishPrimary
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = { vocabViewModel.toggleBookmark(card) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = "Bookmark",
                                        tint = if (card.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.outline,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { vocabViewModel.speakJapanese(card.kanji) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "Pronounce",
                                        tint = PolishPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = card.reading,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PolishPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = card.kanji,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = card.meaningBurmese,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                            ) {
                                Text(
                                    text = card.partOfSpeech.ifBlank { "N3 Vocab" },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. DAILY JAPANESE PROVERB (Clean minimalist tile)
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PolishPrimary.copy(alpha = 0.08f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = PolishPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = todayProverb.japanese,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "(${todayProverb.reading})",
                                style = MaterialTheme.typography.bodySmall,
                                color = PolishPrimary,
                                fontSize = 11.sp
                            )
                        }
                        Text(
                            text = todayProverb.meaningBurmese,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = { vocabViewModel.speakJapanese(todayProverb.japanese) },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Speak Proverb",
                            tint = PolishPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // 5. LESSON DIRECTORY SECTION (Structured, uncluttered)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "JLPT N3 Lessons",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$totalCount Words Total",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                // Search Bar for Lessons
                OutlinedTextField(
                    value = lessonSearchQuery,
                    onValueChange = { lessonSearchQuery = it },
                    placeholder = { Text("Filter lessons by title or number...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Lessons",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    trailingIcon = {
                        if (lessonSearchQuery.isNotBlank()) {
                            IconButton(onClick = { lessonSearchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear Search",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )

                // Filter Tabs (All, In Progress, Mastered)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LessonFilterTab.values().forEach { tab ->
                        FilterChip(
                            selected = selectedLessonTab == tab,
                            onClick = { selectedLessonTab = tab },
                            label = { Text(tab.title, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PolishPrimaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }
        }

        // 6. LESSON PROGRESS CARDS
        items(filteredLessons) { lesson ->
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

        if (filteredLessons.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No lessons match your search query.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Text(
                text = count,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
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
    val isCompleted = lesson.totalCards > 0 && lesson.masteredCards >= lesson.totalCards

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onViewCardsClick() }
            .testTag("lesson_card_${lesson.lessonNumber}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(
            1.dp,
            if (isCompleted) MasteredGreen.copy(alpha = 0.35f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
        )
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
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (isCompleted) MasteredGreen.copy(alpha = 0.12f)
                                else PolishPrimary.copy(alpha = 0.08f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (lesson.lessonNumber == 999) "★" else "${lesson.lessonNumber}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (isCompleted) MasteredGreen else PolishPrimary
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = if (lesson.lessonNumber == 999) "Personalized Cards" else lesson.lessonTitle,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${lesson.totalCards} Words • ${lesson.masteredCards} Mastered (${(progress * 100).toInt()}%)",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                FilledTonalButton(
                    onClick = onStudyLessonClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = if (isCompleted) MasteredGreen.copy(alpha = 0.12f) else PolishPrimaryContainer,
                        contentColor = if (isCompleted) MasteredGreen else MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isCompleted) "Review" else "Study",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Progress Bar
            LinearProgressIndicator(
                progress = { progress.coerceIn(0.02f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(50)),
                color = if (isCompleted) MasteredGreen else MaterialTheme.colorScheme.primary,
                trackColor = PolishOutlineVariant
            )
        }
    }
}

