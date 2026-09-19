package com.example.reminder

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

class DailyStudyReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val reminderPrefs = ReminderPreferences(applicationContext)
        val settings = reminderPrefs.getSettings()

        if (!settings.isEnabled) {
            return@withContext Result.success()
        }

        try {
            val db = AppDatabase.getInstance(applicationContext)
            val profile = db.userProfileDao().getProfileDirect()
            val dueCount = db.vocabDao().getDueCardCountDirect(System.currentTimeMillis())

            val dailyGoal = profile?.dailyGoal ?: 15
            val streak = profile?.currentStreak ?: 1
            val userName = profile?.name ?: "Scholar"

            // Calculate start and end of today in local timezone
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val startOfDayMs = calendar.timeInMillis

            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            val endOfDayMs = calendar.timeInMillis

            // Compute total study count today (cards reviewed in SRS + cards tested in quizzes)
            val cardsReviewedToday = db.vocabDao().getCardsReviewedCountInRange(startOfDayMs, endOfDayMs)
            val quizCardsToday = db.quizDao().getQuestionsAnsweredInRange(startOfDayMs, endOfDayMs)
            val totalCompletedToday = cardsReviewedToday + quizCardsToday

            // SMART REMINDER CHECK:
            // If smart reminders are enabled AND the user has already met or exceeded their daily goal today, skip notification.
            if (settings.isSmartReminderEnabled && totalCompletedToday >= dailyGoal) {
                // User already completed their daily goal! No intrusive reminder needed.
                return@withContext Result.success()
            }

            NotificationHelper.showStudyReminder(
                context = applicationContext,
                streakDays = streak,
                dueCardCount = dueCount,
                userName = userName,
                todayCompletedCount = totalCompletedToday,
                dailyGoal = dailyGoal,
                isSmartReminder = settings.isSmartReminderEnabled
            )

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
