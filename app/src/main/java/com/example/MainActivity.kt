package com.example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import com.example.reminder.NotificationHelper
import com.example.reminder.ReminderScheduler
import com.example.ui.MainApp

class MainActivity : ComponentActivity() {
    private val initialTargetDestination = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize notification channel and ensure daily study reminder is scheduled
        NotificationHelper.createNotificationChannel(this)
        ReminderScheduler.rescheduleFromPreferences(this)

        initialTargetDestination.value = intent?.getStringExtra("EXTRA_NAVIGATE_TO")

        setContent {
            MainApp(initialDestination = initialTargetDestination.value)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val target = intent.getStringExtra("EXTRA_NAVIGATE_TO")
        if (target != null) {
            initialTargetDestination.value = target
        }
    }
}

