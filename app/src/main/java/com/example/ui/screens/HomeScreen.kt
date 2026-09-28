package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Translate
import androidx.compose.ui.platform.LocalContext
import com.example.ui.components.SrsScheduleTrackingSheet
import com.example.ui.util.StudyActionHelper
import com.example.ui.viewmodel.FlashcardStudyMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Badge
import com.example.data.model.LessonProgress
import com.example.data.model.VocabCard
import com.example.ui.viewmodel.KanjiCategoryMastery
import com.example.ui.components.BadgeDetailDialog
import com.example.ui.components.DailyGoalProgressRing
import com.example.ui.components.SetDailyGoalDialog
import com.example.data.util.CollocationData
import com.example.ui.theme.JapaneseFontFamily
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.MasteredGreen
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
    ),
    DailyProverb(
        japanese = "初心忘るべからず",
        reading = "しょしんわするべからず",
        meaningBurmese = "အစဦး ရည်မှန်းချက်နှင့် စိတ်အားထက်သန်မှုကို မမေ့ပါနှင့်",
        meaningEnglish = "Never forget your beginner's spirit"
    )
)

private enum class LessonFilterTab(val title: String, val burmeseTitle: String) {
    ALL("All Lessons", "အားလုံး"),
    IN_PROGRESS("In Progress", "လေ့လာဆဲ"),
    MASTERED("Mastered", "ကျွမ်းကျင်")
}

private enum class CurriculumViewTab(val title: String, val burmeseTitle: String) {
    LESSONS("Lessons", "သင်ခန်းစာ"),
    CATEGORIES("Categories", "ကဏ္ဍများ")
}

@Composable
fun HomeScreen(
    vocabViewModel: VocabViewModel,
    onStartStudy: (List<VocabCard>) -> Unit,
    onNavigateToBrowse: (VocabFilterType) -> Unit,
    onNavigateToQuiz: () -> Unit,
    onOpenAddCustomCard: () -> Unit,
    onNavigateToStats: () -> Unit = {},
    onNavigateToFlashcard: () -> Unit = {},
    onNavigateToKanjiProgress: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val profile by vocabViewModel.userProfile.collectAsState()
    val totalCount by vocabViewModel.totalCardCount.collectAsState()
    val masteredCount by vocabViewModel.masteredCount.collectAsState()
    val dueCount by vocabViewModel.dueCount.collectAsState()
    val lessonProgressList by vocabViewModel.lessonProgressList.collectAsState()
    val allCards by vocabViewModel.filteredCards.collectAsState()
    val badges by vocabViewModel.allBadges.collectAsState()
    val categoryStats by vocabViewModel.kanjiCategoryStats.collectAsState()
    val todayReviewedCount by vocabViewModel.todayReviewedCardsCount.collectAsState()
    val context = LocalContext.current

    var showSetDailyGoalDialog by remember { mutableStateOf(false) }
    var showSrsSheet by remember { mutableStateOf(false) }
    var selectedBadgeForDetail by remember { mutableStateOf<Badge?>(null) }
    var lessonSearchQuery by remember { mutableStateOf("") }
    var selectedLessonTab by remember { mutableStateOf(LessonFilterTab.ALL) }
    var selectedCurriculumTab by remember { mutableStateOf(CurriculumViewTab.LESSONS) }
    var selectedDailyTab by remember { mutableStateOf(0) } // 0: Word of the Day, 1: Daily Proverb

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
            .background(MaterialTheme.colorScheme.background)
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 96.dp, top = 12.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. HERO LEARNING DASHBOARD (Clean, spacious, high-contrast)
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
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Top Header: Greeting, Level & Streak
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = timeGreeting.first,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${profile?.name ?: "Learner"} • Level ${profile?.level ?: 1} (${profile?.targetJlptLevel ?: "JLPT N3"})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = StreakOrange.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, StreakOrange.copy(alpha = 0.35f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .clickable { showSetDailyGoalDialog = true }
                                .testTag("hero_streak_pill")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = "Streak",
                                    tint = StreakOrange,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "${profile?.currentStreak ?: 1} Days",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = StreakOrange
                                )
                            }
                        }
                    }

                    // Unified SRS & Goal Section
                    val dailyGoal = profile?.dailyGoal ?: 15
                    val ringProgress = if (dailyGoal > 0) todayReviewedCount.toFloat() / dailyGoal.toFloat() else 0f
                    val isGoalAchieved = todayReviewedCount >= dailyGoal
                    val remainingCards = (dailyGoal - todayReviewedCount).coerceAtLeast(0)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dashboard_progress_ring_section"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DailyGoalProgressRing(
                            progress = ringProgress,
                            reviewedCount = todayReviewedCount,
                            targetGoal = dailyGoal,
                            ringSize = 74.dp,
                            strokeWidth = 8.dp
                        )

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (dueCount > 0) "$dueCount Cards Due" else "All Caught Up! ✨",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (dueCount > 0) MaterialTheme.colorScheme.primary else MasteredGreen
                                )

                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .clickable { showSetDailyGoalDialog = true }
                                        .testTag("set_target_btn")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Tune,
                                            contentDescription = "Set Target",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = "Goal: $dailyGoal",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 11.sp
                                            ),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }

                            Text(
                                text = if (isGoalAchieved) "Daily goal achieved! Streak protected 🔥"
                                else "$remainingCards cards left to reach today's target",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Mobile-friendly Study & Schedule Buttons (Clean 2-tier layout)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                vocabViewModel.startDueCardsStudySession(
                                    mode = FlashcardStudyMode.JP_TO_MY,
                                    onSuccess = { cards -> onStartStudy(cards) }
                                )
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("start_srs_study_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (dueCount > 0) "Review Due Cards ($dueCount)" else "Start Today's Study • လေ့လာမည်",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilledTonalButton(
                                onClick = onNavigateToFlashcard,
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("hero_open_flashcards_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Flip,
                                    contentDescription = "3D Cards",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "3D Flashcard",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.5.sp
                                )
                            }

                            OutlinedButton(
                                onClick = { showSrsSheet = true },
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("view_srs_schedule_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = "SM-2 Schedule",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "အချိန်ဇယား (SRS)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. UNIFIED STUDY MODES (Clean 2x2 Grid - Uncluttered, Accessible & Clear)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Study Modes • လေ့လာမှုကဏ္ဍများ",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(
                        onClick = onOpenAddCustomCard,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Add Card",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StudyModeTile(
                        title = "3D Flashcards",
                        subtitle = "တွဲလုံးပါ ကတ်လှန်",
                        badge = "3D Flip",
                        icon = Icons.Default.Flip,
                        tintColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f),
                        testTag = "open_flashcard_flip_btn",
                        onClick = onNavigateToFlashcard
                    )

                    StudyModeTile(
                        title = "တွဲလုံး လေ့ကျင့်ခန်း",
                        subtitle = "Collocations (၂၅+)",
                        badge = "အထူး",
                        icon = Icons.Default.AutoAwesome,
                        tintColor = Color(0xFF8E24AA),
                        modifier = Modifier.weight(1f),
                        testTag = "open_collocations_mode_btn",
                        onClick = {
                            vocabViewModel.startCollocationStudySession { cards ->
                                onStartStudy(cards)
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StudyModeTile(
                        title = "Quiz Arena",
                        subtitle = "ဉာဏ်စမ်းစစ်ဆေး",
                        icon = Icons.Default.Quiz,
                        tintColor = ReviewBlue,
                        modifier = Modifier.weight(1f),
                        testTag = "open_quiz_arena_btn",
                        onClick = onNavigateToQuiz
                    )

                    StudyModeTile(
                        title = "Kanji Goal (650)",
                        subtitle = "ကန်ဂျီပန်းတိုင်",
                        icon = Icons.Default.PieChart,
                        tintColor = MasteredGreen,
                        modifier = Modifier.weight(1f),
                        testTag = "home_kanji_progress_banner",
                        onClick = onNavigateToKanjiProgress
                    )
                }
            }
        }

        // 2b. D3 / Recharts Progress Dashboard Quick Banner (Mobile responsive)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { onNavigateToDashboard() }
                    .testTag("home_d3_dashboard_banner"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, JapaneseCrimson.copy(alpha = 0.22f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    JapaneseCrimson.copy(alpha = 0.08f),
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.04f)
                                )
                            )
                        )
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = JapaneseCrimson,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.ShowChart,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Progress Dashboard",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = JapaneseCrimson.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "D3 Chart",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = JapaneseCrimson,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }

                            Text(
                                text = "Learned Kanji over time & streak matrix",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Open Dashboard",
                        tint = JapaneseCrimson,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // 4. DAILY SPOTLIGHT (Clean Segmented Tab for Word of the Day & Proverb)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("word_of_the_day_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Segmented Tab Switcher
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            .padding(3.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(9.dp))
                                .clickable { selectedDailyTab = 0 },
                            color = if (selectedDailyTab == 0) MaterialTheme.colorScheme.surface else Color.Transparent,
                            shadowElevation = if (selectedDailyTab == 0) 1.dp else 0.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = if (selectedDailyTab == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Word of the Day",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (selectedDailyTab == 0) FontWeight.Bold else FontWeight.Medium,
                                        fontSize = 11.sp
                                    ),
                                    color = if (selectedDailyTab == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(9.dp))
                                .clickable { selectedDailyTab = 1 },
                            color = if (selectedDailyTab == 1) MaterialTheme.colorScheme.surface else Color.Transparent,
                            shadowElevation = if (selectedDailyTab == 1) 1.dp else 0.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatQuote,
                                    contentDescription = null,
                                    tint = if (selectedDailyTab == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Daily Proverb",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (selectedDailyTab == 1) FontWeight.Bold else FontWeight.Medium,
                                        fontSize = 11.sp
                                    ),
                                    color = if (selectedDailyTab == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Content display based on active tab
                    if (selectedDailyTab == 0) {
                        wordOfTheDay?.let { card ->
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text(
                                            text = card.reading,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = JapaneseFontFamily),
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = card.kanji,
                                            style = MaterialTheme.typography.headlineMedium.copy(
                                                fontFamily = JapaneseFontFamily,
                                                fontSize = 28.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = card.meaningBurmese,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.secondaryContainer,
                                        modifier = Modifier.padding(start = 8.dp)
                                    ) {
                                        Text(
                                            text = card.partOfSpeech.ifBlank { "N3 Vocab" },
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                // Featured Collocation (တွဲလုံး) for Word of the Day
                                val featuredCollocation = remember(card.kanji) {
                                    CollocationData.getCollocationsForWord(card.kanji).firstOrNull()
                                }
                                if (featuredCollocation != null) {
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.45f),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.25f)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 10.dp, vertical = 6.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = "တွဲလုံး: ${featuredCollocation.phraseJapanese} (${featuredCollocation.reading})",
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontFamily = JapaneseFontFamily,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                Text(
                                                    text = featuredCollocation.meaningBurmese,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                            IconButton(
                                                onClick = { vocabViewModel.speakJapanese(featuredCollocation.phraseJapanese) },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                                    contentDescription = "Speak Collocation",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(15.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                // Quick Action Pills for Word of the Day
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp)
                                        .horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // 3D Flashcard Quick Action
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { onNavigateToFlashcard() }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Flip,
                                                contentDescription = "3D Card",
                                                tint = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Text(
                                                text = "3D Flashcard",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    // Audio
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { vocabViewModel.speakJapanese(card.kanji) }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                                contentDescription = "Speak",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Text(
                                                text = "Audio",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }

                                    // Copy
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                StudyActionHelper.copyToClipboard(
                                                    context = context,
                                                    text = "${card.kanji}【${card.reading}】: ${card.meaningBurmese}",
                                                    label = "Card",
                                                    toastMessage = "${card.kanji} ကို Copy ကူးပြီးပါပြီ ✓"
                                                )
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ContentCopy,
                                                contentDescription = "Copy",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Text(
                                                text = "Copy",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }

                                    // Google Translate
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                StudyActionHelper.openGoogleTranslate(
                                                    context = context,
                                                    text = card.kanji,
                                                    sourceLang = "ja",
                                                    targetLang = "my"
                                                )
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Translate,
                                                contentDescription = "Translate",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Text(
                                                text = "Translate",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }

                                    // Jisho.org
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = JapaneseCrimson.copy(alpha = 0.12f),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable {
                                                StudyActionHelper.openJisho(context = context, query = card.kanji)
                                            }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.OpenInNew,
                                                contentDescription = "Jisho",
                                                tint = JapaneseCrimson,
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Text(
                                                text = "jisho.org",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = JapaneseCrimson
                                            )
                                        }
                                    }

                                    // Bookmark
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (card.isBookmarked) SakuraPinkDark.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { vocabViewModel.toggleBookmark(card) }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                                contentDescription = "Bookmark",
                                                tint = if (card.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Text(
                                                text = if (card.isBookmarked) "Saved" else "Save",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (card.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // Daily Proverb display with enhanced literary layout
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "「${todayProverb.japanese}」",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontFamily = JapaneseFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 19.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = todayProverb.reading,
                                style = MaterialTheme.typography.bodySmall.copy(fontFamily = JapaneseFontFamily),
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = todayProverb.meaningBurmese,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = todayProverb.meaningEnglish,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )

                            // Quick Actions for Proverb
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Speak
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { vocabViewModel.speakJapanese(todayProverb.japanese) }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Speak",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "Audio",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                // Copy
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            StudyActionHelper.copyToClipboard(
                                                context = context,
                                                text = "${todayProverb.japanese} (${todayProverb.reading}) - ${todayProverb.meaningBurmese}",
                                                label = "Proverb",
                                                toastMessage = "စကားပုံကို Copy ကူးပြီးပါပြီ ✓"
                                            )
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Text(
                                            text = "Copy",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Translate
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            StudyActionHelper.openGoogleTranslate(
                                                context = context,
                                                text = todayProverb.japanese,
                                                sourceLang = "ja",
                                                targetLang = "my"
                                            )
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Translate,
                                            contentDescription = "Translate",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Text(
                                            text = "Translate",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. CURRICULUM SECTION (Clean Segmented Switcher: Lessons vs Categories)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Segmented Switcher: Lessons vs Kanji Categories
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    CurriculumViewTab.values().forEach { tab ->
                        val isSelected = selectedCurriculumTab == tab
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { selectedCurriculumTab = tab },
                            color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
                            shadowElevation = if (isSelected) 1.dp else 0.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${tab.title} (${tab.burmeseTitle})",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        fontSize = 12.sp
                                    ),
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                if (selectedCurriculumTab == CurriculumViewTab.LESSONS) {
                    // Search Bar for Lessons
                    OutlinedTextField(
                        value = lessonSearchQuery,
                        onValueChange = { lessonSearchQuery = it },
                        placeholder = {
                            Text(
                                "Search lessons (e.g. Lesson 1, Society)...",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Lessons",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        trailingIcon = {
                            if (lessonSearchQuery.isNotBlank()) {
                                IconButton(onClick = { lessonSearchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear Search",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        singleLine = true
                    )

                    // Filter Chips (All, In Progress, Mastered)
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(LessonFilterTab.values()) { tab ->
                            FilterChip(
                                selected = selectedLessonTab == tab,
                                onClick = { selectedLessonTab = tab },
                                label = {
                                    Text(
                                        text = "${tab.title} (${tab.burmeseTitle})",
                                        fontSize = 12.sp,
                                        fontWeight = if (selectedLessonTab == tab) FontWeight.Bold else FontWeight.Medium
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }
                } else {
                    // Categories Section Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Kanji Categories (${categoryStats.size})",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "ကဏ္ဍအလိုက် စနစ်တကျ လေ့လာရန်",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        TextButton(
                            onClick = onNavigateToStats,
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "All Stats →",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        if (selectedCurriculumTab == CurriculumViewTab.CATEGORIES) {
            items(categoryStats, key = { it.categoryId }) { cat ->
                CategoryListCard(
                    category = cat,
                    onStudyCategory = {
                        val rangeNums = if (cat.lessonRange.contains("-")) {
                            val parts = cat.lessonRange.replace("Lessons ", "").replace("Lesson ", "").split("-")
                            val start = parts[0].trim().toIntOrNull() ?: 1
                            val end = parts.getOrNull(1)?.trim()?.toIntOrNull() ?: start
                            (start..end).toSet()
                        } else {
                            val num = cat.lessonRange.replace("Lesson ", "").trim().toIntOrNull() ?: 1
                            setOf(num)
                        }
                        val matchedCards = allCards.filter { it.lessonNumber in rangeNums }
                        if (matchedCards.isNotEmpty()) {
                            onStartStudy(matchedCards)
                        }
                    }
                )
            }
        } else {
            // Lessons list
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
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
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

    if (showSetDailyGoalDialog) {
        SetDailyGoalDialog(
            currentGoal = profile?.dailyGoal ?: 15,
            onDismiss = { showSetDailyGoalDialog = false },
            onSaveGoal = { newGoal ->
                vocabViewModel.updateDailyGoal(newGoal)
                showSetDailyGoalDialog = false
            }
        )
    }

    if (showSrsSheet) {
        SrsScheduleTrackingSheet(
            vocabViewModel = vocabViewModel,
            onDismiss = { showSrsSheet = false },
            onStartReview = {
                showSrsSheet = false
                onStartStudy(allCards)
            }
        )
    }
}

@Composable
private fun StudyModeTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    tintColor: Color,
    modifier: Modifier = Modifier,
    badge: String? = null,
    testTag: String = "",
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, tintColor.copy(alpha = 0.22f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(tintColor.copy(alpha = 0.14f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = tintColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                if (badge != null) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = tintColor.copy(alpha = 0.16f)
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                            fontWeight = FontWeight.Bold,
                            color = tintColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 13.5.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
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
            .clip(RoundedCornerShape(16.dp))
            .clickable { onViewCardsClick() }
            .testTag("lesson_card_${lesson.lessonNumber}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(
            1.dp,
            if (isCompleted) MasteredGreen.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
        ),
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                if (isCompleted) MasteredGreen.copy(alpha = 0.15f)
                                else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (lesson.lessonNumber == 999) "★" else "${lesson.lessonNumber}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = if (isCompleted) MasteredGreen else MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = if (lesson.lessonNumber == 999) "Personalized Cards" else lesson.lessonTitle,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${lesson.totalCards} Words • ${lesson.masteredCards} Mastered (${(progress * 100).toInt()}%)",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Button(
                    onClick = onStudyLessonClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCompleted) MasteredGreen.copy(alpha = 0.18f) else MaterialTheme.colorScheme.primary,
                        contentColor = if (isCompleted) MasteredGreen else MaterialTheme.colorScheme.onPrimary
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.PlayArrow,
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
                    .height(5.dp)
                    .clip(RoundedCornerShape(50)),
                color = if (isCompleted) MasteredGreen else MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
private fun CategoryListCard(
    category: KanjiCategoryMastery,
    onStudyCategory: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onStudyCategory() }
            .testTag("home_category_card_${category.categoryId}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = category.iconEmoji, fontSize = 20.sp)
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = category.categoryName,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${category.masteryPercent.toInt()}%",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        ),
                        color = if (category.masteryPercent >= 80) MasteredGreen else MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "${category.categoryJapanese} • ${category.lessonRange} (${category.totalCount} cards)",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                LinearProgressIndicator(
                    progress = { (category.masteryPercent / 100f).coerceIn(0.04f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(50)),
                    color = if (category.masteryPercent >= 80) MasteredGreen else MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            IconButton(
                onClick = onStudyCategory,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Study Category",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
