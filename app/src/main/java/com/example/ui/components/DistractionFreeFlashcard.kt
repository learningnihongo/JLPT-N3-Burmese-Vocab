package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VocabCard
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.SakuraPinkDark

/**
 * A clean, distraction-free flashcard component that displays the Japanese Kanji
 * and provides a prominent button to reveal the Burmese meaning.
 *
 * Designed with generous whitespace, crisp typography, and serene aesthetics
 * to maximize retention without cognitive clutter.
 */
@Composable
fun DistractionFreeFlashcard(
    kanji: String,
    meaningBurmese: String,
    reading: String = "",
    partOfSpeech: String = "",
    lessonNumber: Int = 1,
    exampleSentence: String = "",
    exampleMeaningBurmese: String = "",
    isBookmarked: Boolean = false,
    fontScale: Float = 1.0f,
    showReadingInitially: Boolean = true,
    isRevealed: Boolean? = null,
    onRevealToggle: (() -> Unit)? = null,
    onPlayAudio: (() -> Unit)? = null,
    onToggleBookmark: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    // Internal reveal state fallback if not controlled externally
    var internalRevealed by remember { mutableStateOf(false) }
    val revealed = isRevealed ?: internalRevealed
    val toggleReveal = onRevealToggle ?: { internalRevealed = !internalRevealed }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("distraction_free_flashcard"),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.55f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 1. Minimal Header: Lesson indicator, Part of Speech, Audio & Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Lesson Badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
                    ) {
                        Text(
                            text = "Lesson $lessonNumber",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                        )
                    }

                    // Part of speech (e.g. 名詞, 動詞)
                    if (partOfSpeech.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                        ) {
                            Text(
                                text = partOfSpeech,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Header Utility Actions: Audio Pronunciation and Bookmark
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (onPlayAudio != null) {
                        IconButton(
                            onClick = onPlayAudio,
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("flashcard_audio_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Listen to pronunciation",
                                tint = JapaneseCrimson,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    if (onToggleBookmark != null) {
                        IconButton(
                            onClick = onToggleBookmark,
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("flashcard_bookmark_button")
                        ) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = if (isBookmarked) "Bookmarked" else "Bookmark",
                                tint = if (isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }

            // 2. Focused Centerpiece: Prominent Japanese Kanji & Reading
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Kana reading (furigana) with gentle contrast
                if (showReadingInitially && reading.isNotBlank()) {
                    Text(
                        text = reading,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = (15 * fontScale).sp,
                            lineHeight = (20 * fontScale).sp
                        ),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .testTag("flashcard_kanji_reading")
                    )
                }

                // HERO: Japanese Kanji in striking, uncluttered typography
                val baseKanjiSize = when {
                    kanji.length > 5 -> 28f
                    kanji.length > 3 -> 36f
                    else -> 46f
                }
                Text(
                    text = kanji,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = (baseKanjiSize * fontScale).sp,
                        lineHeight = ((baseKanjiSize + 8f) * fontScale).sp
                    ),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .testTag("flashcard_kanji_hero")
                )

                // 3. Revealed Burmese Meaning Section (Smooth Animated Reveal)
                AnimatedVisibility(
                    visible = revealed,
                    enter = fadeIn(tween(250)) + expandVertically(spring(dampingRatio = 0.8f, stiffness = 400f)),
                    exit = fadeOut(tween(180)) + shrinkVertically(spring(dampingRatio = 0.8f, stiffness = 400f))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .testTag("flashcard_burmese_meaning_container")
                    ) {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = JapaneseCrimson.copy(alpha = 0.08f),
                            border = BorderStroke(1.dp, JapaneseCrimson.copy(alpha = 0.25f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp, vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "မြန်မာအဓိပ္ပာယ်",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = JapaneseCrimson,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.5.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                // Clear, high-contrast Burmese Meaning
                                Text(
                                    text = meaningBurmese,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontSize = (21 * fontScale).sp,
                                        lineHeight = (30 * fontScale).sp
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.testTag("flashcard_burmese_meaning_text")
                                )

                                // Optional Example sentence with Burmese translation if present
                                val hasCustomExample = exampleSentence.isNotBlank() &&
                                        exampleSentence != "【$kanji】$meaningBurmese"
                                if (hasCustomExample) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    HorizontalDivider(
                                        color = JapaneseCrimson.copy(alpha = 0.15f),
                                        thickness = 0.75.dp
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = exampleSentence,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = (13 * fontScale).sp,
                                            lineHeight = (18 * fontScale).sp
                                        ),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                    if (exampleMeaningBurmese.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = exampleMeaningBurmese,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = (12 * fontScale).sp,
                                                lineHeight = (17 * fontScale).sp
                                            ),
                                            color = MaterialTheme.colorScheme.outline,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. THE HERO BUTTON: Provides a button to reveal the Burmese meaning
            Button(
                onClick = toggleReveal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("reveal_burmese_meaning_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (revealed) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        JapaneseCrimson
                    },
                    contentColor = if (revealed) {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    } else {
                        Color.White
                    }
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = if (revealed) 0.dp else 2.dp,
                    pressedElevation = 1.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (revealed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (revealed) "Hide Burmese Meaning" else "Reveal Burmese Meaning",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (revealed) {
                            "အဓိပ္ပာယ် ဝှက်မည် • Hide Meaning"
                        } else {
                            "အဓိပ္ပာယ် ကြည့်မည် • Reveal Meaning"
                        },
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/**
 * Convenience overload accepting a [VocabCard] entity.
 */
@Composable
fun DistractionFreeFlashcard(
    card: VocabCard,
    isRevealed: Boolean? = null,
    onRevealToggle: (() -> Unit)? = null,
    onPlayAudio: (() -> Unit)? = null,
    onToggleBookmark: (() -> Unit)? = null,
    fontScale: Float = 1.0f,
    showReadingInitially: Boolean = true,
    modifier: Modifier = Modifier
) {
    DistractionFreeFlashcard(
        kanji = card.kanji,
        meaningBurmese = card.meaningBurmese,
        reading = card.reading,
        partOfSpeech = card.partOfSpeech,
        lessonNumber = card.lessonNumber,
        exampleSentence = card.exampleSentence,
        exampleMeaningBurmese = card.exampleMeaningBurmese,
        isBookmarked = card.isBookmarked,
        fontScale = fontScale,
        showReadingInitially = showReadingInitially,
        isRevealed = isRevealed,
        onRevealToggle = onRevealToggle,
        onPlayAudio = onPlayAudio,
        onToggleBookmark = onToggleBookmark,
        modifier = modifier
    )
}
