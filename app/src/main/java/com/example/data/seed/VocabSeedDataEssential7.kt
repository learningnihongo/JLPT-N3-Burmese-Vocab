package com.example.data.seed

import com.example.data.model.VocabCard

object VocabSeedDataEssential7 {
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

        // Lesson 113 (29 items)
        card(3375L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "禁煙", "きんえん", "ဆေးလိပ်မသောက်ရ", "名詞", "禁煙の使い方や意味をしっかり確認して復習しましょう。", "禁煙 ၏ အဓိပ္ပာယ်မှာ 'ဆေးလိပ်မသောက်ရ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3376L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "書留", "かきとめ", "မှတ်ပုံတင်စာ", "名詞", "書留の使い方や意味をしっかり確認して復習しましょう。", "書留 ၏ အဓိပ္ပာယ်မှာ 'မှတ်ပုံတင်စာ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3377L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "書類", "しょるい", "စာရွက်စာတမ်း", "名詞", "書類の使い方や意味をしっかり確認して復習しましょう。", "書類 ၏ အဓိပ္ပာယ်မှာ 'စာရွက်စာတမ်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3378L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "最初", "さいしょ", "ပထမဦးဆုံး၊ အရင်ဆုံး", "名詞", "最初の使い方や意味をしっかり確認して復習しましょう。", "最初 ၏ အဓိပ္ပာယ်မှာ 'ပထမဦးဆုံး၊ အရင်ဆုံး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3379L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "最後", "さいご", "နောက်ဆုံး", "名詞", "最後の使い方や意味をしっかり確認して復習しましょう。", "最後 ၏ အဓိပ္ပာယ်မှာ 'နောက်ဆုံး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3380L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "最近", "さいきん", "ယခုတလော", "名詞", "最近の使い方や意味をしっかり確認して復習しましょう。", "最近 ၏ အဓိပ္ပာယ်မှာ 'ယခုတလော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3381L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "期間", "きかん", "အချိန်ကာလ", "名詞", "期間の使い方や意味をしっかり確認して復習しましょう。", "期間 ၏ အဓိပ္ပာယ်မှာ 'အချိန်ကာလ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3382L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "期限", "きげん", "ရက်ကန့်သတ်မှု့၊ နောက်ဆုံးပိတ်ရက်", "名詞", "期限の使い方や意味をしっかり確認して復習しましょう。", "期限 ၏ အဓိပ္ပာယ်မှာ 'ရက်ကန့်သတ်မှု့၊ နောက်ဆုံးပိတ်ရက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3383L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "月末", "げつまつ", "လကုန်", "名詞", "月末の使い方や意味をしっかり確認して復習しましょう。", "月末 ၏ အဓိပ္ပာယ်မှာ 'လကုန်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3384L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "未定", "みてい", "မသတ်မှတ်၊ မဆုံးဖြတ်ရသေးခြင်း", "名詞", "未定の使い方や意味をしっかり確認して復習しましょう。", "未定 ၏ အဓိပ္ပာယ်မှာ 'မသတ်မှတ်၊ မဆုံးဖြတ်ရသေးခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3385L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "未来", "みらい", "အနာဂါတ်", "名詞", "未来の使い方や意味をしっかり確認して復習しましょう。", "未来 ၏ အဓိပ္ပာယ်မှာ 'အနာဂါတ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3386L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "本店", "ほんてん", "ပင်မဆိုင်", "名詞", "本店の使い方や意味をしっかり確認して復習しましょう。", "本店 ၏ အဓိပ္ပာယ်မှာ 'ပင်မဆိုင်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3387L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "本当", "ほんとう", "အမှန်တကယ်", "名詞", "本当の使い方や意味をしっかり確認して復習しましょう。", "本当 ၏ အဓိပ္ပာယ်မှာ 'အမှန်တကယ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3388L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "本社", "ほんしゃ", "ရုံးချုပ်", "名詞", "本社の使い方や意味をしっかり確認して復習しましょう。", "本社 ၏ အဓိပ္ပာယ်မှာ 'ရုံးချုပ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3389L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "材料", "ざいりょう", "ကုန်ကြမ်း၊ ပါ၀င်ပစ္စည်း", "名詞", "材料の使い方や意味をしっかり確認して復習しましょう。", "材料 ၏ အဓိပ္ပာယ်မှာ 'ကုန်ကြမ်း၊ ပါ၀င်ပစ္စည်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3390L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "枚数", "まいすう", "အရွက်အရေအတွက်", "名詞", "枚数の使い方や意味をしっかり確認して復習しましょう。", "枚数 ၏ အဓိပ္ပာယ်မှာ 'အရွက်အရေအတွက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3391L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "格安", "かくやす", "ဈေးသက်သာခြင်း", "名詞", "格安の使い方や意味をしっかり確認して復習しましょう。", "格安 ၏ အဓိပ္ပာယ်မှာ 'ဈေးသက်သာခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3392L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "樣子", "ようす", "အခြေအနေ", "名詞", "樣子の使い方や意味をしっかり確認して復習しましょう。", "樣子 ၏ အဓိပ္ပာယ်မှာ 'အခြေအနေ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3393L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "横断", "おうだん", "လူကူးလမ်း", "名詞", "横断の使い方や意味をしっかり確認して復習しましょう。", "横断 ၏ အဓိပ္ပာယ်မှာ 'လူကူးလမ်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3394L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "横断歩道", "おうだんほどう", "လူကူးမျဉ်းကြား", "名詞", "横断歩道の使い方や意味をしっかり確認して復習しましょう。", "横断歩道 ၏ အဓိပ္ပာယ်မှာ 'လူကူးမျဉ်းကြား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3395L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "機会", "きかい", "အခွင့်အရေး", "名詞", "機会の使い方や意味をしっかり確認して復習しましょう。", "機会 ၏ အဓိပ္ပာယ်မှာ 'အခွင့်အရေး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3396L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "機械", "きかい", "စက်", "名詞", "機械の使い方や意味をしっかり確認して復習しましょう。", "機械 ၏ အဓိပ္ပာယ်မှာ 'စက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3397L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "果物", "くだもの", "သစ်သီး", "名詞", "果物の使い方や意味をしっかり確認して復習しましょう。", "果物 ၏ အဓိပ္ပာယ်မှာ 'သစ်သီး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3398L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "案内", "あんない", "လမ်းညွန်သည်", "名詞", "案内の使い方や意味をしっかり確認して復習しましょう。", "案内 ၏ အဓိပ္ပာယ်မှာ 'လမ်းညွန်သည်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3399L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "米国", "べいこく", "အမေရိကန်နိုင်ငံ", "名詞", "米国の使い方や意味をしっかり確認して復習しましょう。", "米国 ၏ အဓိပ္ပာယ်မှာ 'အမေရိကန်နိုင်ငံ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3400L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "半額", "はんがく", "ဈေးနှုန်းတစ်၀က်", "名詞", "半額の使い方や意味をしっかり確認して復習しましょう。", "半額 ၏ အဓိပ္ပာယ်မှာ 'ဈေးနှုန်းတစ်၀က်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3401L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "料理酒", "りょうりしゅ", "ဟင်းချက်အရက်", "名詞", "料理酒の使い方や意味をしっかり確認して復習しましょう。", "料理酒 ၏ အဓိပ္ပာယ်မှာ 'ဟင်းချက်အရက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3402L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "普通", "ふつう(の)", "ပုံမှန်၊ ရိုးရိုး", "名詞", "普通の使い方や意味をしっかり確認して復習しましょう。", "普通 ၏ အဓိပ္ပာယ်မှာ 'ပုံမှန်၊ ရိုးရိုး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")
        card(3403L, 113, "第113課 書類・時間・案内 (Documents, Timing & Guidance)", "N3 漢字言葉: 書・最・期・本・横・機 (Documents & Guidance)", "科学", "かがく", "သိပ္ပံဘာသာ", "名詞", "科学の使い方や意味をしっかり確認して復習しましょう。", "科学 ၏ အဓိပ္ပာယ်မှာ 'သိပ္ပံဘာသာ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson113")

        // Lesson 114 (29 items)
        card(3404L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "移動", "いどう", "ရွှေ့ပြောင်းခြင်း၊ နေရာပြောင်းခြင်း", "名詞", "移動の使い方や意味をしっかり確認して復習しましょう。", "移動 ၏ အဓိပ္ပာယ်မှာ 'ရွှေ့ပြောင်းခြင်း၊ နေရာပြောင်းခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3405L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "税務署", "ぜいむしょ", "အခွန်ရုံး", "名詞", "税務署の使い方や意味をしっかり確認して復習しましょう。", "税務署 ၏ အဓိပ္ပာယ်မှာ 'အခွန်ရုံး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3406L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "税込み", "ぜいこみ", "အခွန်ပါပြီး", "名詞", "税込みの使い方や意味をしっかり確認して復習しましょう。", "税込み ၏ အဓိပ္ပာယ်မှာ 'အခွန်ပါပြီး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3407L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "税金", "ぜいきん", "အခွန်ငွေ", "名詞", "税金の使い方や意味をしっかり確認して復習しましょう。", "税金 ၏ အဓိပ္ပာယ်မှာ 'အခွန်ငွေ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3408L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "種類", "しゅるい", "အမျိုးအစား", "名詞", "種類の使い方や意味をしっかり確認して復習しましょう。", "種類 ၏ အဓိပ္ပာယ်မှာ 'အမျိုးအစား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3409L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "~組", "~くみ", "~ အတွဲ", "接辞・助数詞", "組の使い方や意味をしっかり確認して復習しましょう。", "~組 ၏ အဓိပ္ပာယ်မှာ '~ အတွဲ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3410L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "約~", "やく~", "ခန့်မှန်းခြေ", "接辞・助数詞", "約の使い方や意味をしっかり確認して復習しましょう。", "約~ ၏ အဓိပ္ပာယ်မှာ 'ခန့်မှန်းခြေ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3411L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "約束", "やくそく", "ကတိ၊ ချိန်းဆိုချက်", "名詞", "約束の使い方や意味をしっかり確認して復習しましょう。", "約束 ၏ အဓိပ္ပာယ်မှာ 'ကတိ၊ ချိန်းဆိုချက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3412L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "紅茶", "こうちゃ", "လက်ဖက်ရည်", "名詞", "紅茶の使い方や意味をしっかり確認して復習しましょう。", "紅茶 ၏ အဓိပ္ပာယ်မှာ 'လက်ဖက်ရည်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3413L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "紅葉", "こうよう", "အရွက်နီ၊ မေပယ်သစ်ရွက်", "名詞", "紅葉の使い方や意味をしっかり確認して復習しましょう。", "紅葉 ၏ အဓိပ္ပာယ်မှာ 'အရွက်နီ၊ မေပယ်သစ်ရွက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3414L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "紙袋", "かみぶくろ", "စက္ကူအိတ်", "名詞", "紙袋の使い方や意味をしっかり確認して復習しましょう。", "紙袋 ၏ အဓိပ္ပာယ်မှာ 'စက္ကူအိတ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3415L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "終了", "しゅうりょう", "ပြီးဆုံးခြင်း", "名詞", "終了の使い方や意味をしっかり確認して復習しましょう。", "終了 ၏ အဓိပ္ပာယ်မှာ 'ပြီးဆုံးခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3416L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "組み立てる", "くみたてる", "တပ်ဆင်သည်", "動詞", "組み立てるの使い方や意味をしっかり確認して復習しましょう。", "組み立てる ၏ အဓိပ္ပာယ်မှာ 'တပ်ဆင်သည်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3417L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "経営", "けいえい", "စီမံခန့်ခွဲခြင်း", "名詞", "経営の使い方や意味をしっかり確認して復習しましょう。", "経営 ၏ အဓိပ္ပာယ်မှာ 'စီမံခန့်ခွဲခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3418L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "経済", "けいざい", "စီးပွားရေး", "名詞", "経済の使い方や意味をしっかり確認して復習しましょう。", "経済 ၏ အဓိပ္ပာယ်မှာ 'စီးပွားရေး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3419L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "経験", "けいけん", "အတွေ့အကြုံ", "名詞", "経験の使い方や意味をしっかり確認して復習しましょう。", "経験 ၏ အဓိပ္ပာယ်မှာ 'အတွေ့အကြုံ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3420L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "結婚", "けっこん", "လက်ထပ်ခြင်း", "名詞", "結婚の使い方や意味をしっかり確認して復習しましょう。", "結婚 ၏ အဓိပ္ပာယ်မှာ 'လက်ထပ်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3421L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "結局", "けっきょく", "နောက်ဆုံးတော့", "名詞", "結局の使い方や意味をしっかり確認して復習しましょう。", "結局 ၏ အဓိပ္ပာယ်မှာ 'နောက်ဆုံးတော့' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3422L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "結果", "けっか", "ရလဒ်", "名詞", "結果の使い方や意味をしっかり確認して復習しましょう。", "結果 ၏ အဓိပ္ပာယ်မှာ 'ရလဒ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3423L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "結構", "けっこう(な)", "အတော်လေး", "名詞", "結構の使い方や意味をしっかり確認して復習しましょう。", "結構 ၏ အဓိပ္ပာယ်မှာ 'အတော်လေး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3424L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "絵本", "えほん", "ရုပ်ပြစာအုပ်", "名詞", "絵本の使い方や意味をしっかり確認して復習しましょう。", "絵本 ၏ အဓိပ္ပာယ်မှာ 'ရုပ်ပြစာအုပ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3425L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "絵画", "かいが", "ပန်းချီကား", "名詞", "絵画の使い方や意味をしっかり確認して復習しましょう。", "絵画 ၏ အဓိပ္ပာယ်မှာ 'ပန်းချီကား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3426L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "緑茶", "りょくちゃ", "Green Tea", "名詞", "緑茶の使い方や意味をしっかり確認して復習しましょう。", "緑茶 ၏ အဓိပ္ပာယ်မှာ 'Green Tea' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3427L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "線路", "せんろ", "သံလမ်း", "名詞", "線路の使い方や意味をしっかり確認して復習しましょう。", "線路 ၏ အဓိပ္ပာယ်မှာ 'သံလမ်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3428L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "練習", "れんしゅう", "လေ့ကျင့်ခြင်း", "名詞", "練習の使い方や意味をしっかり確認して復習しましょう。", "練習 ၏ အဓိပ္ပာယ်မှာ 'လေ့ကျင့်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3429L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "新型", "しんがた", "ပုံစံအသစ်", "名詞", "新型の使い方や意味をしっかり確認して復習しましょう。", "新型 ၏ အဓိပ္ပာယ်မှာ 'ပုံစံအသစ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3430L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "新婚旅行", "しんこんりょこう", "ဟန်းနီးမွန်းခရီး", "名詞", "新婚旅行の使い方や意味をしっかり確認して復習しましょう。", "新婚旅行 ၏ အဓိပ္ပာယ်မှာ 'ဟန်းနီးမွန်းခရီး' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3431L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "断水", "だんすい", "ရေပြတ်တောက်ခြင်း", "名詞", "断水の使い方や意味をしっかり確認して復習しましょう。", "断水 ၏ အဓိပ္ပာယ်မှာ 'ရေပြတ်တောက်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")
        card(3432L, 114, "第114課 経済・約束・上達 (Economy, Commitments & Progress)", "N3 漢字言葉: 税・約・経・結・新・上 (Economy & Progress)", "上級", "じょうきゅう", "အထက်တန်းအဆင့်", "名詞", "上級の使い方や意味をしっかり確認して復習しましょう。", "上級 ၏ အဓိပ္ပာယ်မှာ 'အထက်တန်းအဆင့်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson114")

        // Lesson 115 (29 items)
        card(3433L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "上達", "じょうたつ", "တော်လာ၊ တိုးတက်လာခြင်း (အရည်အချင်း)", "名詞", "上達の使い方や意味をしっかり確認して復習しましょう。", "上達 ၏ အဓိပ္ပာယ်မှာ 'တော်လာ၊ တိုးတက်လာခြင်း (အရည်အချင်း)' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3434L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "正常", "せいじょう(な)", "ပုံမှန်ဖြစ်သော", "形容動詞", "正常の使い方や意味をしっかり確認して復習しましょう。", "正常 ၏ အဓိပ္ပာယ်မှာ 'ပုံမှန်ဖြစ်သော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3435L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "正座", "せいざ", "ဂျပန်ယဉ်ကျေးမှု့ပုံစံဖြင့်ပုဆစ်တုပ်ထိုင်ခြင်း", "名詞", "正座の使い方や意味をしっかり確認して復習しましょう。", "正座 ၏ အဓိပ္ပာယ်မှာ 'ဂျပန်ယဉ်ကျေးမှု့ပုံစံဖြင့်ပုဆစ်တုပ်ထိုင်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3436L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "正確", "せいかく(な)", "မှန်ကန်သော", "形容動詞", "正確の使い方や意味をしっかり確認して復習しましょう。", "正確 ၏ အဓိပ္ပာယ်မှာ 'မှန်ကန်သော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3437L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "歩道橋", "ほどうきょう", "လူကူးတံတား", "名詞", "歩道橋の使い方や意味をしっかり確認して復習しましょう。", "歩道橋 ၏ အဓိပ္ပာယ်မှာ 'လူကူးတံတား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3438L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "歯並び", "はならび", "သွားညီခြင်း", "名詞", "歯並びの使い方や意味をしっかり確認して復習しましょう。", "歯並び ၏ အဓိပ္ပာယ်မှာ 'သွားညီခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3439L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "歯医者", "はいしゃ", "သွားဆရာ၀န်", "名詞", "歯医者の使い方や意味をしっかり確認して復習しましょう。", "歯医者 ၏ အဓိပ္ပာယ်မှာ 'သွားဆရာ၀န်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3440L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "歯科", "しか", "သွားဖက်ဆိုင်ရာဌာန", "名詞", "歯科の使い方や意味をしっかり確認して復習しましょう。", "歯科 ၏ အဓိပ္ပာယ်မှာ 'သွားဖက်ဆိုင်ရာဌာန' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3441L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "点数", "てんすう", "ရမှတ်", "名詞", "点数の使い方や意味をしっかり確認して復習しましょう。", "点数 ၏ အဓိပ္ပာယ်မှာ 'ရမှတ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3442L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "無休", "むきゅう", "လစာမဲ့ပိတ်ရက်", "名詞", "無休の使い方や意味をしっかり確認して復習しましょう。", "無休 ၏ အဓိပ္ပာယ်မှာ 'လစာမဲ့ပိတ်ရက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3443L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "無料", "むりょう", "အခမဲ့", "名詞", "無料の使い方や意味をしっかり確認して復習しましょう。", "無料 ၏ အဓိပ္ပာယ်မှာ 'အခမဲ့' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3444L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "無断", "むだん", "ခွင့်ပြုချက်မရှိခြင်း", "名詞", "無断の使い方や意味をしっかり確認して復習しましょう。", "無断 ၏ အဓိပ္ပာယ်မှာ 'ခွင့်ပြုချက်မရှိခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3445L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "無理", "むり(な)", "မဖြစ်နိုင်သော", "形容動詞", "無理の使い方や意味をしっかり確認して復習しましょう。", "無理 ၏ အဓိပ္ပာယ်မှာ 'မဖြစ်နိုင်သော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3446L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "熱心", "ねっしん(な)", "စိတ်အားထက်သန်သော", "形容動詞", "熱心の使い方や意味をしっかり確認して復習しましょう。", "熱心 ၏ အဓိပ္ပာယ်မှာ 'စိတ်အားထက်သန်သော' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3447L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "現在", "げんざい", "မျက်မှောက်ခေတ်", "名詞", "現在の使い方や意味をしっかり確認して復習しましょう。", "現在 ၏ အဓိပ္ပာယ်မှာ 'မျက်မှောက်ခေတ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3448L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "現金", "げんきん", "ငွေသား", "名詞", "現金の使い方や意味をしっかり確認して復習しましょう。", "現金 ၏ အဓိပ္ပာယ်မှာ 'ငွေသား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3449L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "理由", "りゆう", "အကြောင်းပြချက်", "名詞", "理由の使い方や意味をしっかり確認して復習しましょう。", "理由 ၏ အဓိပ္ပာယ်မှာ 'အကြောင်းပြချက်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3450L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "理解", "りかい", "နားလည်ခြင်း", "名詞", "理解の使い方や意味をしっかり確認して復習しましょう。", "理解 ၏ အဓိပ္ပာယ်မှာ 'နားလည်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3451L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "申告", "しんこく", "သတင်းပို့ခြင်း၊ လျှောက်ထားခြင်း", "名詞", "申告の使い方や意味をしっかり確認して復習しましょう。", "申告 ၏ အဓိပ္ပာယ်မှာ 'သတင်းပို့ခြင်း၊ လျှောက်ထားခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3452L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "申請", "しんせい", "လျှောက်လွှာတင်ခြင်း", "名詞", "申請の使い方や意味をしっかり確認して復習しましょう。", "申請 ၏ အဓိပ္ပာယ်မှာ 'လျှောက်လွှာတင်ခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3453L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "申込書", "もうしこみしょ", "လျှောက်လွှာ", "名詞", "申込書の使い方や意味をしっかり確認して復習しましょう。", "申込書 ၏ အဓိပ္ပာယ်မှာ 'လျှောက်လွှာ' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3454L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "申し上げる", "もうしあげる", "အစီရင်ခံခြင်း၊ ပြောကြားခြင်း", "動詞", "申し上げるの使い方や意味をしっかり確認して復習しましょう。", "申し上げる ၏ အဓိပ္ပာယ်မှာ 'အစီရင်ခံခြင်း၊ ပြောကြားခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3455L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "申し込む", "もうしこむ", "လျှောက်လွှာတင်သည်", "動詞", "申し込むの使い方や意味をしっかり確認して復習しましょう。", "申し込む ၏ အဓိပ္ပာယ်မှာ 'လျှောက်လွှာတင်သည်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3456L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "男性", "だんせい", "အမျိုးသား", "名詞", "男性の使い方や意味をしっかり確認して復習しましょう。", "男性 ၏ အဓိပ္ပာယ်မှာ 'အမျိုးသား' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3457L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "画面", "がめん", "မျက်နှာပြင်", "名詞", "画面の使い方や意味をしっかり確認して復習しましょう。", "画面 ၏ အဓိပ္ပာယ်မှာ 'မျက်နှာပြင်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3458L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "留学", "りゅうがく", "ပညာတော်သင်သွားခြင်း", "名詞", "留学の使い方や意味をしっかり確認して復習しましょう。", "留学 ၏ အဓိပ္ပာယ်မှာ 'ပညာတော်သင်သွားခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3459L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "留守", "るす", "အိမ်မှာမရှိခြင်း", "名詞", "留守の使い方や意味をしっかり確認して復習しましょう。", "留守 ၏ အဓိပ္ပာယ်မှာ 'အိမ်မှာမရှိခြင်း' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3460L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "留守番", "るすばん", "အိမ်စောင့်", "名詞", "留守番の使い方や意味をしっかり確認して復習しましょう。", "留守番 ၏ အဓိပ္ပာယ်မှာ 'အိမ်စောင့်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")
        card(3461L, 115, "第115課 正確・健康・申請 (Accuracy, Health & Applications)", "N3 漢字言葉: 正・歯・無・現・申・番 (Accuracy & Applications)", "番号", "ばんごう", "နံပါတ်", "名詞", "番号の使い方や意味をしっかり確認して復習しましょう。", "番号 ၏ အဓိပ္ပာယ်မှာ 'နံပါတ်' ဖြစ်သည်။", "EssentialKanji, N3, Lesson115")

        return list
    }
}
