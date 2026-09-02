package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VocabCard
import com.example.ui.components.AddCustomCardDialog
import com.example.ui.components.CardTagDialog
import com.example.ui.components.DEFAULT_SUGGESTED_TAGS
import com.example.ui.components.PersonalNoteDialog
import com.example.ui.components.VocabCardItem
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.PolishPrimary
import com.example.ui.viewmodel.VocabFilterType
import com.example.ui.viewmodel.VocabViewModel

@Composable
fun VocabBrowseScreen(
    vocabViewModel: VocabViewModel,
    onStartStudy: (List<VocabCard>) -> Unit,
    modifier: Modifier = Modifier
) {
    val cards by vocabViewModel.filteredCards.collectAsState()
    val searchQuery by vocabViewModel.searchQuery.collectAsState()
    val currentFilter by vocabViewModel.currentFilter.collectAsState()
    val selectedLesson by vocabViewModel.selectedLesson.collectAsState()
    val selectedTag by vocabViewModel.selectedTag.collectAsState()
    val allCustomTags by vocabViewModel.allCustomTags.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var editingCardForNote by remember { mutableStateOf<VocabCard?>(null) }
    var editingCardForTags by remember { mutableStateOf<VocabCard?>(null) }

    val displayedTagFilters = remember(allCustomTags) {
        (DEFAULT_SUGGESTED_TAGS + allCustomTags).distinct()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("vocab_browse_screen"),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Search Input Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { vocabViewModel.setSearchQuery(it) },
                placeholder = { Text("Search Kanji, Reading, or Burmese...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { vocabViewModel.setSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear search")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("vocab_search_field")
            )

            // Horizontal Filter Chips (Status & Deck type)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    FilterChip(
                        selected = currentFilter == VocabFilterType.ALL && selectedLesson == null && selectedTag == null,
                        onClick = {
                            vocabViewModel.selectLesson(null)
                            vocabViewModel.selectTag(null)
                            vocabViewModel.setFilter(VocabFilterType.ALL)
                        },
                        label = { Text("All Words") },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = JapaneseCrimson.copy(alpha = 0.2f))
                    )
                }
                item {
                    FilterChip(
                        selected = currentFilter == VocabFilterType.DUE_REVIEWS,
                        onClick = {
                            vocabViewModel.selectTag(null)
                            vocabViewModel.setFilter(VocabFilterType.DUE_REVIEWS)
                        },
                        label = { Text("Due for SRS") }
                    )
                }
                item {
                    FilterChip(
                        selected = currentFilter == VocabFilterType.BOOKMARKED,
                        onClick = {
                            vocabViewModel.selectTag(null)
                            vocabViewModel.setFilter(VocabFilterType.BOOKMARKED)
                        },
                        label = { Text("Bookmarked") }
                    )
                }
                item {
                    FilterChip(
                        selected = currentFilter == VocabFilterType.CUSTOM_CARDS,
                        onClick = {
                            vocabViewModel.selectTag(null)
                            vocabViewModel.setFilter(VocabFilterType.CUSTOM_CARDS)
                        },
                        label = { Text("Personalized") }
                    )
                }
                item {
                    FilterChip(
                        selected = currentFilter == VocabFilterType.WEAK_CARDS,
                        onClick = {
                            vocabViewModel.selectTag(null)
                            vocabViewModel.setFilter(VocabFilterType.WEAK_CARDS)
                        },
                        label = { Text("Weak Words") }
                    )
                }
                item {
                    FilterChip(
                        selected = currentFilter == VocabFilterType.MASTERED,
                        onClick = {
                            vocabViewModel.selectTag(null)
                            vocabViewModel.setFilter(VocabFilterType.MASTERED)
                        },
                        label = { Text("Mastered") }
                    )
                }
            }

            // Categories & Tag Filter Bar
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Label,
                            contentDescription = null,
                            tint = PolishPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Category Groups:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (selectedTag != null) {
                        Text(
                            text = "Clear Tag",
                            style = MaterialTheme.typography.labelSmall,
                            color = PolishPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { vocabViewModel.selectTag(null) }
                        )
                    }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(displayedTagFilters) { tag ->
                        val isSelected = selectedTag.equals(tag, ignoreCase = true)
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) PolishPrimary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            border = if (isSelected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)),
                            modifier = Modifier
                                .clickable {
                                    if (isSelected) {
                                        vocabViewModel.selectTag(null)
                                    } else {
                                        vocabViewModel.selectTag(tag)
                                    }
                                }
                                .testTag("tag_filter_$tag")
                        ) {
                            Text(
                                text = "#$tag",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }

            // Results count + Study deck button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when {
                        selectedTag != null -> "#$selectedTag (${cards.size} words)"
                        selectedLesson != null -> "Lesson $selectedLesson (${cards.size} words)"
                        else -> "${cards.size} Flashcards"
                    },
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (cards.isNotEmpty()) {
                    FloatingActionButton(
                        onClick = { onStartStudy(cards) },
                        containerColor = JapaneseCrimson,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(38.dp)
                            .testTag("study_filtered_deck_btn")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Text("Study List", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Vocabulary List
            if (cards.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (selectedTag != null) "No cards tagged with #$selectedTag yet.\nOpen any card to add this tag!" else "No flashcards found matching this filter.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag("vocab_card_list"),
                    contentPadding = PaddingValues(bottom = 90.dp, top = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(cards, key = { it.id }) { card ->
                        VocabCardItem(
                            card = card,
                            onBookmarkToggle = { vocabViewModel.toggleBookmark(card) },
                            onSpeak = { vocabViewModel.speakCard(card) },
                            onSpeakPhonetic = { vocabViewModel.speakPhonetic(it) },
                            onSpeakSlow = { vocabViewModel.speakSlow(it) },
                            onEditNote = { editingCardForNote = card },
                            onEditTags = { editingCardForTags = card },
                            onTagClick = { clickedTag -> vocabViewModel.selectTag(clickedTag) },
                            onDelete = if (card.isCustom) {
                                { vocabViewModel.deleteCard(card) }
                            } else null
                        )
                    }
                }
            }
        }

        // Floating Action Button to create personalized flashcard
        FloatingActionButton(
            onClick = { showAddDialog = true },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp, end = 20.dp)
                .testTag("add_custom_card_fab")
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Personalized Card")
        }
    }

    if (showAddDialog) {
        AddCustomCardDialog(
            onDismiss = { showAddDialog = false },
            availableTags = allCustomTags,
            onConfirm = { kanji, reading, burmese, pos, example, exBurmese, note, tags ->
                vocabViewModel.addCustomFlashcard(
                    kanji = kanji,
                    reading = reading,
                    meaningBurmese = burmese,
                    partOfSpeech = pos,
                    exampleSentence = example,
                    exampleMeaningBurmese = exBurmese,
                    personalNote = note,
                    tags = tags
                )
                showAddDialog = false
            }
        )
    }

    editingCardForNote?.let { card ->
        PersonalNoteDialog(
            initialNote = card.personalNote,
            vocabWord = card.kanji,
            onDismiss = { editingCardForNote = null },
            onSave = { updatedNote ->
                vocabViewModel.updatePersonalNote(card.id, updatedNote)
                editingCardForNote = null
            }
        )
    }

    editingCardForTags?.let { card ->
        CardTagDialog(
            card = card,
            availableTags = allCustomTags,
            onDismiss = { editingCardForTags = null },
            onSaveTags = { cardId, updatedTags ->
                vocabViewModel.updateCardTags(cardId, updatedTags)
                editingCardForTags = null
            }
        )
    }
}
