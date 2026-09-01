package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocab_cards")
data class VocabCard(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val lessonNumber: Int,
    val lessonTitle: String,
    val sectionTitle: String,
    val kanji: String,
    val reading: String,
    val meaningBurmese: String,
    val partOfSpeech: String = "",
    val exampleSentence: String = "",
    val exampleMeaningBurmese: String = "",
    val personalNote: String = "",
    val isBookmarked: Boolean = false,
    val isCustom: Boolean = false,
    
    // Spaced Repetition (SM-2) fields
    val repetitions: Int = 0,             // Consecutive successful recalls
    val intervalDays: Int = 0,           // Days until next review
    val easeFactor: Float = 2.5f,        // SM-2 Ease factor
    val nextReviewTimestamp: Long = 0L,  // Due timestamp (millis)
    val lastReviewedTimestamp: Long = 0L,// Last studied timestamp (millis)
    val masteryLevel: Int = 0,           // 0: New, 1: Learning, 2: Review, 3: Mastered
    val timesCorrect: Int = 0,
    val timesIncorrect: Int = 0
) {
    val displayReading: String
        get() = if (reading.isNotBlank()) reading else kanji

    val isDue: Boolean
        get() {
            if (repetitions == 0 || nextReviewTimestamp == 0L) return true
            return System.currentTimeMillis() >= nextReviewTimestamp
        }
}

data class LessonProgress(
    val lessonNumber: Int,
    val lessonTitle: String,
    val totalCards: Int,
    val masteredCards: Int,
    val dueCards: Int
)
