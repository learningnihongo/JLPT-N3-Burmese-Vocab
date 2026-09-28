package com.example.reminder

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.MainActivity
import com.example.R
import kotlin.random.Random

object NotificationHelper {
    const val CHANNEL_ID = "kanji_study_reminders"
    const val CHANNEL_NAME = "Daily Study Reminders"
    const val CHANNEL_DESCRIPTION = "Notifications to remind you of daily JLPT N3 vocabulary reviews and keep your study streak active."
    const val NOTIFICATION_ID = 1001

    private val MOTIVATIONAL_PROVERBS = listOf(
        "継続は力なり (Continuity is power — Persistence pays off)" to "A few minutes of spaced repetition today locks words into long-term memory.",
        "千里の行も足下に始まる (A journey of a thousand miles begins with a single step)" to "Every kanji mastered brings you one step closer to JLPT N3 proficiency.",
        "七転び八起き (Fall seven times, stand up eight)" to "Reviewing tricky vocabulary builds resilient recall and exam confidence.",
        "初心忘るべからず (Never forget your initial humble motivation)" to "Keep your daily habit alive and watch your reading fluency skyrocket.",
        "石の上にも三年 (Perseverance conquers everything)" to "Daily practice makes even complex JLPT grammar feel second nature.",
        "日進月歩 (Steady, rapid progress every day and month)" to "Clear your daily flashcard queue to earn Student XP and level up."
    )

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                lightColor = Color.parseColor("#E65100")
                enableVibration(true)
                setShowBadge(true)
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun showStudyReminder(
        context: Context,
        streakDays: Int = 1,
        dueCardCount: Int = 0,
        userName: String = "Scholar",
        todayCompletedCount: Int = 0,
        dailyGoal: Int = 15,
        isSmartReminder: Boolean = false
    ) {
        createNotificationChannel(context)

        // Check notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("EXTRA_NAVIGATE_TO", "study")
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val proverb = MOTIVATIONAL_PROVERBS[Random.nextInt(MOTIVATIONAL_PROVERBS.size)]
        val remainingGoal = (dailyGoal - todayCompletedCount).coerceAtLeast(0)

        val title = when {
            isSmartReminder && remainingGoal > 0 -> "🎯 $remainingGoal words left to reach today's goal, $userName!"
            streakDays > 1 -> "🔥 Keep your $streakDays-Day Streak Alive, $userName!"
            dueCardCount > 0 -> "⏰ $dueCardCount JLPT N3 Words Ready for Review"
            else -> "⛩️ Time for Daily Japanese Study!"
        }

        val contentText = when {
            isSmartReminder && remainingGoal > 0 -> "You've studied $todayCompletedCount/$dailyGoal words today. Finish your daily goal to protect your $streakDays-day streak!"
            dueCardCount > 0 -> "You have $dueCardCount cards due in your SRS queue today. Quick 5-minute session!"
            else -> "Keep your daily rhythm strong! Review cards or take a quick Quiz Arena test."
        }

        val bigText = buildString {
            append(contentText)
            append("\n\n")
            append("💡 “${proverb.first}”\n")
            append(proverb.second)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(contentText)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setBigContentTitle(title)
                    .bigText(bigText)
                    .setSummaryText(if (isSmartReminder) "Smart Goal Reminder ($todayCompletedCount/$dailyGoal)" else "JLPT N3 Spaced Repetition")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setColor(0xFFE65100.toInt())
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        try {
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
        } catch (e: SecurityException) {
            // In case permission was revoked in settings
        }
    }
}
