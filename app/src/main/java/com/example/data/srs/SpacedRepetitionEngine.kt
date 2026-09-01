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

object SpacedRepetitionEngine {
    private const val ONE_DAY_MS = 24 * 60 * 60 * 1000L

    /**
     * Applies SM-2 Spaced Repetition calculation to a VocabCard based on user performance rating.
     */
    fun reviewCard(card: VocabCard, rating: ReviewRating, currentTimeMs: Long = System.currentTimeMillis()): VocabCard {
        val q = rating.quality
        var repetitions = card.repetitions
        var interval = card.intervalDays
        var ef = card.easeFactor
        var correctCount = card.timesCorrect
        var incorrectCount = card.timesIncorrect

        // Quality rating calculation
        if (q < 3) {
            // Failed recall
            repetitions = 0
            interval = 1
            incorrectCount += 1
        } else {
            // Successful recall
            when (repetitions) {
                0 -> interval = 1
                1 -> interval = if (q == ReviewRating.EASY.quality) 4 else 2
                2 -> interval = if (q == ReviewRating.EASY.quality) 8 else 5
                else -> interval = (interval * ef).roundToInt()
            }
            repetitions += 1
            correctCount += 1
        }

        // Calculate new Ease Factor: EF' = EF + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02))
        val newEf = ef + (0.1f - (5 - q) * (0.08f + (5 - q) * 0.02f))
        ef = max(1.3f, newEf)

        val nextReview = currentTimeMs + (interval * ONE_DAY_MS)
        val mastery = when {
            repetitions >= 4 && interval >= 14 -> 3 // Mastered
            repetitions >= 2 -> 2                  // Reviewing
            repetitions >= 1 -> 1                  // Learning
            else -> 0                              // New
        }

        return card.copy(
            repetitions = repetitions,
            intervalDays = interval,
            easeFactor = ef,
            nextReviewTimestamp = nextReview,
            lastReviewedTimestamp = currentTimeMs,
            masteryLevel = mastery,
            timesCorrect = correctCount,
            timesIncorrect = incorrectCount
        )
    }
}
