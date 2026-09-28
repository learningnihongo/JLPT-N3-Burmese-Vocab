package com.example.data.seed

import com.example.data.model.VocabCard

object VocabSeedDataMastery3 {
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

        // Lesson 126 (28 items)
        card(4166L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "お別れ", "おわかれ", "ကွဲကွာခြင်း", "名詞", "お別れの使い方や意味をしっかり確認して復習しましょう。", "お別れ ၏ အဓိပ္ပာယ်မှာ 'ကွဲကွာခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4167L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "監督", "かんとく", "စီစဉ်ညွှန်ကြားသူ", "名詞", "監督の使い方や意味をしっかり確認して復習しましょう。", "監督 ၏ အဓိပ္ပာယ်မှာ 'စီစဉ်ညွှန်ကြားသူ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4168L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "命令", "めいれい", "အမိန့်ပေးခြင်း", "名詞", "命令の使い方や意味をしっかり確認して復習しましょう。", "命令 ၏ အဓိပ္ပာယ်မှာ 'အမိန့်ပေးခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4169L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "立て札", "たてふだ", "ထောင်ထားသောဆိုင်းဘုတ်", "形容動詞", "立て札の使い方や意味をしっかり確認して復習しましょう。", "立て札 ၏ အဓိပ္ပာယ်မှာ 'ထောင်ထားသောဆိုင်းဘုတ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4170L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "話し合う", "はなしあう", "ပြောဆိုဆွေးနွေးသည်။", "名詞", "話し合うの使い方や意味をしっかり確認して復習しましょう。", "話し合う ၏ အဓိပ္ပာယ်မှာ 'ပြောဆိုဆွေးနွေးသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4171L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "せっかく", "せっかく", "တကူးတက", "動詞", "せっかくの使い方や意味をしっかり確認して復習しましょう。", "せっかく ၏ အဓိပ္ပာယ်မှာ 'တကူးတက' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4172L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "健康診断", "けんこうしんだん", "ကျန်းမာရေးစစ်ဆေးချက်", "名詞", "健康診断の使い方や意味をしっかり確認して復習しましょう。", "健康診断 ၏ အဓိပ္ပာယ်မှာ 'ကျန်းမာရေးစစ်ဆေးချက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4173L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "迷惑", "めいわく", "အနှောင့်အယှက်", "名詞", "迷惑の使い方や意味をしっかり確認して復習しましょう。", "迷惑 ၏ အဓိပ္ပာယ်မှာ 'အနှောင့်အယှက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4174L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "一言", "ひとこと", "စကားလေးတစ်ခွန်း", "名詞", "一言の使い方や意味をしっかり確認して復習しましょう。", "一言 ၏ အဓိပ္ပာယ်မှာ 'စကားလေးတစ်ခွန်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4175L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "謝る", "あやまる", "တောင်းပန်သည်။", "動詞", "謝るの使い方や意味をしっかり確認して復習しましょう。", "謝る ၏ အဓိပ္ပာယ်မှာ 'တောင်းပန်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4176L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "張り紙", "はりがみ", "နံရံကပ်ပိုစတာ", "名詞", "張り紙の使い方や意味をしっかり確認して復習しましょう。", "張り紙 ၏ အဓိပ္ပာယ်မှာ 'နံရံကပ်ပိုစတာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4177L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "芝生", "しばふ", "မြက်ခင်းပြင်", "名詞", "芝生の使い方や意味をしっかり確認して復習しましょう。", "芝生 ၏ အဓိပ္ပာယ်မှာ 'မြက်ခင်းပြင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4178L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "新入社員", "しんにゅうしゃいん", "ဝန်ထမ်းသစ်", "名詞", "新入社員の使い方や意味をしっかり確認して復習しましょう。", "新入社員 ၏ အဓိပ္ပာယ်မှာ 'ဝန်ထမ်းသစ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4179L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "法律", "ほうりつ", "ဥပဒေ", "名詞", "法律の使い方や意味をしっかり確認して復習しましょう。", "法律 ၏ အဓိပ္ပာယ်မှာ 'ဥပဒေ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4180L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "似合う", "にあう", "လိုက်ဖက်သည်။", "名詞", "似合うの使い方や意味をしっかり確認して復習しましょう。", "似合う ၏ အဓိပ္ပာယ်မှာ 'လိုက်ဖက်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4181L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "発表会", "はっぴょうかい", "စာတမ်းဖတ်ပွဲ", "名詞", "発表会の使い方や意味をしっかり確認して復習しましょう。", "発表会 ၏ အဓိပ္ပာယ်မှာ 'စာတမ်းဖတ်ပွဲ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4182L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "残念", "ざんねん", "စိတ်မကောင်းခြင်း", "名詞", "残念の使い方や意味をしっかり確認して復習しましょう。", "残念 ၏ အဓိပ္ပာယ်မှာ 'စိတ်မကောင်းခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4183L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "怒る", "おこる", "ဒေါသထွက်ခြင်း", "動詞", "怒るの使い方や意味をしっかり確認して復習しましょう。", "怒る ၏ အဓိပ္ပာယ်မှာ 'ဒေါသထွက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4184L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "入国", "にゅうこく", "နိုင်ငံသို့ဝင်ရောက်ခြင်း", "名詞", "入国の使い方や意味をしっかり確認して復習しましょう。", "入国 ၏ အဓိပ္ပာယ်မှာ 'နိုင်ငံသို့ဝင်ရောက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4185L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "出国", "しゅっこく", "နိုင်ငံမှထွက်ခွာခြင်း", "名詞", "出国の使い方や意味をしっかり確認して復習しましょう。", "出国 ၏ အဓိပ္ပာယ်မှာ 'နိုင်ငံမှထွက်ခွာခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4186L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "怒る", "おこる", "ဒေါသထွက်သည်။", "動詞", "怒るの使い方や意味をしっかり確認して復習しましょう。", "怒る ၏ အဓိပ္ပာယ်မှာ 'ဒေါသထွက်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4187L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "黙る", "だまる", "နှုတ်ဆိတ်သည်။", "動詞", "黙るの使い方や意味をしっかり確認して復習しましょう。", "黙る ၏ အဓိပ္ပာယ်မှာ 'နှုတ်ဆိတ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4188L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "集合時間", "しゅうごうじかん", "စုဝေးချိန်", "名詞", "集合時間の使い方や意味をしっかり確認して復習しましょう。", "集合時間 ၏ အဓိပ္ပာယ်မှာ 'စုဝေးချိန်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4189L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "勧める", "すすめる", "အကြံပေးသည်။", "動詞", "勧めるの使い方や意味をしっかり確認して復習しましょう。", "勧める ၏ အဓိပ္ပာယ်မှာ 'အကြံပေးသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4190L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "事情", "じじょう", "အခြေအနေ", "名詞", "事情の使い方や意味をしっかり確認して復習しましょう。", "事情 ၏ အဓိပ္ပာယ်မှာ 'အခြေအနေ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4191L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "冗談", "じょうだん", "နောက်ပြောင်ခြင်း", "名詞", "冗談の使い方や意味をしっかり確認して復習しましょう。", "冗談 ၏ အဓိပ္ပာယ်မှာ 'နောက်ပြောင်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4192L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "汚い", "きたない", "ညစ်ပေသော", "形容動詞", "汚いの使い方や意味をしっかり確認して復習しましょう。", "汚い ၏ အဓိပ္ပာယ်မှာ 'ညစ်ပေသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")
        card(4193L, 126, "第126課 自然・地域・伝統文化 (Nature, Region & Tradition)", "Mastery: 自然・地域・伝統・行事 (Nature & Tradition)", "研究会", "けんきゅうかい", "သုတေသနပွဲ", "名詞", "研究会の使い方や意味をしっかり確認して復習しましょう。", "研究会 ၏ အဓိပ္ပာယ်မှာ 'သုတေသနပွဲ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson126")

        // Lesson 127 (28 items)
        card(4194L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "会長", "かいちょう", "အဖွဲ့မှူး", "名詞", "会長の使い方や意味をしっかり確認して復習しましょう。", "会長 ၏ အဓိပ္ပာယ်မှာ 'အဖွဲ့မှူး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4195L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "新エネルギー", "しんエネルギー", "စွမ်းအင်အသစ်", "名詞", "新エネルギーの使い方や意味をしっかり確認して復習しましょう。", "新エネルギー ၏ အဓိပ္ပာယ်မှာ 'စွမ်းအင်အသစ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4196L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "推薦書", "すいせんしょ", "ထောက်ခံစာ", "名詞", "推薦書の使い方や意味をしっかり確認して復習しましょう。", "推薦書 ၏ အဓိပ္ပာယ်မှာ 'ထောက်ခံစာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4197L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "和食", "わしょく", "ဂျပန်အစားအစာ", "名詞", "和食の使い方や意味をしっかり確認して復習しましょう。", "和食 ၏ အဓိပ္ပာယ်မှာ 'ဂျပန်အစားအစာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4198L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "完成品", "かんせいひん", "ကုန်ချော", "名詞", "完成品の使い方や意味をしっかり確認して復習しましょう。", "完成品 ၏ အဓိပ္ပာယ်မှာ 'ကုန်ချော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4199L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "工場見学", "こうじょうけんがく", "စက်ရုံလေ့လာကြည့်ရှုရေး", "名詞", "工場見学の使い方や意味をしっかり確認して復習しましょう。", "工場見学 ၏ အဓိပ္ပာယ်မှာ 'စက်ရုံလေ့လာကြည့်ရှုရေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4200L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "訪ねる", "たずねる", "အလည်သွားသည်။", "動詞", "訪ねるの使い方や意味をしっかり確認して復習しましょう。", "訪ねる ၏ အဓိပ္ပာယ်မှာ 'အလည်သွားသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4201L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "持ち出し禁止", "もちだしきんし", "ယူမသွားရ", "名詞", "持ち出し禁止の使い方や意味をしっかり確認して復習しましょう。", "持ち出し禁止 ၏ အဓိပ္ပာယ်မှာ 'ယူမသွားရ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4202L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "閉店時間", "へいてんじかん", "ဆိုင်ပိတ်ချိန်", "名詞", "閉店時間の使い方や意味をしっかり確認して復習しましょう。", "閉店時間 ၏ အဓိပ္ပာယ်မှာ 'ဆိုင်ပိတ်ချိန်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4203L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "長い間", "ながいあいだ", "အချိန်အကြာကြီး", "副詞", "長い間の使い方や意味をしっかり確認して復習しましょう。", "長い間 ၏ အဓိပ္ပာယ်မှာ 'အချိန်အကြာကြီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4204L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "見落とす", "みおとす", "သတိမမူဖြစ်ခြင်း", "動詞", "見落とすの使い方や意味をしっかり確認して復習しましょう。", "見落とす ၏ အဓိပ္ပာယ်မှာ 'သတိမမူဖြစ်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4205L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "旅好き", "たびすき", "ခရီးသွားရတာကြိုက်နှစ်သက်ခြင်း", "名詞", "旅好きの使い方や意味をしっかり確認して復習しましょう。", "旅好き ၏ အဓိပ္ပာယ်မှာ 'ခရီးသွားရတာကြိုက်နှစ်သက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4206L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "北極", "ほっきょく", "မြောက်ဝင်ရိုးစွန်း", "名詞", "北極の使い方や意味をしっかり確認して復習しましょう。", "北極 ၏ အဓိပ္ပာယ်မှာ 'မြောက်ဝင်ရိုးစွန်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4207L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "学者", "がくしゃ", "ထူးချွန်ကျောင်းသား", "名詞", "学者の使い方や意味をしっかり確認して復習しましょう。", "学者 ၏ အဓိပ္ပာယ်မှာ 'ထူးချွန်ကျောင်းသား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4208L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "迷子", "まいご", "လူပျောက်ခြင်း။လမ်းပျောက်ခြင်း။", "名詞", "迷子の使い方や意味をしっかり確認して復習しましょう。", "迷子 ၏ အဓိပ္ပာယ်မှာ 'လူပျောက်ခြင်း။လမ်းပျောက်ခြင်း။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4209L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "赤ん坊", "あかんぼう", "နီတာရဲကလေးလေး", "名詞", "赤ん坊の使い方や意味をしっかり確認して復習しましょう。", "赤ん坊 ၏ အဓိပ္ပာယ်မှာ 'နီတာရဲကလေးလေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4210L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "借金", "しゃっきん", "ပိုက်ဆံချေးခြင်း", "名詞", "借金の使い方や意味をしっかり確認して復習しましょう。", "借金 ၏ အဓိပ္ပာယ်မှာ 'ပိုက်ဆံချေးခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4211L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "年賀状", "ねんがじょう", "နှစ်သစ်ဆုတောင်းပို့စကတ်", "名詞", "年賀状の使い方や意味をしっかり確認して復習しましょう。", "年賀状 ၏ အဓိပ္ပာယ်မှာ 'နှစ်သစ်ဆုတောင်းပို့စကတ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4212L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "へび", "へび", "မြွေ", "名詞", "へびの使い方や意味をしっかり確認して復習しましょう。", "へび ၏ အဓိပ္ပာယ်မှာ 'မြွေ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4213L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "黙る", "だまる", "နှုတ်ဆိတ်သည်။", "動詞", "黙るの使い方や意味をしっかり確認して復習しましょう。", "黙る ၏ အဓိပ္ပာယ်မှာ 'နှုတ်ဆိတ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4214L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "苦手な", "にがてな", "ညံ့သော။အားနည်းသော။", "形容動詞", "苦手の使い方や意味をしっかり確認して復習しましょう。", "苦手な ၏ အဓိပ္ပာယ်မှာ 'ညံ့သော။အားနည်းသော။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4215L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "検査", "けんさ", "စစ်ဆေးမှု", "名詞", "検査の使い方や意味をしっかり確認して復習しましょう。", "検査 ၏ အဓိပ္ပာယ်မှာ 'စစ်ဆေးမှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4216L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "手続き", "てつづき", "လုပ်ထုံးလုပ်နည်း", "名詞", "手続きの使い方や意味をしっかり確認して復習しましょう。", "手続き ၏ အဓိပ္ပာယ်မှာ 'လုပ်ထုံးလုပ်နည်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4217L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "作品", "さくひん", "လက်ရာ", "名詞", "作品の使い方や意味をしっかり確認して復習しましょう。", "作品 ၏ အဓိပ္ပာယ်မှာ 'လက်ရာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4218L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "反対", "はんたい", "ဆန့်ကျင်ခြင်း", "名詞", "反対の使い方や意味をしっかり確認して復習しましょう。", "反対 ၏ အဓိပ္ပာယ်မှာ 'ဆန့်ကျင်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4219L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "近所", "きんじょ", "အနီးအနားပတ်ဝန်းကျင်", "名詞", "近所の使い方や意味をしっかり確認して復習しましょう。", "近所 ၏ အဓိပ္ပာယ်မှာ 'အနီးအနားပတ်ဝန်းကျင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4220L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "求める", "もとめる", "တောင်းဆိုသည်။", "動詞", "求めるの使い方や意味をしっかり確認して復習しましょう。", "求める ၏ အဓိပ္ပာယ်မှာ 'တောင်းဆိုသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")
        card(4221L, 127, "第127課 規則・人間性・対人関係 (Rules, Nature & Humanity)", "Mastery: 法律・対話・環境・感情 (Rules & Humanity)", "政府", "せいふ", "အစိုးရ", "名詞", "政府の使い方や意味をしっかり確認して復習しましょう。", "政府 ၏ အဓိပ္ပာယ်မှာ 'အစိုးရ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson127")

        // Lesson 128 (28 items)
        card(4222L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "批判", "ひはん", "ဝေဖန်မှု", "名詞", "批判の使い方や意味をしっかり確認して復習しましょう。", "批判 ၏ အဓိပ္ပာယ်မှာ 'ဝေဖန်မှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4223L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "伝統的な", "でんとうてきな", "ထုံးတမ်းစဉ်လာဖြစ်သော", "形容動詞", "伝統的の使い方や意味をしっかり確認して復習しましょう。", "伝統的な ၏ အဓိပ္ပာယ်မှာ 'ထုံးတမ်းစဉ်လာဖြစ်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4224L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "無名", "むめい", "နာမည်မကြီးသော", "形容動詞", "無名の使い方や意味をしっかり確認して復習しましょう。", "無名 ၏ အဓိပ္ပာယ်မှာ 'နာမည်မကြီးသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4225L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "タクシー代", "タクシーだい", "တက္ကဆီအဖိုးအခ", "名詞", "タクシー代の使い方や意味をしっかり確認して復習しましょう。", "タクシー代 ၏ အဓိပ္ပာယ်မှာ 'တက္ကဆီအဖိုးအခ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4226L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "利用者", "りようしゃ", "အသုံးပြုသူ", "名詞", "利用者の使い方や意味をしっかり確認して復習しましょう。", "利用者 ၏ အဓိပ္ပာယ်မှာ 'အသုံးပြုသူ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4227L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "値上げ", "ねあげ", "ဈေးတက်ခြင်း", "名詞", "値上げの使い方や意味をしっかり確認して復習しましょう。", "値上げ ၏ အဓိပ္ပာယ်မှာ 'ဈေးတက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4228L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "円高", "えんだか", "ယန်းဈေးမြင့်တက်ခြင်း", "名詞", "円高の使い方や意味をしっかり確認して復習しましょう。", "円高 ၏ အဓိပ္ပာယ်မှာ 'ယန်းဈေးမြင့်တက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4229L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "重大な", "じゅうだいな", "အရေးကြီးသော", "形容動詞", "重大の使い方や意味をしっかり確認して復習しましょう。", "重大な ၏ အဓိပ္ပာယ်မှာ 'အရေးကြီးသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4230L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "思い出", "おもいで", "အမှတ်တရ", "名詞", "思い出の使い方や意味をしっかり確認して復習しましょう。", "思い出 ၏ အဓိပ္ပာယ်မှာ 'အမှတ်တရ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4231L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "国費留学生", "こくひりゅうがくせい", "နိုင်ငံတော်ပညာသင်ဆု", "名詞", "国費留学生の使い方や意味をしっかり確認して復習しましょう。", "国費留学生 ၏ အဓိပ္ပာယ်မှာ 'နိုင်ငံတော်ပညာသင်ဆု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4232L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "代表", "だいひょう", "ကိုယ်စားလှယ်", "名詞", "代表の使い方や意味をしっかり確認して復習しましょう。", "代表 ၏ အဓိပ္ပာယ်မှာ 'ကိုယ်စားလှယ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4233L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "產地", "さんち", "ထုတ်လုပ်တဲ့နေရာ", "名詞", "產地の使い方や意味をしっかり確認して復習しましょう。", "產地 ၏ အဓိပ္ပာယ်မှာ 'ထုတ်လုပ်တဲ့နေရာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4234L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "改めて", "あらためて", "တဖန်ထပ်ပြီး", "名詞", "改めての使い方や意味をしっかり確認して復習しましょう。", "改めて ၏ အဓိပ္ပာယ်မှာ 'တဖန်ထပ်ပြီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4235L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "感謝", "かんしゃ", "ကျေးဇူးတရား", "名詞", "感謝の使い方や意味をしっかり確認して復習しましょう。", "感謝 ၏ အဓိပ္ပာယ်မှာ 'ကျေးဇူးတရား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4236L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "風力発電", "ふうりょくはつでん", "လေအားလျှပ်စစ်", "名詞", "風力発電の使い方や意味をしっかり確認して復習しましょう。", "風力発電 ၏ အဓိပ္ပာယ်မှာ 'လေအားလျှပ်စစ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4237L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "風車", "ふうしゃ", "လေဒလတ်", "名詞", "風車の使い方や意味をしっかり確認して復習しましょう。", "風車 ၏ အဓိပ္ပာယ်မှာ 'လေဒလတ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4238L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "作家", "さっか", "စာရေးဆရာ", "名詞", "作家の使い方や意味をしっかり確認して復習しましょう。", "作家 ၏ အဓိပ္ပာယ်မှာ 'စာရေးဆရာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4239L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "長男", "ちょうなん", "သားအကြီး", "名詞", "長男の使い方や意味をしっかり確認して復習しましょう。", "長男 ၏ အဓိပ္ပာယ်မှာ 'သားအကြီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4240L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "成長期", "せいちょうき", "ကြီးထွားချိန်", "名詞", "成長期の使い方や意味をしっかり確認して復習しましょう。", "成長期 ၏ အဓိပ္ပာယ်မှာ 'ကြီးထွားချိန်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4241L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "発達", "はったつ", "ဖွံ့ဖြိုးတိုးတက်မှု", "名詞", "発達の使い方や意味をしっかり確認して復習しましょう。", "発達 ၏ အဓိပ္ပာယ်မှာ 'ဖွံ့ဖြိုးတိုးတက်မှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4242L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "作曲家", "さっきょくか", "တေးရေးဆရာ", "名詞", "作曲家の使い方や意味をしっかり確認して復習しましょう。", "作曲家 ၏ အဓိပ္ပာယ်မှာ 'တေးရေးဆရာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4243L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "一人暮らし", "ひとりぐらし", "တစ်ယောက်ထဲနေထိုင်ခြင်း", "名詞", "一人暮らしの使い方や意味をしっかり確認して復習しましょう。", "一人暮らし ၏ အဓိပ္ပာယ်မှာ 'တစ်ယောက်ထဲနေထိုင်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4244L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "態度", "たいど", "အပြုအမူ", "名詞", "態度の使い方や意味をしっかり確認して復習しましょう。", "態度 ၏ အဓိပ္ပာယ်မှာ 'အပြုအမူ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4245L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "漫画家", "まんがか", "ကာတွန်းရေးဆရာ", "名詞", "漫画家の使い方や意味をしっかり確認して復習しましょう。", "漫画家 ၏ အဓိပ္ပာယ်မှာ 'ကာတွန်းရေးဆရာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4246L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "特徵", "とくちょう", "အားသာချက်", "名詞", "特徵の使い方や意味をしっかり確認して復習しましょう。", "特徵 ၏ အဓိပ္ပာယ်မှာ 'အားသာချက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4247L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "基本的", "きほんてき", "အခြေခံအားဖြင့်", "形容動詞", "基本的の使い方や意味をしっかり確認して復習しましょう。", "基本的 ၏ အဓိပ္ပာယ်မှာ 'အခြေခံအားဖြင့်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4248L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "話しかける", "はなしかける", "စကားလှမ်းပြောသည်။", "動詞", "話しかけるの使い方や意味をしっかり確認して復習しましょう。", "話しかける ၏ အဓိပ္ပာယ်မှာ 'စကားလှမ်းပြောသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")
        card(4249L, 128, "第128課 社会経済・専門・進路 (Society, Careers & Profession)", "Mastery: 経済・専門・進路・生活 (Society & Careers)", "以前", "いぜん", "အရင်က", "名詞", "以前の使い方や意味をしっかり確認して復習しましょう。", "以前 ၏ အဓိပ္ပာယ်မှာ 'အရင်က' ဖြစ်သည်။", "MasteryKanji, N3, Lesson128")

        return list
    }
}
