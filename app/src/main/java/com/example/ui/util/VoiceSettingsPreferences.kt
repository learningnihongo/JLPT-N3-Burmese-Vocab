package com.example.ui.util

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class VoiceSettings(
    val speechRate: Float = 1.0f,
    val pitch: Float = 1.0f,
    val preferPhoneticReading: Boolean = false,
    val autoPlayAudioOnFlip: Boolean = false,
    val slowSpeechRate: Float = 0.7f
)

class VoiceSettingsPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("kanji_voice_settings", Context.MODE_PRIVATE)

    private val _settingsFlow = MutableStateFlow(loadSettings())
    val settingsFlow: StateFlow<VoiceSettings> = _settingsFlow.asStateFlow()

    fun getSettings(): VoiceSettings = loadSettings()

    fun updateSettings(
        speechRate: Float = _settingsFlow.value.speechRate,
        pitch: Float = _settingsFlow.value.pitch,
        preferPhoneticReading: Boolean = _settingsFlow.value.preferPhoneticReading,
        autoPlayAudioOnFlip: Boolean = _settingsFlow.value.autoPlayAudioOnFlip,
        slowSpeechRate: Float = _settingsFlow.value.slowSpeechRate
    ) {
        prefs.edit().apply {
            putFloat(KEY_SPEECH_RATE, speechRate.coerceIn(0.5f, 1.5f))
            putFloat(KEY_PITCH, pitch.coerceIn(0.7f, 1.4f))
            putBoolean(KEY_PREFER_PHONETIC, preferPhoneticReading)
            putBoolean(KEY_AUTO_PLAY_FLIP, autoPlayAudioOnFlip)
            putFloat(KEY_SLOW_RATE, slowSpeechRate.coerceIn(0.5f, 0.9f))
            apply()
        }
        _settingsFlow.value = loadSettings()
    }

    private fun loadSettings(): VoiceSettings {
        return VoiceSettings(
            speechRate = prefs.getFloat(KEY_SPEECH_RATE, 1.0f),
            pitch = prefs.getFloat(KEY_PITCH, 1.0f),
            preferPhoneticReading = prefs.getBoolean(KEY_PREFER_PHONETIC, false),
            autoPlayAudioOnFlip = prefs.getBoolean(KEY_AUTO_PLAY_FLIP, false),
            slowSpeechRate = prefs.getFloat(KEY_SLOW_RATE, 0.7f)
        )
    }

    companion object {
        private const val KEY_SPEECH_RATE = "voice_speech_rate"
        private const val KEY_PITCH = "voice_pitch"
        private const val KEY_PREFER_PHONETIC = "voice_prefer_phonetic"
        private const val KEY_AUTO_PLAY_FLIP = "voice_auto_play_flip"
        private const val KEY_SLOW_RATE = "voice_slow_rate"
    }
}
