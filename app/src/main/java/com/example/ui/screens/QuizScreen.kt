package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
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
fun QuizScreen(
    quizViewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    val state by quizViewModel.quizState.collectAsState()
    val quizHistory by quizViewModel.quizHistory.collectAsState()

    var questionCount by remember { mutableIntStateOf(10) }
    var selectedLessonFilter by remember { mutableIntStateOf(0) } // 0: All, -1: Weak words, 1..10: Lessons

    if (state.isFinished) {
        // Quiz Results Summary View
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
            onToggleBookmark = { cardId, isBookmarked ->
                quizViewModel.toggleBookmarkInReview(cardId, isBookmarked)
            },
            onSpeak = { quizViewModel.speak(it) },
            onExit = { quizViewModel.exitQuiz() }
        )
    } else if (state.isQuizActive && state.questions.isNotEmpty()) {
        // Active Quiz Question View
        ActiveQuizView(
            state = state,
            onSelectOption = { quizViewModel.selectOption(it) },
            onUseFiftyFifty = { quizViewModel.useFiftyFifty() },
            onToggleClue = { quizViewModel.toggleClue() },
            onToggleBookmark = { quizViewModel.toggleCurrentCardBookmark() },
            onNext = { quizViewModel.nextQuestion() },
            onSpeak = { quizViewModel.speak(it) },
            onExit = { quizViewModel.exitQuiz() }
        )
    } else {
        // Quiz Lobby / Mode Select View
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("quiz_lobby_screen"),
            contentPadding = PaddingValues(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        JapaneseCrimson.copy(alpha = 0.10f),
                                        StreakOrange.copy(alpha = 0.08f),
                                        JapaneseIndigo.copy(alpha = 0.10f)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(JapaneseCrimson.copy(alpha = 0.2f)),
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
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Interactive Drills & Fast Recall",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = JapaneseCrimson,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "Test recognition, listening comprehension, furigana readings, and sentence context to earn XP and level up your mastery.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Scope & Lesson Filter
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
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Study Scope / Lesson Filter",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedLessonFilter == 0,
                                onClick = { selectedLessonFilter = 0 },
                                label = { Text("All JLPT N3", fontWeight = FontWeight.Bold) },
                                leadingIcon = {
                                    Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp))
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = JapaneseIndigo,
                                    selectedLabelColor = Color.White,
                                    selectedLeadingIconColor = Color.White
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
                                    selectedContainerColor = JapaneseCrimson,
                                    selectedLabelColor = Color.White,
                                    selectedLeadingIconColor = Color.White
                                )
                            )
                        }

                        items((1..29).toList()) { lessonNum ->
                            val labelText = if (lessonNum <= 21) "Lesson $lessonNum" else "Part 2 L${lessonNum - 21}"
                            FilterChip(
                                selected = selectedLessonFilter == lessonNum,
                                onClick = { selectedLessonFilter = lessonNum },
                                label = { Text(labelText, fontWeight = FontWeight.SemiBold) },
                                leadingIcon = {
                                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ReviewBlue,
                                    selectedLabelColor = Color.White,
                                    selectedLeadingIconColor = Color.White
                                )
                            )
                        }
                    }
                }
            }

            // Question Count Selector
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Questions per Round:",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(5, 10, 15, 20).forEach { count ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (questionCount == count) JapaneseCrimson else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (questionCount == count) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { questionCount = count }
                                    .testTag("quiz_count_$count")
                            ) {
                                Text(
                                    text = "$count Qs",
                                    color = if (questionCount == count) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Challenge Modes Section
            item {
                Text(
                    text = "Select Challenge Mode",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // 1. Kanji to Meaning
            item {
                QuizModeCard(
                    title = QuizType.KANJI_TO_MEANING.displayName,
                    description = QuizType.KANJI_TO_MEANING.description,
                    icon = Icons.Default.Translate,
                    badge = QuizType.KANJI_TO_MEANING.badge,
                    badgeColor = ReviewBlue,
                    onClick = {
                        val filter = if (selectedLessonFilter == 0) null else selectedLessonFilter
                        quizViewModel.startQuiz(QuizType.KANJI_TO_MEANING, filter, questionCount)
                    }
                )
            }

            // 2. Meaning to Kanji
            item {
                QuizModeCard(
                    title = QuizType.MEANING_TO_KANJI.displayName,
                    description = QuizType.MEANING_TO_KANJI.description,
                    icon = Icons.Default.Psychology,
                    badge = QuizType.MEANING_TO_KANJI.badge,
                    badgeColor = JapaneseCrimson,
                    onClick = {
                        val filter = if (selectedLessonFilter == 0) null else selectedLessonFilter
                        quizViewModel.startQuiz(QuizType.MEANING_TO_KANJI, filter, questionCount)
                    }
                )
            }

            // 3. Furigana Reading Challenge
            item {
                QuizModeCard(
                    title = QuizType.READING_CHALLENGE.displayName,
                    description = QuizType.READING_CHALLENGE.description,
                    icon = Icons.Default.Bolt,
                    badge = QuizType.READING_CHALLENGE.badge,
                    badgeColor = MasteredGreen,
                    onClick = {
                        val filter = if (selectedLessonFilter == 0) null else selectedLessonFilter
                        quizViewModel.startQuiz(QuizType.READING_CHALLENGE, filter, questionCount)
                    }
                )
            }

            // 4. Listening Audio Recall
            item {
                QuizModeCard(
                    title = QuizType.LISTENING_CHALLENGE.displayName,
                    description = QuizType.LISTENING_CHALLENGE.description,
                    icon = Icons.Default.Headphones,
                    badge = QuizType.LISTENING_CHALLENGE.badge,
                    badgeColor = JapaneseIndigo,
                    onClick = {
                        val filter = if (selectedLessonFilter == 0) null else selectedLessonFilter
                        quizViewModel.startQuiz(QuizType.LISTENING_CHALLENGE, filter, questionCount)
                    }
                )
            }

            // 5. Sentence Cloze Fill-in
            item {
                QuizModeCard(
                    title = QuizType.SENTENCE_CLOZE.displayName,
                    description = QuizType.SENTENCE_CLOZE.description,
                    icon = Icons.Default.MenuBook,
                    badge = QuizType.SENTENCE_CLOZE.badge,
                    badgeColor = StreakOrange,
                    onClick = {
                        val filter = if (selectedLessonFilter == 0) null else selectedLessonFilter
                        quizViewModel.startQuiz(QuizType.SENTENCE_CLOZE, filter, questionCount)
                    }
                )
            }

            // 6. Speed Test (10s)
            item {
                QuizModeCard(
                    title = QuizType.SPEED_TEST.displayName,
                    description = QuizType.SPEED_TEST.description,
                    icon = Icons.Default.Speed,
                    badge = QuizType.SPEED_TEST.badge,
                    badgeColor = WeakOrange,
                    onClick = {
                        val filter = if (selectedLessonFilter == 0) null else selectedLessonFilter
                        quizViewModel.startQuiz(QuizType.SPEED_TEST, filter, questionCount)
                    }
                )
            }

            // Recent Quiz History Section
            if (quizHistory.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
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
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Recent Quiz Arena History",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                items(quizHistory.take(5)) { history ->
                    QuizHistoryItem(history = history)
                }
            }
        }
    }
}

@Composable
private fun QuizModeCard(
    title: String,
    description: String,
    icon: ImageVector,
    badge: String,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .testTag("quiz_mode_${title.replace(" ", "_").replace("→", "to")}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(badgeColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = badgeColor,
                    modifier = Modifier.size(26.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = badgeColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = badge,
                            color = badgeColor,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ActiveQuizView(
    state: QuizState,
    onSelectOption: (Int) -> Unit,
    onUseFiftyFifty: () -> Unit,
    onToggleClue: () -> Unit,
    onToggleBookmark: () -> Unit,
    onNext: () -> Unit,
    onSpeak: (String) -> Unit,
    onExit: () -> Unit
) {
    val currentQuestion = state.questions.getOrNull(state.currentIndex) ?: return
    val progress by animateFloatAsState(
        targetValue = (state.currentIndex + 1).toFloat() / state.questions.size.toFloat(),
        label = "quiz_progress"
    )
    val maxTimerSec = if (state.quizType == QuizType.SPEED_TEST) 10 else 15
    val timerFraction = (state.remainingSeconds.toFloat() / maxTimerSec.toFloat()).coerceIn(0f, 1f)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("active_quiz_view"),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top status row (Exit, Counter, Streak, Score, Lifelines)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onExit) {
                        Icon(Icons.Default.Close, contentDescription = "Exit Quiz", tint = MaterialTheme.colorScheme.onSurface)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${state.lessonTitle}",
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

                    // Score & Streak Pills
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
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

                // Question Progress
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
                            imageVector = Icons.Default.Speed,
                            contentDescription = null,
                            tint = if (state.remainingSeconds <= 3) JapaneseCrimson else MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Timer",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
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

                // Lifelines Bar (50/50 and Clue Hint)
                if (!state.isAnswerSubmitted) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 50/50 Lifeline Button
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (state.hasUsedFiftyFifty) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) else JapaneseIndigo.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, if (state.hasUsedFiftyFifty) Color.Transparent else JapaneseIndigo.copy(alpha = 0.3f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(enabled = !state.hasUsedFiftyFifty) { onUseFiftyFifty() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "50:50",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (state.hasUsedFiftyFifty) MaterialTheme.colorScheme.outline else JapaneseIndigo
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Clue / Hint Button
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (state.showClue) StreakOrange.copy(alpha = 0.2f) else StreakOrange.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, StreakOrange.copy(alpha = 0.3f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onToggleClue() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = "Hint",
                                    tint = StreakOrange,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (state.showClue) "Hide Hint" else "Hint",
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

        // Hint Card Visibility
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
                            text = "Part of Speech: ${currentQuestion.card.partOfSpeech.ifBlank { "Noun / Expression" }} • ${currentQuestion.card.sectionTitle}",
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
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (currentQuestion.isListening) {
                        // Audio Challenge Specific Prompt
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(JapaneseIndigo.copy(alpha = 0.15f))
                                .clickable { onSpeak(currentQuestion.card.kanji) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hearing,
                                contentDescription = "Listen",
                                tint = JapaneseIndigo,
                                modifier = Modifier.size(44.dp)
                            )
                        }

                        Text(
                            text = "Tap to Listen to Pronunciation",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = JapaneseIndigo
                        )

                        OutlinedButton(
                            onClick = { onSpeak(currentQuestion.card.kanji) },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Replay Audio")
                        }
                    } else {
                        // Standard / Reading / Cloze Prompt
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
                            style = if (currentQuestion.clozeSentence.isNotBlank()) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        IconButton(
                            onClick = { onSpeak(currentQuestion.card.kanji) },
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

        // 4 Options Grid/List
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                currentQuestion.options.forEachIndexed { index, option ->
                    val isSelected = state.selectedOptionIndex == index
                    val isCorrectAnswer = option == currentQuestion.correctAnswer
                    val isEliminated = state.eliminatedOptionIndices.contains(index)

                    val backgroundColor by animateColorAsState(
                        targetValue = when {
                            isEliminated -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
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
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
                            .clickable(enabled = !state.isAnswerSubmitted && !isEliminated) {
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
                            Text(
                                text = if (isEliminated) "— Eliminated (50:50) —" else option,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isEliminated) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )

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
                                    text = if (currentQuestion.isCorrect) "Correct! +10 XP" else "Review & Insight",
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
                            Column {
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

                            IconButton(
                                onClick = { onSpeak(currentQuestion.card.kanji) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Pronounce", tint = JapaneseIndigo)
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
    onToggleBookmark: (Long, Boolean) -> Unit,
    onSpeak: (String) -> Unit,
    onExit: () -> Unit
) {
    val score = state.score
    val total = state.questions.size
    val percentage = if (total > 0) (score * 100) / total else 0
    val missedQuestions = state.questions.filter { !it.isCorrect }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("quiz_result_view"),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
                            .background(
                                if (percentage >= 80) MasteredGreen.copy(alpha = 0.15f) else WeakOrange.copy(alpha = 0.15f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = if (percentage >= 80) MasteredGreen else WeakOrange,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Text(
                        text = if (percentage == 100) "🌟 Perfect JLPT N3 Recall!" else if (percentage >= 80) "Outstanding Mastery!" else "Great Practice Session!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Score: $score / $total ($percentage%) • ${state.durationSeconds}s time elapsed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // XP and Combo Breakdown
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
                                text = "Experience Earned",
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
                                text = "${percentage}%",
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

                    Spacer(modifier = Modifier.height(6.dp))

                    // Action buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Missed Words Drill Button (If any missed)
                        if (missedQuestions.isNotEmpty()) {
                            Button(
                                onClick = onRetryMissed,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("retry_missed_btn"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = StreakOrange)
                            ) {
                                Icon(Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Drill Missed Words (${missedQuestions.size})", fontWeight = FontWeight.Bold)
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

        item {
            Text(
                text = "Question Review & Explanations (${state.questions.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(state.questions) { q ->
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

                        Icon(
                            imageVector = if (q.isCorrect) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null,
                            tint = if (q.isCorrect) MasteredGreen else JapaneseCrimson,
                            modifier = Modifier.size(22.dp)
                        )
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
            }
        }
    }
}
