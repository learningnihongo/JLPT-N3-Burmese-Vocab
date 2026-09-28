package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VocabCard
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.SakuraPinkDark
import com.example.ui.util.StudyActionHelper

/**
 * Bottom Sheet for deep study of a Kanji or Kotoba word:
 * - One-tap Text Copy (Kanji, Furigana reading, Myanmar meaning, or full card)
 * - Google Translate (Japanese to Myanmar / English)
 * - Jisho.org search for stroke order, radicals, and dictionary lookup
 * - Kanji compound words (တွဲလုံးများ / 熟語 - Jukugo) explorer
 * - Custom word lookup in Jisho and Google Translate
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanjiCompoundLookupSheet(
    card: VocabCard,
    allCards: List<VocabCard>,
    onDismiss: () -> Unit,
    onSpeak: ((String) -> Unit)? = null,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    val context = LocalContext.current

    // Extract unique Kanji characters in this word
    val extractedKanji = remember(card.kanji) {
        StudyActionHelper.extractKanjiCharacters(card.kanji)
    }

    var selectedKanjiChar by remember(card.kanji) {
        mutableStateOf(extractedKanji.firstOrNull())
    }

    var customSearchQuery by remember { mutableStateOf("") }
    var showCopyOptions by remember { mutableStateOf(false) }

    // Find compounds for the selected Kanji
    val relatedCompounds = remember(selectedKanjiChar, allCards) {
        if (selectedKanjiChar != null) {
            StudyActionHelper.findRelatedCompounds(selectedKanjiChar!!, allCards)
        } else {
            emptyList()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .testTag("kanji_compound_lookup_sheet"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: Word, Reading, Meaning & TTS Audio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (card.reading.isNotBlank()) {
                        Text(
                            text = card.reading,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = card.kanji,
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 30.sp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = card.meaningBurmese,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                IconButton(
                    onClick = {
                        val toSpeak = card.reading.ifBlank { card.kanji }
                        if (onSpeak != null) {
                            onSpeak(toSpeak)
                        } else {
                            StudyActionHelper.speakText(context, toSpeak)
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            CircleShape
                        )
                        .testTag("compound_sheet_speak_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Speak Pronunciation",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Quick Action Buttons Row: Copy, Google Translate, Jisho.org
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Copy Action Button with Menu
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedButton(
                        onClick = { showCopyOptions = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("compound_sheet_copy_btn"),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Copy", fontSize = 13.sp, maxLines = 1)
                    }

                    DropdownMenu(
                        expanded = showCopyOptions,
                        onDismissRequest = { showCopyOptions = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("စာလုံး Copy: ${card.kanji}") },
                            onClick = {
                                StudyActionHelper.copyToClipboard(context, card.kanji, "Kanji")
                                showCopyOptions = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                            }
                        )
                        if (card.reading.isNotBlank()) {
                            DropdownMenuItem(
                                text = { Text("ဖတ်နည်း Copy: ${card.reading}") },
                                onClick = {
                                    StudyActionHelper.copyToClipboard(context, card.reading, "Reading")
                                    showCopyOptions = false
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                                }
                            )
                        }
                        DropdownMenuItem(
                            text = { Text("အဓိပ္ပာယ် Copy: ${card.meaningBurmese}") },
                            onClick = {
                                StudyActionHelper.copyToClipboard(context, card.meaningBurmese, "Meaning")
                                showCopyOptions = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("အားလုံး Copy (All details)") },
                            onClick = {
                                StudyActionHelper.copyFullCard(context, card)
                                showCopyOptions = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                            }
                        )
                    }
                }

                // Google Translate Button
                Button(
                    onClick = {
                        StudyActionHelper.openGoogleTranslate(context, card.kanji, sourceLang = "ja", targetLang = "my")
                    },
                    modifier = Modifier
                        .weight(1.2f)
                        .testTag("compound_sheet_translate_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = "Google Translate",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Translate", fontSize = 13.sp, maxLines = 1)
                }

                // Jisho.org Search Button
                Button(
                    onClick = {
                        StudyActionHelper.openJisho(context, card.kanji, isKanjiLookup = false)
                    },
                    modifier = Modifier
                        .weight(1.1f)
                        .testTag("compound_sheet_jisho_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = JapaneseCrimson,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = "Jisho.org",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("jisho.org", fontSize = 13.sp, maxLines = 1)
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Extracted Kanji Characters Selector (for compound breakdown)
            if (extractedKanji.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Kanji တွဲလုံးများ / 熟語 ရှာဖွေရေး",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        if (selectedKanjiChar != null) {
                            TextButton(
                                onClick = {
                                    StudyActionHelper.openJisho(
                                        context,
                                        selectedKanjiChar.toString(),
                                        isKanjiLookup = true
                                    )
                                },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Jisho Kanji",
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "'$selectedKanjiChar' Jisho Kanji",
                                    fontSize = 11.sp,
                                    color = JapaneseCrimson,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(extractedKanji) { kanjiChar ->
                            val isSelected = kanjiChar == selectedKanjiChar
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedKanjiChar = kanjiChar },
                                label = {
                                    Text(
                                        text = kanjiChar.toString(),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }

                    // Related compound words list
                    if (relatedCompounds.isNotEmpty()) {
                        Text(
                            text = "'$selectedKanjiChar' ပါဝင်သော တွဲလုံးများ (${relatedCompounds.size} ခု):",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 240.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(relatedCompounds) { compoundCard ->
                                CompoundCardRow(
                                    compound = compoundCard,
                                    onSpeak = onSpeak
                                )
                            }
                        }
                    } else if (selectedKanjiChar != null) {
                        Text(
                            text = "ဒီ Kanji အတွက် အခြားတွဲလုံး မရှိသေးပါ (jisho.org တွင် ရှာဖွေနိုင်ပါသည်)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Custom Jisho / Google Translate Search Input
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "စိတ်ကြိုက် Kanji / စကားလုံး ရှာဖွေရန်",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = customSearchQuery,
                        onValueChange = { customSearchQuery = it },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("custom_jisho_input"),
                        placeholder = { Text("ဥပမာ: 会社, 勉強, 食", fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = {
                            if (customSearchQuery.isNotEmpty()) {
                                IconButton(onClick = { customSearchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    )

                    // Jisho Search Button for custom word
                    IconButton(
                        onClick = {
                            val query = customSearchQuery.ifBlank { card.kanji }
                            StudyActionHelper.openJisho(context, query)
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(JapaneseCrimson, RoundedCornerShape(12.dp))
                            .testTag("custom_jisho_submit_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Jisho",
                            tint = Color.White
                        )
                    }

                    // Speak Button for custom word
                    IconButton(
                        onClick = {
                            val query = customSearchQuery.ifBlank { card.reading.ifBlank { card.kanji } }
                            if (onSpeak != null) onSpeak(query)
                            else StudyActionHelper.speakText(context, query)
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp))
                            .testTag("custom_speak_submit_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Speak Custom Text",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }

                    // Google Translate Button for custom word
                    IconButton(
                        onClick = {
                            val query = customSearchQuery.ifBlank { card.kanji }
                            StudyActionHelper.openGoogleTranslate(context, query, sourceLang = "ja", targetLang = "my")
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp))
                            .testTag("custom_translate_submit_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = "Translate on Google",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

/**
 * Single item row for a Kanji compound word (တွဲလုံး / 熟語)
 */
@Composable
private fun CompoundCardRow(
    compound: VocabCard,
    onSpeak: ((String) -> Unit)? = null
) {
    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        StudyActionHelper.copyToClipboard(
                            context = context,
                            text = "${compound.kanji} (${compound.reading}): ${compound.meaningBurmese}",
                            label = "Compound",
                            toastMessage = "${compound.kanji} ကို Copy ကူးပြီးပါပြီ ✓"
                        )
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = compound.kanji,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            val toSpeak = compound.kanji
                            if (onSpeak != null) onSpeak(toSpeak)
                            else StudyActionHelper.speakText(context, toSpeak)
                        }
                    )
                    if (compound.reading.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                            modifier = Modifier.clickable {
                                val toSpeak = compound.reading
                                if (onSpeak != null) onSpeak(toSpeak)
                                else StudyActionHelper.speakText(context, toSpeak)
                            }
                        ) {
                            Text(
                                text = compound.reading,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
                Text(
                    text = compound.meaningBurmese,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Audio Speak button - pronounces reading (or kanji) accurately
                IconButton(
                    onClick = {
                        val toSpeak = compound.reading.ifBlank { compound.kanji }
                        if (onSpeak != null) {
                            onSpeak(toSpeak)
                        } else {
                            StudyActionHelper.speakText(context, toSpeak)
                        }
                    },
                    modifier = Modifier.size(32.dp).testTag("compound_speak_item_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Speak pronunciation",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Copy
                IconButton(
                    onClick = {
                        StudyActionHelper.copyToClipboard(
                            context = context,
                            text = compound.kanji,
                            label = "Kanji",
                            toastMessage = "'${compound.kanji}' ကို Copy ကူးပြီးပါပြီ ✓"
                        )
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy compound",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Google Translate
                IconButton(
                    onClick = {
                        StudyActionHelper.openGoogleTranslate(context, compound.kanji, sourceLang = "ja", targetLang = "my")
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = "Translate compound",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Jisho.org Search
                IconButton(
                    onClick = {
                        StudyActionHelper.openJisho(context, compound.kanji, isKanjiLookup = false)
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = "Search on Jisho.org",
                        tint = JapaneseCrimson,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
