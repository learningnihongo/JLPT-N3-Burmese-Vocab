package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.reminder.NotificationHelper
import com.example.reminder.ReminderScheduler
import com.example.ui.MainApp
import com.example.ui.theme.KanjiKotobaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize notification channel and ensure daily study reminder is scheduled
        NotificationHelper.createNotificationChannel(this)
        ReminderScheduler.rescheduleFromPreferences(this)

        setContent {
            MainApp()
        }
    }
}

