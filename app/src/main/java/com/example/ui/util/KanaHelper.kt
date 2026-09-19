package com.example.ui.util

import com.example.data.model.VocabCard
import com.example.ui.viewmodel.SearchFilterTarget

/**
 * KanaHelper provides smart conversions between Romaji, Hiragana, and Katakana,
 * and handles intelligent multi-script search matching for Kanji flashcards
 * by their reading, meaning, and characters.
 */
object KanaHelper {

    private val ROMAJI_TO_HIRAGANA_MAP = mapOf(
        // Digraphs & Palatalized sounds (3 chars)
        "kya" to "きゃ", "kyu" to "きゅ", "kyo" to "きょ",
        "sha" to "しゃ", "shu" to "しゅ", "sho" to "しょ",
        "cha" to "ちゃ", "chu" to "ちゅ", "cho" to "ちょ",
        "nya" to "にゃ", "nyu" to "にゅ", "nyo" to "にょ",
        "hya" to "ひゃ", "hyu" to "ひゅ", "hyo" to "ひょ",
        "mya" to "みゃ", "myu" to "みゅ", "myo" to "みょ",
        "rya" to "りゃ", "ryu" to "りゅ", "ryo" to "りょ",
        "gya" to "ぎゃ", "gyu" to "ぎゅ", "gyo" to "ぎょ",
        "jya" to "じゃ", "jyu" to "じゅ", "jyo" to "じょ",
        "bya" to "びゃ", "byu" to "びゅ", "byo" to "びょ",
        "pya" to "ぴゃ", "pyu" to "ぴゅ", "pyo" to "ぴょ",
        "shi" to "し", "chi" to "ち", "tsu" to "つ",
        "dzu" to "づ", "dji" to "ぢ",

        // Basic 2-char syllables
        "ka" to "か", "ki" to "き", "ku" to "く", "ke" to "け", "ko" to "こ",
        "sa" to "さ", "si" to "し", "su" to "す", "se" to "せ", "so" to "そ",
        "ta" to "た", "ti" to "ち", "tu" to "つ", "te" to "て", "to" to "と",
        "na" to "な", "ni" to "に", "nu" to "ぬ", "ne" to "ね", "no" to "の",
        "ha" to "は", "hi" to "ひ", "fu" to "ふ", "hu" to "ふ", "he" to "へ", "ho" to "ほ",
        "ma" to "ま", "mi" to "み", "mu" to "む", "me" to "め", "mo" to "も",
        "ya" to "や", "yu" to "ゆ", "yo" to "よ",
        "ra" to "ら", "ri" to "り", "ru" to "る", "re" to "れ", "ro" to "ろ",
        "wa" to "わ", "wo" to "を",
        "ga" to "が", "gi" to "ぎ", "gu" to "ぐ", "ge" to "げ", "go" to "ご",
        "za" to "ざ", "zi" to "じ", "zu" to "ず", "ze" to "ぜ", "zo" to "ぞ",
        "ja" to "じゃ", "ji" to "じ", "ju" to "じゅ", "je" to "じぇ", "jo" to "じょ",
        "da" to "だ", "di" to "ぢ", "du" to "づ", "de" to "で", "do" to "ど",
        "ba" to "ば", "bi" to "び", "bu" to "ぶ", "be" to "べ", "bo" to "ぼ",
        "pa" to "ぱ", "pi" to "ぴ", "pu" to "ぷ", "pe" to "ぺ", "po" to "ぽ",
        "nn" to "ん",

        // Single vowels
        "a" to "あ", "i" to "い", "u" to "う", "e" to "え", "o" to "お"
    )

    /**
     * Converts Romaji input (e.g. "dansei", "taberu", "sensei") into Hiragana ("だんせい", "たべる", "せんせい").
     * Non-romaji characters (e.g. spaces, existing Japanese/Burmese characters) are preserved.
     */
    fun romajiToHiragana(input: String): String {
        val lower = input.lowercase().trim()
        if (lower.isEmpty()) return ""

        val sb = StringBuilder()
        var i = 0
        val len = lower.length

        while (i < len) {
            // Check double consonants for sokuon (っ) e.g., "kk", "tt", "ss", "pp", "tch"
            if (i + 1 < len) {
                val c1 = lower[i]
                val c2 = lower[i + 1]
                if (c1 == c2 && c1 in "bcdfghjklmpqrstvwxyz" && c1 != 'n') {
                    sb.append('っ')
                    i++
                    continue
                }
                if (c1 == 't' && c2 == 'c' && i + 2 < len && lower[i + 2] == 'h') {
                    sb.append('っ')
                    i++
                    continue
                }
            }

            // Check 3-character prefixes
            if (i + 3 <= len) {
                val sub3 = lower.substring(i, i + 3)
                val hira3 = ROMAJI_TO_HIRAGANA_MAP[sub3]
                if (hira3 != null) {
                    sb.append(hira3)
                    i += 3
                    continue
                }
            }

            // Check 2-character prefixes
            if (i + 2 <= len) {
                val sub2 = lower.substring(i, i + 2)
                val hira2 = ROMAJI_TO_HIRAGANA_MAP[sub2]
                if (hira2 != null) {
                    sb.append(hira2)
                    i += 2
                    continue
                }
            }

            // Check 1-character vowel
            val sub1 = lower.substring(i, i + 1)
            val hira1 = ROMAJI_TO_HIRAGANA_MAP[sub1]
            if (hira1 != null) {
                sb.append(hira1)
                i += 1
                continue
            }

            // Single 'n' at end or before non-vowel
            if (sub1 == "n") {
                if (i + 1 == len || lower[i + 1] !in "aeiouy") {
                    sb.append('ん')
                    i += 1
                    continue
                }
            }

            // Keep original character if not romaji
            sb.append(lower[i])
            i++
        }

        return sb.toString()
    }

    /**
     * Converts Katakana characters to Hiragana.
     */
    fun katakanaToHiragana(text: String): String {
        val sb = StringBuilder(text.length)
        for (c in text) {
            if (c in '\u30A1'..'\u30F6') {
                sb.append((c.code - 0x60).toChar())
            } else {
                sb.append(c)
            }
        }
        return sb.toString()
    }

    /**
     * Converts Hiragana characters to Katakana.
     */
    fun hiraganaToKatakana(text: String): String {
        val sb = StringBuilder(text.length)
        for (c in text) {
            if (c in '\u3041'..'\u3096') {
                sb.append((c.code + 0x60).toChar())
            } else {
                sb.append(c)
            }
        }
        return sb.toString()
    }

    /**
     * Matches a VocabCard against a user query and a search filter target.
     * Supports:
     * - Kanji characters
     * - Reading in Hiragana, Katakana, or Romaji (e.g. "dansei", "だんせい", "ダンセイ")
     * - Meaning in Burmese or English notes
     */
    fun matchesCard(
        card: VocabCard,
        query: String,
        target: SearchFilterTarget = SearchFilterTarget.ALL
    ): Boolean {
        val raw = query.trim()
        if (raw.isEmpty()) return true

        val lowerQuery = raw.lowercase()
        val hiraganaQuery = romajiToHiragana(lowerQuery)
        val katakanaQuery = hiraganaToKatakana(hiraganaQuery)

        val cardReading = card.reading.trim()
        val cardReadingHira = katakanaToHiragana(cardReading)
        val cardReadingKata = hiraganaToKatakana(cardReading)

        // Reading match:
        // 1. Exact or substring match on card reading
        // 2. Hiragana converted match (handles romaji inputs like "dansei")
        // 3. Katakana converted match
        val matchesReading = cardReading.contains(raw, ignoreCase = true) ||
                (hiraganaQuery.isNotEmpty() && cardReadingHira.contains(hiraganaQuery)) ||
                (katakanaQuery.isNotEmpty() && cardReadingKata.contains(katakanaQuery)) ||
                card.displayReading.contains(raw, ignoreCase = true) ||
                (hiraganaQuery.isNotEmpty() && katakanaToHiragana(card.displayReading).contains(hiraganaQuery))

        // Meaning match:
        // Matches Burmese definition, example sentences, or personal mnemonic notes
        val matchesMeaning = card.meaningBurmese.contains(raw, ignoreCase = true) ||
                card.exampleMeaningBurmese.contains(raw, ignoreCase = true) ||
                card.personalNote.contains(raw, ignoreCase = true) ||
                card.tags.contains(raw, ignoreCase = true)

        // Kanji match:
        val matchesKanji = card.kanji.contains(raw, ignoreCase = true)

        return when (target) {
            SearchFilterTarget.ALL -> matchesReading || matchesMeaning || matchesKanji
            SearchFilterTarget.READING -> matchesReading
            SearchFilterTarget.MEANING -> matchesMeaning
        }
    }
}
