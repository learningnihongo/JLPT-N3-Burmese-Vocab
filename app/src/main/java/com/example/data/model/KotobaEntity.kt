package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Database entity representing a Japanese Kotoba (Vocabulary word) for offline study.
 */
@Entity(tableName = "kotoba_entries")
data class KotobaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val word: String,                           // e.g., "勉強する", "手帳", "美味しい"
    val reading: String,                        // Hiragana/Katakana reading e.g., "べんきょうする"
    val romaji: String = "",                    // Romaji reading
    val meaningBurmese: String,                 // Meaning in Myanmar/Burmese
    val meaningEnglish: String = "",            // Meaning in English
    val partOfSpeech: String = "",              // Noun, Verb Group 1, i-Adjective, etc.
    val exampleSentenceJapanese: String = "",   // Example sentence in Japanese
    val exampleSentenceReading: String = "",    // Reading for the example sentence
    val exampleSentenceBurmese: String = "",    // Burmese translation of example sentence
    val jlptLevel: String = "N3",               // JLPT Level (N5, N4, N3, N2, N1)
    val lessonNumber: Int = 1,                  // Grouping / Lesson
    val category: String = "General",           // Category (Daily, Business, Travel, etc.)
    val personalNotes: String = "",             // User notes
    val isBookmarked: Boolean = false,          // Favorite / Bookmarked
    val masteryLevel: Int = 0,                  // 0: New, 1: Learning, 2: Review, 3: Mastered
    val repetitions: Int = 0,                   // SM-2 consecutive successful reviews
    val intervalDays: Int = 0,                  // Days until next review
    val easeFactor: Float = 2.5f,               // SM-2 Ease Factor
    val nextReviewTimestamp: Long = 0L,         // Next review epoch millis
    val lastReviewedTimestamp: Long = 0L,       // Last review epoch millis
    val timesCorrect: Int = 0,                  // Quiz/study correct counter
    val timesIncorrect: Int = 0,                // Quiz/study incorrect counter
    val createdAt: Long = System.currentTimeMillis()
) {
    val displayReading: String
        get() = reading.ifBlank { word }

    val isDue: Boolean
        get() = repetitions == 0 || nextReviewTimestamp <= System.currentTimeMillis()
}
