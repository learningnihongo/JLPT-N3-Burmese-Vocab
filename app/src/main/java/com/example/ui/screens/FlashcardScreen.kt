package com.example.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
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
import com.example.ui.theme.SakuraPinkDark
import com.example.ui.util.TtsHelper
import com.example.ui.viewmodel.VocabViewModel

/**
 * Curated sample N3 vocabulary cards for standalone preview or instant fallback.
 */
val SAMPLE_N3_FLASHCARDS = listOf(
    VocabCard(
        id = 1L,
        lessonNumber = 1,
        lessonTitle = "人間関係 (Human Relations)",
        sectionTitle = "Daily Vocab",
        kanji = "約束",
        reading = "やくそく",
        meaningBurmese = "ကတိ၊ ချိန်းဆိုချက်",
        partOfSpeech = "名詞・スル動詞 (Noun / Suru Verb)",
        exampleSentence = "友達と映画を見る約束をしました。",
        exampleMeaningBurmese = "သူငယ်ချင်းနှင့် ရုပ်ရှင်ကြည့်ရန် ချိန်းဆိုခဲ့သည်။"
    ),
    VocabCard(
        id = 2L,
        lessonNumber = 1,
        lessonTitle = "人間関係 (Human Relations)",
        sectionTitle = "Daily Vocab",
        kanji = "案内",
        reading = "あんない",
        meaningBurmese = "လမ်းညွှန်ပြသခြင်း၊ အသိပေးခြင်း",
        partOfSpeech = "名詞・スル動詞 (Noun / Suru Verb)",
        exampleSentence = "東京の観光名所を案内します。",
        exampleMeaningBurmese = "တိုကျိုမြို့၏ အထင်ကရနေရာများကို လမ်းညွှန်ပြသပေးပါမည်။"
    ),
    VocabCard(
        id = 3L,
        lessonNumber = 1,
        lessonTitle = "人間関係 (Human Relations)",
        sectionTitle = "Daily Vocab",
        kanji = "遠慮",
        reading = "えんりょ",
        meaningBurmese = "အားနာခြင်း၊ ချင့်ချိန်တွန့်ဆုတ်ခြင်း",
        partOfSpeech = "名詞・スル動詞 (Noun / Suru Verb)",
        exampleSentence = "どうぞ遠慮しないで召し上がってください。",
        exampleMeaningBurmese = "ကျေးဇူးပြု၍ အားမနာဘဲ သုံးဆောင်ပါ။"
    ),
    VocabCard(
        id = 4L,
        lessonNumber = 2,
        lessonTitle = "暮らしと社会 (Daily Life & Society)",
        sectionTitle = "Social Life",
        kanji = "相談",
        reading = "そうだん",
        meaningBurmese = "တိုင်ပင်ဆွေးနွေးခြင်း၊ အကြံဉာဏ်တောင်းခြင်း",
        partOfSpeech = "名詞・スル動詞 (Noun / Suru Verb)",
        exampleSentence = "進路について先生に相談しました。",
        exampleMeaningBurmese = "အနာဂတ်ရှေ့ရေးနှင့် ပတ်သက်၍ ဆရာနှင့် တိုင်ပင်ဆွေးနွေးခဲ့သည်။"
    ),
    VocabCard(
        id = 5L,
        lessonNumber = 2,
        lessonTitle = "暮らしと社会 (Daily Life & Society)",
        sectionTitle = "Social Life",
        kanji = "経験",
        reading = "けいけん",
        meaningBurmese = "အတွေ့အကြုံ၊ လက်တွေ့ခံစားဖူးခြင်း",
        partOfSpeech = "名詞・スル動詞 (Noun / Suru Verb)",
        exampleSentence = "海外でのボランティア活動は良い経験になりました。",
        exampleMeaningBurmese = "နိုင်ငံခြားတွင် စေတနာ့ဝန်ထမ်းလုပ်ခြင်းသည် ကောင်းသော အတွေ့အကြုံဖြစ်ခဲ့သည်။"
    )
)

/**
 * A dedicated Jetpack Compose screen that displays an interactive 3D flippable flashcard.
 *
 * Front Side: Shows the Japanese word/kanji with lesson and part of speech metadata.
 * Back Side: Revealed via a smooth flip animation to show the Burmese meaning, Hiragana reading,
 *            and example sentence.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(
    vocabViewModel: VocabViewModel? = null,
    initialCards: List<VocabCard>? = null,
    onNavigateBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val ttsHelper = remember { TtsHelper(context) }

    // Cards list determination
    val viewModelCards by (vocabViewModel?.studyDeck?.collectAsState() ?: remember { mutableStateOf<List<VocabCard>>(emptyList()) })
    val allCards by (vocabViewModel?.allCards?.collectAsState() ?: remember { mutableStateOf<List<VocabCard>>(emptyList()) })

    var deck by remember(viewModelCards, allCards, initialCards) {
        val list = when {
            viewModelCards.isNotEmpty() -> viewModelCards
            initialCards != null && initialCards.isNotEmpty() -> initialCards
            allCards.isNotEmpty() -> allCards
            else -> SAMPLE_N3_FLASHCARDS
        }
        mutableStateOf(list)
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    var showRomajiHint by remember { mutableStateOf(false) }

    val currentCard = deck.getOrNull(currentIndex) ?: SAMPLE_N3_FLASHCARDS.first()

    // 3D Flip animation state
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 380f),
        label = "flashcard_3d_flip"
    )

    // Reset flip when switching cards
    fun goToNextCard() {
        if (currentIndex < deck.size - 1) {
            isFlipped = false
            currentIndex++
        }
    }

    fun goToPrevCard() {
        if (currentIndex > 0) {
            isFlipped = false
            currentIndex--
        }
    }

    fun shuffleDeck() {
        deck = deck.shuffled()
        currentIndex = 0
        isFlipped = false
    }

    fun restartDeck() {
        currentIndex = 0
        isFlipped = false
    }

    fun toggleBookmark() {
        vocabViewModel?.toggleBookmark(currentCard)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Flashcard Study",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "JLPT N3 Vocabulary • Flip to reveal",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (onNavigateBack != null) {
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier
                                .testTag("flashcard_screen_back_btn")
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go Back"
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { shuffleDeck() },
                        modifier = Modifier
                            .testTag("flashcard_shuffle_btn")
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Shuffle Deck"
                        )
                    }
                    IconButton(
                        onClick = { restartDeck() },
                        modifier = Modifier
                            .testTag("flashcard_restart_btn")
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Replay,
                            contentDescription = "Restart Deck"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Deck Progress Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Card ${currentIndex + 1} of ${deck.size}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Lesson ${currentCard.lessonNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / deck.size.coerceAtLeast(1) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = JapaneseCrimson,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main 3D Flippable Flashcard
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 16f * density
                    }
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            change.consume()
                            if (dragAmount > 50) {
                                goToPrevCard()
                            } else if (dragAmount < -50) {
                                goToNextCard()
                            }
                        }
                    }
                    .clickable { isFlipped = !isFlipped }
                    .testTag("flashcard_card_container"),
                contentAlignment = Alignment.Center
            ) {
                if (rotation <= 90f) {
                    // ================= FRONT SIDE =================
                    FlashcardFrontView(
                        card = currentCard,
                        showRomaji = showRomajiHint,
                        onToggleRomaji = { showRomajiHint = !showRomajiHint },
                        onSpeak = { ttsHelper.speak(currentCard.kanji) },
                        onBookmark = { toggleBookmark() },
                        onFlip = { isFlipped = true }
                    )
                } else {
                    // ================= BACK SIDE =================
                    FlashcardBackView(
                        card = currentCard,
                        onSpeak = { ttsHelper.speak(currentCard.kanji) },
                        onSpeakReading = { ttsHelper.speak(currentCard.reading) },
                        onSpeakSentence = { ttsHelper.speak(currentCard.exampleSentence) },
                        onBookmark = { toggleBookmark() },
                        onFlip = { isFlipped = false },
                        modifier = Modifier.graphicsLayer { rotationY = 180f }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Controls Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous Button
                OutlinedButton(
                    onClick = { goToPrevCard() },
                    enabled = currentIndex > 0,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .testTag("flashcard_prev_btn")
                        .size(width = 96.dp, height = 50.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous Card"
                    )
                }

                // Center Flip Button
                Button(
                    onClick = { isFlipped = !isFlipped },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFlipped) JapaneseIndigo else JapaneseCrimson
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                        .height(52.dp)
                        .testTag("flashcard_flip_btn")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Flip,
                            contentDescription = "Flip Icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = if (isFlipped) "Show Front" else "Flip to Reveal",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }

                // Next Button
                FilledTonalButton(
                    onClick = { goToNextCard() },
                    enabled = currentIndex < deck.size - 1,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .testTag("flashcard_next_btn")
                        .size(width = 96.dp, height = 50.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next Card"
                    )
                }
            }
        }
    }
}

/**
 * Front side of the flashcard: Features the Kanji / Japanese word, Lesson metadata,
 * Part of speech, Romaji hint button, and a clear call-to-action to flip.
 */
@Composable
private fun FlashcardFrontView(
    card: VocabCard,
    showRomaji: Boolean,
    onToggleRomaji: () -> Unit,
    onSpeak: () -> Unit,
    onBookmark: () -> Unit,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Badges Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "JLPT N3 • L${card.lessonNumber}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = onSpeak,
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("flashcard_front_audio_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Pronounce Word",
                            tint = JapaneseCrimson
                        )
                    }

                    IconButton(
                        onClick = onBookmark,
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("flashcard_bookmark_btn")
                    ) {
                        Icon(
                            imageVector = if (card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (card.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            // Main Japanese Kanji / Word Area
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                // Optional Part of speech
                if (card.partOfSpeech.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = card.partOfSpeech,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                // Display Japanese Kanji in prominent typography
                Text(
                    text = card.kanji,
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontSize = if (card.kanji.length > 5) 32.sp else if (card.kanji.length > 3) 40.sp else 48.sp,
                        lineHeight = 54.sp
                    ),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("flashcard_front_kanji")
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Romaji Hint Pill Toggle
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier
                        .clickable { onToggleRomaji() }
                        .testTag("flashcard_romaji_toggle")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (showRomaji) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle Romaji",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = if (showRomaji) "Hint: ${card.reading}" else "Show Reading Hint",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // Bottom Prompt to Flip
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = JapaneseCrimson.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, JapaneseCrimson.copy(alpha = 0.2f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFlip() }
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = null,
                        tint = JapaneseCrimson,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ကတ်ပြားကိုနှိပ်၍ အဓိပ္ပာယ်နှင့် အသံထွက်ကို ကြည့်ပါ (Tap to flip)",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = JapaneseCrimson,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Back side of the flashcard: Prominently reveals:
 * 1. The Reading (Hiragana / Furigana) with audio pronunciation
 * 2. The Burmese Meaning (မြန်မာအဓိပ္ပာယ်)
 * 3. Example sentence (Japanese + Burmese translation)
 */
@Composable
private fun FlashcardBackView(
    card: VocabCard,
    onSpeak: () -> Unit,
    onSpeakReading: () -> Unit,
    onSpeakSentence: () -> Unit,
    onBookmark: () -> Unit,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Answer Banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MasteredGreen.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "✨ Revealed Answer (အဖြေ)",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MasteredGreen,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = onSpeak,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("flashcard_back_audio_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Speak Word",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = onBookmark,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (card.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main Answer Content: Reading + Burmese Meaning
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Reading (Furigana / Hiragana) Box
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = JapaneseIndigo.copy(alpha = 0.08f),
                    border = BorderStroke(1.dp, JapaneseIndigo.copy(alpha = 0.25f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSpeakReading() }
                        .testTag("flashcard_revealed_reading")
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "ဖတ်နည်း (Reading)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = JapaneseIndigo
                            )
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Listen reading",
                                tint = JapaneseIndigo,
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        Text(
                            text = "【 ${card.reading} 】",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = JapaneseIndigo,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = card.kanji,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // 2. Burmese Meaning Box (မြန်မာအဓိပ္ပာယ်)
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = JapaneseCrimson.copy(alpha = 0.08f),
                    border = BorderStroke(1.dp, JapaneseCrimson.copy(alpha = 0.25f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("flashcard_revealed_burmese")
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = null,
                                tint = JapaneseCrimson,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "မြန်မာအဓိပ္ပာယ် (Burmese Meaning)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = JapaneseCrimson
                            )
                        }

                        Text(
                            text = card.meaningBurmese,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // 3. Example Sentence (if present)
                if (card.exampleSentence.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "ဝါကျဥပမာ (Example Sentence)",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                IconButton(
                                    onClick = onSpeakSentence,
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "Speak sentence",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                            }

                            Text(
                                text = card.exampleSentence,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (card.exampleMeaningBurmese.isNotBlank()) {
                                Text(
                                    text = card.exampleMeaningBurmese,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Flip back button
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFlip() }
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ရှေ့မျက်နှာပြင်သို့ ပြန်လှန်ရန် နှိပ်ပါ (Flip back to Front)",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
