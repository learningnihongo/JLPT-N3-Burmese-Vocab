package com.example.data.seed

import com.example.data.model.VocabCard

object VocabSeedDataMastery2 {
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

        // Lesson 123 (28 items)
        card(4082L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "出張", "しゅっちょう", "အလုပ်ခရီး", "名詞", "出張の使い方や意味をしっかり確認して復習しましょう。", "出張 ၏ အဓိပ္ပာယ်မှာ 'အလုပ်ခရီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4083L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "欠席", "けっせき", "ပျက်ကွက်ခြင်း", "名詞", "欠席の使い方や意味をしっかり確認して復習しましょう。", "欠席 ၏ အဓိပ္ပာယ်မှာ 'ပျက်ကွက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4084L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "工場", "こうじょう", "စက်ရုံ", "名詞", "工場の使い方や意味をしっかり確認して復習しましょう。", "工場 ၏ အဓိပ္ပာယ်မှာ 'စက်ရုံ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4085L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "材料不足", "ざいりょうぶそく", "ပါဝင်ပစ္စည်းမလုံလောက်ခြင်း", "名詞", "材料不足の使い方や意味をしっかり確認して復習しましょう。", "材料不足 ၏ အဓိပ္ပာယ်မှာ 'ပါဝင်ပစ္စည်းမလုံလောက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4086L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "生產", "せいさん", "ထုတ်လုပ်မှု", "名詞", "生產の使い方や意味をしっかり確認して復習しましょう。", "生產 ၏ အဓိပ္ပာယ်မှာ 'ထုတ်လုပ်မှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4087L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "希望", "きぼう", "မျှော်လင့်ချက်", "名詞", "希望の使い方や意味をしっかり確認して復習しましょう。", "希望 ၏ အဓိပ္ပာယ်မှာ 'မျှော်လင့်ချက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4088L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "誤解", "ごかい", "နားလည်မှုလွဲခြင်း", "名詞", "誤解の使い方や意味をしっかり確認して復習しましょう。", "誤解 ၏ အဓိပ္ပာယ်မှာ 'နားလည်မှုလွဲခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4089L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "就職", "しゅうしょく", "အလုပ်လုပ်ခြင်း", "名詞", "就職の使い方や意味をしっかり確認して復習しましょう。", "就職 ၏ အဓိပ္ပာယ်မှာ 'အလုပ်လုပ်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4090L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "当然", "とうぜん", "သဘာဝ။ပုံမှန်ဖြစ်သော", "形容動詞", "当然の使い方や意味をしっかり確認して復習しましょう。", "当然 ၏ အဓိပ္ပာယ်မှာ 'သဘာဝ။ပုံမှန်ဖြစ်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4091L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "笑う", "わらう", "ရယ်သည်။", "名詞", "笑うの使い方や意味をしっかり確認して復習しましょう。", "笑う ၏ အဓိပ္ပာယ်မှာ 'ရယ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4092L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "真剣", "しんけん", "လေးနက်သော", "形容動詞", "真剣の使い方や意味をしっかり確認して復習しましょう。", "真剣 ၏ အဓိပ္ပာယ်မှာ 'လေးနက်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4093L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "技術", "ぎじゅつ", "နည်းပညာ", "名詞", "技術の使い方や意味をしっかり確認して復習しましょう。", "技術 ၏ အဓိပ္ပာယ်မှာ 'နည်းပညာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4094L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "手術", "しゅじゅつ", "ခွဲစိတ်ခြင်း", "名詞", "手術の使い方や意味をしっかり確認して復習しましょう。", "手術 ၏ အဓိပ္ပာယ်မှာ 'ခွဲစိတ်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4095L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "お見舞い", "おみまい", "လူနာသတင်းမေးသည်။", "形容詞", "お見舞いの使い方や意味をしっかり確認して復習しましょう。", "お見舞い ၏ အဓိပ္ပာယ်မှာ 'လူနာသတင်းမေးသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4096L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "迷惑", "めいわく", "အနှောင့်အယှက်", "名詞", "迷惑の使い方や意味をしっかり確認して復習しましょう。", "迷惑 ၏ အဓိပ္ပာယ်မှာ 'အနှောင့်အယှက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4097L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "無責任な", "むせきにんな", "တာဝန်မဲ့သော", "形容動詞", "無責任の使い方や意味をしっかり確認して復習しましょう。", "無責任な ၏ အဓိပ္ပာယ်မှာ 'တာဝန်မဲ့သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4098L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "態度", "たいど", "အပြုအမူ", "名詞", "態度の使い方や意味をしっかり確認して復習しましょう。", "態度 ၏ အဓိပ္ပာယ်မှာ 'အပြုအမူ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4099L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "建て始める", "たてはじめる", "စတင်ဆောက်လုပ်ခြင်း", "動詞", "建て始めるの使い方や意味をしっかり確認して復習しましょう。", "建て始める ၏ အဓိပ္ပာယ်မှာ 'စတင်ဆောက်လုပ်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4100L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "年内", "ねんない", "ဒီနှစ်အတွင်း", "名詞", "年内の使い方や意味をしっかり確認して復習しましょう。", "年内 ၏ အဓိပ္ပာယ်မှာ 'ဒီနှစ်အတွင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4101L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "漫画", "マンガ", "ရုပ်ပြကာတွန်း", "名詞", "漫画の使い方や意味をしっかり確認して復習しましょう。", "漫画 ၏ အဓိပ္ပာယ်မှာ 'ရုပ်ပြကာတွန်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4102L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "退屈", "たいくつ", "ပျင်းရိငြီးငွေ့ခြင်း", "名詞", "退屈の使い方や意味をしっかり確認して復習しましょう。", "退屈 ၏ အဓိပ္ပာယ်မှာ 'ပျင်းရိငြီးငွေ့ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4103L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "丈夫", "じょうぶ", "တောင့်တင်းသော။ခိုင်မာသော", "形容動詞", "丈夫の使い方や意味をしっかり確認して復習しましょう。", "丈夫 ၏ အဓိပ္ပာယ်မှာ 'တောင့်တင်းသော။ခိုင်မာသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4104L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "挑戦", "ちょうせん", "စိန်ခေါ်သည်။", "名詞", "挑戦の使い方や意味をしっかり確認して復習しましょう。", "挑戦 ၏ အဓိပ္ပာယ်မှာ 'စိန်ခေါ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4105L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "相手", "あいて", "တစ်ဖက်လူ", "名詞", "相手の使い方や意味をしっかり確認して復習しましょう。", "相手 ၏ အဓိပ္ပာယ်မှာ 'တစ်ဖက်လူ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4106L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "許す", "ゆるす", "ခွင့်လွှတ်သည်။", "動詞", "許すの使い方や意味をしっかり確認して復習しましょう。", "許す ၏ အဓိပ္ပာယ်မှာ 'ခွင့်လွှတ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4107L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "反対", "はんたい", "ဆန့်ကျင်ခြင်း", "名詞", "反対の使い方や意味をしっかり確認して復習しましょう。", "反対 ၏ အဓိပ္ပာယ်မှာ 'ဆန့်ကျင်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4108L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "必要な", "ひつような", "လိုအပ်သော", "形容動詞", "必要の使い方や意味をしっかり確認して復習しましょう。", "必要な ၏ အဓိပ္ပာယ်မှာ 'လိုအပ်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")
        card(4109L, 123, "第123課 職場・製造・仕事能力 (Workplace & Capability)", "Mastery: 職場・製品・能力・仕事 (Workplace & Capability)", "面倒", "めんどう", "စိတ်ရှုပ်ဖို့ကောင်းသော", "形容動詞", "面倒の使い方や意味をしっかり確認して復習しましょう。", "面倒 ၏ အဓိပ္ပာယ်မှာ 'စိတ်ရှုပ်ဖို့ကောင်းသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson123")

        // Lesson 124 (28 items)
        card(4110L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "健康診断", "けんこうしんだん", "ကျန်းမာရေးစစ်ဆေးမှု", "名詞", "健康診断の使い方や意味をしっかり確認して復習しましょう。", "健康診断 ၏ အဓိပ္ပာယ်မှာ 'ကျန်းမာရေးစစ်ဆေးမှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4111L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "寝坊", "ねぼう", "အိပ်ပုတ်ကြီးခြင်း", "名詞", "寝坊の使い方や意味をしっかり確認して復習しましょう。", "寝坊 ၏ အဓိပ္ပာယ်မှာ 'အိပ်ပုတ်ကြီးခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4112L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "残念な", "ざんねんな", "စိတ်မကောင်းသော", "形容動詞", "残念の使い方や意味をしっかり確認して復習しましょう。", "残念な ၏ အဓိပ္ပာယ်မှာ 'စိတ်မကောင်းသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4113L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "割引", "わりびき", "လျှော့ဈေး", "名詞", "割引の使い方や意味をしっかり確認して復習しましょう。", "割引 ၏ အဓိပ္ပာယ်မှာ 'လျှော့ဈေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4114L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "賛成する", "さんせいする", "ထောက်ခံသည်။", "動詞", "賛成するの使い方や意味をしっかり確認して復習しましょう。", "賛成する ၏ အဓိပ္ပာယ်မှာ 'ထောက်ခံသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4115L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "離れる", "はなれる", "ကွဲကွာသည်။", "動詞", "離れるの使い方や意味をしっかり確認して復習しましょう。", "離れる ၏ အဓိပ္ပာယ်မှာ 'ကွဲကွာသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4116L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "太陽熱", "たいようねつ", "နေရောင်ခြည်အပူစွမ်းအင်", "名詞", "太陽熱の使い方や意味をしっかり確認して復習しましょう。", "太陽熱 ၏ အဓိပ္ပာယ်မှာ 'နေရောင်ခြည်အပူစွမ်းအင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4117L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "発電", "はつでん", "လျှပ်စစ်ထုတ်လွှင့်ခြင်း", "名詞", "発電の使い方や意味をしっかり確認して復習しましょう。", "発電 ၏ အဓိပ္ပာယ်မှာ 'လျှပ်စစ်ထုတ်လွှင့်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4118L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "可能", "かのう", "ဖြစ်နိုင်သော", "形容動詞", "可能の使い方や意味をしっかり確認して復習しましょう。", "可能 ၏ အဓိပ္ပာယ်မှာ 'ဖြစ်နိုင်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4119L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "機械", "きかい", "အခါအခွင့်။အခွင့်အရေး", "名詞", "機械の使い方や意味をしっかり確認して復習しましょう。", "機械 ၏ အဓိပ္ပာယ်မှာ 'အခါအခွင့်။အခွင့်အရေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4120L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "お知らせ", "おしらせ", "အသိပေးခြင်း", "名詞", "お知らせの使い方や意味をしっかり確認して復習しましょう。", "お知らせ ၏ အဓိပ္ပာယ်မှာ 'အသိပေးခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4121L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "工事", "こうじ", "ဆောက်လုပ်ရေး", "名詞", "工事の使い方や意味をしっかり確認して復習しましょう。", "工事 ၏ အဓိပ္ပာယ်မှာ 'ဆောက်လုပ်ရေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4122L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "戻る", "もどる", "ပြန်လာသည်။", "動詞", "戻るの使い方や意味をしっかり確認して復習しましょう。", "戻る ၏ အဓိပ္ပာယ်မှာ 'ပြန်လာသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4123L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "納豆", "なっとう", "အချဉ်ဖောက်ထားသောပဲပိစပ်", "形容動詞", "納豆の使い方や意味をしっかり確認して復習しましょう。", "納豆 ၏ အဓိပ္ပာယ်မှာ 'အချဉ်ဖောက်ထားသောပဲပိစပ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4124L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "流行", "りゅうこう", "ရေပန်းစားခြင်း", "名詞", "流行の使い方や意味をしっかり確認して復習しましょう。", "流行 ၏ အဓိပ္ပာယ်မှာ 'ရေပန်းစားခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4125L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "選手", "せんしゅ", "အားကစားသမား", "名詞", "選手の使い方や意味をしっかり確認して復習しましょう。", "選手 ၏ အဓိပ္ပာယ်မှာ 'အားကစားသမား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4126L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "優勝する", "ゆうしょうする", "ဗိုလ်စွဲသည်။အနိုင်ရသည်။", "動詞", "優勝するの使い方や意味をしっかり確認して復習しましょう。", "優勝する ၏ အဓိပ္ပာယ်မှာ 'ဗိုလ်စွဲသည်။အနိုင်ရသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4127L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "出張", "しゅっちょう", "အလုပ်ခရီး", "名詞", "出張の使い方や意味をしっかり確認して復習しましょう。", "出張 ၏ အဓိပ္ပာယ်မှာ 'အလုပ်ခရီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4128L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "お宅", "おたく", "အိမ်", "名詞", "お宅の使い方や意味をしっかり確認して復習しましょう。", "お宅 ၏ အဓိပ္ပာယ်မှာ 'အိမ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4129L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "教員室", "きょういんしつ", "ဆရာမများနားနေခန်း", "名詞", "教員室の使い方や意味をしっかり確認して復習しましょう。", "教員室 ၏ အဓိပ္ပာယ်မှာ 'ဆရာမများနားနေခန်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4130L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "辺り", "あたり", "အနီးတစ်ဝိုက်ပတ်ဝန်းကျင်", "名詞", "辺りの使い方や意味をしっかり確認して復習しましょう。", "辺り ၏ အဓိပ္ပာယ်မှာ 'အနီးတစ်ဝိုက်ပတ်ဝန်းကျင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4131L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "野原", "のはら", "ကွင်းပြင်", "名詞", "野原の使い方や意味をしっかり確認して復習しましょう。", "野原 ၏ အဓိပ္ပာယ်မှာ 'ကွင်းပြင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4132L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "祭り", "まつり", "ပွဲတော်", "名詞", "祭りの使い方や意味をしっかり確認して復習しましょう。", "祭り ၏ အဓိပ္ပာယ်မှာ 'ပွဲတော်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4133L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "豆腐", "とうふ", "တို့ဖူး", "名詞", "豆腐の使い方や意味をしっかり確認して復習しましょう。", "豆腐 ၏ အဓိပ္ပာယ်မှာ 'တို့ဖူး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4134L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "痛み止め", "いたみどめ", "နာကျင်မှုရပ်တန့်စေသော။အကိုက်အခဲပျောက်သော။", "形容動詞", "痛み止めの使い方や意味をしっかり確認して復習しましょう。", "痛み止め ၏ အဓိပ္ပာယ်မှာ 'နာကျင်မှုရပ်တန့်စေသော။အကိုက်အခဲပျောက်သော။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4135L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "天気予報", "てんきよほう", "မိုးလေဝသခန့်မှန်းချက်", "名詞", "天気予報の使い方や意味をしっかり確認して復習しましょう。", "天気予報 ၏ အဓိပ္ပာယ်မှာ 'မိုးလေဝသခန့်မှန်းချက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4136L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "全国的", "ぜんこくてき", "တစ်နိုင်ငံလုံးအတိုင်းအတာဖြင့်", "形容動詞", "全国的の使い方や意味をしっかり確認して復習しましょう。", "全国的 ၏ အဓိပ္ပာယ်မှာ 'တစ်နိုင်ငံလုံးအတိုင်းအတာဖြင့်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")
        card(4137L, 124, "第124課 計画・医療・態度 (Plans, Healthcare & Attitude)", "Mastery: 計画・健康・態度・責任 (Healthcare & Attitude)", "足の裏", "あしのうら", "ခြေဖဝါး", "名詞", "足の裏の使い方や意味をしっかり確認して復習しましょう。", "足の裏 ၏ အဓိပ္ပာယ်မှာ 'ခြေဖဝါး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson124")

        // Lesson 125 (28 items)
        card(4138L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "珍しい", "めずらしい", "ရှားပါးသော", "形容動詞", "珍しいの使い方や意味をしっかり確認して復習しましょう。", "珍しい ၏ အဓိပ္ပာယ်မှာ 'ရှားပါးသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4139L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "地域", "ちいき", "ဧရိယာ", "名詞", "地域の使い方や意味をしっかり確認して復習しましょう。", "地域 ၏ အဓိပ္ပာယ်မှာ 'ဧရိယာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4140L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "国家試験", "こっかしけん", "အစိုးရစာမေးပွဲ", "名詞", "国家試験の使い方や意味をしっかり確認して復習しましょう。", "国家試験 ၏ အဓိပ္ပာယ်မှာ 'အစိုးရစာမေးပွဲ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4141L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "易しい", "やさしい", "လွယ်ကူသော", "形容動詞", "易しいの使い方や意味をしっかり確認して復習しましょう。", "易しい ၏ အဓိပ္ပာယ်မှာ 'လွယ်ကူသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4142L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "質", "しつ", "အရည်အသွေး", "名詞", "質の使い方や意味をしっかり確認して復習しましょう。", "質 ၏ အဓိပ္ပာယ်မှာ 'အရည်အသွေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4143L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "保険", "ほけん", "အာမခံ", "名詞", "保険の使い方や意味をしっかり確認して復習しましょう。", "保険 ၏ အဓိပ္ပာယ်မှာ 'အာမခံ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4144L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "長い間", "ながいあいだ", "အချိန်အကြာကြီး", "副詞", "長い間の使い方や意味をしっかり確認して復習しましょう。", "長い間 ၏ အဓိပ္ပာယ်မှာ 'အချိန်အကြာကြီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4145L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "距離", "きょり", "အကွာအဝေး", "名詞", "距離の使い方や意味をしっかり確認して復習しましょう。", "距離 ၏ အဓိပ္ပာယ်မှာ 'အကွာအဝေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4146L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "成長", "せいちょう", "ကြီးပြင်းသည်။", "名詞", "成長の使い方や意味をしっかり確認して復習しましょう。", "成長 ၏ အဓိပ္ပာယ်မှာ 'ကြီးပြင်းသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4147L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "仲", "なか", "ဆက်ဆံရေး", "名詞", "仲の使い方や意味をしっかり確認して復習しましょう。", "仲 ၏ အဓိပ္ပာယ်မှာ 'ဆက်ဆံရေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4148L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "記事", "きじ", "ဆောင်းပါး", "名詞", "記事の使い方や意味をしっかり確認して復習しましょう。", "記事 ၏ အဓိပ္ပာယ်မှာ 'ဆောင်းပါး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4149L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "退院する", "たいいんする", "ဆေးရုံဆင်းသည်။", "動詞", "退院するの使い方や意味をしっかり確認して復習しましょう。", "退院する ၏ အဓိပ္ပာယ်မှာ 'ဆေးရုံဆင်းသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4150L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "満足する", "まんぞくする", "စိတ်ကျေနပ်မှုရှိသည်။", "動詞", "満足するの使い方や意味をしっかり確認して復習しましょう。", "満足する ၏ အဓိပ္ပာယ်မှာ 'စိတ်ကျေနပ်မှုရှိသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4151L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "実力", "じつりょく", "စွမ်းရည်", "名詞", "実力の使い方や意味をしっかり確認して復習しましょう。", "実力 ၏ အဓိပ္ပာယ်မှာ 'စွမ်းရည်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4152L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "伝統的な", "でんとうてきな", "ထုံးတမ်းစဉ်လာဖြစ်သော", "形容動詞", "伝統的の使い方や意味をしっかり確認して復習しましょう。", "伝統的な ၏ အဓိပ္ပာယ်မှာ 'ထုံးတမ်းစဉ်လာဖြစ်သော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4153L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "女優", "じょゆう", "သရုပ်ဆောင်မင်းသမီး", "名詞", "女優の使い方や意味をしっかり確認して復習しましょう。", "女優 ၏ အဓိပ္ပာယ်မှာ 'သရုပ်ဆောင်မင်းသမီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4154L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "用語", "ようご", "ဘာသာရပ်ဆိုင်ရာကျွမ်းကျင်စကားလုံးများ", "名詞", "用語の使い方や意味をしっかり確認して復習しましょう。", "用語 ၏ အဓိပ္ပာယ်မှာ 'ဘာသာရပ်ဆိုင်ရာကျွမ်းကျင်စကားလုံးများ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4155L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "匹", "びき", "-ကောင်(သေးငယ်သောတိရစ္ဆာန်လေးများကိုခေါ်ဝေါ်သည်)", "形容動詞", "匹の使い方や意味をしっかり確認して復習しましょう。", "匹 ၏ အဓိပ္ပာယ်မှာ '-ကောင်(သေးငယ်သောတိရစ္ဆာန်လေးများကိုခေါ်ဝေါ်သည်)' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4156L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "実験", "じっけん", "လက်တွေ့စမ်းသပ်ခြင်း", "名詞", "実験の使い方や意味をしっかり確認して復習しましょう。", "実験 ၏ အဓိပ္ပာယ်မှာ 'လက်တွေ့စမ်းသပ်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4157L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "成功", "せいこう", "အောင်မြင်ခြင်း", "名詞", "成功の使い方や意味をしっかり確認して復習しましょう。", "成功 ၏ အဓိပ္ပာယ်မှာ 'အောင်မြင်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4158L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "梅雨", "つゆ", "မိုးရာသီ", "名詞", "梅雨の使い方や意味をしっかり確認して復習しましょう。", "梅雨 ၏ အဓိပ္ပာယ်မှာ 'မိုးရာသီ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4159L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "上着", "うわぎ", "အပေါ်ဝတ်အင်္ကျီ", "名詞", "上着の使い方や意味をしっかり確認して復習しましょう。", "上着 ၏ အဓိပ္ပာယ်မှာ 'အပေါ်ဝတ်အင်္ကျီ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4160L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "悩み", "なやみ", "ပူပင်သောက", "形容動詞", "悩みの使い方や意味をしっかり確認して復習しましょう。", "悩み ၏ အဓိပ္ပာယ်မှာ 'ပူပင်သောက' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4161L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "自然", "しぜん", "သဘာဝ", "名詞", "自然の使い方や意味をしっかり確認して復習しましょう。", "自然 ၏ အဓိပ္ပာယ်မှာ 'သဘာဝ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4162L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "環境", "かんきょう", "ပတ်ဝန်းကျင်", "名詞", "環境の使い方や意味をしっかり確認して復習しましょう。", "環境 ၏ အဓိပ္ပာယ်မှာ 'ပတ်ဝန်းကျင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4163L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "入館", "にゅうかん", "လူဝင်ကြီးကြပ်ရေး", "名詞", "入館の使い方や意味をしっかり確認して復習しましょう。", "入館 ၏ အဓိပ္ပာယ်မှာ 'လူဝင်ကြီးကြပ်ရေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4164L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "運動場", "うんどうじょう", "အားကစားခန်းမ", "名詞", "運動場の使い方や意味をしっかり確認して復習しましょう。", "運動場 ၏ အဓိပ္ပာယ်မှာ 'အားကစားခန်းမ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")
        card(4165L, 125, "第125課 挑戦・意見・生活習慣 (Challenge & Daily Habits)", "Mastery: 挑戦・対話・生活習慣 (Challenge & Habits)", "文化祭", "ぶんかさい", "ယဉ်ကျေးမှုပွဲတော်", "名詞", "文化祭の使い方や意味をしっかり確認して復習しましょう。", "文化祭 ၏ အဓိပ္ပာယ်မှာ 'ယဉ်ကျေးမှုပွဲတော်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson125")

        return list
    }
}
