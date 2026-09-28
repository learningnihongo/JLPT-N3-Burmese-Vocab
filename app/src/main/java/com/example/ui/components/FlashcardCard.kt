package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.example.data.util.CollocationData
import com.example.ui.theme.JapaneseFontFamily
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VocabCard
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.SakuraPinkDark
import com.example.ui.util.KanaHelper
import com.example.ui.util.StudyActionHelper
import com.example.ui.viewmodel.FlashcardStudyMode

/**
 * Flipping axis orientation for 3D card rotation animations.
 */
enum class CardFlipAxis {
    HORIZONTAL,
    VERTICAL
}

/**
 * A reusable, low-level 3D flippable card container in Compose.
 *
 * Supports realistic perspective camera distance, smooth spring physics,
 * elevation shift during mid-flip, and proper non-mirrored back-face rendering.
 */
@Composable
fun FlippableCardContainer(
    isFlipped: Boolean,
    onFlip: () -> Unit,
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    flipAxis: CardFlipAxis = CardFlipAxis.HORIZONTAL,
    cameraDistance: Float = 16f,
    animationSpec: AnimationSpec<Float> = spring(dampingRatio = 0.82f, stiffness = 380f),
    contentAlignment: Alignment = Alignment.Center
) {
    // 3D rotation animation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = animationSpec,
        label = "flippable_card_3d_rotation"
    )

    // Elevation dynamically peaks during the flip to simulate lifting off the surface
    val isMidFlip = rotation in 35f..145f
    val dynamicElevation by animateDpAsState(
        targetValue = if (isMidFlip) 8.dp else 3.dp,
        animationSpec = spring(dampingRatio = 0.85f),
        label = "flippable_card_elevation"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                if (flipAxis == CardFlipAxis.HORIZONTAL) {
                    rotationY = rotation
                } else {
                    rotationX = rotation
                }
                this.cameraDistance = cameraDistance * density
                shadowElevation = dynamicElevation.toPx()
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onFlip
            ),
        contentAlignment = contentAlignment
    ) {
        if (rotation <= 90f) {
            // Front face (normal orientation)
            Box(modifier = Modifier.fillMaxSize()) {
                frontContent()
            }
        } else {
            // Back face (counter-rotated 180 deg so text and UI are right-side-up and unmirrored)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        if (flipAxis == CardFlipAxis.HORIZONTAL) {
                            rotationY = 180f
                        } else {
                            rotationX = 180f
                        }
                    }
            ) {
                backContent()
            }
        }
    }
}

/**
 * A comprehensive, production-grade Flashcard UI component for Japanese-Burmese study sessions.
 *
 * Features:
 * - 3D card flip animation with interactive reveal toggle.
 * - Multi-mode support: Japanese -> Burmese (Standard), Burmese -> Japanese (Reverse Active Recall),
 *   and Audio-First (Listening Comprehension).
 * - Pronunciation audio controls: Standard speed, Phonetic Kana, and Slow (0.7x).
 * - Furigana reading toggle and Romaji hint toggle for active recall.
 * - Quick learning tools: Copy to clipboard, Google Translate, Jisho lookup, and Kanji Compound words sheet.
 * - Personal mnemonic note indicator and editor trigger.
 * - Spaced Repetition (SM-2) intervals and repetition status badge.
 * - Swipe drag visual feedback overlays (GOOD ✓ / AGAIN ✕).
 */
@Composable
fun StudyFlashcardCard(
    card: VocabCard,
    modifier: Modifier = Modifier,
    isFlipped: Boolean = false,
    onFlip: () -> Unit = {},
    studyMode: FlashcardStudyMode = FlashcardStudyMode.JP_TO_MY,
    fontScale: Float = 1.0f,
    showFurigana: Boolean = true,
    showRomajiHint: Boolean = false,
    isSpeaking: Boolean = false,
    dragOffsetX: Float = 0f,
    onSpeakWord: (() -> Unit)? = null,
    onSpeakReading: (() -> Unit)? = null,
    onSpeakSlow: (() -> Unit)? = null,
    onSpeakSentence: (() -> Unit)? = null,
    onToggleBookmark: (() -> Unit)? = null,
    onToggleFurigana: (() -> Unit)? = null,
    onToggleRomajiHint: (() -> Unit)? = null,
    onOpenNoteDialog: (() -> Unit)? = null,
    onOpenCompoundLookup: (() -> Unit)? = null,
    cardFlipAxis: CardFlipAxis = CardFlipAxis.HORIZONTAL
) {
    val context = LocalContext.current
    val dragRatio = (dragOffsetX / 300f).coerceIn(-1f, 1f)

    FlippableCardContainer(
        isFlipped = isFlipped,
        onFlip = onFlip,
        flipAxis = cardFlipAxis,
        modifier = modifier.testTag("study_flashcard_card"),
        frontContent = {
            StudyFlashcardFrontSide(
                card = card,
                studyMode = studyMode,
                fontScale = fontScale,
                showFurigana = showFurigana,
                showRomajiHint = showRomajiHint,
                isSpeaking = isSpeaking,
                dragOffsetX = dragOffsetX,
                dragRatio = dragRatio,
                onFlip = onFlip,
                onSpeakWord = onSpeakWord,
                onSpeakReading = onSpeakReading,
                onSpeakSlow = onSpeakSlow,
                onToggleBookmark = onToggleBookmark,
                onToggleFurigana = onToggleFurigana,
                onToggleRomajiHint = onToggleRomajiHint,
                onOpenCompoundLookup = onOpenCompoundLookup
            )
        },
        backContent = {
            StudyFlashcardBackSide(
                card = card,
                studyMode = studyMode,
                fontScale = fontScale,
                isSpeaking = isSpeaking,
                onFlip = onFlip,
                onSpeakWord = onSpeakWord,
                onSpeakReading = onSpeakReading,
                onSpeakSlow = onSpeakSlow,
                onSpeakSentence = onSpeakSentence,
                onToggleBookmark = onToggleBookmark,
                onOpenNoteDialog = onOpenNoteDialog,
                onOpenCompoundLookup = onOpenCompoundLookup
            )
        }
    )
}

/**
 * Front side of the Flashcard: Presents the prompt (Kanji, Burmese cue, or Listening prompt)
 * and active recall tools.
 */
@Composable
private fun StudyFlashcardFrontSide(
    card: VocabCard,
    studyMode: FlashcardStudyMode,
    fontScale: Float,
    showFurigana: Boolean,
    showRomajiHint: Boolean,
    isSpeaking: Boolean,
    dragOffsetX: Float,
    dragRatio: Float,
    onFlip: () -> Unit,
    onSpeakWord: (() -> Unit)?,
    onSpeakReading: (() -> Unit)?,
    onSpeakSlow: (() -> Unit)?,
    onToggleBookmark: (() -> Unit)?,
    onToggleFurigana: (() -> Unit)?,
    onToggleRomajiHint: (() -> Unit)?,
    onOpenCompoundLookup: (() -> Unit)?
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxSize()
            .testTag("flashcard_front_surface"),
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
                // Top Header: Badges & Controls
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
                                text = if (card.lessonNumber <= 21) "N3 • L${card.lessonNumber}" else "Part 2 • L${card.lessonNumber - 21}",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        if (card.partOfSpeech.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = card.partOfSpeech,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = if (card.repetitions > 0) "SM-2 • ${card.intervalDays}d • Rep ${card.repetitions}" else "SM-2 • New",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Furigana toggle (JP -> MM mode)
                        if (studyMode == FlashcardStudyMode.JP_TO_MY && onToggleFurigana != null) {
                            IconButton(
                                onClick = onToggleFurigana,
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("flashcard_furigana_toggle_btn")
                            ) {
                                Icon(
                                    imageVector = if (showFurigana) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle Furigana",
                                    tint = if (showFurigana) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Bookmark button
                        if (onToggleBookmark != null) {
                            IconButton(
                                onClick = onToggleBookmark,
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("flashcard_bookmark_btn")
                            ) {
                                Icon(
                                    imageVector = if (card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = if (card.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                // Center Content based on Study Mode
                when (studyMode) {
                    FlashcardStudyMode.JP_TO_MY -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Furigana Reading
                            Text(
                                text = if (showFurigana) card.reading else "••••",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = (13 * fontScale).sp,
                                    lineHeight = (17 * fontScale).sp
                                ),
                                color = if (showFurigana) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            // Main Kanji Display
                            val baseKanjiSize = if (card.kanji.length > 5) 24f else if (card.kanji.length > 3) 30f else 36f
                            Text(
                                text = card.kanji,
                                style = MaterialTheme.typography.displaySmall.copy(
                                    fontSize = (baseKanjiSize * fontScale).sp,
                                    lineHeight = ((baseKanjiSize + 6f) * fontScale).sp
                                ),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Audio & Pronunciation Controls
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (onSpeakWord != null) {
                                    IconButton(
                                        onClick = onSpeakWord,
                                        modifier = Modifier
                                            .size(42.dp)
                                            .background(
                                                if (isSpeaking) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                                                CircleShape
                                            )
                                            .testTag("flashcard_front_audio_btn")
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Speak Pronunciation",
                                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                if (card.reading.isNotBlank() && onSpeakReading != null) {
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                                        modifier = Modifier
                                            .clickable { onSpeakReading() }
                                            .testTag("flashcard_phonetic_audio_btn")
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

                                if (onSpeakSlow != null) {
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        modifier = Modifier
                                            .clickable { onSpeakSlow() }
                                            .testTag("flashcard_slow_audio_btn")
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

                            // Quick Reference Action Chips (Copy, Translate, Jisho, Compounds)
                            Row(
                                modifier = Modifier.padding(top = 10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                    modifier = Modifier
                                        .clickable {
                                            StudyActionHelper.copyToClipboard(
                                                context = context,
                                                text = card.kanji,
                                                label = "Word",
                                                toastMessage = "${card.kanji} ကို Copy ကူးပြီးပါပြီ ✓"
                                            )
                                        }
                                        .testTag("flashcard_copy_btn")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text("Copy", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .clickable {
                                            StudyActionHelper.openGoogleTranslate(context, card.kanji, sourceLang = "ja", targetLang = "my")
                                        }
                                        .testTag("flashcard_translate_btn")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(Icons.Default.Translate, contentDescription = "Translate", modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.primary)
                                        Text("Translate", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = JapaneseCrimson.copy(alpha = 0.12f),
                                    modifier = Modifier
                                        .clickable {
                                            StudyActionHelper.openJisho(context, card.kanji)
                                        }
                                        .testTag("flashcard_jisho_btn")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(Icons.Default.OpenInNew, contentDescription = "jisho", modifier = Modifier.size(13.dp), tint = JapaneseCrimson)
                                        Text("jisho.org", style = MaterialTheme.typography.labelSmall, color = JapaneseCrimson)
                                    }
                                }

                                if (onOpenCompoundLookup != null && StudyActionHelper.extractKanjiCharacters(card.kanji).isNotEmpty()) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.65f),
                                        modifier = Modifier
                                            .clickable { onOpenCompoundLookup() }
                                            .testTag("flashcard_compound_btn")
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text("တွဲလုံး", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Prominent Button to Reveal the Burmese Meaning
                            Button(
                                onClick = onFlip,
                                modifier = Modifier
                                    .fillMaxWidth(0.92f)
                                    .height(48.dp)
                                    .testTag("reveal_burmese_meaning_button"),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 0.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Visibility,
                                        contentDescription = "Reveal Meaning",
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "အဓိပ္ပာယ် ကြည့်မည် • Reveal Meaning",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    FlashcardStudyMode.MY_TO_JP -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                            ) {
                                Text(
                                    text = "MM → JP • Active Recall",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = card.meaningBurmese,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontSize = (22 * fontScale).sp,
                                    lineHeight = (30 * fontScale).sp
                                ),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            if (card.exampleMeaningBurmese.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = card.exampleMeaningBurmese,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = (13 * fontScale).sp),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            Button(
                                onClick = onFlip,
                                modifier = Modifier
                                    .fillMaxWidth(0.92f)
                                    .height(48.dp)
                                    .testTag("reveal_burmese_meaning_button"),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(Icons.Default.Flip, contentDescription = "Flip", modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Reveal Japanese (ဂျပန်စာ ကြည့်မည်)", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    FlashcardStudyMode.AUDIO_FIRST -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size((64 * fontScale.coerceIn(0.85f, 1.15f)).dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f))
                                    .clickable { onSpeakWord?.invoke() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Headphones,
                                    contentDescription = "Listen",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size((32 * fontScale.coerceIn(0.85f, 1.15f)).dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Listen carefully to the word",
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = (15 * fontScale).sp),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Tap to replay audio, or flip to verify",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = (11 * fontScale).sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = onFlip,
                                modifier = Modifier
                                    .fillMaxWidth(0.92f)
                                    .height(48.dp)
                                    .testTag("reveal_burmese_meaning_button"),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Check Answer • စစ်ဆေးမည်", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    FlashcardStudyMode.COLLOCATION -> {
                        val collocation = remember(card.kanji) {
                            CollocationData.getCollocationsForWord(card.kanji).firstOrNull()
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        modifier = Modifier.size(13.dp),
                                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                    Text(
                                        text = "တွဲလုံး လေ့ကျင့်ခန်း • Collocation Pairing",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            val clozePrompt = collocation?.clozePrompt ?: if (card.exampleSentence.contains("［")) card.exampleSentence else "${card.kanji} ［ … ］"
                            Text(
                                text = clozePrompt,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontFamily = JapaneseFontFamily,
                                    fontSize = (26 * fontScale).sp,
                                    lineHeight = (34 * fontScale).sp
                                ),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = card.meaningBurmese,
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = (15 * fontScale).sp),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            Button(
                                onClick = onFlip,
                                modifier = Modifier
                                    .fillMaxWidth(0.92f)
                                    .height(48.dp)
                                    .testTag("reveal_burmese_meaning_button"),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(Icons.Default.Flip, contentDescription = "Flip", modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Reveal Pairing • တွဲလုံး အဖြေကြည့်မည်", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                // Bottom Hint & Section Title
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (card.sectionTitle.isNotBlank()) {
                        Text(
                            text = card.sectionTitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Tap card or button to flip • Swipe right for Good",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Swipe Feedback Overlays for Spaced Repetition (SRS)
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
                    color = MaterialTheme.colorScheme.primary.copy(alpha = (-dragRatio * 0.85f).coerceIn(0.2f, 0.9f)),
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
}

/**
 * Back side of the Flashcard: Reveals the complete Japanese & Burmese information,
 * example sentences, audio pronunciation, and mnemonic notes.
 */
@Composable
private fun StudyFlashcardBackSide(
    card: VocabCard,
    studyMode: FlashcardStudyMode,
    fontScale: Float,
    isSpeaking: Boolean,
    onFlip: () -> Unit,
    onSpeakWord: (() -> Unit)?,
    onSpeakReading: (() -> Unit)?,
    onSpeakSlow: (() -> Unit)?,
    onSpeakSentence: (() -> Unit)?,
    onToggleBookmark: (() -> Unit)?,
    onOpenNoteDialog: (() -> Unit)?,
    onOpenCompoundLookup: (() -> Unit)?
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxSize()
            .testTag("flashcard_back_surface"),
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
            // Header Row: Revealed Answer Badge + Mnemonic Note Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                ) {
                    Text(
                        text = "Revealed Answer (အဖြေ)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (onOpenNoteDialog != null) {
                        IconButton(
                            onClick = onOpenNoteDialog,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Mnemonic Note",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    if (onToggleBookmark != null) {
                        IconButton(
                            onClick = onToggleBookmark,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (card.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // Word Section: Reading & Main Word Display
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                if (card.reading.isNotBlank()) {
                    Text(
                        text = card.reading,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = (15 * fontScale).sp,
                            lineHeight = (20 * fontScale).sp
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }

                val baseKanjiSize = if (card.kanji.length > 5) 24f else if (card.kanji.length > 3) 28f else 34f
                Text(
                    text = card.kanji,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = (baseKanjiSize * fontScale).sp,
                        lineHeight = ((baseKanjiSize + 6f) * fontScale).sp
                    ),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.testTag("flashcard_revealed_japanese_text")
                )

                // Audio Controls on Back Face
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (onSpeakWord != null) {
                        IconButton(
                            onClick = onSpeakWord,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    if (isSpeaking) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                                    CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Speak Pronunciation",
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    if (card.reading.isNotBlank() && onSpeakReading != null) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                            modifier = Modifier.clickable { onSpeakReading() }
                        ) {
                            Text(
                                text = "🗣️ Kana",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    if (onSpeakSlow != null) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.clickable { onSpeakSlow() }
                        ) {
                            Text(
                                text = "🐢 0.7x",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Burmese Meaning Box
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "မြန်မာအဓိပ္ပာယ်",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = card.meaningBurmese,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = (16 * fontScale).sp,
                            lineHeight = (22 * fontScale).sp
                        ),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Example Sentence Box (if available)
            if (card.exampleSentence.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "例文 (Example Sentence)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            if (onSpeakSentence != null) {
                                IconButton(
                                    onClick = onSpeakSentence,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "Speak Sentence",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = card.exampleSentence,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = (13 * fontScale).sp),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )

                        if (card.exampleMeaningBurmese.isNotBlank()) {
                            Text(
                                text = card.exampleMeaningBurmese,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = (12 * fontScale).sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Collocations & Compound Words (တွဲလုံးများ) Section
            val collocations = remember(card.kanji) {
                CollocationData.getCollocationsForWord(card.kanji)
            }
            if (collocations.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .testTag("flashcard_collocations_section")
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
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "ဆက်စပ် တွဲလုံးများ (Collocations)",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${collocations.size} ခု",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        collocations.take(3).forEach { item ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
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
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(
                                                text = item.phraseJapanese,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontFamily = JapaneseFontFamily,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = "(${item.reading})",
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontFamily = JapaneseFontFamily,
                                                    fontSize = 11.sp
                                                ),
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Text(
                                            text = item.meaningBurmese,
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            StudyActionHelper.speakText(context, item.reading.ifBlank { item.phraseJapanese })
                                        },
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
                    }
                }
            }

            // Personal Mnemonic Note (if present)
            if (card.personalNote.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.35f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = card.personalNote,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = (11 * fontScale).sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Action Tools on Back: Copy Full Card, Google Translate, Jisho.org
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.clickable {
                        StudyActionHelper.copyFullCard(context, card)
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy Card", modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Copy Full", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.clickable {
                        StudyActionHelper.openGoogleTranslate(context, card.kanji, sourceLang = "ja", targetLang = "my")
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.Translate, contentDescription = "Translate", modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.primary)
                        Text("Translate", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = JapaneseCrimson.copy(alpha = 0.12f),
                    modifier = Modifier.clickable {
                        StudyActionHelper.openJisho(context, card.kanji)
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.OpenInNew, contentDescription = "jisho", modifier = Modifier.size(13.dp), tint = JapaneseCrimson)
                        Text("jisho.org", style = MaterialTheme.typography.labelSmall, color = JapaneseCrimson)
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Flip Back Button
            OutlinedButton(
                onClick = onFlip,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(44.dp)
                    .testTag("flip_back_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Flip,
                        contentDescription = "Flip Back",
                        modifier = Modifier.size(16.dp)
                    )
                    Text("မူလသို့ • Flip Back to Front", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
