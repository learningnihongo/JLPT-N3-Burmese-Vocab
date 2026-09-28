package com.example.data.seed

import com.example.data.model.VocabCard

object VocabSeedDataMastery1 {
    fun getCards(): List<VocabCard> {
        val list = ArrayList<VocabCard>(100)
        fun card(
            id: Long,
            lesson: Int,
            lTitle: String,
            sTitle: String,
            kanji: String,
            reading: String,
            burmese: String,
            pos: String = "名詞",
            example: String = "",
            exampleBurmese: String = "",
            tags: String = ""
        ) {
            list.add(
                VocabCard(
                    id = id,
                    lessonNumber = lesson,
                    lessonTitle = lTitle,
                    sectionTitle = sTitle,
                    kanji = kanji,
                    reading = reading,
                    meaningBurmese = burmese,
                    partOfSpeech = pos,
                    exampleSentence = example,
                    exampleMeaningBurmese = exampleBurmese,
                    tags = tags
                )
            )
        }

        // Lesson 120 (27 items)
        card(4001L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "登る", "のぼる", "တက်သည်။", "動詞", "登るの使い方や意味をしっかり確認して復習しましょう。", "登る ၏ အဓိပ္ပာယ်မှာ 'တက်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4002L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "溶ける", "とける", "အရည်ပျော်သည်။", "動詞", "溶けるの使い方や意味をしっかり確認して復習しましょう。", "溶ける ၏ အဓိပ္ပာယ်မှာ 'အရည်ပျော်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4003L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "庭", "にわ", "ခြံ", "名詞", "庭の使い方や意味をしっかり確認して復習しましょう。", "庭 ၏ အဓိပ္ပာယ်မှာ 'ခြံ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4004L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "掃除する", "そうじする", "သန့်ရှင်းရေးလုပ်သည်။", "動詞", "掃除するの使い方や意味をしっかり確認して復習しましょう。", "掃除する ၏ အဓိပ္ပာယ်မှာ 'သန့်ရှင်းရေးလုပ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4005L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "難しい", "むずかしい", "ခက်ခဲသော", "形容動詞", "難しいの使い方や意味をしっかり確認して復習しましょう。", "難しい ၏ အဓိပ္ပာယ်မှာ 'ခက်ခဲသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4006L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "弾く", "ひく", "တီးခတ်သည်", "動詞", "弾くの使い方や意味をしっかり確認して復習しましょう。", "弾く ၏ အဓိပ္ပာယ်မှာ 'တီးခတ်သည်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4007L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "昼寝", "ひるね", "နေ့လည်အိပ်သည်။", "名詞", "昼寝の使い方や意味をしっかり確認して復習しましょう。", "昼寝 ၏ အဓိပ္ပာယ်မှာ 'နေ့လည်အိပ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4008L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "留守", "るす", "အိမ်စောင့်မရှိခြင်း", "名詞", "留守の使い方や意味をしっかり確認して復習しましょう。", "留守 ၏ အဓိပ္ပာယ်မှာ 'အိမ်စောင့်မရှိခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4009L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "草", "くさ", "မြက်ပင်", "名詞", "草の使い方や意味をしっかり確認して復習しましょう。", "草 ၏ အဓိပ္ပာယ်မှာ 'မြက်ပင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4010L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "生える", "はえる", "ပေါက်ဖွားသည်။", "動詞", "生えるの使い方や意味をしっかり確認して復習しましょう。", "生える ၏ အဓိပ္ပာယ်မှာ 'ပေါက်ဖွားသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4011L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "相談する", "そうだんする", "ဆွေးနွေးသည်။", "動詞", "相談するの使い方や意味をしっかり確認して復習しましょう。", "相談する ၏ အဓိပ္ပာယ်မှာ 'ဆွေးနွေးသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4012L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "運転免許", "うんてんめんきょ", "ယာဉ်မောင်းလိုင်စင်", "名詞", "運転免許の使い方や意味をしっかり確認して復習しましょう。", "運転免許 ၏ အဓိပ္ပာယ်မှာ 'ယာဉ်မောင်းလိုင်စင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4013L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "激しい", "はげしい", "ပြင်းထန်သော", "形容動詞", "激しいの使い方や意味をしっかり確認して復習しましょう。", "激しい ၏ အဓိပ္ပာယ်မှာ 'ပြင်းထန်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4014L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "瞬間", "しゅんかん", "အခိုက်အတန့်", "名詞", "瞬間の使い方や意味をしっかり確認して復習しましょう。", "瞬間 ၏ အဓိပ္ပာယ်မှာ 'အခိုက်အတန့်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4015L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "間もなく", "まもなく", "မကြာမီ", "動詞", "間もくの使い方や意味をしっかり確認して復習しましょう。", "間もなく ၏ အဓိပ္ပာယ်မှာ 'မကြာမီ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4016L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "飛び立つ", "とびたつ", "လွှတ်တင်သည်။", "動詞", "飛び立つの使い方や意味をしっかり確認して復習しましょう。", "飛び立つ ၏ အဓိပ္ပာယ်မှာ 'လွှတ်တင်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4017L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "配達", "はいたつ", "အမြန်အချောပို့ခြင်း", "名詞", "配達の使い方や意味をしっかり確認して復習しましょう。", "配達 ၏ အဓိပ္ပာယ်မှာ 'အမြန်အချောပို့ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4018L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "扇風機", "せんぷうき", "လျှပ်စစ်ပန်ကာ", "名詞", "扇風機の使い方や意味をしっかり確認して復習しましょう。", "扇風機 ၏ အဓိပ္ပာယ်မှာ 'လျှပ်စစ်ပန်ကာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4019L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "交番", "こうばん", "ရဲကင်း", "名詞", "交番の使い方や意味をしっかり確認して復習しましょう。", "交番 ၏ အဓိပ္ပာယ်မှာ 'ရဲကင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4020L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "迷う", "まよう", "တွေဝေသည်။လမ်းပျောက်သည်။", "名詞", "迷うの使い方や意味をしっかり確認して復習しましょう。", "迷う ၏ အဓိပ္ပာယ်မှာ 'တွေဝေသည်။လမ်းပျောက်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4021L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "結果", "けっか", "ရလာဒ်", "名詞", "結果の使い方や意味をしっかり確認して復習しましょう。", "結果 ၏ အဓိပ္ပာယ်မှာ 'ရလာဒ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4022L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "期待", "きたい", "မျှော်လင့်ချက်", "名詞", "期待の使い方や意味をしっかり確認して復習しましょう。", "期待 ၏ အဓိပ္ပာယ်မှာ 'မျှော်လင့်ချက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4023L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "感じ方", "かんじかた", "ခံစားပုံခံစားနည်း", "名詞", "感じ方の使い方や意味をしっかり確認して復習しましょう。", "感じ方 ၏ အဓိပ္ပာယ်မှာ 'ခံစားပုံခံစားနည်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4024L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "帰宅時間", "きたくじかん", "အိမ်ပြန်ချိန်", "名詞", "帰宅時間の使い方や意味をしっかり確認して復習しましょう。", "帰宅時間 ၏ အဓိပ္ပာယ်မှာ 'အိမ်ပြန်ချိန်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4025L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "夜中", "よなか", "ညသန်းခေါင်ယံ", "名詞", "夜中の使い方や意味をしっかり確認して復習しましょう。", "夜中 ၏ အဓိပ္ပာယ်မှာ 'ညသန်းခေါင်ယံ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4026L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "文化祭", "ぶんかさい", "ယဉ်ကျေးမှုပွဲတော်", "名詞", "文化祭の使い方や意味をしっかり確認して復習しましょう。", "文化祭 ၏ အဓိပ္ပာယ်မှာ 'ယဉ်ကျေးမှုပွဲတော်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")
        card(4027L, 120, "第120課 日常の動作・生活環境 (Daily Action & Living)", "Mastery: 行動・環境・住まい (Action & Living)", "地方", "ちほう", "နယ်ဒေသ", "名詞", "地方の使い方や意味をしっかり確認して復習しましょう。", "地方 ၏ အဓိပ္ပာယ်မှာ 'နယ်ဒေသ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson120")

        // Lesson 121 (27 items)
        card(4028L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "害", "がい", "ဆိုးကျိုး", "名詞", "害の使い方や意味をしっかり確認して復習しましょう。", "害 ၏ အဓိပ္ပာယ်မှာ 'ဆိုးကျိုး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4029L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "価値", "かち", "တန်ဖိုး", "名詞", "価値の使い方や意味をしっかり確認して復習しましょう。", "価値 ၏ အဓိပ္ပာယ်မှာ 'တန်ဖိုး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4030L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "玄関", "げんかん", "အိမ်အဝင်ပေါက်", "名詞", "玄関の使い方や意味をしっかり確認して復習しましょう。", "玄関 ၏ အဓိပ္ပာယ်မှာ 'အိမ်အဝင်ပေါက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4031L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "人生", "じんせい", "လူ့ဘဝ", "名詞", "人生の使い方や意味をしっかり確認して復習しましょう。", "人生 ၏ အဓိပ္ပာယ်မှာ 'လူ့ဘဝ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4032L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "畑", "はたけ", "လယ်ကွင်း", "名詞", "畑の使い方や意味をしっかり確認して復習しましょう。", "畑 ၏ အဓိပ္ပာယ်မှာ 'လယ်ကွင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4033L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "呼び方", "よびかた", "ခေါ်ပုံခေါ်နည်း", "名詞", "呼び方の使い方や意味をしっかり確認して復習しましょう。", "呼び方 ၏ အဓိပ္ပာယ်မှာ 'ခေါ်ပုံခေါ်နည်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4034L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "新鮮な", "しんせんな", "လတ်ဆတ်သော", "形容動詞", "新鮮の使い方や意味をしっかり確認して復習しましょう。", "新鮮な ၏ အဓိပ္ပာယ်မှာ 'လတ်ဆတ်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4035L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "偶然", "ぐうぜん", "ရုတ်တရက်", "副詞", "偶然の使い方や意味をしっかり確認して復習しましょう。", "偶然 ၏ အဓိပ္ပာယ်မှာ 'ရုတ်တရက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4036L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "本人", "ほんにん", "ကာယကံရှင်", "名詞", "本人の使い方や意味をしっかり確認して復習しましょう。", "本人 ၏ အဓိပ္ပာယ်မှာ 'ကာယကံရှင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4037L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "感謝", "かんしゃ", "ကျေးဇူးတရား", "名詞", "感謝の使い方や意味をしっかり確認して復習しましょう。", "感謝 ၏ အဓိပ္ပာယ်မှာ 'ကျေးဇူးတရား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4038L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "腕", "うで", "လက်မောင်း", "名詞", "腕の使い方や意味をしっかり確認して復習しましょう。", "腕 ၏ အဓိပ္ပာယ်မှာ 'လက်မောင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4039L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "折れる", "おれる", "ကျိုးသည်", "動詞", "折れるの使い方や意味をしっかり確認して復習しましょう。", "折れる ၏ အဓိပ္ပာယ်မှာ 'ကျိုးသည်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4040L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "天気予報", "てんきよほう", "မိုးလေဝသခန့်မှန်းချက်", "名詞", "天気予報の使い方や意味をしっかり確認して復習しましょう。", "天気予報 ၏ အဓိပ္ပာယ်မှာ 'မိုးလေဝသခန့်မှန်းချက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4041L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "背中", "せなか", "နောက်ကျော", "名詞", "背中の使い方や意味をしっかり確認して復習しましょう。", "背中 ၏ အဓိပ္ပာယ်မှာ 'နောက်ကျော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4042L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "揺れる", "ゆれる", "လှုပ်ယမ်းသည်။", "動詞", "揺れるの使い方や意味をしっかり確認して復習しましょう。", "揺れる ၏ အဓိပ္ပာယ်မှာ 'လှုပ်ယမ်းသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4043L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "不安", "ふあん", "စိတ်မအေးသော", "形容動詞", "不安の使い方や意味をしっかり確認して復習しましょう。", "不安 ၏ အဓိပ္ပာယ်မှာ 'စိတ်မအေးသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4044L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "夜中", "よなか", "ညသန်းခေါင်ယံ", "名詞", "夜中の使い方や意味をしっかり確認して復習しましょう。", "夜中 ၏ အဓိပ္ပာယ်မှာ 'ညသန်းခေါင်ယံ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4045L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "体系", "たいけい", "ကိုယ်ခန္ဓာအချိုးအစား", "名詞", "体系の使い方や意味をしっかり確認して復習しましょう。", "体系 ၏ အဓိပ္ပာယ်မှာ 'ကိုယ်ခန္ဓာအချိုးအစား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4046L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "湖", "みずうみ", "ရေကန်", "名詞", "湖の使い方や意味をしっかり確認して復習しましょう。", "湖 ၏ အဓိပ္ပာယ်မှာ 'ရေကန်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4047L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "紅葉", "こうよう", "မေပယ်ရွက်", "名詞", "紅葉の使い方や意味をしっかり確認して復習しましょう。", "紅葉 ၏ အဓိပ္ပာယ်မှာ 'မေပယ်ရွက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4048L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "残念な", "ざんねんな", "စိတ်မကောင်းသော", "形容動詞", "残念の使い方や意味をしっかり確認して復習しましょう。", "残念な ၏ အဓိပ္ပာယ်မှာ 'စိတ်မကောင်းသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4049L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "きつい", "きつい", "တင်းကြပ်သော", "形容動詞", "きついの使い方や意味をしっかり確認して復習しましょう。", "きつい ၏ အဓိပ္ပာယ်မှာ 'တင်းကြပ်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4050L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "旅行先", "りょこうさき", "ခရီးသွားနေရာ", "名詞", "旅行先の使い方や意味をしっかり確認して復習しましょう。", "旅行先 ၏ အဓိပ္ပာယ်မှာ 'ခရီးသွားနေရာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4051L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "美しい", "うつくしい", "လှပသော", "形容動詞", "美しいの使い方や意味をしっかり確認して復習しましょう。", "美しい ၏ အဓိပ္ပာယ်မှာ 'လှပသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4052L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "長男", "ちょうなん", "သားအကြီး", "名詞", "長男の使い方や意味をしっかり確認して復習しましょう。", "長男 ၏ အဓိပ္ပာယ်မှာ 'သားအကြီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4053L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "次男", "じなん", "ဒုတိယသား", "名詞", "次男の使い方や意味をしっかり確認して復習しましょう。", "次男 ၏ အဓိပ္ပာယ်မှာ 'ဒုတိယသား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")
        card(4054L, 121, "第121課 変化・感情・人間関係 (Change, Feelings & Relations)", "Mastery: 感情・人間関係・価値観 (Feelings & Relations)", "都会", "とかい", "မြို့ကြီး", "名詞", "都会の使い方や意味をしっかり確認して復習しましょう。", "都会 ၏ အဓိပ္ပာယ်မှာ 'မြို့ကြီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson121")

        // Lesson 122 (27 items)
        card(4055L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "気楽な", "きらくな", "စိတ်လက်ပေါ့ပါးသော", "形容動詞", "気楽の使い方や意味をしっかり確認して復習しましょう。", "気楽な ၏ အဓိပ္ပာယ်မှာ 'စိတ်လက်ပေါ့ပါးသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4056L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "教授", "きょうじゅ", "ပါမောက္ခ", "名詞", "教授の使い方や意味をしっかり確認して復習しましょう。", "教授 ၏ အဓိပ္ပာယ်မှာ 'ပါမောက္ခ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4057L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "責任", "せきにん", "တာဝန်ဝတ္တရား", "名詞", "責任の使い方や意味をしっかり確認して復習しましょう。", "責任 ၏ အဓိပ္ပာယ်မှာ 'တာဝန်ဝတ္တရား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4058L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "夜の中", "よのなか", "လူ့လောက", "名詞", "夜の中の使い方や意味をしっかり確認して復習しましょう。", "夜の中 ၏ အဓိပ္ပာယ်မှာ 'လူ့လောက' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4059L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "苦手な", "にがてな", "မကြိုက်သော", "形容動詞", "苦手の使い方や意味をしっかり確認して復習しましょう。", "苦手な ၏ အဓိပ္ပာယ်မှာ 'မကြိုက်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4060L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "競争相手", "きょうそうあいて", "ပြိုင်ဘက်", "名詞", "競争相手の使い方や意味をしっかり確認して復習しましょう。", "競争相手 ၏ အဓိပ္ပာယ်မှာ 'ပြိုင်ဘက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4061L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "命令する", "めいれいする", "အမိန့်ပေးသည်။", "動詞", "命令するの使い方や意味をしっかり確認して復習しましょう。", "命令する ၏ အဓိပ္ပာယ်မှာ 'အမိန့်ပေးသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4062L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "会長", "かいちょう", "အဖွဲ့မှူး", "名詞", "会長の使い方や意味をしっかり確認して復習しましょう。", "会長 ၏ အဓိပ္ပာယ်မှာ 'အဖွဲ့မှူး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4063L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "実行力", "じっこうりょく", "လက်တွေ့အကောင်အထည်ဖော်မှု", "名詞", "実行力の使い方や意味をしっかり確認して復習しましょう。", "実行力 ၏ အဓိပ္ပာယ်မှာ 'လက်တွေ့အကောင်အထည်ဖော်မှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4064L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "深い", "ふかい", "နက်ရှိုင်းသော", "形容動詞", "深いの使い方や意味をしっかり確認して復習しましょう。", "深い ၏ အဓိပ္ပာယ်မှာ 'နက်ရှိုင်းသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4065L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "職場", "しょくば", "လုပ်ငန်းခွင်", "名詞", "職場の使い方や意味をしっかり確認して復習しましょう。", "職場 ၏ အဓိပ္ပာယ်မှာ 'လုပ်ငန်းခွင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4066L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "旧製品", "きゅうせいひん", "ထုတ်ကုန်ဟောင်း", "名詞", "旧製品の使い方や意味をしっかり確認して復習しましょう。", "旧製品 ၏ အဓိပ္ပာယ်မှာ 'ထုတ်ကုန်ဟောင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4067L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "新製品", "しんせいひん", "ထုတ်ကုန်သစ်", "名詞", "新製品の使い方や意味をしっかり確認して復習しましょう。", "新製品 ၏ အဓိပ္ပာယ်မှာ 'ထုတ်ကုန်သစ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4068L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "観光客", "かんこうきゃく", "ခရီးသွားဧည့်သည်", "名詞", "観光客の使い方や意味をしっかり確認して復習しましょう。", "観光客 ၏ အဓိပ္ပာယ်မှာ 'ခရီးသွားဧည့်သည်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4069L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "発売する", "はつばいする", "စတင်ရောင်းချသည်။", "動詞", "発売するの使い方や意味をしっかり確認して復習しましょう。", "発売する ၏ အဓိပ္ပာယ်မှာ 'စတင်ရောင်းချသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4070L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "一年中", "いちねんちゅう", "တစ်နှစ်ပတ်လုံး", "名詞", "一年中の使い方や意味をしっかり確認して復習しましょう。", "一年中 ၏ အဓိပ္ပာယ်မှာ 'တစ်နှစ်ပတ်လုံး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4071L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "労働", "ろうどう", "လုပ်အား", "名詞", "労働の使い方や意味をしっかり確認して復習しましょう。", "労働 ၏ အဓိပ္ပာယ်မှာ 'လုပ်အား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4072L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "工夫する", "くふうする", "တီထွင်ကြံဆသည်။", "動詞", "工夫するの使い方や意味をしっかり確認して復習しましょう。", "工夫する ၏ အဓိပ္ပာယ်မှာ 'တီထွင်ကြံဆသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4073L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "能力", "のうりょく", "စွမ်းရည်", "名詞", "能力の使い方や意味をしっかり確認して復習しましょう。", "能力 ၏ အဓိပ္ပာယ်မှာ 'စွမ်းရည်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4074L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "夢中", "むちゅう", "အာရုံနစ်မြောခြင်း", "名詞", "夢中の使い方や意味をしっかり確認して復習しましょう。", "夢中 ၏ အဓိပ္ပာယ်မှာ 'အာရုံနစ်မြောခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4075L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "気温", "きおん", "အပူချိန်", "名詞", "気温の使い方や意味をしっかり確認して復習しましょう。", "気温 ၏ အဓိပ္ပာယ်မှာ 'အပူချိန်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4076L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "特徴", "とくちょう", "ထူးခြားသွင်ပြင်", "名詞", "特徴の使い方や意味をしっかり確認して復習しましょう。", "特徴 ၏ အဓိပ္ပာယ်မှာ 'ထူးခြားသွင်ပြင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4077L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "遠慮する", "えんりょする", "အားနာသည်။", "動詞", "遠慮するの使い方や意味をしっかり確認して復習しましょう。", "遠慮する ၏ အဓိပ္ပာယ်မှာ 'အားနာသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4078L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "政治", "せいじ", "နိုင်ငံရေး", "名詞", "政治の使い方や意味をしっかり確認して復習しましょう。", "政治 ၏ အဓိပ္ပာယ်မှာ 'နိုင်ငံရေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4079L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "組み立てる", "くみたてる", "တပ်ဆင်သည်။", "動詞", "組み立てるの使い方や意味をしっかり確認して復習しましょう。", "組み立てる ၏ အဓိပ္ပာယ်မှာ 'တပ်ဆင်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4080L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "報告書", "ほうこくしょ", "အစီရင်ခံစာ", "名詞", "報告書の使い方や意味をしっかり確認して復習しましょう。", "報告書 ၏ အဓိပ္ပာယ်မှာ 'အစီရင်ခံစာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")
        card(4081L, 122, "第122課 家族・社会・役割 (Family, Society & Roles)", "Mastery: 家族・役割・社会環境 (Family & Roles)", "見直す", "みなおす", "ပြန်စစ်သည်။", "動詞", "見直すの使い方や意味をしっかり確認して復習しましょう。", "見直す ၏ အဓိပ္ပာယ်မှာ 'ပြန်စစ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson122")

        return list
    }
}
