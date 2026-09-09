package com.example.data.seed

import com.example.data.model.VocabCard

object VocabSeedDataMastery4 {
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

        // Lesson 129 (28 items)
        card(4250L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "面接", "めんせつ", "အင်တာဗျူး", "名詞", "面接の使い方や意味をしっかり確認して復習しましょう。", "面接 ၏ အဓိပ္ပာယ်မှာ 'အင်တာဗျူး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4251L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "短時間", "たんじかん", "အချိန်တို", "名詞", "短時間の使い方や意味をしっかり確認して復習しましょう。", "短時間 ၏ အဓိပ္ပာယ်မှာ 'အချိန်တို' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4252L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "正三角形", "せいさんかくけい", "သုံးနားညီတြိဂံ", "名詞", "正三角形の使い方や意味をしっかり確認して復習しましょう。", "正三角形 ၏ အဓိပ္ပာယ်မှာ 'သုံးနားညီတြိဂံ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4253L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "三辺", "さんぺん", "၃နား", "名詞", "三辺の使い方や意味をしっかり確認して復習しましょう。", "三辺 ၏ အဓိပ္ပာယ်မှာ '၃နား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4254L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "欠点", "けってん", "အားနည်းချက်", "名詞", "欠点の使い方や意味をしっかり確認して復習しましょう。", "欠点 ၏ အဓိပ္ပာယ်မှာ 'အားနည်းချက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4255L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "氷点", "ひょうてん", "ရေခဲမှတ်", "名詞", "氷点の使い方や意味をしっかり確認して復習しましょう。", "氷点 ၏ အဓိပ္ပာယ်မှာ 'ရေခဲမှတ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4256L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "慌てる", "あわてる", "ပြာယာခတ်သည်။", "動詞", "慌てるの使い方や意味をしっかり確認して復習しましょう。", "慌てる ၏ အဓိပ္ပာယ်မှာ 'ပြာယာခတ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4257L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "就職", "しゅうしょく", "အလုပ်ရှာခြင်း", "名詞", "就職の使い方や意味をしっかり確認して復習しましょう。", "就職 ၏ အဓိပ္ပာယ်မှာ 'အလုပ်ရှာခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4258L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "特別賞", "とくべつしょう", "အထူးဆု", "名詞", "特別賞の使い方や意味をしっかり確認して復習しましょう。", "特別賞 ၏ အဓိပ္ပာယ်မှာ 'အထူးဆု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4259L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "作文", "さくぶん", "စာစီစာကုံး", "名詞", "作文の使い方や意味をしっかり確認して復習しましょう。", "作文 ၏ အဓိပ္ပာယ်မှာ 'စာစီစာကုံး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4260L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "自習の時間", "じしゅうのじかん", "ကိုယ်ပိုင်လေ့လာချိန်", "名詞", "自習の時間の使い方や意味をしっかり確認して復習しましょう。", "自習の時間 ၏ အဓိပ္ပာယ်မှာ 'ကိုယ်ပိုင်လေ့လာချိန်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4261L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "人間", "にんげん", "လူသား", "名詞", "人間の使い方や意味をしっかり確認して復習しましょう。", "人間 ၏ အဓိပ္ပာယ်မှာ 'လူသား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4262L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "植物", "しょくぶつ", "အပင်", "名詞", "植物の使い方や意味をしっかり確認して復習しましょう。", "植物 ၏ အဓိပ္ပာယ်မှာ 'အပင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4263L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "栄養", "えいよう", "အာဟာရ", "名詞", "栄養の使い方や意味をしっかり確認して復習しましょう。", "栄養 ၏ အဓိပ္ပာယ်မှာ 'အာဟာရ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4264L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "想像", "そうぞう", "ထင်မြင်မှု", "名詞", "想像の使い方や意味をしっかり確認して復習しましょう。", "想像 ၏ အဓိပ္ပာယ်မှာ 'ထင်မြင်မှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4265L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "言語", "げんご", "ဘာသာစကား", "名詞", "言語の使い方や意味をしっかり確認して復習しましょう。", "言語 ၏ အဓိပ္ပာယ်မှာ 'ဘာသာစကား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4266L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "にんじん", "にんじん", "မုန်လာဥ", "名詞", "にんじんの使い方や意味をしっかり確認して復習しましょう。", "にんじん ၏ အဓိပ္ပာယ်မှာ 'မုန်လာဥ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4267L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "ピーマン", "ピーマン", "ငရုတ်ပွသီး", "名詞", "ピーマンの使い方や意味をしっかり確認して復習しましょう。", "ピーマン ၏ အဓိပ္ပာယ်မှာ 'ငရုတ်ပွသီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4268L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "社会問題", "しゃかいもんだい", "လူမှုရေးပြဿနာ", "名詞", "社会問題の使い方や意味をしっかり確認して復習しましょう。", "社会問題 ၏ အဓိပ္ပာယ်မှာ 'လူမှုရေးပြဿနာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4269L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "祈る", "いのる", "ဆုတောင်းသည်။", "動詞", "祈るの使い方や意味をしっかり確認して復習しましょう。", "祈る ၏ အဓိပ္ပာယ်မှာ 'ဆုတောင်းသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4270L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "我慢強かった", "がまんづよかった", "သည်းခံမှုမြင့်မားခြင်း", "名詞", "我慢強かったの使い方や意味をしっかり確認して復習しましょう。", "我慢強かった ၏ အဓိပ္ပာယ်မှာ 'သည်းခံမှုမြင့်မားခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4271L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "生活習慣", "せいかつしゅうかん", "အသက်မွေးမှုအလေ့အကျင့်", "名詞", "生活習慣の使い方や意味をしっかり確認して復習しましょう。", "生活習慣 ၏ အဓိပ္ပာယ်မှာ 'အသက်မွေးမှုအလေ့အကျင့်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4272L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "実際に", "じっさいに", "လက်တွေ့", "名詞", "実際にの使い方や意味をしっかり確認して復習しましょう。", "実際に ၏ အဓိပ္ပာယ်မှာ 'လက်တွေ့' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4273L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "基本", "きほん", "အခြေခံ", "名詞", "基本の使い方や意味をしっかり確認して復習しましょう。", "基本 ၏ အဓိပ္ပာယ်မှာ 'အခြေခံ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4274L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "立場", "たちば", "ရပ်တည်မှု", "名詞", "立場の使い方や意味をしっかり確認して復習しましょう。", "立場 ၏ အဓိပ္ပာယ်မှာ 'ရပ်တည်မှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4275L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "王様", "おうさま", "ဘုရင်", "名詞", "王様の使い方や意味をしっかり確認して復習しましょう。", "王様 ၏ အဓိပ္ပာယ်မှာ 'ဘုရင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4276L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "大学進学率", "だいがくしんがくりつ", "တက္ကသိုလ်ဆက်တက်မှုနှုန်း", "名詞", "大学進学率の使い方や意味をしっかり確認して復習しましょう。", "大学進学率 ၏ အဓိပ္ပာယ်မှာ 'တက္ကသိုလ်ဆက်တက်မှုနှုန်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")
        card(4277L, 129, "第129課 人間・思考・生活習慣 (Human Mind & Life Habits)", "Mastery: 人間・心理・生活習慣 (Mind & Habits)", "あんパン", "あんパン", "ပဲပေါင်မုန့်", "名詞", "あんパンの使い方や意味をしっかり確認して復習しましょう。", "あんパン ၏ အဓိပ္ပာယ်မှာ 'ပဲပေါင်မုန့်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson129")

        // Lesson 130 (28 items)
        card(4278L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "工夫する", "くふうする", "ကြံဆသည်။", "動詞", "工夫するの使い方や意味をしっかり確認して復習しましょう。", "工夫する ၏ အဓိပ္ပာယ်မှာ 'ကြံဆသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4279L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "親友", "しんゆう", "သူငယ်ချင်းကောင်း", "名詞", "親友の使い方や意味をしっかり確認して復習しましょう。", "親友 ၏ အဓိပ္ပာယ်မှာ 'သူငယ်ချင်းကောင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4280L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "頼む", "たのむ", "တောင်းဆိုသည်။", "動詞", "頼むの使い方や意味をしっかり確認して復習しましょう。", "頼む ၏ အဓိပ္ပာယ်မှာ 'တောင်းဆိုသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4281L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "時給", "じきゅう", "တစ်နာရီလုပ်အားခ", "名詞", "時給の使い方や意味をしっかり確認して復習しましょう。", "時給 ၏ အဓိပ္ပာယ်မှာ 'တစ်နာရီလုပ်အားခ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4282L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "実際", "じっさい", "လက်တွေ့", "名詞", "実際の使い方や意味をしっかり確認して復習しましょう。", "実際 ၏ အဓိပ္ပာယ်မှာ 'လက်တွေ့' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4283L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "態度", "たいど", "အပြုအမူ", "名詞", "態度の使い方や意味をしっかり確認して復習しましょう。", "態度 ၏ အဓိပ္ပာယ်မှာ 'အပြုအမူ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4284L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "結局", "けっきょく", "နောက်ဆုံးတော့", "名詞", "結局の使い方や意味をしっかり確認して復習しましょう。", "結局 ၏ အဓိပ္ပာယ်မှာ 'နောက်ဆုံးတော့' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4285L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "計算", "けいさん", "တွက်ချက်ခြင်း", "名詞", "計算の使い方や意味をしっかり確認して復習しましょう。", "計算 ၏ အဓိပ္ပာယ်မှာ 'တွက်ချက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4286L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "期待", "きたい", "မျှော်လင့်ချက်", "名詞", "期待の使い方や意味をしっかり確認して復習しましょう。", "期待 ၏ အဓိပ္ပာယ်မှာ 'မျှော်လင့်ချက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4287L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "人物", "じんぶつ", "လူသား", "名詞", "人物の使い方や意味をしっかり確認して復習しましょう。", "人物 ၏ အဓိပ္ပာယ်မှာ 'လူသား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4288L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "幸せ", "しあわせ", "ပျော်ရွှင်မှု", "名詞", "幸せの使い方や意味をしっかり確認して復習しましょう。", "幸せ ၏ အဓိပ္ပာယ်မှာ 'ပျော်ရွှင်မှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4289L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "祈る", "いのる", "ဆုတောင်းသည်။", "動詞", "祈るの使い方や意味をしっかり確認して復習しましょう。", "祈る ၏ အဓိပ္ပာယ်မှာ 'ဆုတောင်းသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4290L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "関心", "かんしん", "စိတ်ဝင်စားမှု", "名詞", "関心の使い方や意味をしっかり確認して復習しましょう。", "関心 ၏ အဓိပ္ပာယ်မှာ 'စိတ်ဝင်စားမှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4291L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "招待状", "しょうたいじょう", "ဖိတ်စာ", "名詞", "招待状の使い方や意味をしっかり確認して復習しましょう。", "招待状 ၏ အဓိပ္ပာယ်မှာ 'ဖိတ်စာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4292L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "招待客", "しょうたいきゃく", "ဖိတ်ကြားခံရသူ", "名詞", "招待客の使い方や意味をしっかり確認して復習しましょう。", "招待客 ၏ အဓိပ္ပာယ်မှာ 'ဖိတ်ကြားခံရသူ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4293L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "環境", "かんきょう", "ပတ်ဝန်းကျင်", "名詞", "環境の使い方や意味をしっかり確認して復習しましょう。", "環境 ၏ အဓိပ္ပာယ်မှာ 'ပတ်ဝန်းကျင်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4294L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "意外", "いがい", "ထင်ထားတာနဲ့လွဲပြီး", "名詞", "意外の使い方や意味をしっかり確認して復習しましょう。", "意外 ၏ အဓိပ္ပာယ်မှာ 'ထင်ထားတာနဲ့လွဲပြီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4295L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "行動", "こうどう", "လုပ်ဆောင်ချက်", "名詞", "行動の使い方や意味をしっかり確認して復習しましょう。", "行動 ၏ အဓိပ္ပာယ်မှာ 'လုပ်ဆောင်ချက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4296L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "握手", "あくしゅ", "လက်ဆွဲနှုတ်ဆက်ခြင်း", "名詞", "握手の使い方や意味をしっかり確認して復習しましょう。", "握手 ၏ အဓိပ္ပာယ်မှာ 'လက်ဆွဲနှုတ်ဆက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4297L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "見守る", "みまもる", "ကြည့်ရှုစောင့်ရှောက်ခြင်း", "動詞", "見守るの使い方や意味をしっかり確認して復習しましょう。", "見守る ၏ အဓိပ္ပာယ်မှာ 'ကြည့်ရှုစောင့်ရှောက်ခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4298L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "人間関係", "にんげんかんけい", "လူမှုဆက်ဆံရေး", "名詞", "人間関係の使い方や意味をしっかり確認して復習しましょう。", "人間関係 ၏ အဓိပ္ပာယ်မှာ 'လူမှုဆက်ဆံရေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4299L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "出発日", "しゅっぱつび", "ထွက်ခွာတဲ့နေ့ရက်", "名詞", "出発日の使い方や意味をしっかり確認して復習しましょう。", "出発日 ၏ အဓိပ္ပာယ်မှာ 'ထွက်ခွာတဲ့နေ့ရက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4300L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "社員旅行", "しゃいんりょこう", "ဝန်ထမ်းခရီးစဉ်", "名詞", "社員旅行の使い方や意味をしっかり確認して復習しましょう。", "社員旅行 ၏ အဓိပ္ပာယ်မှာ 'ဝန်ထမ်းခရီးစဉ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4301L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "制服", "せいふく", "uniform", "名詞", "制服の使い方や意味をしっかり確認して復習しましょう。", "制服 ၏ အဓိပ္ပာယ်မှာ 'uniform' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4302L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "位置", "いち", "နေရာ", "名詞", "位置の使い方や意味をしっかり確認して復習しましょう。", "位置 ၏ အဓိပ္ပာယ်မှာ 'နေရာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4303L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "屋根", "やね", "ခေါင်မိုး", "名詞", "屋根の使い方や意味をしっかり確認して復習しましょう。", "屋根 ၏ အဓိပ္ပာယ်မှာ 'ခေါင်မိုး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4304L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "音楽室", "おんがくしつ", "ဂီတခန်း", "名詞", "音楽室の使い方や意味をしっかり確認して復習しましょう。", "音楽室 ၏ အဓိပ္ပာယ်မှာ 'ဂီတခန်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")
        card(4305L, 130, "第130課 交流・設備・学校生活 (School, Facilities & Events)", "Mastery: 交流・設備・学校・音楽 (School & Facilities)", "楽器", "がっき", "တူရိယာ", "名詞", "楽器の使い方や意味をしっかり確認して復習しましょう。", "楽器 ၏ အဓိပ္ပာယ်မှာ 'တူရိယာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson130")

        // Lesson 131 (28 items)
        card(4306L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "十分水分", "じゅうぶんすいぶん", "ရေဓာတ်လုံလောက်မှု", "名詞", "十分水分の使い方や意味をしっかり確認して復習しましょう。", "十分水分 ၏ အဓိပ္ပာယ်မှာ 'ရေဓာတ်လုံလောက်မှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4307L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "調節する", "ちょうせつする", "ချိန်ညှိသည်။", "動詞", "調節するの使い方や意味をしっかり確認して復習しましょう。", "調節する ၏ အဓိပ္ပာယ်မှာ 'ချိန်ညှိသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4308L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "番組表", "ばんぐみひょう", "အစီအစဉ်ဇယား", "名詞", "番組表の使い方や意味をしっかり確認して復習しましょう。", "番組表 ၏ အဓိပ္ပာယ်မှာ 'အစီအစဉ်ဇယား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4309L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "大男", "おおおとこ", "ယောက်ျားကြီး", "名詞", "大男の使い方や意味をしっかり確認して復習しましょう。", "大男 ၏ အဓိပ္ပာယ်မှာ 'ယောက်ျားကြီး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4310L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "数人", "すうにん", "လူပေါင်းများစွာ", "名詞", "数人の使い方や意味をしっかり確認して復習しましょう。", "数人 ၏ အဓိပ္ပာယ်မှာ 'လူပေါင်းများစွာ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4311L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "玄関", "げんかん", "အိမ်အဝင်ဝ", "名詞", "玄関の使い方や意味をしっかり確認して復習しましょう。", "玄関 ၏ အဓိပ္ပာယ်မှာ 'အိမ်အဝင်ဝ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4312L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "雪景色", "ゆきげしき", "နှင်းရှုခင်း", "名詞", "雪景色の使い方や意味をしっかり確認して復習しましょう。", "雪景色 ၏ အဓိပ္ပာယ်မှာ 'နှင်းရှုခင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4313L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "偶然", "ぐうぜん", "ရုတ်တရက်", "副詞", "偶然の使い方や意味をしっかり確認して復習しましょう。", "偶然 ၏ အဓိပ္ပာယ်မှာ 'ရုတ်တရက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4314L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "運が悪い", "うんがわるい", "ကံမကောင်းသော", "形容動詞", "運が悪いの使い方や意味をしっかり確認して復習しましょう。", "運が悪い ၏ အဓိပ္ပာယ်မှာ 'ကံမကောင်းသော' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4315L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "振る", "ふる", "လှုပ်ယမ်းသည်။", "動詞", "振るの使い方や意味をしっかり確認して復習しましょう。", "振る ၏ အဓိပ္ပာယ်မှာ 'လှုပ်ယမ်းသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4316L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "鏡", "かがみ", "ကြည့်မှန်", "名詞", "鏡の使い方や意味をしっかり確認して復習しましょう。", "鏡 ၏ အဓိပ္ပာယ်မှာ 'ကြည့်မှန်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4317L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "解決", "かいけつ", "ဖြေရှင်းခြင်း", "名詞", "解決の使い方や意味をしっかり確認して復習しましょう。", "解決 ၏ အဓိပ္ပာယ်မှာ 'ဖြေရှင်းခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4318L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "電気自動車", "でんきじどうしゃ", "လျှပ်စစ်မော်တော်ကား", "名詞", "電気自動車の使い方や意味をしっかり確認して復習しましょう。", "電気自動車 ၏ အဓိပ္ပာယ်မှာ 'လျှပ်စစ်မော်တော်ကား' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4319L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "水中", "すいちゅう", "ရေထဲ", "名詞", "水中の使い方や意味をしっかり確認して復習しましょう。", "水中 ၏ အဓိပ္ပာယ်မှာ 'ရေထဲ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4320L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "のんびりする", "のんびりする", "အေးအေးဆေးဆေးအနားယူခြင်း", "動詞", "のんびりするの使い方や意味をしっかり確認して復習しましょう。", "のんびりする ၏ အဓိပ္ပာယ်မှာ 'အေးအေးဆေးဆေးအနားယူခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4321L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "山頂", "さんちょう", "တောင်ထိပ်", "名詞", "山頂の使い方や意味をしっかり確認して復習しましょう。", "山頂 ၏ အဓိပ္ပာယ်မှာ 'တောင်ထိပ်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4322L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "不正", "ふせい", "လိမ်လည်မှု", "名詞", "不正の使い方や意味をしっかり確認して復習しましょう。", "不正 ၏ အဓိပ္ပာယ်မှာ 'လိမ်လည်မှု' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4323L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "許す", "ゆるす", "ခွင့်လွှတ်သည်။", "動詞", "許すの使い方や意味をしっかり確認して復習しましょう。", "許す ၏ အဓိပ္ပာယ်မှာ 'ခွင့်လွှတ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4324L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "転ぶ", "ころぶ", "ချော်လဲသည်။", "名詞", "転ぶの使い方や意味をしっかり確認して復習しましょう。", "転ぶ ၏ အဓိပ္ပာယ်မှာ 'ချော်လဲသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4325L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "黙る", "だまる", "နှုတ်ဆိတ်သည်။", "動詞", "黙るの使い方や意味をしっかり確認して復習しましょう。", "黙る ၏ အဓိပ္ပာယ်မှာ 'နှုတ်ဆိတ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4326L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "距離", "きょり", "အကွာအဝေး", "名詞", "距離の使い方や意味をしっかり確認して復習しましょう。", "距離 ၏ အဓိပ္ပာယ်မှာ 'အကွာအဝေး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4327L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "突然", "とつぜん", "ရုတ်တရက်", "副詞", "突然の使い方や意味をしっかり確認して復習しましょう。", "突然 ၏ အဓိပ္ပာယ်မှာ 'ရုတ်တရက်' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4328L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "黒板", "こくばん", "ကျောက်သင်ပုန်း", "名詞", "黒板の使い方や意味をしっかり確認して復習しましょう。", "黒板 ၏ အဓိပ္ပာယ်မှာ 'ကျောက်သင်ပုန်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4329L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "遠慮", "えんりょ", "အားနာခြင်း", "名詞", "遠慮の使い方や意味をしっかり確認して復習しましょう。", "遠慮 ၏ အဓိပ္ပာယ်မှာ 'အားနာခြင်း' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4330L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "踏む", "ふむ", "နင်းသည်။", "動詞", "踏むの使い方や意味をしっかり確認して復習しましょう。", "踏む ၏ အဓိပ္ပာယ်မှာ 'နင်းသည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4331L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "作文", "さくぶん", "စာစီစာကုံး", "名詞", "作文の使い方や意味をしっかり確認して復習しましょう。", "作文 ၏ အဓိပ္ပာယ်မှာ 'စာစီစာကုံး' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4332L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "隠す", "かくす", "ဖုံးကွယ်သည်။", "動詞", "隠すの使い方や意味をしっかり確認して復習しましょう。", "隠す ၏ အဓိပ္ပာယ်မှာ 'ဖုံးကွယ်သည်။' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")
        card(4333L, 131, "第131課 行動・トラブル解決・観光 (Action, Resolution & Tourism)", "Mastery: 行動・解決・観光・表現 (Action & Tourism)", "観光地", "かんこうち", "ခရီးသွားနယ်မြေ", "名詞", "観光地の使い方や意味をしっかり確認して復習しましょう。", "観光地 ၏ အဓိပ္ပာယ်မှာ 'ခရီးသွားနယ်မြေ' ဖြစ်သည်။", "MasteryKanji, N3, Lesson131")

        return list
    }
}
