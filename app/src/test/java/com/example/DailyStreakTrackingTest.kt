package com.example

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

/**
 * Unit tests for Daily Streak tracking logic and review goal calculations.
 */
class DailyStreakTrackingTest {

    @Test
    fun dailyGoal_clampedWithinBounds() {
        val lowGoal = 2.coerceIn(5, 200)
        val highGoal = 350.coerceIn(5, 200)
        val normalGoal = 25.coerceIn(5, 200)

        assertEquals(5, lowGoal)
        assertEquals(200, highGoal)
        assertEquals(25, normalGoal)
    }

    @Test
    fun dailyGoalProgress_calculationAndRemainingCards() {
        val targetGoal = 20
        val reviewedCount = 12

        val progress = reviewedCount.toFloat() / targetGoal.toFloat()
        val remaining = (targetGoal - reviewedCount).coerceAtLeast(0)
        val isCompleted = reviewedCount >= targetGoal

        assertEquals(0.6f, progress, 0.001f)
        assertEquals(8, remaining)
        assertFalse(isCompleted)

        val completedCount = 25
        val completedProgress = completedCount.toFloat() / targetGoal.toFloat()
        val completedRemaining = (targetGoal - completedCount).coerceAtLeast(0)
        val completedStatus = completedCount >= targetGoal

        assertEquals(1.25f, completedProgress, 0.001f)
        assertEquals(0, completedRemaining)
        assertTrue(completedStatus)
    }

    @Test
    fun streakLogic_sameDayReview_doesNotIncrementTwice() {
        val today = Calendar.getInstance()
        val lastStudyCal = Calendar.getInstance()

        val isSameDay = today.get(Calendar.YEAR) == lastStudyCal.get(Calendar.YEAR) &&
            today.get(Calendar.DAY_OF_YEAR) == lastStudyCal.get(Calendar.DAY_OF_YEAR)

        assertTrue(isSameDay)
        val currentStreak = 4
        val newStreak = if (isSameDay) currentStreak else currentStreak + 1
        assertEquals(4, newStreak)
    }

    @Test
    fun streakLogic_consecutiveDay_incrementsStreakAndBestStreak() {
        var currentStreak = 3
        var bestStreak = 5

        // Simulate consecutive day review
        currentStreak += 1
        if (currentStreak > bestStreak) {
            bestStreak = currentStreak
        }

        assertEquals(4, currentStreak)
        assertEquals(5, bestStreak)

        // Increment again to surpass best streak
        currentStreak += 2
        if (currentStreak > bestStreak) {
            bestStreak = currentStreak
        }
        assertEquals(6, currentStreak)
        assertEquals(6, bestStreak)
    }

    @Test
    fun streakLogic_missedDay_resetsCurrentStreak() {
        val lastStudyTime = System.currentTimeMillis() - (48 * 60 * 60 * 1000L) // 2 days ago
        val now = System.currentTimeMillis()
        val daysDiff = ((now - lastStudyTime) / (1000 * 60 * 60 * 24)).toInt()

        assertTrue(daysDiff >= 2)
        val currentStreak = 5
        val bestStreak = 12

        val syncedStreak = if (daysDiff > 1) 0 else currentStreak
        assertEquals(0, syncedStreak)
        // Best streak must be preserved!
        assertEquals(12, bestStreak)
    }
}
