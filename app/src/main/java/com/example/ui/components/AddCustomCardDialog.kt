package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.VocabCard
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.PolishPrimary
import com.example.ui.theme.StreakOrange

/**
 * Enhanced Data Input Form for adding or editing custom vocabulary flashcards.
 * Features:
 * - Window-inset & IME (keyboard) responsive layout
 * - Live Flashcard Preview (Front/Back)
 * - Part of speech quick chips (Noun, Verb, Adj, etc.)
 * - Instant Japanese pronunciation audio preview (TTS)
 * - Clear bilingual labels and validation helper messages (Burmese + Japanese)
 * - Category and tag manager with custom tag support
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddCustomCardDialog(
    onDismiss: () -> Unit,
    availableTags: List<String> = emptyList(),
    initialCard: VocabCard? = null,
    onSpeak: ((String) -> Unit)? = null,
    onConfirm: (
        kanji: String,
        reading: String,
        burmese: String,
        pos: String,
        example: String,
        exBurmese: String,
        note: String,
        tags: String
    ) -> Unit
) {
    var kanji by remember { mutableStateOf(initialCard?.kanji ?: "") }
    var reading by remember { mutableStateOf(initialCard?.reading ?: "") }
    var burmese by remember { mutableStateOf(initialCard?.meaningBurmese ?: "") }
    var pos by remember { mutableStateOf(initialCard?.partOfSpeech?.ifBlank { "Noun" } ?: "Noun") }
    var example by remember { mutableStateOf(initialCard?.exampleSentence ?: "") }
    var exBurmese by remember { mutableStateOf(initialCard?.exampleMeaningBurmese ?: "") }
    var note by remember { mutableStateOf(initialCard?.personalNote ?: "") }

    val selectedTags = remember {
        mutableStateListOf<String>().apply {
            if (initialCard != null && initialCard.tags.isNotBlank()) {
                addAll(initialCard.tagList)
            }
        }
    }
    var customTagInput by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }
    var showLivePreview by remember { mutableStateOf(true) }
    var previewSideTab by remember { mutableIntStateOf(0) } // 0: Front, 1: Back

    val focusManager = LocalFocusManager.current

    val presetPosOptions = remember {
        listOf(
            "Noun" to "နာမ်",
            "Verb" to "ကြိယာ",
            "い-Adj" to "ဣ-နာမ",
            "な-Adj" to "န-နာမ",
            "Adverb" to "ကြိယာဝိသေသန",
            "Expression" to "အသုံးအနှုန်း"
        )
    }

    val presetTagOptions = remember(availableTags) {
        listOf("JLPT N3", "Work", "Daily Life", "School", "Travel", "Grammar", "Conversation")
            .plus(availableTags)
            .distinct()
    }

    val isKanjiValid = kanji.isNotBlank()
    val isReadingValid = reading.isNotBlank()
    val isBurmeseValid = burmese.isNotBlank()
    val isFormValid = isKanjiValid && isReadingValid && isBurmeseValid

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 720.dp)
                    .testTag("add_custom_card_dialog")
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 1. DIALOG HEADER WITH CLOSE BUTTON
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = PolishPrimary.copy(alpha = 0.12f),
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = if (initialCard != null) Icons.Default.Edit else Icons.Default.Add,
                                            contentDescription = null,
                                            tint = PolishPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                                Column {
                                    Text(
                                        text = if (initialCard != null) "Edit Vocabulary (ဝေါဟာရ ပြင်ဆင်ရန်)" else "Add Vocabulary (ဝေါဟာရ အသစ်ထည့်ရန်)",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Japanese Flashcard Data Input Form",
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Form",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))

                    // 2. SCROLLABLE FORM BODY
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = false)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // LIVE FLASHCARD PREVIEW ACCORDION
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
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
                                            imageVector = Icons.Default.Style,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "Card Preview (ကြိုတင်ကြည့်ရှုခြင်း)",
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        TextButton(
                                            onClick = { showLivePreview = !showLivePreview },
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (showLivePreview) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                                contentDescription = null,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = if (showLivePreview) "Hide" else "Show",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                        }
                                    }
                                }

                                AnimatedVisibility(
                                    visible = showLivePreview,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        // Front / Back Tab
                                        TabRow(
                                            selectedTabIndex = previewSideTab,
                                            containerColor = MaterialTheme.colorScheme.surface,
                                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                        ) {
                                            Tab(
                                                selected = previewSideTab == 0,
                                                onClick = { previewSideTab = 0 },
                                                text = { Text("Front (အရှေ့မျက်နှာ)") }
                                            )
                                            Tab(
                                                selected = previewSideTab == 1,
                                                onClick = { previewSideTab = 1 },
                                                text = { Text("Back (အနောက်မျက်နှာ)") }
                                            )
                                        }

                                        // Preview Card Canvas
                                        Card(
                                            shape = RoundedCornerShape(14.dp),
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(14.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                if (previewSideTab == 0) {
                                                    // FRONT OF CARD
                                                    if (reading.isNotBlank()) {
                                                        Text(
                                                            text = reading,
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = JapaneseCrimson,
                                                            fontWeight = FontWeight.Medium
                                                        )
                                                    }
                                                    Text(
                                                        text = if (kanji.isNotBlank()) kanji else "漢字 / Vocabulary",
                                                        style = MaterialTheme.typography.headlineMedium.copy(
                                                            fontWeight = FontWeight.Bold,
                                                            fontSize = 26.sp
                                                        ),
                                                        color = if (kanji.isNotBlank()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                                                    )
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                    ) {
                                                        Surface(
                                                            shape = RoundedCornerShape(50),
                                                            color = PolishPrimary.copy(alpha = 0.12f)
                                                        ) {
                                                            Text(
                                                                text = pos,
                                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                                                color = PolishPrimary,
                                                                fontWeight = FontWeight.SemiBold,
                                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                            )
                                                        }
                                                        if (onSpeak != null && (kanji.isNotBlank() || reading.isNotBlank())) {
                                                            IconButton(
                                                                onClick = { onSpeak(reading.ifBlank { kanji }) },
                                                                modifier = Modifier.size(24.dp)
                                                            ) {
                                                                Icon(
                                                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                                                    contentDescription = "Pronounce",
                                                                    tint = MaterialTheme.colorScheme.primary,
                                                                    modifier = Modifier.size(16.dp)
                                                                )
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    // BACK OF CARD
                                                    Text(
                                                        text = if (burmese.isNotBlank()) burmese else "မြန်မာအဓိပ္ပာယ် (Burmese Meaning)",
                                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                                        color = if (burmese.isNotBlank()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline,
                                                        textAlign = TextAlign.Center
                                                    )
                                                    if (example.isNotBlank()) {
                                                        Text(
                                                            text = "例: $example",
                                                            style = MaterialTheme.typography.bodySmall,
                                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                            textAlign = TextAlign.Center
                                                        )
                                                    }
                                                    if (exBurmese.isNotBlank()) {
                                                        Text(
                                                            text = "($exBurmese)",
                                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                                            color = MaterialTheme.colorScheme.outline,
                                                            textAlign = TextAlign.Center
                                                        )
                                                    }
                                                    if (note.isNotBlank()) {
                                                        Surface(
                                                            shape = RoundedCornerShape(6.dp),
                                                            color = StreakOrange.copy(alpha = 0.1f),
                                                            modifier = Modifier.padding(top = 4.dp)
                                                        ) {
                                                            Text(
                                                                text = "💡 $note",
                                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                                                color = StreakOrange,
                                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // FIELD 1: KANJI / VOCABULARY
                        OutlinedTextField(
                            value = kanji,
                            onValueChange = { kanji = it },
                            label = { Text("Kanji / Word (ဂျပန်စာ / ဝေါဟာရ) *") },
                            placeholder = { Text("e.g. 挑戦する, 勉強, 桜") },
                            isError = isSubmitted && !isKanjiValid,
                            supportingText = {
                                if (isSubmitted && !isKanjiValid) {
                                    Text("ဂျပန်ဝေါဟာရ ထည့်သွင်းရန် လိုအပ်ပါသည်", color = MaterialTheme.colorScheme.error)
                                } else {
                                    Text("Enter Japanese word or phrase", color = MaterialTheme.colorScheme.outline)
                                }
                            },
                            trailingIcon = {
                                if (onSpeak != null && kanji.isNotBlank()) {
                                    IconButton(onClick = { onSpeak(kanji) }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Hear Japanese audio",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_custom_kanji")
                        )

                        // FIELD 2: READING / HIRAGANA
                        OutlinedTextField(
                            value = reading,
                            onValueChange = { reading = it },
                            label = { Text("Hiragana Reading (ဖတ်နည်း / ဟိရဂန) *") },
                            placeholder = { Text("e.g. ちょうせんする, べんきょう, さくら") },
                            isError = isSubmitted && !isReadingValid,
                            supportingText = {
                                if (isSubmitted && !isReadingValid) {
                                    Text("ဟိရဂန ဖတ်နည်း ထည့်သွင်းရန် လိုအပ်ပါသည်", color = MaterialTheme.colorScheme.error)
                                } else {
                                    Text("Phonetic reading for speech and search", color = MaterialTheme.colorScheme.outline)
                                }
                            },
                            trailingIcon = {
                                if (onSpeak != null && reading.isNotBlank()) {
                                    IconButton(onClick = { onSpeak(reading) }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Test Pronunciation",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_custom_reading")
                        )

                        // FIELD 3: BURMESE MEANING
                        OutlinedTextField(
                            value = burmese,
                            onValueChange = { burmese = it },
                            label = { Text("Burmese Meaning (မြန်မာအဓိပ္ပာယ်) *") },
                            placeholder = { Text("e.g. စိန်ခေါ်ယှဉ်ပြိုင်သည်, စာလေ့လာခြင်း") },
                            isError = isSubmitted && !isBurmeseValid,
                            supportingText = {
                                if (isSubmitted && !isBurmeseValid) {
                                    Text("မြန်မာဘာသာပြန် ထည့်သွင်းရန် လိုအပ်ပါသည်", color = MaterialTheme.colorScheme.error)
                                } else {
                                    Text("Clear and concise definition in Burmese", color = MaterialTheme.colorScheme.outline)
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_custom_burmese")
                        )

                        // FIELD 4: PART OF SPEECH QUICK SELECTOR
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Part of Speech (စကားရပ် အမျိုးအစား):",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                presetPosOptions.forEach { (optionName, myanmarLabel) ->
                                    val isSelected = pos.equals(optionName, ignoreCase = true)
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { pos = optionName },
                                        label = {
                                            Text(
                                                text = "$optionName ($myanmarLabel)",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                        )
                                    )
                                }
                            }
                        }

                        // FIELD 5: TAGS & CATEGORIES
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Label,
                                    contentDescription = null,
                                    tint = PolishPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Tags & Categories (အမျိုးအစား ခွဲခြားခြင်း):",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Preset Tag Chips
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                presetTagOptions.forEach { tagOption ->
                                    val isSelected = selectedTags.any { it.equals(tagOption, ignoreCase = true) }
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            if (isSelected) {
                                                selectedTags.removeAll { it.equals(tagOption, ignoreCase = true) }
                                            } else {
                                                selectedTags.add(tagOption)
                                            }
                                        },
                                        leadingIcon = if (isSelected) {
                                            {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        } else null,
                                        label = { Text(tagOption, style = MaterialTheme.typography.labelSmall) },
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }
                            }

                            // Add Custom Tag Input
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                OutlinedTextField(
                                    value = customTagInput,
                                    onValueChange = { customTagInput = it },
                                    placeholder = { Text("Add custom tag (e.g. Medical, Slang)...") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                    keyboardActions = KeyboardActions(onDone = {
                                        val clean = customTagInput.trim()
                                        if (clean.isNotEmpty() && !selectedTags.any { it.equals(clean, ignoreCase = true) }) {
                                            selectedTags.add(clean)
                                            customTagInput = ""
                                        }
                                        focusManager.clearFocus()
                                    })
                                )
                                Button(
                                    onClick = {
                                        val clean = customTagInput.trim()
                                        if (clean.isNotEmpty() && !selectedTags.any { it.equals(clean, ignoreCase = true) }) {
                                            selectedTags.add(clean)
                                            customTagInput = ""
                                        }
                                    },
                                    enabled = customTagInput.isNotBlank(),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Add Tag", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add")
                                }
                            }
                        }

                        // FIELD 6: EXAMPLE SENTENCE
                        OutlinedTextField(
                            value = example,
                            onValueChange = { example = it },
                            label = { Text("Example Sentence (ဥပမာ ဂျပန်ဝါကျ - Optional)") },
                            placeholder = { Text("e.g. 新しいことに挑戦する。") },
                            trailingIcon = {
                                if (onSpeak != null && example.isNotBlank()) {
                                    IconButton(onClick = { onSpeak(example) }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Pronounce sentence",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // FIELD 7: EXAMPLE BURMESE MEANING
                        OutlinedTextField(
                            value = exBurmese,
                            onValueChange = { exBurmese = it },
                            label = { Text("Example Meaning (ဥပမာ မြန်မာပြန် - Optional)") },
                            placeholder = { Text("e.g. အရာသစ်တစ်ခုကို စိန်ခေါ်လုပ်ဆောင်သည်။") },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // FIELD 8: PERSONAL MEMORY NOTE
                        OutlinedTextField(
                            value = note,
                            onValueChange = { note = it },
                            label = { Text("Memory Mnemonic / Personal Note (မှတ်သားချက် - Optional)") },
                            placeholder = { Text("e.g. Mnemonic: 挑 (challenge) + 戦 (fight)") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = StreakOrange,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Form Error Banner
                        if (isSubmitted && !isFormValid) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.3f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "* ပြထားသော အချက်အလက်များ (Kanji, Reading, Burmese) အားလုံး ဖြည့်စွက်ပေးပါ။",
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))

                    // 3. DIALOG FOOTER ACTIONS
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(end = 10.dp)
                        ) {
                            Text("Cancel (မလုပ်တော့ပါ)")
                        }

                        Button(
                            onClick = {
                                isSubmitted = true
                                if (isFormValid) {
                                    val joinedTags = selectedTags.distinct().joinToString(",")
                                    onConfirm(
                                        kanji.trim(),
                                        reading.trim(),
                                        burmese.trim(),
                                        pos.trim(),
                                        example.trim(),
                                        exBurmese.trim(),
                                        note.trim(),
                                        joinedTags
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("save_custom_card_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (initialCard != null) "Update Card (ပြင်ဆင်မည်)" else "Save Card (+15 XP)",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
