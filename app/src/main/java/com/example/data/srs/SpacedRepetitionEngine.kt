package com.example.data.srs

import com.example.data.model.VocabCard
import kotlin.math.max
import kotlin.math.roundToInt

enum class ReviewRating(val quality: Int, val label: String) {
    AGAIN(1, "Again"),
    HARD(2, "Hard"),
    GOOD(4, "Good"),
    EASY(5, "Easy")
}

data class SrsCalculationResult(
    val intervalDays: Int,
    val repetitions: Int,
    val easeFactor: Float,
    val nextReviewTimestamp: Long,
    val masteryLevel: Int,
    val intervalLabel: String,
    val isRetentionSuccess: Boolean
)

object SpacedRepetitionEngine {
    const val ONE_DAY_MS = 24 * 60 * 60 * 1000L
    const val MINIMUM_EASE_FACTOR = 1.3f
    const val DEFAULT_EASE_FACTOR = 2.5f

    /**
     * Calculates the new SM-2 parameters for a given card and user performance rating.
     */
    fun calculateSrsParameters(
        card: VocabCard,
        rating: ReviewRating,
        currentTimeMs: Long = System.currentTimeMillis()
    ): SrsCalculationResult {
        val q = rating.quality
        var repetitions = card.repetitions
        var interval = card.intervalDays
        var ef = if (card.easeFactor < MINIMUM_EASE_FACTOR) DEFAULT_EASE_FACTOR else card.easeFactor

        val isSuccess = q >= 3

        if (!isSuccess) {
            // Failed recall (Again: q=1, Hard: q=2)
            if (rating == ReviewRating.AGAIN) {
                repetitions = 0
                interval = 1
            } else {
                // Hard rating: preserve some repetition progress or reset with conservative step
                repetitions = max(0, repetitions - 1)
                interval = max(1, (interval * 1.2f).roundToInt().coerceAtMost(max(2, interval)))
            }
        } else {
            // Successful recall (Good: q=4, Easy: q=5)
            when (repetitions) {
                0 -> {
                    interval = if (rating == ReviewRating.EASY) 4 else 1
                }
                1 -> {
                    interval = if (rating == ReviewRating.EASY) 8 else 6
                }
                else -> {
                    val multiplier = if (rating == ReviewRating.EASY) ef * 1.3f else ef
                    interval = max(interval + 1, (interval * multiplier).roundToInt())
                }
            }
            repetitions += 1
        }

        // Standard SM-2 Ease Factor calculation formula:
        // EF' = EF + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02))
        val newEf = ef + (0.1f - (5 - q) * (0.08f + (5 - q) * 0.02f))
        ef = max(MINIMUM_EASE_FACTOR, newEf)

        val nextReview = currentTimeMs + (interval.toLong() * ONE_DAY_MS)
        val mastery = when {
            repetitions >= 4 && interval >= 14 -> 3 // Mastered
            repetitions >= 2 -> 2                  // Reviewing
            repetitions >= 1 -> 1                  // Learning
            else -> 0                              // New
        }

        val label = formatInterval(interval, rating)

        return SrsCalculationResult(
            intervalDays = interval,
            repetitions = repetitions,
            easeFactor = ef,
            nextReviewTimestamp = nextReview,
            masteryLevel = mastery,
            intervalLabel = label,
            isRetentionSuccess = isSuccess
        )
    }

    /**
     * Applies SM-2 Spaced Repetition calculation to a VocabCard based on user performance rating.
     */
    fun reviewCard(card: VocabCard, rating: ReviewRating, currentTimeMs: Long = System.currentTimeMillis()): VocabCard {
        val result = calculateSrsParameters(card, rating, currentTimeMs)
        val correctCount = card.timesCorrect + if (result.isRetentionSuccess) 1 else 0
        val incorrectCount = card.timesIncorrect + if (!result.isRetentionSuccess) 1 else 0

        return card.copy(
            repetitions = result.repetitions,
            intervalDays = result.intervalDays,
            easeFactor = result.easeFactor,
            nextReviewTimestamp = result.nextReviewTimestamp,
            lastReviewedTimestamp = currentTimeMs,
            masteryLevel = result.masteryLevel,
            timesCorrect = correctCount,
            timesIncorrect = incorrectCount
        )
    }

    /**
     * Formats an interval in days into a concise, user-friendly label (e.g., 10m, 1d, 6d, 2w, 1mo).
     */
    fun formatInterval(days: Int, rating: ReviewRating? = null): String {
        if (rating == ReviewRating.AGAIN && days <= 1) {
            return "10m"
        }
        return when {
            days <= 1 -> "1d"
            days < 7 -> "${days}d"
            days < 30 -> "${(days / 7)}w"
            days < 365 -> "${(days / 30)}mo"
            else -> "${(days / 365)}y"
        }
    }

    /**
     * Returns a map of predicted intervals for all possible ratings for the given card.
     */
    fun getPredictedIntervals(card: VocabCard, currentTimeMs: Long = System.currentTimeMillis()): Map<ReviewRating, String> {
        return ReviewRating.values().associateWith { rating ->
            val result = calculateSrsParameters(card, rating, currentTimeMs)
            result.intervalLabel
        }
    }
}
