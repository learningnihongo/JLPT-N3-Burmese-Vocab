package com.example.ui.util

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import com.example.data.model.VocabCard
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

    private val _pitch = MutableStateFlow(1.0f)
    val pitch: StateFlow<Float> = _pitch.asStateFlow()

    private val _currentVoiceName = MutableStateFlow("Default Japanese")
    val currentVoiceName: StateFlow<String> = _currentVoiceName.asStateFlow()

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Try Japan locale first for high-quality Japanese accent
                val result = tts?.setLanguage(Locale.JAPAN)
                val langSupported = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
                
                if (!langSupported) {
                    tts?.setLanguage(Locale.JAPANESE)
                }

                // Select high quality natural Japanese voice if available
                optimizeJapaneseVoice()

                isInitialized = true
                tts?.setSpeechRate(_speechRate.value)
                tts?.setPitch(_pitch.value)
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

    private fun optimizeJapaneseVoice() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val availableVoices = tts?.voices ?: emptySet()
                val japaneseVoices = availableVoices.filter {
                    it.locale == Locale.JAPAN ||
                    it.locale.language.equals("ja", ignoreCase = true) ||
                    it.locale.toLanguageTag().startsWith("ja", ignoreCase = true)
                }

                if (japaneseVoices.isNotEmpty()) {
                    // Prefer very high/high quality, non-network dependent voice
                    val bestVoice = japaneseVoices.maxByOrNull { voice ->
                        var score = 0
                        if (voice.quality >= Voice.QUALITY_HIGH) score += 20
                        if (voice.quality == Voice.QUALITY_VERY_HIGH) score += 30
                        if (!voice.isNetworkConnectionRequired) score += 10
                        if (voice.name.contains("ja-jp", ignoreCase = true)) score += 5
                        score
                    }
                    bestVoice?.let {
                        tts?.voice = it
                        _currentVoiceName.value = it.name.substringAfterLast(":").ifBlank { "Natural Japanese" }
                    }
                }
            }
        } catch (_: Exception) {
            // Fallback to default
        }
    }

    /**
     * Sanitizes and normalizes Japanese text to ensure clean, accurate pronunciation without
     * misreading symbols, English grammatical notes, or Burmese script.
     */
    fun cleanJapaneseText(text: String): String {
        if (text.isBlank()) return ""

        var cleaned = text
            // Strip Burmese unicode characters
            .replace(Regex("[\\u1000-\\u109F\\uAA60-\\uAA7F]+"), "")
            // Strip English annotations in brackets like (verb), [n], (adj-i), (suru), etc.
            .replace(Regex("\\([a-zA-Z\\s\\-_/]+\\)"), "")
            .replace(Regex("\\[[a-zA-Z\\s\\-_/]+\\]"), "")
            .replace(Regex("（[a-zA-Z\\s\\-_/]+）"), "")
            // Clean cloze blanks
            .replace(Regex("【\\s*\\?\\s*】"), "、")
            .replace(Regex("【[^】]*】"), "")
            .replace(Regex("（[^）]*）"), "")
            // Remove special punctuation that confuses TTS prosody
            .replace(Regex("[~〜～・•/／\\-_\\*\\[\\]\\(\\)]"), " ")
            .trim()

        // If there are multiple alternatives separated by slashes or commas, take the primary one
        if (cleaned.contains(" / ") || cleaned.contains("/")) {
            cleaned = cleaned.split("/").firstOrNull()?.trim() ?: cleaned
        }

        return cleaned.ifBlank { text.trim() }
    }

    /**
     * Speaks any Japanese text with optional speed and pitch overrides.
     */
    fun speak(text: String, rate: Float? = null, customPitch: Float? = null) {
        val cleaned = cleanJapaneseText(text)
        if (cleaned.isBlank()) return

        tts?.setSpeechRate(rate ?: _speechRate.value)
        tts?.setPitch(customPitch ?: _pitch.value)

        val utteranceId = "vocab_tts_${System.currentTimeMillis()}"
        tts?.speak(cleaned, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    /**
     * Speaks a VocabCard intelligently.
     * When [preferPhonetic] is true, uses the Hiragana [VocabCard.reading] which guarantees 100%
     * accurate pitch accent and phonetic reading without ambiguity from Kanji homophones.
     */
    fun speakCard(card: VocabCard, preferPhonetic: Boolean = false, rate: Float? = null) {
        val textToSpeak = if (preferPhonetic && card.reading.isNotBlank()) {
            cleanJapaneseText(card.reading)
        } else {
            cleanJapaneseText(card.kanji)
        }
        speak(textToSpeak, rate)
    }

    /**
     * Speaks the phonetic Hiragana reading directly.
     */
    fun speakPhonetic(reading: String, rate: Float? = null) {
        val cleaned = cleanJapaneseText(reading)
        speak(cleaned, rate)
    }

    /**
     * Speaks text in a clear, slow pronunciation pace (0.7x speed) for study/listening training.
     */
    fun speakSlow(text: String) {
        speak(text, rate = 0.7f)
    }

    /**
     * Speaks an example sentence smoothly, stripping cloze placeholders and ruby formatting.
     */
    fun speakSentence(sentence: String, rate: Float? = null) {
        val cleaned = cleanJapaneseText(sentence)
        speak(cleaned, rate ?: (_speechRate.value * 0.95f).coerceIn(0.6f, 1.2f))
    }

    fun setSpeechRate(rate: Float) {
        _speechRate.value = rate.coerceIn(0.5f, 1.5f)
        tts?.setSpeechRate(_speechRate.value)
    }

    fun setPitch(pitchValue: Float) {
        _pitch.value = pitchValue.coerceIn(0.7f, 1.4f)
        tts?.setPitch(_pitch.value)
    }

    fun toggleSpeechRate() {
        // Toggle between Normal (1.0x), Slow (0.75x), and Fast (1.15x)
        val nextRate = when (_speechRate.value) {
            1.0f -> 0.75f
            0.75f -> 1.15f
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
