package com.example.data.srs

import com.example.data.model.SrsDayForecast
import com.example.data.model.SrsScheduleSummary
import com.example.data.model.VocabCard
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.exp
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
    val isRetentionSuccess: Boolean,
    val isLapse: Boolean = false
)

/**
 * Complete implementation of the SuperMemo-2 (SM-2) Spaced Repetition Algorithm.
 * 
 * Standard SM-2 Formula:
 * - Ease Factor update: EF' = EF + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02))
 *   Bounded by minimum EF of 1.30.
 * - Interval progression:
 *   If q < 3 (Failed / Again):
 *     repetitions = 0
 *     interval = 1 (or 10 minutes relearn step)
 *   If q >= 3 (Success):
 *     If repetitions == 0: interval = 1 day (or 4 days for Easy)
 *     If repetitions == 1: interval = 6 days (or 8 days for Easy)
 *     If repetitions >= 2: interval = round(interval * EF)
 *     repetitions += 1
 * - Retrievability / Forgetting Curve:
 *   R = exp(-t / S), where t is elapsed days and S is interval stability.
 */
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
        var isLapse = false

        if (!isSuccess) {
            // Failed recall (Again: q=1, Hard: q=2)
            if (card.repetitions > 0 || card.masteryLevel >= 1) {
                isLapse = true
            }
            if (rating == ReviewRating.AGAIN) {
                repetitions = 0
                interval = 1 // Review again tomorrow (or 10 min relearn)
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
            isRetentionSuccess = isSuccess,
            isLapse = isLapse
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
     * Estimates current retrievability / retention probability (0.0 to 1.0) using Ebbinghaus forgetting curve.
     * R = exp(-delta_t / S), where S is stability (interval in days) and delta_t is days since last review.
     */
    fun calculateRetrievability(card: VocabCard, currentTimeMs: Long = System.currentTimeMillis()): Float {
        if (card.repetitions == 0 || card.lastReviewedTimestamp == 0L) return 0f
        val elapsedDays = max(0.0, (currentTimeMs - card.lastReviewedTimestamp).toDouble() / ONE_DAY_MS)
        val stabilityDays = max(1.0, card.intervalDays.toDouble())
        val retrievability = exp(-elapsedDays / stabilityDays)
        return retrievability.toFloat().coerceIn(0f, 1f)
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

    /**
     * Builds a comprehensive SM-2 schedule summary across all cards in the database.
     */
    fun buildScheduleSummary(
        allCards: List<VocabCard>,
        totalReviewsLogged: Int,
        successfulReviewsCount: Int,
        currentTimeMs: Long = System.currentTimeMillis()
    ): SrsScheduleSummary {
        val total = allCards.size
        if (total == 0) return SrsScheduleSummary()

        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTimeMs
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        val endOfTodayMs = calendar.timeInMillis
        val endOfTomorrowMs = endOfTodayMs + ONE_DAY_MS
        val endOf3DaysMs = endOfTodayMs + 3 * ONE_DAY_MS
        val endOf7DaysMs = endOfTodayMs + 7 * ONE_DAY_MS

        var dueNow = 0
        var dueToday = 0
        var dueTomorrow = 0
        var dueNext3 = 0
        var dueNext7 = 0
        var dueLater = 0

        var newCards = 0
        var learning = 0
        var reviewing = 0
        var mastered = 0
        var totalEf = 0f

        allCards.forEach { card ->
            totalEf += card.easeFactor
            when {
                card.masteryLevel >= 3 -> mastered++
                card.repetitions >= 2 -> reviewing++
                card.repetitions == 1 -> learning++
                else -> newCards++
            }

            val dueTime = card.nextReviewTimestamp
            val isDueNow = card.repetitions == 0 || dueTime <= currentTimeMs

            if (isDueNow) {
                dueNow++
                dueToday++
            } else if (dueTime <= endOfTodayMs) {
                dueToday++
            } else if (dueTime <= endOfTomorrowMs) {
                dueTomorrow++
            } else if (dueTime <= endOf3DaysMs) {
                dueNext3++
            } else if (dueTime <= endOf7DaysMs) {
                dueNext7++
            } else {
                dueLater++
            }
        }

        val avgEf = if (total > 0) totalEf / total else 2.5f
        val retentionRate = if (totalReviewsLogged > 0) {
            (successfulReviewsCount.toFloat() / totalReviewsLogged.toFloat()) * 100f
        } else {
            0f
        }

        // Build 7-day schedule forecast
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
        val forecastList = (0..6).map { dayOffset ->
            val dayCal = Calendar.getInstance().apply {
                timeInMillis = currentTimeMs
                add(Calendar.DAY_OF_YEAR, dayOffset)
            }
            val startOfDay = Calendar.getInstance().apply {
                time = dayCal.time
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val endOfDay = Calendar.getInstance().apply {
                time = dayCal.time
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.timeInMillis

            val count = if (dayOffset == 0) {
                // Today: include due now cards + cards due today
                allCards.count { it.repetitions == 0 || it.nextReviewTimestamp <= endOfDay }
            } else {
                allCards.count { it.repetitions > 0 && it.nextReviewTimestamp in startOfDay..endOfDay }
            }

            val label = when (dayOffset) {
                0 -> "Today"
                1 -> "Tomorrow"
                else -> dayFormat.format(dayCal.time)
            }

            SrsDayForecast(
                dayOffset = dayOffset,
                dayLabel = label,
                dateFormatted = dateFormat.format(dayCal.time),
                scheduledCardsCount = count,
                isToday = dayOffset == 0
            )
        }

        return SrsScheduleSummary(
            totalCards = total,
            dueNowCount = dueNow,
            dueTodayCount = dueToday,
            dueTomorrowCount = dueTomorrow,
            dueNext3DaysCount = dueNext3,
            dueNext7DaysCount = dueNext7,
            dueLaterCount = dueLater,
            newCardsCount = newCards,
            learningCardsCount = learning,
            reviewingCardsCount = reviewing,
            masteredCardsCount = mastered,
            averageEaseFactor = avgEf,
            overallRetentionRate = retentionRate,
            totalReviewsLogged = totalReviewsLogged,
            successfulReviewsCount = successfulReviewsCount,
            dailyScheduleForecast = forecastList
        )
    }
}
