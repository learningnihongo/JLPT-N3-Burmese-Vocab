package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Database entity representing a Japanese Kanji character for offline flashcard study.
 */
@Entity(tableName = "kanji_entries")
data class KanjiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val kanji: String,                          // e.g., "日", "私", "食"
    val onyomi: String = "",                     // e.g., "ニチ, ジツ" (Katakana readings)
    val kunyomi: String = "",                    // e.g., "ひ, -び, -か" (Hiragana readings)
    val meaningBurmese: String,                 // Meaning in Myanmar/Burmese
    val meaningEnglish: String = "",            // Meaning in English
    val strokeCount: Int = 0,                   // Number of strokes
    val jlptLevel: String = "N3",               // N5, N4, N3, N2, N1
    val radical: String = "",                   // Radical (部首)
    val exampleCompounds: String = "",          // Example compound words with readings
    val personalNotes: String = "",             // User personal notes
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
    val isDue: Boolean
        get() = repetitions == 0 || nextReviewTimestamp <= System.currentTimeMillis()
}
