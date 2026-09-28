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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Speed
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
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VocabCard
import com.example.data.util.CollocationData
import com.example.data.util.CollocationItem
import com.example.ui.components.FlippableCardContainer
import com.example.ui.components.KanjiCompoundLookupSheet
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.JapaneseFontFamily
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.SakuraPinkDark
import com.example.ui.util.StudyActionHelper
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

private enum class FlashcardDeckFilter(val label: String, val burmeseLabel: String) {
    ALL("All Cards", "ဝေါဟာရ အားလုံး"),
    COLLOCATIONS("Collocations", "⚡ တွဲလုံးများ"),
    BOOKMARKED("Bookmarked", "⭐ မှတ်ထားသော")
}

/**
 * A dedicated Jetpack Compose screen that displays an interactive 3D flippable flashcard
 * for Kanji & Vocabulary study with integrated Collocations (တွဲလုံးများ / 連語・コロケーション) support.
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

    var selectedFilter by remember { mutableStateOf(FlashcardDeckFilter.ALL) }
    var showCompoundSheet by remember { mutableStateOf(false) }

    val baseDeck = remember(viewModelCards, allCards, initialCards) {
        when {
            viewModelCards.isNotEmpty() -> viewModelCards
            initialCards != null && initialCards.isNotEmpty() -> initialCards
            allCards.isNotEmpty() -> allCards
            else -> SAMPLE_N3_FLASHCARDS
        }
    }

    var deck by remember(baseDeck, selectedFilter) {
        val list = when (selectedFilter) {
            FlashcardDeckFilter.ALL -> baseDeck
            FlashcardDeckFilter.COLLOCATIONS -> CollocationData.getAllCollocationCards()
            FlashcardDeckFilter.BOOKMARKED -> baseDeck.filter { it.isBookmarked }.ifEmpty { baseDeck }
        }
        mutableStateOf(list)
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    var showRomajiHint by remember { mutableStateOf(false) }

    val currentCard = deck.getOrNull(currentIndex) ?: SAMPLE_N3_FLASHCARDS.first()

    // Reset state on deck change
    LaunchedEffect(selectedFilter) {
        currentIndex = 0
        isFlipped = false
        showRomajiHint = false
    }

    fun goToNextCard() {
        if (currentIndex < deck.size - 1) {
            isFlipped = false
            showRomajiHint = false
            currentIndex++
        }
    }

    fun goToPrevCard() {
        if (currentIndex > 0) {
            isFlipped = false
            showRomajiHint = false
            currentIndex--
        }
    }

    fun shuffleDeck() {
        deck = deck.shuffled()
        currentIndex = 0
        isFlipped = false
        showRomajiHint = false
    }

    fun restartDeck() {
        currentIndex = 0
        isFlipped = false
        showRomajiHint = false
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
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "3D Flashcard Study",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "တွဲလုံးပါ",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = if (selectedFilter == FlashcardDeckFilter.COLLOCATIONS) "တွဲလုံးများ လေ့ကျင့်ခန်း • Collocations & Compounds" else "JLPT N3 Kanji • Flip 3D to reveal",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    if (onNavigateBack != null) {
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier
                                .testTag("flashcard_back_btn")
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Navigate Back"
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
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Study Mode Filter Tabs (All / တွဲလုံးများ / Bookmarks)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FlashcardDeckFilter.values().forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = {
                            if (selectedFilter != filter) {
                                selectedFilter = filter
                            }
                        },
                        label = {
                            Text(
                                text = filter.burmeseLabel,
                                fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.5.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Deck Progress Header
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
                        text = "Card ${currentIndex + 1} of ${deck.size}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = currentCard.lessonTitle.ifBlank { "Lesson ${currentCard.lessonNumber}" },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / deck.size.coerceAtLeast(1) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main 3D Flippable Flashcard
            FlippableCardContainer(
                isFlipped = isFlipped,
                onFlip = { isFlipped = !isFlipped },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
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
                    .testTag("flashcard_card_container"),
                frontContent = {
                    FlashcardFrontView(
                        card = currentCard,
                        showRomaji = showRomajiHint,
                        onToggleRomaji = { showRomajiHint = !showRomajiHint },
                        onSpeak = { ttsHelper.speakCard(currentCard) },
                        onBookmark = { toggleBookmark() },
                        onFlip = { isFlipped = true },
                        isCollocationDeck = selectedFilter == FlashcardDeckFilter.COLLOCATIONS,
                        allCards = allCards
                    )
                },
                backContent = {
                    FlashcardBackView(
                        card = currentCard,
                        allCards = allCards,
                        onSpeak = { ttsHelper.speakCard(currentCard) },
                        onSpeakReading = { ttsHelper.speakPhonetic(currentCard.reading.ifBlank { currentCard.kanji }) },
                        onSpeakSlow = { ttsHelper.speakSlow(currentCard.reading.ifBlank { currentCard.kanji }) },
                        onSpeakSentence = { ttsHelper.speakSentence(currentCard.exampleSentence) },
                        onBookmark = { toggleBookmark() },
                        onFlip = { isFlipped = false },
                        onOpenCompoundLookup = { showCompoundSheet = true },
                        onSpeakCollocation = { phrase -> ttsHelper.speak(phrase) }
                    )
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Navigation Controls Bar (Mobile-optimized with spacious Center Flip button)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Previous Button (Compact on phone)
                OutlinedButton(
                    onClick = { goToPrevCard() },
                    enabled = currentIndex > 0,
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .testTag("flashcard_prev_btn")
                        .size(width = 54.dp, height = 50.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous Card",
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Center 3D Flip Button (Wide, prominent touch target)
                Button(
                    onClick = { isFlipped = !isFlipped },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFlipped) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                        contentColor = if (isFlipped) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("flashcard_flip_btn")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Flip,
                            contentDescription = "Flip Icon",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = if (isFlipped) "ရှေ့သို့ ပြန်လှန်မည် ↻ (Front)" else "ကတ်လှန်ကြည့်မည် ↻ (Flip 3D)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            maxLines = 1
                        )
                    }
                }

                // Next Button (Compact on phone)
                FilledTonalButton(
                    onClick = { goToNextCard() },
                    enabled = currentIndex < deck.size - 1,
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .testTag("flashcard_next_btn")
                        .size(width = 54.dp, height = 50.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next Card",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }

    // Kanji Compounds & Radical Lookup Bottom Sheet
    if (showCompoundSheet) {
        KanjiCompoundLookupSheet(
            card = currentCard,
            allCards = allCards,
            onDismiss = { showCompoundSheet = false },
            onSpeak = { phrase -> ttsHelper.speak(phrase) }
        )
    }
}

/**
 * Front side of the 3D Kanji Flashcard:
 * - Premium Japanese tactile card with subtle gradient & Kanji watermark depth
 * - Level & Lesson badge, Part of speech pill, and quick audio & bookmark actions
 * - Interactive Furigana / Reading Peek Pill
 * - Prominent center Kanji glyph with Japanese typography & accent divider
 * - Collocations (တွဲလုံးများ) preview section with direct pronunciation speaker
 * - 3D Flip affordance button
 */
@Composable
private fun FlashcardFrontView(
    card: VocabCard,
    showRomaji: Boolean,
    onToggleRomaji: () -> Unit,
    onSpeak: () -> Unit,
    onBookmark: () -> Unit,
    onFlip: () -> Unit,
    isCollocationDeck: Boolean = false,
    allCards: List<VocabCard> = emptyList(),
    modifier: Modifier = Modifier
) {
    val collocations = remember(card.kanji) {
        CollocationData.getCollocationsForWord(card.kanji)
    }

    val relatedCompounds = remember(card.kanji, allCards) {
        if (collocations.isEmpty() && card.kanji.isNotBlank()) {
            val kanjiChar = card.kanji.firstOrNull { it.toString().matches(Regex("[\\u4e00-\\u9faf]")) }
            if (kanjiChar != null) {
                StudyActionHelper.findRelatedCompounds(kanjiChar, allCards).filter { it.id != card.id }.take(3)
            } else emptyList()
        } else emptyList()
    }

    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.28f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.32f)
                        )
                    )
                )
        ) {
            // Subtle Kanji Watermark in background for depth
            if (card.kanji.isNotBlank()) {
                Text(
                    text = card.kanji.take(2),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = JapaneseFontFamily,
                        fontSize = 110.sp,
                        fontWeight = FontWeight.Black
                    ),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // 1. Top Badges & Action Controls Bar
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
                            shape = RoundedCornerShape(10.dp),
                            color = if (isCollocationDeck) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer,
                            border = BorderStroke(1.dp, if (isCollocationDeck) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                if (isCollocationDeck) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        modifier = Modifier.size(13.dp),
                                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                                Text(
                                    text = if (isCollocationDeck) "JLPT N3 • တွဲလုံး" else "JLPT N3 • L${card.lessonNumber}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCollocationDeck) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }

                        if (card.partOfSpeech.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                            ) {
                                Text(
                                    text = card.partOfSpeech.take(14),
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        FilledIconButton(
                            onClick = onSpeak,
                            shape = CircleShape,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .size(38.dp)
                                .testTag("flashcard_front_audio_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Pronounce Word",
                                modifier = Modifier.size(19.dp)
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
                                tint = if (card.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // 2. Main Center Kanji Focus Area
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    // Interactive Furigana / Reading Peek Pill
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (showRomaji) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f),
                        border = BorderStroke(
                            1.2.dp,
                            if (showRomaji) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onToggleRomaji() }
                            .testTag("flashcard_romaji_toggle")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (showRomaji) Icons.AutoMirrored.Filled.VolumeUp else Icons.Default.Visibility,
                                contentDescription = "Toggle Reading Hint",
                                modifier = Modifier.size(16.dp),
                                tint = if (showRomaji) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = if (showRomaji) "【 ${card.reading} 】" else "ဖတ်နည်း အရိပ်အမြွက် (Tap to Peek Furigana)",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = if (showRomaji) JapaneseFontFamily else null,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = if (showRomaji) 15.sp else 12.sp
                                ),
                                color = if (showRomaji) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }

                    // Display Japanese Kanji in massive, sharp typography
                    val displayKanji = if (isCollocationDeck && card.exampleSentence.contains("［")) card.exampleSentence else card.kanji
                    Text(
                        text = displayKanji,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontFamily = JapaneseFontFamily,
                            fontSize = if (displayKanji.length > 7) 26.sp else if (displayKanji.length > 4) 34.sp else 48.sp,
                            lineHeight = if (displayKanji.length > 7) 32.sp else 54.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("flashcard_front_kanji")
                    )

                    // Accent Underline Bar
                    Box(
                        modifier = Modifier
                            .width(52.dp)
                            .height(3.5.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        JapaneseCrimson,
                                        MaterialTheme.colorScheme.primary
                                    )
                                )
                            )
                    )

                    // Collocations (တွဲလုံးများ) Preview Section on Front
                    if (collocations.isNotEmpty() && !isCollocationDeck) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.55f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp),
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                    Text(
                                        text = "တွဲလုံး (Collocations) ${collocations.size} ခု ပါရှိသည်",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                                val sample = collocations.first()
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${sample.phraseJapanese} (${sample.reading})",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontFamily = JapaneseFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = sample.meaningBurmese,
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    } else if (relatedCompounds.isNotEmpty() && !isCollocationDeck) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = "ဆက်စပ် တွဲလုံး: ${relatedCompounds.joinToString(" • ") { "${it.kanji} (${it.meaningBurmese})" }}",
                                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = JapaneseFontFamily, fontSize = 11.5.sp),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }

                // 3. Bottom 3D Flip Prompt Affordance
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                    border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.32f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onFlip() }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 14.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(19.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ကတ်လှန်၍ အဓိပ္ပာယ် & တွဲလုံးများ ကြည့်ပါ ↻ (Tap to Flip 3D)",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

/**
 * Back side of the 3D Kanji Flashcard:
 * 1. Unified Furigana Reading + Kanji Header with standard & slow audio
 * 2. High-contrast Burmese Meaning (မြန်မာအဓိပ္ပာယ်)
 * 3. Comprehensive တွဲလုံးများ (Collocations & Compounds) Section with individual pronunciation
 * 4. Example sentence with audio
 * 5. Quick Tools (Copy, Translate, Jisho, Kanji Compound Sheet)
 */
@Composable
private fun FlashcardBackView(
    card: VocabCard,
    allCards: List<VocabCard> = emptyList(),
    onSpeak: () -> Unit,
    onSpeakReading: () -> Unit,
    onSpeakSlow: (() -> Unit)? = null,
    onSpeakSentence: () -> Unit,
    onBookmark: () -> Unit,
    onFlip: () -> Unit,
    onOpenCompoundLookup: () -> Unit,
    onSpeakCollocation: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val collocations = remember(card.kanji) {
        CollocationData.getCollocationsForWord(card.kanji)
    }

    val relatedCompounds = remember(card.kanji, allCards) {
        if (collocations.isEmpty() && card.kanji.isNotBlank()) {
            val kanjiChar = card.kanji.firstOrNull { it.toString().matches(Regex("[\\u4e00-\\u9faf]")) }
            if (kanjiChar != null) {
                StudyActionHelper.findRelatedCompounds(kanjiChar, allCards).filter { it.id != card.id }.take(3)
            } else emptyList()
        } else emptyList()
    }

    Card(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.38f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Top Answer Banner & Audio Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MasteredGreen.copy(alpha = 0.16f),
                        border = BorderStroke(1.dp, MasteredGreen.copy(alpha = 0.35f))
                    ) {
                        Text(
                            text = "✨ အဖြေနှင့် ရှင်းလင်းချက် (Revealed)",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MasteredGreen,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Standard Pronunciation
                        FilledIconButton(
                            onClick = onSpeakReading,
                            shape = CircleShape,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("flashcard_back_audio_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Speak Word",
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Slow Pronunciation
                        if (onSpeakSlow != null) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { onSpeakSlow() }
                                    .testTag("flashcard_back_slow_audio_btn")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Speed,
                                        contentDescription = "Slow Pronunciation",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "0.7x",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        // Bookmark
                        IconButton(
                            onClick = onBookmark,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = if (card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (card.isBookmarked) SakuraPinkDark else MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(19.dp)
                            )
                        }
                    }
                }

                // 1. Reading (Furigana / Hiragana) & Kanji Card
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.38f),
                    border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { onSpeakReading() }
                        .testTag("flashcard_revealed_reading")
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "【 ${card.reading} 】",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = JapaneseFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = card.kanji,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = JapaneseFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = null,
                                modifier = Modifier.size(13.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "အသံထွက် နားဆင်ရန် နှိပ်ပါ (Tap to pronounce)",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                            )
                        }
                    }
                }

                // 2. Burmese Meaning Box (မြန်မာအဓိပ္ပာယ်)
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f),
                    border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("flashcard_revealed_burmese")
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = "မြန်မာအဓိပ္ပာယ် (Meaning)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        Text(
                            text = card.meaningBurmese,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )

                        if (card.partOfSpeech.isNotBlank()) {
                            Text(
                                text = card.partOfSpeech,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // 3. COLLOCATIONS & COMPOUNDS (တွဲလုံးများ / 連語・コロケーション) SECTION
                if (collocations.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.38f),
                        border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("flashcard_collocations_section")
                    ) {
                        Column(
                            modifier = Modifier.padding(13.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "ဆက်စပ် တွဲလုံးများ (Collocations)",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.18f)
                                ) {
                                    Text(
                                        text = "${collocations.size} ခု",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            collocations.take(3).forEach { item ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.14f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp, vertical = 8.dp),
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
                                                        fontSize = 11.5.sp
                                                    ),
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = item.meaningBurmese,
                                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        if (onSpeakCollocation != null) {
                                            IconButton(
                                                onClick = { onSpeakCollocation(item.reading.ifBlank { item.phraseJapanese }) },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                                    contentDescription = "Pronounce Collocation",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(17.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (relatedCompounds.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.38f),
                        border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("flashcard_related_compounds_section")
                    ) {
                        Column(
                            modifier = Modifier.padding(13.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "ဆက်စပ် Kanji တွဲလုံးများ (Compounds)",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.18f)
                                ) {
                                    Text(
                                        text = "${relatedCompounds.size} ခု",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            relatedCompounds.forEach { compound ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.14f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Text(
                                                    text = compound.kanji,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        fontFamily = JapaneseFontFamily,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                Text(
                                                    text = "(${compound.reading})",
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        fontFamily = JapaneseFontFamily,
                                                        fontSize = 11.5.sp
                                                    ),
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = compound.meaningBurmese,
                                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        if (onSpeakCollocation != null) {
                                            IconButton(
                                                onClick = { onSpeakCollocation(compound.reading.ifBlank { compound.kanji }) },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                                    contentDescription = "Pronounce Compound",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(17.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 4. Example Sentence (if present)
                if (card.exampleSentence.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.14f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(13.dp),
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
                                    modifier = Modifier.size(26.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "Speak sentence",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Text(
                                text = card.exampleSentence,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = JapaneseFontFamily,
                                    fontSize = 14.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            if (card.exampleMeaningBurmese.isNotBlank()) {
                                Text(
                                    text = card.exampleMeaningBurmese,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.5.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // 5. Quick Tools Action Row (Copy, Translate, Jisho, Kanji Compound Sheet)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Copy Card
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                StudyActionHelper.copyToClipboard(
                                    context = context,
                                    text = "${card.kanji}【${card.reading}】\nအဓိပ္ပာယ်: ${card.meaningBurmese}\nဝါကျ: ${card.exampleSentence}",
                                    label = "Kanji Card",
                                    toastMessage = "${card.kanji} ကို Copy ကူးပြီးပါပြီ ✓"
                                )
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 9.dp, horizontal = 6.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Copy", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    // Google Translate
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
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
                            modifier = Modifier.padding(vertical = 9.dp, horizontal = 6.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Translate, contentDescription = "Translate", modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Translate", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.primary)
                        }
                    }

                    // Jisho.org
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = JapaneseCrimson.copy(alpha = 0.12f),
                        border = BorderStroke(1.dp, JapaneseCrimson.copy(alpha = 0.25f)),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                StudyActionHelper.openJisho(context = context, query = card.kanji)
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 9.dp, horizontal = 6.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.OpenInNew, contentDescription = "Jisho", modifier = Modifier.size(13.dp), tint = JapaneseCrimson)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("jisho", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = JapaneseCrimson)
                        }
                    }

                    // Kanji Compound Explorer Sheet
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.55f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .weight(1.15f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onOpenCompoundLookup() }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 9.dp, horizontal = 6.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = "Compounds", modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.tertiary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("တွဲလုံးရှာ", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                }

                // 6. Flip Back Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onFlip() }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 11.dp, horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ရှေ့မျက်နှာပြင်သို့ ပြန်လှန်ရန် နှိပ်ပါ (Flip back to Front)",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
