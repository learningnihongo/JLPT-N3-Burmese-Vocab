package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.StreakOrange
import com.example.ui.theme.WeakOrange
import com.example.ui.viewmodel.QuizQuestion
import com.example.ui.viewmodel.QuizState
import com.example.ui.viewmodel.QuizType
import com.example.ui.viewmodel.QuizViewModel

@Composable
fun QuizScreen(quizViewModel: QuizViewModel) {
    val state by quizViewModel.quizState.collectAsState()
    val history by quizViewModel.quizHistory.collectAsState()
    val isSpeaking by quizViewModel.isTtsSpeaking.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            state.isQuizActive -> {
                ActiveQuizView(
                    state = state,
                    isSpeaking = isSpeaking,
                    onSelectOption = { quizViewModel.selectOption(it) },
                    onUseFiftyFifty = { quizViewModel.useFiftyFifty() },
                    onSwapQuestion = { quizViewModel.swapCurrentQuestion() },
                    onToggleClue = { quizViewModel.toggleClue() },
                    onTogglePause = { quizViewModel.togglePause() },
                    onToggleAudioSpeed = { quizViewModel.toggleAudioSpeed() },
                    onToggleBookmark = { quizViewModel.toggleCurrentCardBookmark() },
                    onNext = { quizViewModel.nextQuestion() },
                    onSpeak = { text, rate -> quizViewModel.speak(text, rate) },
                    onExit = { quizViewModel.exitQuiz() }
                )
            }
            state.isFinished -> {
                QuizResultView(
                    state = state,
                    onRestart = {
                        quizViewModel.startQuiz(
                            quizType = state.quizType,
                            lessonFilter = state.lessonFilter,
                            questionCount = state.questions.size
                        )
                    },
                    onRetryMissed = { quizViewModel.retryMissedQuestions() },
                    onBookmarkAllMissed = { quizViewModel.bookmarkAllMissedCards() },
                    onToggleBookmark = { id, current -> quizViewModel.toggleBookmarkInReview(id, current) },
                    onSpeak = { text -> quizViewModel.speak(text) },
                    onExit = { quizViewModel.exitQuiz() }
                )
            }
            else -> {
                QuizLobbyView(
                    history = history,
                    onStartQuiz = { type, lesson, count ->
                        quizViewModel.startQuiz(type, lesson, count)
                    }
                )
            }
        }
    }
}

@Composable
private fun QuizLobbyView(
    history: List<QuizHistory>,
    onStartQuiz: (QuizType, Int?, Int) -> Unit
) {
    var selectedLessonFilter by remember { mutableStateOf<Int?>(null) } // null = All, -2 = Bookmarked, -1 = Weak, 1..29 = Lesson
    var selectedQuestionCount by remember { mutableIntStateOf(10) }
    var showHistoryDialog by remember { mutableStateOf(false) }

    val questionCountOptions = listOf(5, 10, 15, 20, 25)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("quiz_lobby_screen"),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Arena Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
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
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(JapaneseCrimson.copy(alpha = 0.18f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.EmojiEvents,
                                        contentDescription = null,
                                        tint = JapaneseCrimson,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "JLPT N3 Quiz Arena",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Interactive Mastery & Spaced Testing",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            if (history.isNotEmpty()) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable { showHistoryDialog = !showHistoryDialog }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.History,
                                            contentDescription = "History",
                                            tint = JapaneseIndigo,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "${history.size} Runs",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = JapaneseIndigo
                                        )
                                    }
                                }
                            }
                        }

                        // Quick Recommendation Pill
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                            border = BorderStroke(1.dp, StreakOrange.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = null,
                                        tint = StreakOrange,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Column {
                                        Text(
                                            text = "Recommended Daily Challenge",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = StreakOrange
                                        )
                                        Text(
                                            text = "True / False Blitz (○/× Fast Recall)",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                Button(
                                    onClick = { onStartQuiz(QuizType.TRUE_FALSE_DRILL, selectedLessonFilter, selectedQuestionCount) },
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = StreakOrange)
                                ) {
                                    Text("Quick Play", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Question Count Selector
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Question Count",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    questionCountOptions.forEach { count ->
                        val isSelected = selectedQuestionCount == count
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) JapaneseCrimson else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) JapaneseCrimson else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { selectedQuestionCount = count }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "$count Qs",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        // Scope / Filter Chips (All N3, Starred, Weak, Lessons)
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
                            imageVector = Icons.Default.FilterList,
                            contentDescription = null,
                            tint = JapaneseIndigo,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Study Scope & Lesson Filter",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    if (selectedLessonFilter != null) {
                        Text(
                            text = "Reset Scope",
                            style = MaterialTheme.typography.labelSmall,
                            color = JapaneseCrimson,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { selectedLessonFilter = null }
                        )
                    }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 2.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedLessonFilter == null,
                            onClick = { selectedLessonFilter = null },
                            label = { Text("All JLPT N3", fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = JapaneseIndigo,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedLessonFilter == -2,
                            onClick = { selectedLessonFilter = -2 },
                            label = { Text("⭐ Starred Only", fontWeight = FontWeight.Bold) },
                            leadingIcon = {
                                Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = StreakOrange,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedLessonFilter == -1,
                            onClick = { selectedLessonFilter = -1 },
                            label = { Text("⚠️ Weak Words Only", fontWeight = FontWeight.Bold) },
                            leadingIcon = {
                                Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(16.dp))
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = WeakOrange,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    items(29) { idx ->
                        val lessonNum = idx + 1
                        FilterChip(
                            selected = selectedLessonFilter == lessonNum,
                            onClick = { selectedLessonFilter = lessonNum },
                            label = { Text("Lesson $lessonNum", fontWeight = FontWeight.Medium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = JapaneseCrimson,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Quiz Modes Header
        item {
            Text(
                text = "Select Challenge Arena",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Grid of 9 Challenge Modes
        items(QuizType.values()) { type ->
            QuizModeCard(
                type = type,
                filterLesson = selectedLessonFilter,
                questionCount = selectedQuestionCount,
                onClick = { onStartQuiz(type, selectedLessonFilter, selectedQuestionCount) }
            )
        }

        // Recent History Section
        if (history.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Arena Records (${history.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            items(history.take(5)) { item ->
                QuizHistoryItem(history = item)
            }
        }
    }
}

@Composable
private fun QuizModeCard(
    type: QuizType,
    filterLesson: Int?,
    questionCount: Int,
    onClick: () -> Unit
) {
    val (icon, color) = when (type) {
        QuizType.KANJI_MASTERY -> Icons.Default.Psychology to JapaneseCrimson
        QuizType.KANJI_TO_MEANING -> Icons.Default.Translate to JapaneseIndigo
        QuizType.MEANING_TO_KANJI -> Icons.Default.School to ReviewBlue
        QuizType.READING_CHALLENGE -> Icons.Default.MenuBook to WeakOrange
        QuizType.LISTENING_CHALLENGE -> Icons.Default.Headphones to JapaneseIndigo
        QuizType.SENTENCE_CLOZE -> Icons.Default.MenuBook to MasteredGreen
        QuizType.TRUE_FALSE_DRILL -> Icons.Default.Bolt to StreakOrange
        QuizType.SPEED_TEST -> Icons.Default.Speed to JapaneseCrimson
        QuizType.BOOKMARKED_DRILL -> Icons.Default.Star to StreakOrange
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .testTag("quiz_mode_${type.name.lowercase()}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
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
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = type.displayName,
                    tint = color,
                    modifier = Modifier.size(26.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = type.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = color.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = type.badge,
                            color = color,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = type.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Start",
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ActiveQuizView(
    state: QuizState,
    isSpeaking: Boolean,
    onSelectOption: (Int) -> Unit,
    onUseFiftyFifty: () -> Unit,
    onSwapQuestion: () -> Unit,
    onToggleClue: () -> Unit,
    onTogglePause: () -> Unit,
    onToggleAudioSpeed: () -> Unit,
    onToggleBookmark: () -> Unit,
    onNext: () -> Unit,
    onSpeak: (String, Float?) -> Unit,
    onExit: () -> Unit
) {
    val currentQuestion = state.questions.getOrNull(state.currentIndex) ?: return
    val progress by animateFloatAsState(
        targetValue = (state.currentIndex + 1).toFloat() / state.questions.size.toFloat(),
        label = "quiz_progress"
    )
    val maxTimerSec = if (state.quizType.isSpeed) 10 else 15
    val timerFraction = (state.remainingSeconds.toFloat() / maxTimerSec.toFloat()).coerceIn(0f, 1f)

    // Sound wave pulse animation when TTS is playing
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = if (isSpeaking) 1.25f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("active_quiz_view"),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top status row (Exit, Counter, Pause, Streak Combo, Score)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(onClick = onExit, modifier = Modifier.size(36.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Exit Quiz", tint = MaterialTheme.colorScheme.onSurface)
                        }

                        IconButton(onClick = onTogglePause, modifier = Modifier.size(36.dp)) {
                            Icon(
                                imageVector = if (state.isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                                contentDescription = if (state.isPaused) "Resume" else "Pause",
                                tint = if (state.isPaused) StreakOrange else MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.lessonTitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = JapaneseCrimson,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Question ${state.currentIndex + 1} / ${state.questions.size}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Score & Streak Badges
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Combo Multiplier Badge
                        if (state.comboMultiplier > 1) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = JapaneseCrimson
                            ) {
                                Text(
                                    text = "${state.comboMultiplier}x XP",
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Streak Badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = WeakOrange.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = WeakOrange,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "${state.currentStreak}",
                                    color = WeakOrange,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        // Score Badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MasteredGreen.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = MasteredGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "${state.score}",
                                    color = MasteredGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                // Question Progress Indicator
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = JapaneseIndigo,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )

                // Timer Countdown Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (state.isPaused) Icons.Default.Timer else Icons.Default.Speed,
                            contentDescription = null,
                            tint = if (state.isPaused) StreakOrange else if (state.remainingSeconds <= 3) JapaneseCrimson else MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = if (state.isPaused) "PAUSED" else "Time Remaining",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (state.isPaused) FontWeight.Bold else FontWeight.Normal,
                            color = if (state.isPaused) StreakOrange else MaterialTheme.colorScheme.outline
                        )
                    }
                    Text(
                        text = "${state.remainingSeconds}s",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (state.remainingSeconds <= 3) JapaneseCrimson else MaterialTheme.colorScheme.primary
                    )
                }

                LinearProgressIndicator(
                    progress = { timerFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = if (state.remainingSeconds <= 3) JapaneseCrimson else if (state.remainingSeconds <= 6) WeakOrange else MasteredGreen,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )

                // Lifelines Bar (50:50, Radical Hint, Swap Question, Audio Speed)
                if (!state.isAnswerSubmitted) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Audio Speed Switcher (1.0x / 0.75x Slow)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onToggleAudioSpeed() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(13.dp), tint = JapaneseIndigo)
                                Text(
                                    text = if (state.speechRate == 1.0f) "1.0x Audio" else "0.75x Slow",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = JapaneseIndigo
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // 50:50 Lifeline Button
                            if (!currentQuestion.isTrueFalse) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (state.hasUsedFiftyFifty) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) else JapaneseIndigo.copy(alpha = 0.12f),
                                    border = BorderStroke(1.dp, if (state.hasUsedFiftyFifty) Color.Transparent else JapaneseIndigo.copy(alpha = 0.3f)),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable(enabled = !state.hasUsedFiftyFifty) { onUseFiftyFifty() }
                                ) {
                                    Text(
                                        text = "50:50",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (state.hasUsedFiftyFifty) MaterialTheme.colorScheme.outline else JapaneseIndigo,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            // Question Swap Lifeline
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (state.hasUsedSwap) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) else ReviewBlue.copy(alpha = 0.12f),
                                border = BorderStroke(1.dp, if (state.hasUsedSwap) Color.Transparent else ReviewBlue.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable(enabled = !state.hasUsedSwap) { onSwapQuestion() }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SwapHoriz,
                                        contentDescription = "Swap",
                                        tint = if (state.hasUsedSwap) MaterialTheme.colorScheme.outline else ReviewBlue,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "Swap",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (state.hasUsedSwap) MaterialTheme.colorScheme.outline else ReviewBlue
                                    )
                                }
                            }

                            // Hint Button
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (state.showClue) StreakOrange.copy(alpha = 0.2f) else StreakOrange.copy(alpha = 0.12f),
                                border = BorderStroke(1.dp, StreakOrange.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onToggleClue() }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = "Hint",
                                        tint = StreakOrange,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = if (state.showClue) "Hide Clue" else "Clue",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = StreakOrange
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Hint Card
        if (state.showClue && !state.isAnswerSubmitted) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = StreakOrange.copy(alpha = 0.08f),
                    border = BorderStroke(1.dp, StreakOrange.copy(alpha = 0.25f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = StreakOrange,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = currentQuestion.clueHint.ifBlank { "Part of Speech: ${currentQuestion.card.partOfSpeech.ifBlank { "Noun / Expression" }} • ${currentQuestion.card.sectionTitle}" },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Prompt Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (currentQuestion.isListening) {
                        // Audio Challenge Specific Prompt
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .scale(pulseScale)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(JapaneseIndigo.copy(alpha = 0.25f), JapaneseIndigo.copy(alpha = 0.08f))
                                    )
                                )
                                .clickable { onSpeak(currentQuestion.card.kanji, state.speechRate) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hearing,
                                contentDescription = "Listen",
                                tint = JapaneseIndigo,
                                modifier = Modifier.size(46.dp)
                            )
                        }

                        Text(
                            text = "Tap to Listen to Japanese Audio",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = JapaneseIndigo
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { onSpeak(currentQuestion.card.kanji, 1.0f) },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("1.0x Normal", fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = { onSpeak(currentQuestion.card.kanji, 0.75f) },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("0.75x Slow", fontSize = 12.sp)
                            }
                        }
                    } else {
                        // Standard / Reading / Cloze / True-False Prompt
                        if (currentQuestion.promptSub.isNotBlank()) {
                            Text(
                                text = currentQuestion.promptSub,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }

                        Text(
                            text = currentQuestion.prompt,
                            style = if (currentQuestion.clozeSentence.isNotBlank() || currentQuestion.isTrueFalse) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        IconButton(
                            onClick = { onSpeak(currentQuestion.card.kanji, state.speechRate) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Speak Pronunciation",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        // 4 Options Grid/List or True/False Buttons
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                currentQuestion.options.forEachIndexed { index, option ->
                    val isSelected = state.selectedOptionIndex == index
                    val isCorrectAnswer = option == currentQuestion.correctAnswer
                    val isEliminated = state.eliminatedOptionIndices.contains(index)
                    val letterPrefix = if (currentQuestion.isTrueFalse) "" else "${('A' + index)}. "

                    val backgroundColor by animateColorAsState(
                        targetValue = when {
                            isEliminated -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                            !state.isAnswerSubmitted -> MaterialTheme.colorScheme.surface
                            isCorrectAnswer -> MasteredGreen.copy(alpha = 0.15f)
                            isSelected -> JapaneseCrimson.copy(alpha = 0.15f)
                            else -> MaterialTheme.colorScheme.surface
                        },
                        label = "option_bg"
                    )

                    val borderColor = when {
                        isEliminated -> Color.Transparent
                        !state.isAnswerSubmitted && isSelected -> MaterialTheme.colorScheme.primary
                        state.isAnswerSubmitted && isCorrectAnswer -> MasteredGreen
                        state.isAnswerSubmitted && isSelected -> JapaneseCrimson
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
                            .clickable(enabled = !state.isAnswerSubmitted && !isEliminated && !state.isPaused) {
                                onSelectOption(index)
                            }
                            .testTag("quiz_option_$index"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = backgroundColor)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                if (letterPrefix.isNotEmpty() && !isEliminated) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (state.isAnswerSubmitted && isCorrectAnswer) MasteredGreen else if (state.isAnswerSubmitted && isSelected) JapaneseCrimson else MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(
                                            text = letterPrefix.trim(),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (state.isAnswerSubmitted && (isCorrectAnswer || isSelected)) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = if (isEliminated) "— Eliminated (50:50) —" else option,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isEliminated) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                                )
                            }

                            if (state.isAnswerSubmitted && !isEliminated) {
                                if (isCorrectAnswer) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Correct",
                                        tint = MasteredGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Incorrect",
                                        tint = JapaneseCrimson,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Instant Detailed Explanation Card (Reveals on Answer Submission)
        if (state.isAnswerSubmitted) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
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
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = if (currentQuestion.isCorrect) Icons.Default.Check else Icons.Default.HelpOutline,
                                    contentDescription = null,
                                    tint = if (currentQuestion.isCorrect) MasteredGreen else JapaneseCrimson,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = if (currentQuestion.isCorrect) "Correct! +${10 * state.comboMultiplier} XP" else "Review & Study Insight",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (currentQuestion.isCorrect) MasteredGreen else JapaneseCrimson
                                )
                            }

                            IconButton(
                                onClick = onToggleBookmark,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = if (state.isCurrentCardBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = if (state.isCurrentCardBookmarked) StreakOrange else MaterialTheme.colorScheme.outline
                                )
                            }
                        }

                        // Word Header & Burmese Meaning
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${currentQuestion.card.kanji} 【${currentQuestion.card.reading}】",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = currentQuestion.card.meaningBurmese,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = { onSpeak(currentQuestion.card.kanji, 1.0f) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Pronounce", tint = JapaneseIndigo)
                                }
                            }
                        }

                        // Example Sentence with Translation
                        if (currentQuestion.card.exampleSentence.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = currentQuestion.card.exampleSentence,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    if (currentQuestion.card.exampleMeaningBurmese.isNotBlank()) {
                                        Text(
                                            text = currentQuestion.card.exampleMeaningBurmese,
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Next Question / Finish Button
            item {
                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("quiz_next_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = JapaneseCrimson)
                ) {
                    Text(
                        text = if (state.currentIndex + 1 < state.questions.size) "Next Question →" else "View Arena Results 🏆",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizResultView(
    state: QuizState,
    onRestart: () -> Unit,
    onRetryMissed: () -> Unit,
    onBookmarkAllMissed: () -> Unit,
    onToggleBookmark: (Long, Boolean) -> Unit,
    onSpeak: (String) -> Unit,
    onExit: () -> Unit
) {
    val score = state.score
    val total = state.questions.size
    val percentage = if (total > 0) (score * 100) / total else 0
    val missedQuestions = state.questions.filter { !it.isCorrect }
    val correctQuestions = state.questions.filter { it.isCorrect }

    var selectedReviewTab by remember { mutableIntStateOf(0) } // 0 = All, 1 = Missed, 2 = Correct
    var hasBookmarkedMissed by remember { mutableStateOf(false) }

    val displayedQuestions = when (selectedReviewTab) {
        1 -> missedQuestions
        2 -> correctQuestions
        else -> state.questions
    }

    val (tierTitle, tierBadgeColor) = when {
        percentage == 100 -> "🏆 Grandmaster JLPT N3 Recall!" to MasteredGreen
        percentage >= 80 -> "🥇 Outstanding JLPT Mastery!" to JapaneseIndigo
        percentage >= 60 -> "🥈 Strong Practice Session!" to ReviewBlue
        else -> "🥉 Good Effort — Drill Missed Words" to WeakOrange
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("quiz_result_view"),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Result Summary Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(tierBadgeColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = tierBadgeColor,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Text(
                        text = tierTitle,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Score: $score / $total ($percentage%) • ${state.durationSeconds}s elapsed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // XP and Performance Metrics
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "+${state.xpEarned} XP",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = JapaneseCrimson
                            )
                            Text(
                                text = "XP Earned",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${state.maxStreak} 🔥",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = WeakOrange
                            )
                            Text(
                                text = "Max Streak",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$percentage%",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (percentage >= 80) MasteredGreen else JapaneseIndigo
                            )
                            Text(
                                text = "Accuracy",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    // SQLite Database reinforcement notice
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MasteredGreen.copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, MasteredGreen.copy(alpha = 0.2f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.DoneAll, contentDescription = null, tint = MasteredGreen, modifier = Modifier.size(16.dp))
                            Text(
                                text = "SRS Spaced Repetition stats updated for $total cards in SQLite database",
                                style = MaterialTheme.typography.labelSmall,
                                color = MasteredGreen,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Action buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Missed Words Drill & Bookmark All Missed
                        if (missedQuestions.isNotEmpty()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = onRetryMissed,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .testTag("retry_missed_btn"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = StreakOrange)
                                ) {
                                    Icon(Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Drill Missed (${missedQuestions.size})", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                }

                                OutlinedButton(
                                    onClick = {
                                        onBookmarkAllMissed()
                                        hasBookmarkedMissed = true
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(
                                        imageVector = if (hasBookmarkedMissed) Icons.Default.Bookmark else Icons.Default.BookmarkAdd,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp),
                                        tint = StreakOrange
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (hasBookmarkedMissed) "Saved!" else "Bookmark Missed",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = StreakOrange
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = onRestart,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("quiz_retry_all_btn"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Retry All", fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = onExit,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("quiz_done_btn"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = JapaneseIndigo)
                            ) {
                                Text("Finish Arena", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Review Tabs (All, Missed, Correct)
        item {
            TabRow(
                selectedTabIndex = selectedReviewTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = JapaneseCrimson
            ) {
                Tab(
                    selected = selectedReviewTab == 0,
                    onClick = { selectedReviewTab = 0 },
                    text = { Text("All (${state.questions.size})", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedReviewTab == 1,
                    onClick = { selectedReviewTab = 1 },
                    text = { Text("Missed (${missedQuestions.size})", fontWeight = FontWeight.Bold, color = if (missedQuestions.isNotEmpty()) JapaneseCrimson else MaterialTheme.colorScheme.outline) }
                )
                Tab(
                    selected = selectedReviewTab == 2,
                    onClick = { selectedReviewTab = 2 },
                    text = { Text("Correct (${correctQuestions.size})", fontWeight = FontWeight.Bold, color = MasteredGreen) }
                )
            }
        }

        items(displayedQuestions) { q ->
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = if (q.isCorrect) MasteredGreen.copy(alpha = 0.05f) else JapaneseCrimson.copy(alpha = 0.05f)
                ),
                border = BorderStroke(1.dp, if (q.isCorrect) MasteredGreen.copy(alpha = 0.4f) else JapaneseCrimson.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "${q.card.kanji} 【${q.card.reading}】",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            IconButton(
                                onClick = { onSpeak(q.card.kanji) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Pronounce", tint = JapaneseIndigo, modifier = Modifier.size(16.dp))
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(
                                onClick = { onToggleBookmark(q.card.id, q.card.isBookmarked) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = if (q.card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = if (q.card.isBookmarked) StreakOrange else MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Icon(
                                imageVector = if (q.isCorrect) Icons.Default.Check else Icons.Default.Close,
                                contentDescription = null,
                                tint = if (q.isCorrect) MasteredGreen else JapaneseCrimson,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Text(
                        text = "Correct Answer: ${q.correctAnswer}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MasteredGreen,
                        fontWeight = FontWeight.Bold
                    )

                    if (!q.isCorrect && q.selectedAnswer != null) {
                        Text(
                            text = "Your Choice: ${q.selectedAnswer}",
                            style = MaterialTheme.typography.bodySmall,
                            color = JapaneseCrimson,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (q.card.exampleSentence.isNotBlank()) {
                        Text(
                            text = "Example: ${q.card.exampleSentence}",
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
private fun QuizHistoryItem(history: QuizHistory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = history.quizType,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${history.lessonFilter} • ${history.timeSpentSeconds}s elapsed",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                val percent = if (history.totalQuestions > 0) (history.score * 100) / history.totalQuestions else 0
                Text(
                    text = "${history.score}/${history.totalQuestions} ($percent%)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (percent >= 80) MasteredGreen else JapaneseCrimson
                )
                Text(
                    text = "+${history.xpEarned} XP",
                    style = MaterialTheme.typography.labelSmall,
                    color = JapaneseCrimson,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
