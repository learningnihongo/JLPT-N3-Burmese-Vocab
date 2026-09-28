package com.example.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity for tracking spaced repetition (SM-2) review logs in Room database.
 * Every time a user rates a flashcard, a log entry is persisted to track retention,
 * ease factor evolution, intervals, and scheduling performance.
 */
@Entity(
    tableName = "srs_review_logs",
    indices = [
        Index(value = ["cardId"]),
        Index(value = ["reviewTimestamp"]),
        Index(value = ["isRetentionSuccess"])
    ]
)
data class SrsReviewLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardId: Long,
    val kanji: String,
    val reading: String,
    val meaningBurmese: String,
    val rating: String,                  // "AGAIN", "HARD", "GOOD", "EASY"
    val ratingQuality: Int,              // 1, 2, 4, 5
    val repetitionsBefore: Int,
    val repetitionsAfter: Int,
    val intervalDaysBefore: Int,
    val intervalDaysAfter: Int,
    val easeFactorBefore: Float,
    val easeFactorAfter: Float,
    val reviewTimestamp: Long = System.currentTimeMillis(),
    val scheduledNextReviewTimestamp: Long,
    val isRetentionSuccess: Boolean,
    val timeSpentSeconds: Int = 0
)

/**
 * Summary data class for SM-2 Spaced Repetition schedule and performance metrics.
 */
data class SrsScheduleSummary(
    val totalCards: Int = 0,
    val dueNowCount: Int = 0,
    val dueTodayCount: Int = 0,
    val dueTomorrowCount: Int = 0,
    val dueNext3DaysCount: Int = 0,
    val dueNext7DaysCount: Int = 0,
    val dueLaterCount: Int = 0,
    val newCardsCount: Int = 0,
    val learningCardsCount: Int = 0,
    val reviewingCardsCount: Int = 0,
    val masteredCardsCount: Int = 0,
    val averageEaseFactor: Float = 2.5f,
    val overallRetentionRate: Float = 0f,
    val totalReviewsLogged: Int = 0,
    val successfulReviewsCount: Int = 0,
    val dailyScheduleForecast: List<SrsDayForecast> = emptyList()
)

data class SrsDayForecast(
    val dayOffset: Int,            // 0 = Today, 1 = Tomorrow, 2 = Day 2, etc.
    val dayLabel: String,          // "Today", "Mon", "Tue", etc.
    val dateFormatted: String,     // "Sep 28"
    val scheduledCardsCount: Int,
    val isToday: Boolean = false
)
