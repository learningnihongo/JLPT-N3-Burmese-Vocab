package com.example.ui.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.data.model.VocabCard
import java.net.URLEncoder

/**
 * Utility helper for:
 * 1. Copying Kanji, Kotoba, readings, and example sentences to clipboard.
 * 2. Opening Google Translate for Japanese to Burmese (Myanmar) or English translations.
 * 3. Searching Kanji, compounds (တွဲလုံးများ), and Kotoba on Jisho.org.
 * 4. Finding related Kanji compound words (တွဲလုံးများ) across the offline vocabulary library.
 */
object StudyActionHelper {

    /**
     * Copies text to the Android Clipboard with friendly Burmese and English toast feedback.
     */
    fun copyToClipboard(
        context: Context,
        text: String,
        label: String = "KanjiKotoba",
        toastMessage: String? = null
    ) {
        if (text.isBlank()) return
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            if (clipboard != null) {
                val clip = ClipData.newPlainText(label, text)
                clipboard.setPrimaryClip(clip)
                val msg = toastMessage ?: "စာသားကို Copy ကူးယူပြီးပါပြီ ✓ ($text)"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Copy မအောင်မြင်ပါ: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Formats and copies full card details (Kanji, Furigana reading, Myanmar meaning, and example).
     */
    fun copyFullCard(context: Context, card: VocabCard) {
        val formatted = buildString {
            append("${card.kanji}【${card.reading}】\n")
            append("အဓိပ္ပာယ်: ${card.meaningBurmese}\n")
            if (card.partOfSpeech.isNotBlank()) {
                append("ဝါစင်္ဂ: ${card.partOfSpeech}\n")
            }
            if (card.exampleSentence.isNotBlank()) {
                append("ဥပမာ: ${card.exampleSentence}\n")
                if (card.exampleMeaningBurmese.isNotBlank()) {
                    append("ဘာသာပြန်: ${card.exampleMeaningBurmese}\n")
                }
            }
            if (card.personalNote.isNotBlank()) {
                append("မှတ်စု: ${card.personalNote}\n")
            }
        }.trim()

        copyToClipboard(
            context = context,
            text = formatted,
            label = "KanjiKotobaCard",
            toastMessage = "${card.kanji} အချက်အလက်အားလုံးကို Copy ကူးပြီးပါပြီ ✓"
        )
    }

    /**
     * Opens Google Translate with Japanese source text and Burmese (my) target.
     * Always copies the Japanese/Kanji text to clipboard so it is never lost,
     * and directly passes the text to the Google Translate App (or browser fallback).
     */
    fun openGoogleTranslate(
        context: Context,
        text: String,
        sourceLang: String = "ja",
        targetLang: String = "my"
    ) {
        if (text.isBlank()) return
        val trimmed = text.trim()
        try {
            // 1. ALWAYS copy text to clipboard first so the user has the Kanji ready to paste
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            val clip = ClipData.newPlainText("Japanese Text", trimmed)
            clipboard?.setPrimaryClip(clip)

            // 2. Try launching Google Translate App with text directly attached via ACTION_SEND
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, trimmed)
                setPackage("com.google.android.apps.translate")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            val packageManager = context.packageManager
            val canLaunchApp = sendIntent.resolveActivity(packageManager) != null

            if (canLaunchApp) {
                Toast.makeText(
                    context,
                    "'$trimmed' ကို Copy ကူးပြီး Google Translate ဖွင့်နေပါသည် ✓",
                    Toast.LENGTH_SHORT
                ).show()
                context.startActivity(sendIntent)
            } else {
                // Fallback to browser URL with encoded text
                val encodedText = URLEncoder.encode(trimmed, "UTF-8")
                val url = "https://translate.google.com/?sl=$sourceLang&tl=$targetLang&text=$encodedText&op=translate"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                Toast.makeText(
                    context,
                    "'$trimmed' ကို Copy ကူးပြီး Google Translate ဖွင့်နေပါသည် ✓",
                    Toast.LENGTH_SHORT
                ).show()
                context.startActivity(browserIntent)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Google Translate ဖွင့်၍မရပါ: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Searches a word, Kanji, or compound on jisho.org.
     * If [isKanjiLookup] is true, appends "#kanji" to jump directly to the Jisho stroke order and radical breakdown page.
     */
    fun openJisho(
        context: Context,
        query: String,
        isKanjiLookup: Boolean = false
    ) {
        if (query.isBlank()) return
        try {
            val trimmed = query.trim()
            val searchQuery = if (isKanjiLookup && trimmed.length == 1 && isKanji(trimmed[0])) {
                "$trimmed #kanji"
            } else {
                trimmed
            }
            val encodedQuery = URLEncoder.encode(searchQuery, "UTF-8")
            val url = "https://jisho.org/search/$encodedQuery"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "jisho.org ဖွင့်၍မရပါ: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Checks if a character is a CJK Unified Ideograph (Kanji).
     */
    fun isKanji(char: Char): Boolean {
        val code = char.code
        return (code in 0x4E00..0x9FFF) ||   // CJK Unified Ideographs
                (code in 0x3400..0x4DBF) ||   // CJK Extension A
                (code in 0xF900..0xFAFF)      // CJK Compatibility Ideographs
    }

    /**
     * Extracts all unique Kanji characters from a given Japanese string.
     */
    fun extractKanjiCharacters(text: String): List<Char> {
        return text.filter { isKanji(it) }.toList().distinct()
    }

    /**
     * Finds all vocabulary words in the local database that contain the given Kanji character.
     * This provides the user with related compound words (တွဲလုံးများ / 熟語 - Jukugo).
     */
    fun findRelatedCompounds(
        kanjiChar: Char,
        allCards: List<VocabCard>
    ): List<VocabCard> {
        val searchStr = kanjiChar.toString()
        return allCards.filter { card ->
            card.kanji.contains(searchStr)
        }
    }

    /**
     * Speaks text using the global TTS helper instance.
     */
    private var sharedTts: TtsHelper? = null

    fun speakText(context: Context, text: String) {
        if (text.isBlank()) return
        if (sharedTts == null) {
            sharedTts = TtsHelper(context.applicationContext)
        }
        sharedTts?.speak(text)
    }
}
