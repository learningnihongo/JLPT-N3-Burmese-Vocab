package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Database entity representing custom user-created flashcards for offline study.
 */
@Entity(tableName = "custom_flashcards")
data class CustomFlashcardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val front: String,                          // Front side: Japanese Word, Kanji, Grammar point, or Phrase
    val reading: String = "",                   // Pronunciation / Furigana
    val back: String,                           // Back side: Burmese meaning or explanation
    val cardType: String = "KOTOBA",            // "KANJI", "KOTOBA", "GRAMMAR", "SENTENCE", "CUSTOM"
    val category: String = "Custom Deck",       // User defined collection/category
    val exampleSentence: String = "",           // Optional example sentence
    val exampleMeaning: String = "",            // Example sentence meaning in Burmese
    val tags: String = "",                      // Comma-separated tags (e.g. "Work,JLPT,Food")
    val personalNotes: String = "",             // Personal hints or mnemonic notes
    val isBookmarked: Boolean = false,          // Starred / Marked favorite
    val masteryLevel: Int = 0,                  // 0: New, 1: Learning, 2: Review, 3: Mastered
    val repetitions: Int = 0,                   // SM-2 consecutive successful reviews
    val intervalDays: Int = 0,                  // Days until next review
    val easeFactor: Float = 2.5f,               // SM-2 Ease factor
    val nextReviewTimestamp: Long = 0L,         // Next review epoch millis
    val lastReviewedTimestamp: Long = 0L,       // Last review epoch millis
    val timesCorrect: Int = 0,                  // Quiz/study correct counter
    val timesIncorrect: Int = 0,                // Quiz/study incorrect counter
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    val isDue: Boolean
        get() = repetitions == 0 || nextReviewTimestamp <= System.currentTimeMillis()

    val tagList: List<String>
        get() = if (tags.isBlank()) emptyList() else tags.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    fun hasTag(tag: String): Boolean {
        return tagList.any { it.equals(tag.trim(), ignoreCase = true) }
    }
}
