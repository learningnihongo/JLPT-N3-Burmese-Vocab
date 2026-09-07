package com.example.data.seed

import com.example.data.model.VocabCard

object VocabSeedDataEssential2 {
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

        // Lesson 102 (28 items)
        card(3057L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "復習", "ふくしゅう", "ပြန်လှန်လေ့ကျင့်ခြင်း", "名詞", "復習の使い方や意味をしっかり確認して復習しましょう。", "復習 ၏ အဓိပ္ပာယ်မှာ 'ပြန်လှန်လေ့ကျင့်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3058L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "心配", "しんぱい", "စိတ်ပူပန်ခြင်း", "名詞", "心配の使い方や意味をしっかり確認して復習しましょう。", "心配 ၏ အဓိပ္ပာယ်မှာ 'စိတ်ပူပန်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3059L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "必死", "ひっし(に)", "အသေအလဲ၊ ပြင်းပြင်းထန်ထန်", "名詞", "必死の使い方や意味をしっかり確認して復習しましょう。", "必死 ၏ အဓိပ္ပာယ်မှာ 'အသေအလဲ၊ ပြင်းပြင်းထန်ထန်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3060L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "必要", "ひつよう(な)", "လိုအပ်သော", "形容動詞", "必要の使い方や意味をしっかり確認して復習しましょう。", "必要 ၏ အဓိပ္ပာယ်မှာ 'လိုအပ်သော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3061L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "忘れ物", "わすれもの", "မေ့ကျန်ပစ္စည်း", "名詞", "忘れ物の使い方や意味をしっかり確認して復習しましょう。", "忘れ物 ၏ အဓိပ္ပာယ်မှာ 'မေ့ကျန်ပစ္စည်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3062L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "忘年会", "ぼうねんかい", "နှစ်ကုန်ဂုဏ်ပြုပွဲ", "名詞", "忘年会の使い方や意味をしっかり確認して復習しましょう。", "忘年会 ၏ အဓိပ္ပာယ်မှာ 'နှစ်ကုန်ဂုဏ်ပြုပွဲ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3063L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "息切れ", "いきぎれ", "အသက်ရှူမ၀ခြင်း", "名詞", "息切れの使い方や意味をしっかり確認して復習しましょう。", "息切れ ၏ အဓိပ္ပာယ်မှာ 'အသက်ရှူမ၀ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3064L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "息子", "むすこ", "သား", "名詞", "息子の使い方や意味をしっかり確認して復習しましょう。", "息子 ၏ အဓိပ္ပာယ်မှာ 'သား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3065L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "意欲", "いよく", "အလုပ်လုပ်ချင်တဲ့စိတ်၊ စိတ်စေတနာ", "名詞", "意欲の使い方や意味をしっかり確認して復習しましょう。", "意欲 ၏ အဓိပ္ပာယ်မှာ 'အလုပ်လုပ်ချင်တဲ့စိတ်၊ စိတ်စေတနာ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3066L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "感動", "かんどう", "စိတ်လှုပ်ရှားမှု့", "名詞", "感動の使い方や意味をしっかり確認して復習しましょう。", "感動 ၏ အဓိပ္ပာယ်မှာ 'စိတ်လှုပ်ရှားမှု့' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3067L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "感情", "かんじょう", "စိတ်အာရုံခံစားမှု့", "名詞", "感情の使い方や意味をしっかり確認して復習しましょう。", "感情 ၏ အဓိပ္ပာယ်မှာ 'စိတ်အာရုံခံစားမှု့' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3068L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "感想", "かんそう", "ထင်မြင်ချက်၊ ခံစားချက်", "名詞", "感想の使い方や意味をしっかり確認して復習しましょう。", "感想 ၏ အဓိပ္ပာယ်မှာ 'ထင်မြင်ချက်၊ ခံစားချက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3069L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "成長", "せいちょう", "ကြီးပြင်းခြင်း၊ ကြီးထွားခြင်း", "名詞", "成長の使い方や意味をしっかり確認して復習しましょう。", "成長 ၏ အဓိပ္ပာယ်မှာ 'ကြီးပြင်းခြင်း၊ ကြီးထွားခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3070L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "駐車", "ちゅうしゃ", "ကားရပ်နားခြင်း", "名詞", "駐車の使い方や意味をしっかり確認して復習しましょう。", "駐車 ၏ အဓိပ္ပာယ်မှာ 'ကားရပ်နားခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3071L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "駐車券", "ちゅうしゃけん", "ယာဉ်ရပ်နားခွင့်လက်မှတ်", "名詞", "駐車券の使い方や意味をしっかり確認して復習しましょう。", "駐車券 ၏ အဓိပ္ပာယ်မှာ 'ယာဉ်ရပ်နားခွင့်လက်မှတ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3072L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "駐車場", "ちゅうしゃじょう", "ကားရပ်ရန်နေရာ၊ ကားပါကင်", "名詞", "駐車場の使い方や意味をしっかり確認して復習しましょう。", "駐車場 ၏ အဓိပ္ပာယ်မှာ 'ကားရပ်ရန်နေရာ၊ ကားပါကင်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3073L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "以内", "いない", "~ အတွင်း", "名詞", "以内の使い方や意味をしっかり確認して復習しましょう。", "以内 ၏ အဓိပ္ပာယ်မှာ '~ အတွင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3074L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "以降", "いこう", "အဲဒီနောက်ပိုင်း", "名詞", "以降の使い方や意味をしっかり確認して復習しましょう。", "以降 ၏ အဓိပ္ပာယ်မှာ 'အဲဒီနောက်ပိုင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3075L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "人形", "にんぎょう", "အရုပ်၊ လူရုပ်", "名詞", "人形の使い方や意味をしっかり確認して復習しましょう。", "人形 ၏ အဓိပ္ပာယ်မှာ 'အရုပ်၊ လူရုပ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3076L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "人類", "じんるい", "လူသားမျိုးနွယ်", "名詞", "人類の使い方や意味をしっかり確認して復習しましょう。", "人類 ၏ အဓိပ္ပာယ်မှာ 'လူသားမျိုးနွယ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3077L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "入学式", "にゅうがくしき", "ကျောင်း၀င်ခွင့်ပွဲအခမ်းအနား", "名詞", "入学式の使い方や意味をしっかり確認して復習しましょう。", "入学式 ၏ အဓိပ္ပာယ်မှာ 'ကျောင်း၀င်ခွင့်ပွဲအခမ်းအနား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3078L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "入荷", "にゅうか", "ပစ္စည်း၀င်ခြင်း၊ လက်ခံရရှိခြင်း", "名詞", "入荷の使い方や意味をしっかり確認して復習しましょう。", "入荷 ၏ အဓိပ္ပာယ်မှာ 'ပစ္စည်း၀င်ခြင်း၊ လက်ခံရရှိခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3079L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "大型", "おおがた", "အကြီးစား၊ ဆိုဒ်ကြီး", "名詞", "大型の使い方や意味をしっかり確認して復習しましょう。", "大型 ၏ အဓိပ္ပာယ်မှာ 'အကြီးစား၊ ဆိုဒ်ကြီး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3080L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "大変", "たいへん(な)", "ပင်ပန်းသော", "形容動詞", "大変の使い方や意味をしっかり確認して復習しましょう。", "大変 ၏ အဓိပ္ပာယ်မှာ 'ပင်ပန်းသော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3081L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "夫妻", "ふさい", "ဇနီးမောင်နှံ", "名詞", "夫妻の使い方や意味をしっかり確認して復習しましょう。", "夫妻 ၏ အဓိပ္ပာယ်မှာ 'ဇနီးမောင်နှံ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3082L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "夫婦", "ふうふ", "လင်မယား", "名詞", "夫婦の使い方や意味をしっかり確認して復習しましょう。", "夫婦 ၏ အဓိပ္ပာယ်မှာ 'လင်မယား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3083L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "失敗", "しっぱい", "ဆုံးရှုံးခြင်း", "名詞", "失敗の使い方や意味をしっかり確認して復習しましょう。", "失敗 ၏ အဓိပ္ပာယ်မှာ 'ဆုံးရှုံးခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")
        card(3084L, 102, "第102課 往復・心構え・人間 (Mind, Preparation & Family)", "N3 漢字言葉: 復・心・息・感・人・夫 (Mind & Human Relations)", "失望", "しつぼう", "စိတ်ပျက်ခြင်း", "名詞", "失望の使い方や意味をしっかり確認して復習しましょう。", "失望 ၏ အဓိပ္ပာယ်မှာ 'စိတ်ပျက်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson102")

        // Lesson 103 (29 items)
        card(3085L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "失礼", "しつれい(な)", "ရိုင်းစိုင်းသော", "形容動詞", "失礼の使い方や意味をしっかり確認して復習しましょう。", "失礼 ၏ အဓိပ္ပာယ်မှာ 'ရိုင်းစိုင်းသော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3086L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "失礼する", "しつれいする", "၀င်ခွင့်ပြုပါ၊ ခွင့်တောင်းသည်", "動詞", "失礼するの使い方や意味をしっかり確認して復習しましょう。", "失礼する ၏ အဓိပ္ပာယ်မှာ '၀င်ခွင့်ပြုပါ၊ ခွင့်တောင်းသည်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3087L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "美人", "びじん", "မိန်းမချော၊ မိန်းမလှ", "名詞", "美人の使い方や意味をしっかり確認して復習しましょう。", "美人 ၏ အဓိပ္ပာယ်မှာ 'မိန်းမချော၊ မိန်းမလှ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3088L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "美容院", "びょういん", "အလှပြင်ဆိုင်", "名詞", "美容院の使い方や意味をしっかり確認して復習しましょう。", "美容院 ၏ အဓိပ္ပာယ်မှာ 'အလှပြင်ဆိုင်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3089L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "美術", "びじゅつ", "အနုပညာ", "名詞", "美術の使い方や意味をしっかり確認して復習しましょう。", "美術 ၏ အဓိပ္ပာယ်မှာ 'အနုပညာ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3090L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "美術館", "びじゅつかん", "အနုပညာပြတိုက်", "名詞", "美術館の使い方や意味をしっかり確認して復習しましょう。", "美術館 ၏ အဓိပ္ပာယ်မှာ 'အနုပညာပြတိုက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3091L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "欠席", "けっせき", "ပျက်ကွက်ခြင်း", "名詞", "欠席の使い方や意味をしっかり確認して復習しましょう。", "欠席 ၏ အဓိပ္ပာယ်မှာ 'ပျက်ကွက်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3092L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "欠点", "けってん", "အားနည်းချက်", "名詞", "欠点の使い方や意味をしっかり確認して復習しましょう。", "欠点 ၏ အဓိပ္ပာယ်မှာ 'အားနည်းချက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3093L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "次回", "じかい", "နောက်တစ်ကြိမ်", "名詞", "次回の使い方や意味をしっかり確認して復習しましょう。", "次回 ၏ အဓိပ္ပာယ်မှာ 'နောက်တစ်ကြိမ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3094L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "内側", "うちがわ", "အတွင်းဘက်", "名詞", "内側の使い方や意味をしっかり確認して復習しましょう。", "内側 ၏ အဓိပ္ပာယ်မှာ 'အတွင်းဘက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3095L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "内容", "ないよう", "ပါ၀င်သည့်အကြောင်းအရာ", "名詞", "内容の使い方や意味をしっかり確認して復習しましょう。", "内容 ၏ အဓိပ္ပာယ်မှာ 'ပါ၀င်သည့်အကြောင်းအရာ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3096L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "内科", "ないか", "အထွေထွေရောဂါကုသခြင်း", "名詞", "内科の使い方や意味をしっかり確認して復習しましょう。", "内科 ၏ အဓိပ္ပာယ်မှာ 'အထွေထွေရောဂါကုသခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3097L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "不在", "ふざい", "မရှိခြင်း", "名詞", "不在の使い方や意味をしっかり確認して復習しましょう。", "不在 ၏ အဓိပ္ပာယ်မှာ 'မရှိခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3098L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "不満", "ふまん(な)", "မကျေနပ်သော", "形容動詞", "不満の使い方や意味をしっかり確認して復習しましょう。", "不満 ၏ အဓိပ္ပာယ်မှာ 'မကျေနပ်သော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3099L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "否定", "ひてい", "ငြင်းဆန်ခြင်း", "名詞", "否定の使い方や意味をしっかり確認して復習しましょう。", "否定 ၏ အဓိပ္ပာယ်မှာ 'ငြင်းဆန်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3100L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "否定的", "ひていてき(な)", "အနုတ်သဘောဆောင်သော", "形容動詞", "否定的の使い方や意味をしっかり確認して復習しましょう。", "否定的 ၏ အဓိပ္ပာယ်မှာ 'အနုတ်သဘောဆောင်သော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3101L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "会議", "かいぎ", "အစည်းအဝေး", "名詞", "会議の使い方や意味をしっかり確認して復習しましょう。", "会議 ၏ အဓိပ္ပာယ်မှာ 'အစည်းအဝေး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3102L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "会費", "かいひ", "အဖွဲ့၀င်ကြေး", "名詞", "会費の使い方や意味をしっかり確認して復習しましょう。", "会費 ၏ အဓိပ္ပာယ်မှာ 'အဖွဲ့၀င်ကြေး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3103L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "合格", "ごうかく", "စာမေးပွဲအောင်ခြင်း", "名詞", "合格の使い方や意味をしっかり確認して復習しましょう。", "合格 ၏ အဓိပ္ပာယ်မှာ 'စာမေးပွဲအောင်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3104L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "全席", "ぜんせき", "ထိုင်ခုံအားလုံး", "名詞", "全席の使い方や意味をしっかり確認して復習しましょう。", "全席 ၏ အဓိပ္ပာယ်မှာ 'ထိုင်ခုံအားလုံး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3105L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "全部", "ぜんぶ", "အားလုံး", "名詞", "全部の使い方や意味をしっかり確認して復習しましょう。", "全部 ၏ အဓိပ္ပာယ်မှာ 'အားလုံး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3106L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "全面", "ぜんめん", "တစ်ခွင်လုံး", "名詞", "全面の使い方や意味をしっかり確認して復習しましょう。", "全面 ၏ အဓိပ္ပာယ်မှာ 'တစ်ခွင်လုံး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3107L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "金庫", "きんこ", "မီးခံသေတ္တာ", "名詞", "金庫の使い方や意味をしっかり確認して復習しましょう。", "金庫 ၏ အဓိပ္ပာယ်မှာ 'မီးခံသေတ္တာ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3108L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "金額", "きんがく", "ငွေပမာဏ", "名詞", "金額の使い方や意味をしっかり確認して復習しましょう。", "金額 ၏ အဓိပ္ပာယ်မှာ 'ငွေပမာဏ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3109L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "鉄道", "てつどう", "သံလမ်း", "名詞", "鉄道の使い方や意味をしっかり確認して復習しましょう。", "鉄道 ၏ အဓိပ္ပာယ်မှာ 'သံလမ်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3110L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "録画", "ろくが", "ရုပ်သံဖမ်းခြင်း", "名詞", "録画の使い方や意味をしっかり確認して復習しましょう。", "録画 ၏ အဓိပ္ပာယ်မှာ 'ရုပ်သံဖမ်းခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3111L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "録音", "ろくおん", "အသံသွင်းခြင်း", "名詞", "録音の使い方や意味をしっかり確認して復習しましょう。", "録音 ၏ အဓိပ္ပာယ်မှာ 'အသံသွင်းခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3112L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "食塩", "しょくえん", "စားပွဲတင်ဆား", "名詞", "食塩の使い方や意味をしっかり確認して復習しましょう。", "食塩 ၏ အဓိပ္ပာယ်မှာ 'စားပွဲတင်ဆား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")
        card(3113L, 103, "第103課 状況・会議・記録 (Circumstances & Records)", "N3 漢字言葉: 失・美・内・否・会・録 (Circumstances & Meetings)", "食欲", "しょくよく", "အစာစားချင်စိတ်", "名詞", "食欲の使い方や意味をしっかり確認して復習しましょう。", "食欲 ၏ အဓိပ္ပာယ်မှာ 'အစာစားချင်စိတ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson103")

        return list
    }
}
