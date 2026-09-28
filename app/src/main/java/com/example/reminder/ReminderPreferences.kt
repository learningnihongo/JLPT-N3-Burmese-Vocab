package com.example.reminder

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ReminderSettings(
    val isEnabled: Boolean = true,
    val reminderHour: Int = 20, // 8:00 PM default
    val reminderMinute: Int = 0,
    val soundEnabled: Boolean = true,
    val motivationalQuotesEnabled: Boolean = true,
    val isSmartReminderEnabled: Boolean = true // Smart reminder: only notifies if daily study goal is not yet completed
) {
    val formattedTime: String
        get() {
            val period = if (reminderHour >= 12) "PM" else "AM"
            val hourIn12 = when {
                reminderHour == 0 -> 12
                reminderHour > 12 -> reminderHour - 12
                else -> reminderHour
            }
            return String.format("%d:%02d %s", hourIn12, reminderMinute, period)
        }
}

class ReminderPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("kanji_study_reminders", Context.MODE_PRIVATE)

    private val _settingsFlow = MutableStateFlow(loadSettings())
    val settingsFlow: StateFlow<ReminderSettings> = _settingsFlow.asStateFlow()

    fun getSettings(): ReminderSettings = loadSettings()

    fun updateSettings(
        isEnabled: Boolean,
        reminderHour: Int,
        reminderMinute: Int,
        soundEnabled: Boolean = true,
        motivationalQuotesEnabled: Boolean = true,
        isSmartReminderEnabled: Boolean = true
    ) {
        prefs.edit().apply {
            putBoolean(KEY_ENABLED, isEnabled)
            putInt(KEY_HOUR, reminderHour.coerceIn(0, 23))
            putInt(KEY_MINUTE, reminderMinute.coerceIn(0, 59))
            putBoolean(KEY_SOUND, soundEnabled)
            putBoolean(KEY_QUOTES, motivationalQuotesEnabled)
            putBoolean(KEY_SMART_REMINDER, isSmartReminderEnabled)
            apply()
        }
        _settingsFlow.value = loadSettings()
    }

    fun setEnabled(isEnabled: Boolean) {
        val current = loadSettings()
        updateSettings(
            isEnabled = isEnabled,
            reminderHour = current.reminderHour,
            reminderMinute = current.reminderMinute,
            soundEnabled = current.soundEnabled,
            motivationalQuotesEnabled = current.motivationalQuotesEnabled,
            isSmartReminderEnabled = current.isSmartReminderEnabled
        )
    }

    fun setSmartReminderEnabled(isSmart: Boolean) {
        val current = loadSettings()
        updateSettings(
            isEnabled = current.isEnabled,
            reminderHour = current.reminderHour,
            reminderMinute = current.reminderMinute,
            soundEnabled = current.soundEnabled,
            motivationalQuotesEnabled = current.motivationalQuotesEnabled,
            isSmartReminderEnabled = isSmart
        )
    }

    fun setTime(hour: Int, minute: Int) {
        val current = loadSettings()
        updateSettings(
            isEnabled = current.isEnabled,
            reminderHour = hour,
            reminderMinute = minute,
            soundEnabled = current.soundEnabled,
            motivationalQuotesEnabled = current.motivationalQuotesEnabled,
            isSmartReminderEnabled = current.isSmartReminderEnabled
        )
    }

    private fun loadSettings(): ReminderSettings {
        return ReminderSettings(
            isEnabled = prefs.getBoolean(KEY_ENABLED, true),
            reminderHour = prefs.getInt(KEY_HOUR, 20),
            reminderMinute = prefs.getInt(KEY_MINUTE, 0),
            soundEnabled = prefs.getBoolean(KEY_SOUND, true),
            motivationalQuotesEnabled = prefs.getBoolean(KEY_QUOTES, true),
            isSmartReminderEnabled = prefs.getBoolean(KEY_SMART_REMINDER, true)
        )
    }

    companion object {
        private const val KEY_ENABLED = "key_reminder_enabled"
        private const val KEY_HOUR = "key_reminder_hour"
        private const val KEY_MINUTE = "key_reminder_minute"
        private const val KEY_SOUND = "key_reminder_sound"
        private const val KEY_QUOTES = "key_reminder_quotes"
        private const val KEY_SMART_REMINDER = "key_smart_reminder"
    }
}
