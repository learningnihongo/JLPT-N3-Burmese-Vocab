package com.example.data.util

import com.example.data.model.VocabCard

/**
 * Data model for Japanese collocations, compound words, and phrase pairings (တွဲလုံးများ / 連語・コロケーション).
 * Essential for JLPT N3/N2 mastery and natural Japanese expression.
 */
data class CollocationItem(
    val id: Long,
    val targetWord: String,            // Base vocabulary / kanji (e.g., "約束", "気", "予定")
    val phraseJapanese: String,        // Full collocation (e.g., "約束を守る", "気がする", "予定を立てる")
    val reading: String,               // Reading with furigana/hiragana (e.g., "やくそくをまもる")
    val meaningBurmese: String,        // Myanmar translation (e.g., "ကတိတည်သည်")
    val meaningEnglish: String = "",   // English translation
    val clozePrompt: String = "",      // Cloze prompt for active recall (e.g., "約束を［ … ］")
    val clozeAnswer: String = "",      // Expected pairing answer (e.g., "守る")
    val exampleSentence: String = "",  // Context sentence
    val exampleBurmese: String = "",   // Example Burmese translation
    val patternType: String = "動詞＋助詞 (Collocation)" // Grammar pattern
)

object CollocationData {

    /**
     * Curated list of high-frequency JLPT N3 Collocations (တွဲလုံးများ).
     */
    val JLPT_N3_COLLOCATIONS = listOf(
        CollocationItem(
            id = 1L,
            targetWord = "約束",
            phraseJapanese = "約束を守る",
            reading = "やくそくをまもる",
            meaningBurmese = "ကတိတည်သည် / ချိန်းဆိုချက်အတိုင်း လိုက်နာသည်",
            meaningEnglish = "Keep a promise",
            clozePrompt = "約束を［ … ］",
            clozeAnswer = "守る",
            exampleSentence = "親友との約束は必ず守ります。",
            exampleBurmese = "သူငယ်ချင်းအရင်းနှင့် ထားသော ကတိကို မပျက်မကွက် တည်ပါသည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 2L,
            targetWord = "約束",
            phraseJapanese = "約束を破る",
            reading = "やくそくをやぶる",
            meaningBurmese = "ကတိဖျက်သည် / ချိန်းဆိုချက် ချိုးဖောက်သည်",
            meaningEnglish = "Break a promise",
            clozePrompt = "約束を［ … ］",
            clozeAnswer = "破る",
            exampleSentence = "決して約束を破ってはいけません。",
            exampleBurmese = "မည်သည့်အခါမျှ ကတိမဖျက်ရပါ။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 3L,
            targetWord = "予定",
            phraseJapanese = "予定を立てる",
            reading = "よていをたてる",
            meaningBurmese = "အစီအစဉ်ဆွဲသည် / အချိန်ဇယားချမှတ်သည်",
            meaningEnglish = "Make a plan / schedule",
            clozePrompt = "予定を［ … ］",
            clozeAnswer = "立てる",
            exampleSentence = "週末の旅行の予定を立てましょう。",
            exampleBurmese = "စနေ၊ တနင်္ဂနွေ ခရီးစဉ် အစီအစဉ်ဆွဲကြစို့။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 4L,
            targetWord = "相談",
            phraseJapanese = "相談に乗る",
            reading = "そうだんにのる",
            meaningBurmese = "တိုင်ပင်ဆွေးနွေးမှုကို လက်ခံအကြံပေးသည်",
            meaningEnglish = "Give advice / listen to someone's consultation",
            clozePrompt = "相談に［ … ］",
            clozeAnswer = "乗る",
            exampleSentence = "先輩はいつも親身に相談に乗ってくれる。",
            exampleBurmese = "စီနီယာသည် အမြဲတစေ စိတ်ရင်းစေတနာဖြင့် အကြံဉာဏ်ပေးတတ်သည်။",
            patternType = "Noun + に + Verb"
        ),
        CollocationItem(
            id = 5L,
            targetWord = "気",
            phraseJapanese = "気がする",
            reading = "きがする",
            meaningBurmese = "စိတ်ထဲထင်သည် / ခံစားရသည်",
            meaningEnglish = "Have a feeling / feel like",
            clozePrompt = "気が［ … ］",
            clozeAnswer = "する",
            exampleSentence = "今日は何かいいことがありそうな気がする。",
            exampleBurmese = "ဒီနေ့ ကောင်းတဲ့ကိစ္စတစ်ခုခု ဖြစ်လာမယ့်ပုံ ထင်နေမိသည်။",
            patternType = "Set Phrase (慣用句)"
        ),
        CollocationItem(
            id = 6L,
            targetWord = "気",
            phraseJapanese = "気に入る",
            reading = "きにいる",
            meaningBurmese = "စိတ်ကြိုက်တွေ့သည် / သဘောကျသည်",
            meaningEnglish = "Like / be pleased with",
            clozePrompt = "気に［ … ］",
            clozeAnswer = "入る",
            exampleSentence = "このデザインがとても気に入りました。",
            exampleBurmese = "ဤဒီဇိုင်းကို အလွန် သဘောကျပါသည်။",
            patternType = "Set Phrase (慣用句)"
        ),
        CollocationItem(
            id = 7L,
            targetWord = "気",
            phraseJapanese = "気を配る",
            reading = "きをくばる",
            meaningBurmese = "အသေးစိတ် အလေးထားဂရုစိုက်သည်",
            meaningEnglish = "Pay attention / be considerate to everyone",
            clozePrompt = "気を［ … ］",
            clozeAnswer = "配る",
            exampleSentence = "周囲の人々に気を配ることが大切です。",
            exampleBurmese = "ပတ်ဝန်းကျင်ရှိ လူများကို အလေးထားဂရုစိုက်ပေးခြင်းသည် အရေးကြီးသည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 8L,
            targetWord = "席",
            phraseJapanese = "席を外す",
            reading = "せきをはずす",
            meaningBurmese = "နေရာမှ ခေတ္တထွက်သည် / ခေတ္တပျက်ကွက်သည်",
            meaningEnglish = "Leave one's desk / step away temporarily",
            clozePrompt = "席を［ … ］",
            clozeAnswer = "外す",
            exampleSentence = "田中さんは今、席を外しております。",
            exampleBurmese = "တနခစံသည် ယခု နေရာမှ ခေတ္တထွက်သွားပါသည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 9L,
            targetWord = "迷惑",
            phraseJapanese = "迷惑をかける",
            reading = "めいわくをかける",
            meaningBurmese = "ဒုက္ခပေးသည် / အနှောင့်အယှက်ဖြစ်စေသည်",
            meaningEnglish = "Cause trouble / inconvenience to others",
            clozePrompt = "迷惑を［ … ］",
            clozeAnswer = "かける",
            exampleSentence = "他人に迷惑をかけてはいけません。",
            exampleBurmese = "သူတစ်ပါးအား အနှောင့်အယှက် ဒုက္ခမပေးရပါ။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 10L,
            targetWord = "連絡",
            phraseJapanese = "連絡を取る",
            reading = "れんらくをとる",
            meaningBurmese = "အဆက်အသွယ်လုပ်သည် / အကြောင်းကြားသည်",
            meaningEnglish = "Get in touch / establish contact",
            clozePrompt = "連絡を［ … ］",
            clozeAnswer = "取る",
            exampleSentence = "急ぎの用件で先生と連絡を取りました。",
            exampleBurmese = "အရေးကြီးကိစ္စဖြင့် ဆရာနှင့် အဆက်အသွယ် ပြုလုပ်ခဲ့သည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 11L,
            targetWord = "世話",
            phraseJapanese = "世話をする",
            reading = "せわをする",
            meaningBurmese = "ပြုစုစောင့်ရှောက်သည် / ကူညီစောင့်ရှောက်သည်",
            meaningEnglish = "Take care of / look after",
            clozePrompt = "世話を［ … ］",
            clozeAnswer = "する",
            exampleSentence = "病気の妹の世話をしました。",
            exampleBurmese = "နေမကောင်းသော ညီမလေးအား ပြုစုစောင့်ရှောက်ခဲ့သည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 12L,
            targetWord = "役",
            phraseJapanese = "役に立つ",
            reading = "やくにたつ",
            meaningBurmese = "အသုံးဝင်သည် / အကျိုးရှိသည်",
            meaningEnglish = "Be useful / helpful",
            clozePrompt = "役に［ … ］",
            clozeAnswer = "立つ",
            exampleSentence = "このアプリは日本語の勉強に大いに役に立つ。",
            exampleBurmese = "ဤအက်ပ်သည် ဂျပန်စာလေ့လာရာတွင် အလွန် အသုံးဝင်သည်။",
            patternType = "Noun + に + Verb"
        ),
        CollocationItem(
            id = 13L,
            targetWord = "興味",
            phraseJapanese = "興味を持つ",
            reading = "きょうみをもつ",
            meaningBurmese = "စိတ်ဝင်စားမှုရှိသည် / စိတ်ဝင်စားသည်",
            meaningEnglish = "Have / take an interest in",
            clozePrompt = "興味を［ … ］",
            clozeAnswer = "持つ",
            exampleSentence = "日本の伝統文化に興味を持っています。",
            exampleBurmese = "ဂျပန်ရိုးရာယဉ်ကျေးမှုကို စိတ်ဝင်စားမှုရှိပါသည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 14L,
            targetWord = "責任",
            phraseJapanese = "責任を持つ",
            reading = "せきにんをもつ",
            meaningBurmese = "တာဝန်ယူသည် / တာဝန်ရှိသည်",
            meaningEnglish = "Take responsibility",
            clozePrompt = "責任を［ … ］",
            clozeAnswer = "持つ",
            exampleSentence = "自分の仕事には最後まで責任を持つべきだ。",
            exampleBurmese = "မိမိလုပ်ငန်းအား အဆုံးထိ တာဝန်ယူသင့်သည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 15L,
            targetWord = "影響",
            phraseJapanese = "影響を与える",
            reading = "えいきょうをあたえる",
            meaningBurmese = "လွှမ်းမိုးမှုပေးသည် / သက်ရောက်မှုရှိစေသည်",
            meaningEnglish = "Exert influence / have an impact on",
            clozePrompt = "影響を［ … ］",
            clozeAnswer = "与える",
            exampleSentence = "親の行動は子どもに大きな影響を与える。",
            exampleBurmese = "မိဘ၏ အပြုအမူသည် ကလေးအပေါ် ကြီးမားသော လွှမ်းမိုးမှု သက်ရောက်စေသည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 16L,
            targetWord = "習慣",
            phraseJapanese = "習慣が身につく",
            reading = "しゅうかんがみにつく",
            meaningBurmese = "အလေ့အကျင့်ရသွားသည် / အကျင့်ဖြစ်လာသည်",
            meaningEnglish = "Form a habit / acquire a habit",
            clozePrompt = "習慣が［ … ］",
            clozeAnswer = "身につく",
            exampleSentence = "毎朝の単語学習の習慣が身についた。",
            exampleBurmese = "မနက်တိုင်း ဝေါဟာရကျက်မှတ်သည့် အလေ့အကျင့် ရသွားခဲ့သည်။",
            patternType = "Set Phrase (慣用句)"
        ),
        CollocationItem(
            id = 17L,
            targetWord = "期待",
            phraseJapanese = "期待に応える",
            reading = "きたいにこたえる",
            meaningBurmese = "မျှော်လင့်ချက်ကို ဖြည့်ဆည်းပေးသည် / ပြည့်မီစေသည်",
            meaningEnglish = "Meet expectations / live up to expectations",
            clozePrompt = "期待に［ … ］",
            clozeAnswer = "応える",
            exampleSentence = "両親の期待に応えるよう全力で励みます。",
            exampleBurmese = "မိဘများ၏ မျှော်လင့်ချက်ကို ပြည့်မီစေရန် အစွမ်းကုန် ကြိုးစားပါမည်။",
            patternType = "Noun + に + Verb"
        ),
        CollocationItem(
            id = 18L,
            targetWord = "注目",
            phraseJapanese = "注目を集める",
            reading = "ちゅうもくをあつめる",
            meaningBurmese = "အာရုံစိုက်မှုခံရသည် / လူအများ သတိပြုမိစေသည်",
            meaningEnglish = "Attract attention / draw spotlight",
            clozePrompt = "注目を［ … ］",
            clozeAnswer = "集める",
            exampleSentence = "新しいAI技術が世界中で注目を集めている。",
            exampleBurmese = "AI နည်းပညာအသစ်သည် တစ်ကမ္ဘာလုံးတွင် အာရုံစိုက်မှု ခံနေရသည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 19L,
            targetWord = "解決",
            phraseJapanese = "解決を図る",
            reading = "かいけつをはかる",
            meaningBurmese = "ပြဿနာဖြေရှင်းရန် ကြိုးပမ်းသည်",
            meaningEnglish = "Seek a solution / strive for resolution",
            clozePrompt = "解決を［ … ］",
            clozeAnswer = "図る",
            exampleSentence = "話し合いによって問題の早期解決を図る。",
            exampleBurmese = "ဆွေးနွေးညှိနှိုင်းခြင်းဖြင့် ပြဿနာကို အမြန်ဆုံး ဖြေရှင်းရန် ကြိုးပမ်းသည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 20L,
            targetWord = "案内",
            phraseJapanese = "案内をする",
            reading = "あんないをする",
            meaningBurmese = "လမ်းညွှန်ပြသပေးသည် / မိတ်ဆက်ပေးသည်",
            meaningEnglish = "Show around / guide",
            clozePrompt = "案内を［ … ］",
            clozeAnswer = "する",
            exampleSentence = "外国からのゲストに街の案内をしました。",
            exampleBurmese = "နိုင်ငံခြားမှ ဧည့်သည်များအား မြို့တွင်း လမ်းညွှန်ပြသပေးခဲ့သည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 21L,
            targetWord = "遠慮",
            phraseJapanese = "遠慮なく",
            reading = "えんりょなく",
            meaningBurmese = "အားမနာဘဲ / လွတ်လပ်စွာ",
            meaningEnglish = "Without hesitation / freely",
            clozePrompt = "遠慮［ … ］",
            clozeAnswer = "なく",
            exampleSentence = "遠慮なく質問してください。",
            exampleBurmese = "အားမနာဘဲ လွတ်လပ်စွာ မေးမြန်းနိုင်ပါသည်။",
            patternType = "Adverbial Pairing"
        ),
        CollocationItem(
            id = 22L,
            targetWord = "経験",
            phraseJapanese = "経験を積む",
            reading = "けいけんをつむ",
            meaningBurmese = "အတွေ့အကြုံ စုဆောင်းသည် / အတွေ့အကြုံ ယူသည်",
            meaningEnglish = "Gain experience / build experience",
            clozePrompt = "経験を［ … ］",
            clozeAnswer = "積む",
            exampleSentence = "若いうちに様々な経験を積むことが肝要だ。",
            exampleBurmese = "ငယ်ရွယ်စဉ်တွင် အမျိုးမျိုးသော အတွေ့အကြုံ စုဆောင်းရန် အဓိကကျသည်။",
            patternType = "Noun + を + Verb"
        ),
        CollocationItem(
            id = 23L,
            targetWord = "腹",
            phraseJapanese = "腹が立つ",
            reading = "はらがたつ",
            meaningBurmese = "ဒေါသထွက်သည် / စိတ်ဆိုးသည်",
            meaningEnglish = "Get angry / lose one's temper",
            clozePrompt = "腹が［ … ］",
            clozeAnswer = "立つ",
            exampleSentence = "理不尽な対応に思わず腹が立った。",
            exampleBurmese = "မမျှတသော တုံ့ပြန်မှုကြောင့် မရည်ရွယ်ဘဲ ဒေါသထွက်ခဲ့သည်။",
            patternType = "Set Phrase (慣用句)"
        ),
        CollocationItem(
            id = 24L,
            targetWord = "手",
            phraseJapanese = "手を抜く",
            reading = "てをぬく",
            meaningBurmese = "ပေါ့ပေါ့ဆဆလုပ်သည် / အစွမ်းကုန်မကြိုးစားဘဲ ဖြတ်လမ်းလိုက်သည်",
            meaningEnglish = "Cut corners / slack off",
            clozePrompt = "手を［ … ］",
            clozeAnswer = "抜く",
            exampleSentence = "どんな仕事でも手を抜いてはならない。",
            exampleBurmese = "မည်သည့်အလုပ်တွင်မဆို ပေါ့ပေါ့ဆဆ မလုပ်ရပါ။",
            patternType = "Set Phrase (慣用句)"
        ),
        CollocationItem(
            id = 25L,
            targetWord = "耳",
            phraseJapanese = "耳を傾ける",
            reading = "みみをかたむける",
            meaningBurmese = "နားစွင့်သည် / အာရုံစိုက်နားထောင်သည်",
            meaningEnglish = "Listen attentively / lend an ear",
            clozePrompt = "耳を［ … ］",
            clozeAnswer = "傾ける",
            exampleSentence = "国民の声に真摯に耳を傾けるべきだ。",
            exampleBurmese = "ပြည်သူ့အသံကို ရိုးသားစွာ အာရုံစိုက် နားထောင်သင့်သည်။",
            patternType = "Set Phrase (慣用句)"
        )
    )

    /**
     * Finds collocations and compound pairings for a given card or kanji word.
     * Combines exact curated collocations with dynamic compound words from the card library.
     */
    fun getCollocationsForWord(
        word: String,
        allCards: List<VocabCard> = emptyList()
    ): List<CollocationItem> {
        val cleanWord = word.trim()
        val results = mutableListOf<CollocationItem>()

        // 1. Check curated collocations matching the word or substring
        val directMatches = JLPT_N3_COLLOCATIONS.filter { item ->
            item.targetWord.equals(cleanWord, ignoreCase = true) ||
                cleanWord.contains(item.targetWord) ||
                item.phraseJapanese.contains(cleanWord)
        }
        results.addAll(directMatches)

        // 2. Derive compound words (Jukugo တွဲလုံးများ) from card database if available
        if (allCards.isNotEmpty()) {
            val kanjiChars = cleanWord.filter { it in '\u4e00'..'\u9faf' }
            val compoundCards = allCards.filter { other ->
                other.kanji != cleanWord && (
                    other.kanji.contains(cleanWord) ||
                    (kanjiChars.isNotEmpty() && kanjiChars.any { char -> other.kanji.contains(char) })
                )
            }.take(4)

            compoundCards.forEachIndexed { index, card ->
                if (results.none { it.phraseJapanese == card.kanji }) {
                    results.add(
                        CollocationItem(
                            id = 1000L + index,
                            targetWord = cleanWord,
                            phraseJapanese = card.kanji,
                            reading = card.reading,
                            meaningBurmese = card.meaningBurmese,
                            meaningEnglish = card.sectionTitle,
                            clozePrompt = card.kanji.replace(cleanWord, "［ … ］"),
                            clozeAnswer = cleanWord,
                            exampleSentence = card.exampleSentence,
                            exampleBurmese = card.exampleMeaningBurmese,
                            patternType = "Kanji တွဲလုံး (Jukugo)"
                        )
                    )
                }
            }
        }

        return results
    }

    /**
     * Converts a CollocationItem into a VocabCard for seamless 3D flashcard study!
     */
    fun toVocabCard(item: CollocationItem): VocabCard {
        return VocabCard(
            id = 90000L + item.id,
            lessonNumber = 99,
            lessonTitle = "တွဲလုံးများ (Collocations)",
            sectionTitle = item.patternType,
            kanji = item.phraseJapanese,
            reading = item.reading,
            meaningBurmese = item.meaningBurmese,
            partOfSpeech = item.patternType,
            exampleSentence = item.exampleSentence.ifBlank { item.clozePrompt },
            exampleMeaningBurmese = item.exampleBurmese.ifBlank { "အဖြေ: ${item.clozeAnswer}" },
            personalNote = "တွဲလုံး (Collocation): ${item.targetWord} → ${item.clozeAnswer}",
            tags = "တွဲလုံး, Collocation, JLPT N3"
        )
    }

    /**
     * Returns all Collocations formatted as VocabCards for 3D Flashcard study.
     */
    fun getAllCollocationCards(): List<VocabCard> {
        return JLPT_N3_COLLOCATIONS.map { toVocabCard(it) }
    }
}
