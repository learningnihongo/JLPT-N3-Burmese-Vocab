package com.example.ui.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class TtsHelper(context: Context) {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _speechRate = MutableStateFlow(1.0f)
    val speechRate: StateFlow<Float> = _speechRate.asStateFlow()

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.JAPANESE)
                if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    isInitialized = true
                    tts?.setSpeechRate(_speechRate.value)
                }
            }
        }

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _isSpeaking.value = true
            }

            override fun onDone(utteranceId: String?) {
                _isSpeaking.value = false
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                _isSpeaking.value = false
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                _isSpeaking.value = false
            }
        })
    }

    fun speak(text: String, rate: Float? = null) {
        if (text.isBlank()) return
        if (rate != null) {
            tts?.setSpeechRate(rate)
        } else {
            tts?.setSpeechRate(_speechRate.value)
        }
        val utteranceId = "vocab_tts_${System.currentTimeMillis()}"
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun setSpeechRate(rate: Float) {
        _speechRate.value = rate.coerceIn(0.5f, 1.5f)
        tts?.setSpeechRate(_speechRate.value)
    }

    fun toggleSpeechRate() {
        // Toggle between Normal (1.0x), Slow (0.75x), and Natural (0.9x)
        val nextRate = when (_speechRate.value) {
            1.0f -> 0.8f
            0.8f -> 0.6f
            else -> 1.0f
        }
        setSpeechRate(nextRate)
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        _isSpeaking.value = false
    }
}
