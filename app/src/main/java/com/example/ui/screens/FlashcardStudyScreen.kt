package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TextDecrease
import androidx.compose.material.icons.filled.TextIncrease
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VocabCard
import com.example.data.srs.ReviewRating
import com.example.ui.components.PersonalNoteDialog
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.JapaneseIndigo
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.SakuraPinkDark
import com.example.ui.theme.SrsAgainBg
import com.example.ui.theme.SrsAgainText
import com.example.ui.theme.SrsEasyBg
import com.example.ui.theme.SrsEasyText
import com.example.ui.theme.SrsGoodBg
import com.example.ui.theme.SrsGoodText
import com.example.ui.theme.SrsHardBg
import com.example.ui.theme.SrsHardText
import com.example.ui.theme.ThemeMode
import com.example.ui.theme.WeakOrange
import com.example.ui.viewmodel.FlashcardStudyMode
import com.example.ui.viewmodel.VocabViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardStudyScreen(
    vocabViewModel: VocabViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val deck by vocabViewModel.studyDeck.collectAsState()
    val currentIndex by vocabViewModel.currentCardIndex.collectAsState()
    val isFlipped by vocabViewModel.isCardFlipped.collectAsState()
    val isFinished by vocabViewModel.isSessionFinished.collectAsState()
    val speechRate by vocabViewModel.speechRate.collectAsState()
    val isSpeaking by vocabViewModel.isSpeaking.collectAsState()
    val studyMode by vocabViewModel.studyMode.collectAsState()
    val showFurigana by vocabViewModel.showFurigana.collectAsState()
    val isAutoPlay by vocabViewModel.isAutoPlay.collectAsState()
    val autoPlaySpeed by vocabViewModel.autoPlaySpeedSec.collectAsState()
    val sessionStats by vocabViewModel.sessionStats.collectAsState()
    val fontScale by vocabViewModel.flashcardFontScale.collectAsState()
    val themeSettings by vocabViewModel.themeSettings.collectAsState()

    val systemIsDark = isSystemInDarkTheme()
    val isCurrentlyDark = when (themeSettings.themeMode) {
        ThemeMode.SYSTEM -> systemIsDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    var autoSpeakEnabled by remember { mutableStateOf(true) }
    var showNoteDialog by remember { mutableStateOf(false) }
    var showJumpSheet by remember { mutableStateOf(false) }
    var showFontSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val fontSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    // 3D Flip Animation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f),
        label = "cardFlipAnimation"
    )

    // Interactive Swipe State
    var dragOffsetX by remember { mutableFloatStateOf(0f) }
    var dragOffsetY by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX = remember { Animatable(0f) }

    // Pulse animation for Auto-Play / Speaking
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    if (deck.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "No cards selected for this study session.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Button(
                    onClick = onNavigateBack,
                    colors = ButtonDefaults.buttonColors(containerColor = JapaneseCrimson)
                ) {
                    Text("Return to Dashboard")
                }
            }
        }
        return
    }

    if (isFinished) {
        // Study Completed Screen with comprehensive analytics & mistake review
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Study Complete", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .testTag("study_finished_view"),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(16.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Celebration Badge
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(JapaneseCrimson.copy(alpha = 0.2f), MasteredGreen.copy(alpha = 0.2f))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Great Job",
                                tint = JapaneseCrimson,
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        Text(
                            text = "お疲れ様でした！",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = JapaneseCrimson
                        )

                        Text(
                            text = "Great job completing your study session!",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Stats Grid Summary
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Cards Reviewed",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "${sessionStats.totalStudied} words",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "XP Earned",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "+${sessionStats.xpEarned} XP",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MasteredGreen
                                    )
                                }

                                // Ratings breakdown mini bar
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    if (sessionStats.easyCount > 0) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = SrsEasyBg,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Easy: ${sessionStats.easyCount}",
                                                color = SrsEasyText,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                        }
                                    }
                                    if (sessionStats.goodCount > 0) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = SrsGoodBg,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Good: ${sessionStats.goodCount}",
                                                color = SrsGoodText,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                        }
                                    }
                                    if (sessionStats.hardCount > 0) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = SrsHardBg,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Hard: ${sessionStats.hardCount}",
                                                color = SrsHardText,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                        }
                                    }
                                    if (sessionStats.againCount > 0) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = SrsAgainBg,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Again: ${sessionStats.againCount}",
                                                color = SrsAgainText,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Review Mistakes Only (if any exist)
                        if (sessionStats.mistakeCards.isNotEmpty()) {
                            Button(
                                onClick = { vocabViewModel.restartMistakesOnly() },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = WeakOrange)
                            ) {
                                Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Drill Mistakes (${sessionStats.mistakeCards.size} cards)", fontWeight = FontWeight.Bold)
                            }
                        }

                        // Review Full Session Again
                        OutlinedButton(
                            onClick = { vocabViewModel.restartStudySession() },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.5.dp, JapaneseCrimson)
                        ) {
                            Icon(Icons.Default.Replay, contentDescription = null, tint = JapaneseCrimson)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Review Full Deck Again", color = JapaneseCrimson, fontWeight = FontWeight.SemiBold)
                        }

                        // Done & Return Home
                        Button(
                            onClick = onNavigateBack,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = JapaneseIndigo)
                        ) {
                            Text("Done & Return to Library", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        return
    }

    val currentCard = deck.getOrNull(currentIndex) ?: return
    val progress = (currentIndex + 1).toFloat() / deck.size.toFloat()
    val predictedIntervals = remember(currentCard) { vocabViewModel.getPredictedIntervals(currentCard) }

    // Auto-pronounce when new card loads if auto-speak is enabled
    LaunchedEffect(currentIndex, autoSpeakEnabled, studyMode) {
        if (autoSpeakEnabled && !isFlipped && studyMode != FlashcardStudyMode.MY_TO_JP) {
            vocabViewModel.speakJapanese(currentCard.kanji)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (currentCard.lessonNumber <= 21) "Lesson ${currentCard.lessonNumber}" else "Part 2 L${currentCard.lessonNumber - 21}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${currentIndex + 1} of ${deck.size} cards",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Auto-Play Hands-Free Slideshow Toggle
                    IconButton(
                        onClick = { vocabViewModel.toggleAutoPlay() },
                        modifier = Modifier.testTag("auto_play_toggle_btn")
                    ) {
                        Icon(
                            imageVector = if (isAutoPlay) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isAutoPlay) "Pause Auto-play" else "Start Auto-play",
                            tint = if (isAutoPlay) JapaneseCrimson else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Quick Deck Jump / Overview Grid
                    IconButton(
                        onClick = { showJumpSheet = true },
                        modifier = Modifier.testTag("deck_jump_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = "Jump to Card",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Display & Font Settings Sheet
                    IconButton(
                        onClick = { showFontSheet = true },
                        modifier = Modifier.testTag("flashcard_font_size_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = "Study Display Settings",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Global Theme Toggle Button (Light/Dark Mode for Night Study)
                    IconButton(
                        onClick = { vocabViewModel.toggleDarkMode(isCurrentlyDark) },
                        modifier = Modifier.testTag("flashcard_theme_toggle_btn")
                    ) {
                        Icon(
                            imageVector = if (isCurrentlyDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (isCurrentlyDark) "Switch to Light Mode" else "Switch to Dark Mode",
                            tint = if (isCurrentlyDark) Color(0xFFFFD54F) else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Bookmark Button
                    IconButton(onClick = { vocabViewModel.toggleBookmark(currentCard) }) {
                        Icon(
                            imageVector = if (currentCard.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (currentCard.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("study_active_screen"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Mode Selector Chips & Progress Row
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Study Mode Switcher Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FlashcardStudyMode.values().forEach { mode ->
                        FilterChip(
                            selected = studyMode == mode,
                            onClick = { vocabViewModel.setStudyMode(mode) },
                            label = { Text(mode.label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = JapaneseCrimson.copy(alpha = 0.15f),
                                selectedLabelColor = JapaneseCrimson
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = studyMode == mode,
                                selectedBorderColor = JapaneseCrimson
                            ),
                            modifier = Modifier.height(30.dp)
                        )
                    }
                }

                // Progress bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = JapaneseCrimson,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Auto-play active visual indicator banner
            if (isAutoPlay) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = JapaneseCrimson.copy(alpha = 0.12f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .scale(pulseScale)
                                    .clip(CircleShape)
                                    .background(JapaneseCrimson)
                            )
                            Text(
                                text = "Hands-Free Auto-Play Active",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = JapaneseCrimson
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf(2, 3, 5).forEach { sec ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (autoPlaySpeed == sec) JapaneseCrimson else Color.Transparent,
                                    modifier = Modifier.clickable { vocabViewModel.setAutoPlaySpeed(sec) }
                                ) {
                                    Text(
                                        text = "${sec}s",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (autoPlaySpeed == sec) Color.White else JapaneseCrimson,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            // 3D Flippable Flashcard with Interactive Horizontal Gesture Drag
            val dragRatio = (dragOffsetX / 300f).coerceIn(-1f, 1f)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset { IntOffset(dragOffsetX.roundToInt(), (dragOffsetY * 0.2f).roundToInt()) }
                    .rotate(dragOffsetX * 0.05f)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 14f * density
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                if (dragOffsetX > 140f) {
                                    // Swiped Right -> GOOD
                                    vocabViewModel.rateCurrentCard(ReviewRating.GOOD)
                                } else if (dragOffsetX < -140f) {
                                    // Swiped Left -> AGAIN
                                    vocabViewModel.rateCurrentCard(ReviewRating.AGAIN)
                                }
                                dragOffsetX = 0f
                                dragOffsetY = 0f
                            },
                            onDragCancel = {
                                dragOffsetX = 0f
                                dragOffsetY = 0f
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragOffsetX += dragAmount.x
                                dragOffsetY += dragAmount.y
                            }
                        )
                    }
                    .clickable { vocabViewModel.flipCard() }
                    .testTag("study_flashcard_card"),
                contentAlignment = Alignment.Center
            ) {
                if (rotation <= 90f) {
                    // FRONT SIDE OF FLASHCARD
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Top Badges Header
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = MaterialTheme.colorScheme.secondaryContainer
                                        ) {
                                            Text(
                                                text = if (currentCard.lessonNumber <= 21) "N3 • L${currentCard.lessonNumber}" else "Part 2 • L${currentCard.lessonNumber - 21}",
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                            )
                                        }

                                        if (currentCard.partOfSpeech.isNotBlank()) {
                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = MaterialTheme.colorScheme.surfaceVariant
                                            ) {
                                                Text(
                                                    text = currentCard.partOfSpeech,
                                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                                )
                                            }
                                        }
                                    }

                                    // Furigana Hide/Show Toggle (for active recall challenge)
                                    if (studyMode == FlashcardStudyMode.JP_TO_MY) {
                                        IconButton(
                                            onClick = { vocabViewModel.toggleFurigana() },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (showFurigana) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                                contentDescription = "Toggle Furigana",
                                                tint = if (showFurigana) JapaneseCrimson else MaterialTheme.colorScheme.outline,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }

                                // Center Content depending on Study Mode
                                when (studyMode) {
                                    FlashcardStudyMode.JP_TO_MY -> {
                                        // Standard: Japanese Kanji + Furigana on front
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            // Furigana
                                            Text(
                                                text = if (showFurigana) currentCard.reading else "••••",
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontSize = (13 * fontScale).sp,
                                                    lineHeight = (17 * fontScale).sp
                                                ),
                                                color = if (showFurigana) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )

                                            // Main Kanji Display with comfortable font scaling
                                            val baseKanjiSize = if (currentCard.kanji.length > 5) 24f else if (currentCard.kanji.length > 3) 30f else 36f
                                            Text(
                                                text = currentCard.kanji,
                                                style = MaterialTheme.typography.displaySmall.copy(
                                                    fontSize = (baseKanjiSize * fontScale).sp,
                                                    lineHeight = ((baseKanjiSize + 6f) * fontScale).sp
                                                ),
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )

                                            Spacer(modifier = Modifier.height(10.dp))

                                            // Enhanced Pronunciation Audio Controls
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                IconButton(
                                                    onClick = { vocabViewModel.speakCard(currentCard) },
                                                    modifier = Modifier
                                                        .size(42.dp)
                                                        .background(
                                                            if (isSpeaking) JapaneseCrimson.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer,
                                                            CircleShape
                                                        )
                                                        .testTag("flashcard_speak_btn")
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                                        contentDescription = "Speak Pronunciation",
                                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                }

                                                if (currentCard.reading.isNotBlank()) {
                                                    Surface(
                                                        shape = RoundedCornerShape(10.dp),
                                                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                                                        modifier = Modifier
                                                            .clickable { vocabViewModel.speakPhonetic(currentCard.reading) }
                                                            .testTag("flashcard_phonetic_btn")
                                                    ) {
                                                        Row(
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                                        ) {
                                                            Text("🗣️", fontSize = 11.sp)
                                                            Text(
                                                                "Kana",
                                                                fontSize = (10 * fontScale).coerceAtLeast(9f).sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                                            )
                                                        }
                                                    }
                                                }

                                                Surface(
                                                    shape = RoundedCornerShape(10.dp),
                                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                                    modifier = Modifier
                                                        .clickable { vocabViewModel.speakSlow(currentCard.kanji) }
                                                        .testTag("flashcard_slow_btn")
                                                ) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                                    ) {
                                                        Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(13.dp))
                                                        Text("0.7x", fontSize = (10 * fontScale).coerceAtLeast(9f).sp, fontWeight = FontWeight.Bold)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    FlashcardStudyMode.MY_TO_JP -> {
                                        // Reverse Active Recall: Burmese on front -> Recall Japanese
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "How do you say in Japanese?",
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    fontSize = (12 * fontScale).sp
                                                ),
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = currentCard.meaningBurmese,
                                                style = MaterialTheme.typography.titleLarge.copy(
                                                    fontSize = (18 * fontScale).sp,
                                                    lineHeight = (25 * fontScale).sp
                                                ),
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Spacer(modifier = Modifier.height(12.dp))
                                            Surface(
                                                shape = RoundedCornerShape(12.dp),
                                                color = JapaneseCrimson.copy(alpha = 0.1f)
                                            ) {
                                                Text(
                                                    text = "Tap card to reveal Japanese",
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontSize = (11 * fontScale).sp
                                                    ),
                                                    color = JapaneseCrimson,
                                                    fontWeight = FontWeight.SemiBold,
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                                )
                                            }
                                        }
                                    }
                                    FlashcardStudyMode.AUDIO_FIRST -> {
                                        // Audio Listening First: Listen & Guess
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size((64 * fontScale.coerceIn(0.85f, 1.15f)).dp)
                                                    .clip(CircleShape)
                                                    .background(JapaneseCrimson.copy(alpha = 0.15f))
                                                    .clickable { vocabViewModel.speakJapanese(currentCard.kanji) },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Headphones,
                                                    contentDescription = "Listen",
                                                    tint = JapaneseCrimson,
                                                    modifier = Modifier.size((32 * fontScale.coerceIn(0.85f, 1.15f)).dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = "Listen carefully to the word",
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontSize = (15 * fontScale).sp
                                                ),
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = "Tap to replay audio, or flip to verify",
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontSize = (11 * fontScale).sp
                                                ),
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                        }
                                    }
                                }

                                // Bottom Hint & Section Title
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (currentCard.sectionTitle.isNotBlank()) {
                                        Text(
                                            text = currentCard.sectionTitle,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.outline,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.TouchApp,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.outline,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "Tap to flip • Swipe right for Good",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                }
                            }

                            // Swipe Gesture Overlay Badges (Red on Left, Green on Right)
                            if (dragOffsetX > 50f) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MasteredGreen.copy(alpha = (dragRatio * 0.85f).coerceIn(0.2f, 0.9f)),
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "GOOD ✓",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                    )
                                }
                            } else if (dragOffsetX < -50f) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = JapaneseCrimson.copy(alpha = (-dragRatio * 0.85f).coerceIn(0.2f, 0.9f)),
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "AGAIN ✕",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // BACK SIDE OF FLASHCARD (Meaning + Examples + Mnemonic Notes)
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { rotationY = 180f },
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Header Row: Japanese Word & Audio
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "${currentCard.kanji} (${currentCard.reading})",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontSize = (15 * fontScale).sp
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        color = JapaneseCrimson
                                    )
                                    IconButton(
                                        onClick = { vocabViewModel.speakCard(currentCard) },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.VolumeUp,
                                            contentDescription = "Speak Word",
                                            tint = JapaneseCrimson,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = { showNoteDialog = true },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Mnemonic Note",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            // Burmese Meaning (Comfortable & Clear)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Text(
                                    text = currentCard.meaningBurmese,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontSize = (18 * fontScale).sp,
                                        lineHeight = (25 * fontScale).sp
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                if (currentCard.partOfSpeech.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                    ) {
                                        Text(
                                            text = currentCard.partOfSpeech,
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontSize = (11 * fontScale).sp
                                            ),
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                // Example Sentence (if present)
                                if (currentCard.exampleSentence.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(10.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = currentCard.exampleSentence,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        fontSize = (14 * fontScale).sp,
                                                        lineHeight = (19 * fontScale).sp
                                                    ),
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    modifier = Modifier.weight(1f, fill = false)
                                                )
                                                IconButton(
                                                    onClick = { vocabViewModel.speakSentence(currentCard.exampleSentence) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.VolumeUp,
                                                        contentDescription = "Speak Example",
                                                        tint = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.size(15.dp)
                                                    )
                                                }
                                            }
                                            if (currentCard.exampleMeaningBurmese.isNotBlank()) {
                                                Spacer(modifier = Modifier.height(3.dp))
                                                Text(
                                                    text = currentCard.exampleMeaningBurmese,
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontSize = (12 * fontScale).sp,
                                                        lineHeight = (16 * fontScale).sp
                                                    ),
                                                    textAlign = TextAlign.Center,
                                                    color = MaterialTheme.colorScheme.outline
                                                )
                                            }
                                        }
                                    }
                                }

                                // Personal Note / Mnemonic
                                if (currentCard.personalNote.isNotBlank()) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "💡 ${currentCard.personalNote}",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = (12 * fontScale).sp,
                                                lineHeight = (15 * fontScale).sp
                                            ),
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }

                            // Footer interval tip
                            Text(
                                text = "Rate your recall to update SRS review interval",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = (10 * fontScale).sp
                                ),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Navigation Row (Previous Card step-back + SRS 4 Rating Buttons)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("srs_rating_buttons_row"),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Step-back / Previous button
                IconButton(
                    onClick = { vocabViewModel.previousCard() },
                    enabled = currentIndex > 0,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            if (currentIndex > 0) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            RoundedCornerShape(14.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = "Previous Card",
                        tint = if (currentIndex > 0) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.outline
                    )
                }

                // SRS Rating Colors adaptive to Light / Dark (Night Study) mode
                val againBg = if (isCurrentlyDark) Color(0xFF4A1818) else SrsAgainBg
                val againText = if (isCurrentlyDark) Color(0xFFFFB4AB) else SrsAgainText
                val againBorder = if (isCurrentlyDark) Color(0xFF8C1D18) else null

                val hardBg = if (isCurrentlyDark) Color(0xFF422606) else SrsHardBg
                val hardText = if (isCurrentlyDark) Color(0xFFFFD59E) else SrsHardText
                val hardBorder = if (isCurrentlyDark) Color(0xFF8F5300) else null

                val goodBg = if (isCurrentlyDark) Color(0xFF0F325E) else SrsGoodBg
                val goodText = if (isCurrentlyDark) Color(0xFFA6CCFF) else SrsGoodText
                val goodBorder = if (isCurrentlyDark) Color(0xFF1B5599) else null

                val easyBg = if (isCurrentlyDark) Color(0xFF133E18) else SrsEasyBg
                val easyText = if (isCurrentlyDark) Color(0xFFA8EBB0) else SrsEasyText
                val easyBorder = if (isCurrentlyDark) Color(0xFF1F6E29) else null

                // Again
                RatingButton(
                    rating = ReviewRating.AGAIN,
                    label = "AGAIN",
                    interval = predictedIntervals[ReviewRating.AGAIN] ?: "10m",
                    containerColor = againBg,
                    contentColor = againText,
                    borderColor = againBorder,
                    modifier = Modifier.weight(1f),
                    onClick = { vocabViewModel.rateCurrentCard(ReviewRating.AGAIN) }
                )

                // Hard
                RatingButton(
                    rating = ReviewRating.HARD,
                    label = "HARD",
                    interval = predictedIntervals[ReviewRating.HARD] ?: "1d",
                    containerColor = hardBg,
                    contentColor = hardText,
                    borderColor = hardBorder,
                    modifier = Modifier.weight(1f),
                    onClick = { vocabViewModel.rateCurrentCard(ReviewRating.HARD) }
                )

                // Good
                RatingButton(
                    rating = ReviewRating.GOOD,
                    label = "GOOD",
                    interval = predictedIntervals[ReviewRating.GOOD] ?: "6d",
                    containerColor = goodBg,
                    contentColor = goodText,
                    borderColor = goodBorder,
                    modifier = Modifier.weight(1f),
                    onClick = { vocabViewModel.rateCurrentCard(ReviewRating.GOOD) }
                )

                // Easy
                RatingButton(
                    rating = ReviewRating.EASY,
                    label = "EASY",
                    interval = predictedIntervals[ReviewRating.EASY] ?: "8d",
                    containerColor = easyBg,
                    contentColor = easyText,
                    borderColor = easyBorder,
                    modifier = Modifier.weight(1f),
                    onClick = { vocabViewModel.rateCurrentCard(ReviewRating.EASY) }
                )
            }
        }
    }

    // Modal Bottom Sheet for Quick Card Jump
    if (showJumpSheet) {
        ModalBottomSheet(
            onDismissRequest = { showJumpSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Deck Overview (${deck.size} cards)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { showJumpSheet = false }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 64.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                ) {
                    itemsIndexed(deck) { index, card ->
                        val isCurrent = index == currentIndex
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isCurrent) JapaneseCrimson else MaterialTheme.colorScheme.surfaceVariant,
                            border = if (isCurrent) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            modifier = Modifier
                                .height(56.dp)
                                .clickable {
                                    vocabViewModel.jumpToCard(index)
                                    showJumpSheet = false
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = card.kanji.take(3),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) Color.White else MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
                                Text(
                                    text = "#${index + 1}",
                                    fontSize = 10.sp,
                                    color = if (isCurrent) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal Bottom Sheet for Font Size Adjustment
    if (showFontSheet) {
        var previewSide by remember { mutableStateOf(0) } // 0 = Front, 1 = Back
        ModalBottomSheet(
            onDismissRequest = { showFontSheet = false },
            sheetState = fontSheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
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
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = null,
                            tint = JapaneseCrimson
                        )
                        Text(
                            text = "Flashcard Font Sizing",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    TextButton(
                        onClick = { vocabViewModel.setFlashcardFontScale(1.0f) }
                    ) {
                        Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reset (100%)", fontSize = 12.sp)
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                // Live Preview Card with Front / Back Tab Switcher
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
                            text = "Live Card Preview",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.outline
                        )

                        // Toggle Preview Front vs Back
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            FilterChip(
                                selected = previewSide == 0,
                                onClick = { previewSide = 0 },
                                label = { Text("Front View", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = JapaneseCrimson,
                                    selectedLabelColor = Color.White
                                )
                            )
                            FilterChip(
                                selected = previewSide == 1,
                                onClick = { previewSide = 1 },
                                label = { Text("Back View", fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = JapaneseIndigo,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    // Live Card Preview Box
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (previewSide == 0) {
                                // Front Preview
                                if (showFurigana) {
                                    Text(
                                        text = currentCard.reading.ifBlank { "にほんご" },
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontSize = (13 * fontScale).sp
                                        ),
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                }
                                val baseSize = if (currentCard.kanji.length > 5) 24f else if (currentCard.kanji.length > 3) 30f else 36f
                                Text(
                                    text = currentCard.kanji.ifBlank { "日本語" },
                                    style = MaterialTheme.typography.displaySmall.copy(
                                        fontSize = (baseSize * fontScale).sp,
                                        lineHeight = ((baseSize + 6f) * fontScale).sp
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            } else {
                                // Back Preview
                                Text(
                                    text = "${currentCard.kanji} (${currentCard.reading})",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontSize = (14 * fontScale).sp
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    color = JapaneseCrimson
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = currentCard.meaningBurmese.ifBlank { "မြန်မာဘာသာ အဓိပ္ပါယ်" },
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = (17 * fontScale).sp,
                                        lineHeight = (23 * fontScale).sp
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                if (currentCard.exampleSentence.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = currentCard.exampleSentence,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = (13 * fontScale).sp
                                        ),
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                // Preset Buttons Row
                Text(
                    text = "Quick Presets",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.outline
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val presets = listOf(
                        Triple("Compact", 0.85f, "85%"),
                        Triple("Standard", 1.0f, "100%"),
                        Triple("Comfort", 1.15f, "115%"),
                        Triple("Large", 1.30f, "130%")
                    )

                    presets.forEach { (name, scale, pct) ->
                        val isSelected = kotlin.math.abs(fontScale - scale) < 0.06f
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) JapaneseCrimson else MaterialTheme.colorScheme.surfaceVariant,
                            border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { vocabViewModel.setFlashcardFontScale(scale) }
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = pct,
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }

                // Slider with Fine-tune +/- Step Controls
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Fine Tuning",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = "${(fontScale * 100).roundToInt()}% Scale",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = JapaneseCrimson
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = { vocabViewModel.setFlashcardFontScale(fontScale - 0.05f) },
                            enabled = fontScale > 0.76f
                        ) {
                            Icon(
                                imageVector = Icons.Default.TextDecrease,
                                contentDescription = "Decrease Font Size",
                                tint = if (fontScale > 0.76f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                            )
                        }

                        Slider(
                            value = fontScale,
                            onValueChange = { vocabViewModel.setFlashcardFontScale(it) },
                            valueRange = 0.75f..1.35f,
                            steps = 11,
                            modifier = Modifier.weight(1f),
                            colors = SliderDefaults.colors(
                                thumbColor = JapaneseCrimson,
                                activeTrackColor = JapaneseCrimson,
                                inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )

                        IconButton(
                            onClick = { vocabViewModel.setFlashcardFontScale(fontScale + 0.05f) },
                            enabled = fontScale < 1.34f
                        ) {
                            Icon(
                                imageVector = Icons.Default.TextIncrease,
                                contentDescription = "Increase Font Size",
                                tint = if (fontScale < 1.34f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }

                // TTS Speech Speed Selection
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Japanese Audio Speech Rate",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(0.6f to "0.6x (Slow)", 0.8f to "0.8x (Natural)", 1.0f to "1.0x (Normal)").forEach { (rate, label) ->
                            val isSelected = kotlin.math.abs(speechRate - rate) < 0.05f
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (isSelected) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { vocabViewModel.setSpeechRate(rate) }
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                                )
                            }
                        }
                    }
                }

                // Night Study Theme Mode Selector inside Display Settings
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Night Study & Theme Mode",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            Triple("System", Icons.Default.BrightnessAuto, ThemeMode.SYSTEM),
                            Triple("Light", Icons.Default.LightMode, ThemeMode.LIGHT),
                            Triple("Dark", Icons.Default.DarkMode, ThemeMode.DARK)
                        ).forEach { (label, icon, mode) ->
                            val isSelected = themeSettings.themeMode == mode
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                border = if (isSelected) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { vocabViewModel.setThemeMode(mode) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                // OLED Pure Black Option (if Dark or System mode active)
                if (themeSettings.themeMode == ThemeMode.DARK || (themeSettings.themeMode == ThemeMode.SYSTEM && isCurrentlyDark)) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Contrast,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "AMOLED Pure Black (Night Reading)",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Switch(
                                checked = themeSettings.oledBlack,
                                onCheckedChange = { vocabViewModel.setOledBlack(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }

                Button(
                    onClick = { showFontSheet = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Apply & Continue Study", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (showNoteDialog) {
        PersonalNoteDialog(
            initialNote = currentCard.personalNote,
            vocabWord = currentCard.kanji,
            onDismiss = { showNoteDialog = false },
            onSave = { newNote ->
                vocabViewModel.updatePersonalNote(currentCard.id, newNote)
                showNoteDialog = false
            }
        )
    }
}

@Composable
private fun RatingButton(
    rating: ReviewRating,
    label: String,
    interval: String,
    containerColor: Color,
    contentColor: Color,
    borderColor: Color? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(52.dp)
            .testTag("rate_btn_${rating.name.lowercase()}"),
        shape = RoundedCornerShape(14.dp),
        border = if (borderColor != null) BorderStroke(1.dp, borderColor) else null,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Text(
                text = interval,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = contentColor.copy(alpha = 0.7f)
            )
        }
    }
}
