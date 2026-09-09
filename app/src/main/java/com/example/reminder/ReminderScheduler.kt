package com.example.reminder

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object ReminderScheduler {
    const val UNIQUE_WORK_NAME = "kanji_daily_study_reminder_work"
    const val WORK_TAG = "kanji_study_reminder_tag"
    const val TEST_WORK_NAME = "kanji_test_reminder_work"

    /**
     * Calculates the exact delay until the next occurrence of [hour]:[minute].
     */
    fun calculateInitialDelayMs(hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (target.timeInMillis <= now.timeInMillis) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }

        return target.timeInMillis - now.timeInMillis
    }

    /**
     * Formats next scheduled reminder time as human-readable string.
     */
    fun getNextScheduledDescription(hour: Int, minute: Int): String {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val isTomorrow = target.timeInMillis <= now.timeInMillis
        val period = if (hour >= 12) "PM" else "AM"
        val hour12 = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }
        val formattedTime = String.format("%d:%02d %s", hour12, minute, period)
        return if (isTomorrow) "Tomorrow at $formattedTime" else "Today at $formattedTime"
    }

    /**
     * Schedules or updates the 24-hour periodic daily reminder with WorkManager.
     */
    fun scheduleDailyReminder(context: Context, hour: Int, minute: Int) {
        val initialDelay = calculateInitialDelayMs(hour, minute)

        val periodicWorkRequest = PeriodicWorkRequestBuilder<DailyStudyReminderWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .addTag(WORK_TAG)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }

    /**
     * Reschedules the reminder using currently saved preferences.
     */
    fun rescheduleFromPreferences(context: Context) {
        val prefs = ReminderPreferences(context)
        val settings = prefs.getSettings()
        if (settings.isEnabled) {
            scheduleDailyReminder(context, settings.reminderHour, settings.reminderMinute)
        } else {
            cancelDailyReminder(context)
        }
    }

    /**
     * Cancels the daily reminder periodic work.
     */
    fun cancelDailyReminder(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_WORK_NAME)
    }

    /**
     * Dispatches an immediate one-time reminder check via WorkManager for testing.
     */
    fun sendImmediateTestReminder(context: Context) {
        val oneTimeWork = OneTimeWorkRequestBuilder<DailyStudyReminderWorker>()
            .addTag(WORK_TAG)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            TEST_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            oneTimeWork
        )
    }
}
