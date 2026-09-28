package com.example.data.util

import com.example.data.model.VocabCard
import com.example.ui.viewmodel.QuizQuestion

data class HomophoneEntry(
    val kanji: String,
    val reading: String,
    val burmeseMeaning: String,
    val nuance: String,
    val example: String = "",
    val exampleBurmese: String = ""
)

data class HomophoneGroup(
    val reading: String,
    val title: String,
    val burmeseCategory: String,
    val entries: List<HomophoneEntry>
)

object HomophoneData {

    val JLPT_N3_HOMOPHONES: List<HomophoneGroup> = listOf(
        // 1. はかる
        HomophoneGroup(
            reading = "はかる",
            title = "はかる (Measure / Plan)",
            burmeseCategory = "တိုင်းတာသည် / ချိန်တွယ်သည် / အစီအစဉ်ချသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "量る",
                    reading = "はかる",
                    burmeseMeaning = "အလေးချိန်၊ ပမာဏကို ချိန်တွယ်သည်",
                    nuance = "အလေးချိန် (weight) သို့မဟုတ် ထုထည် (volume) ကို ချိန်စက်ဖြင့် ချိန်တွယ်ရာတွင် သုံးသည်",
                    example = "荷物の重さを量る。",
                    exampleBurmese = "ဝန်စည်စလယ်၏ အလေးချိန်ကို ချိန်တွယ်သည်။"
                ),
                HomophoneEntry(
                    kanji = "測る",
                    reading = "はかる",
                    burmeseMeaning = "အလျား၊ အနံ၊ အနက်၊ အကွာအဝေးကို တိုင်းသည်",
                    nuance = "အရှည် (length)၊ အနက် (depth)၊ ဧရိယာ စသည်တို့ကို ပေတံ၊ တိုင်းတာစက်ဖြင့် တိုင်းရာတွင် သုံးသည်",
                    example = "机の長さを測る。",
                    exampleBurmese = "စားပွဲ၏ အလျားကို တိုင်းတာသည်။"
                ),
                HomophoneEntry(
                    kanji = "計る",
                    reading = "はかる",
                    burmeseMeaning = "အချိန်၊ ကိန်းဂဏန်း၊ အပူချိန်ကို တိုင်းတာသည်",
                    nuance = "အချိန် (time)၊ အရေအတွက် (count)၊ အပူချိန် စသည့် ဂဏန်းတန်ဖိုးများကို တွက်ချက်တိုင်းတာရာတွင် သုံးသည်",
                    example = "時間を計ってテストを受ける。",
                    exampleBurmese = "အချိန်ကို တိုင်းတာမှတ်သားပြီး စာမေးပွဲဖြေဆိုသည်။"
                ),
                HomophoneEntry(
                    kanji = "図る",
                    reading = "はかる",
                    burmeseMeaning = "အစီအစဉ်ချသည်၊ ကြံစည်ကြိုးပမ်းသည်",
                    nuance = "ကိစ္စတစ်ခုခုကို အောင်မြင်ပြီးမြောက်စေရန် စီစဉ်ကြိုးပမ်းရာတွင် သုံးသည်",
                    example = "問題の早期解決を図る。",
                    exampleBurmese = "ပြဿနာကို စောစီးစွာဖြေရှင်းနိုင်ရန် ကြိုးပမ်းသည်။"
                )
            )
        ),

        // 2. なおる
        HomophoneGroup(
            reading = "なおる",
            title = "なおる (Cure / Repair)",
            burmeseCategory = "ပြန်လည်ကောင်းမွန်လာသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "治る",
                    reading = "なおる",
                    burmeseMeaning = "ဖျားနာရောဂါ၊ ဒဏ်ရာ ပျောက်ကင်းသည်",
                    nuance = "ခန္ဓာကိုယ်ဖျားနာမှု (illness) သို့မဟုတ် ဒဏ်ရာ (injury) သက်သာပျောက်ကင်းရာတွင် သုံးသည်",
                    example = "風邪が完全に治った。",
                    exampleBurmese = "အအေးမိဖျားနာခြင်း လုံးဝပျောက်ကင်းသွားခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "直る",
                    reading = "なおる",
                    burmeseMeaning = "စက်ပစ္စည်း၊ အခြေအနေ ပြင်ဆင်ပြီး ကောင်းမွန်လာသည်",
                    nuance = "ပျက်စီးနေသော စက်၊ ပစ္စည်း (machine) သို့မဟုတ် မကောင်းသောအကျင့် ပြန်လည်ကောင်းမွန်လာရာတွင် သုံးသည်",
                    example = "壊れた時計が直った。",
                    exampleBurmese = "ပျက်နေသောနာရီ ပြန်ကောင်းသွားသည်။"
                )
            )
        ),

        // 3. かえる
        HomophoneGroup(
            reading = "かえる",
            title = "かえる (Change / Substitute / Return)",
            burmeseCategory = "ပြောင်းလဲသည် / အစားထိုးသည် / ပြန်လည်ရောက်ရှိသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "変える",
                    reading = "かえる",
                    burmeseMeaning = "ပုံစံ၊ အခြေအနေ၊ အစီအစဉ်ကို ပြောင်းလဲသည်",
                    nuance = "အရောင်၊ အသွင်အပြင်၊ အချိန်ဇယား စသည်တို့ကို မတူအောင် ပြောင်းလဲရာတွင် သုံးသည်",
                    example = "気分を変えるために散歩する。",
                    exampleBurmese = "စိတ်ပြောင်းသွားစေရန် လမ်းလျှောက်သည်။"
                ),
                HomophoneEntry(
                    kanji = "代える",
                    reading = "かえる",
                    burmeseMeaning = "တစ်ခုအစား အခြားတစ်ခုကို အစားထိုးသုံးသည်",
                    nuance = "လူ သို့မဟုတ် ပစ္စည်းတစ်ခုနေရာတွင် အခြားတစ်ခုကို ကိုယ်စားပြု အစားထိုးရာတွင် သုံးသည်",
                    example = "言葉に代えて花を贈る。",
                    exampleBurmese = "စကားလုံးများအစား ပန်းလက်ဆောင်ပေးပို့သည်။"
                ),
                HomophoneEntry(
                    kanji = "替える",
                    reading = "かえる",
                    burmeseMeaning = "အဟောင်းနေရာတွင် အသစ်ဖြင့် လဲလှယ်သည်",
                    nuance = "အသုံးမဝင်တော့သောအရာကို အသစ်တစ်ခုဖြင့် အစားထိုးလဲလှယ်ရာတွင် သုံးသည် (ဥပမာ- ဓာတ်ခဲလဲသည်)",
                    example = "時計の電池を替える。",
                    exampleBurmese = "နာရီ၏ ဓာတ်ခဲကို အသစ်လဲလှယ်သည်။"
                ),
                HomophoneEntry(
                    kanji = "換える",
                    reading = "かえる",
                    burmeseMeaning = "ပစ္စည်းအချင်းချင်း သို့မဟုတ် ငွေကြေး အပြန်အလှန် လဲလှယ်သည်",
                    nuance = "တန်ဖိုးညီမျှသောအရာ အပြန်အလှန် လဲလှယ်ခြင်း (ဥပမာ- ငွေလဲခြင်း၊ ရထားပြောင်းစီးခြင်း)",
                    example = "日本円をドルに換える。",
                    exampleBurmese = "ဂျပန်ယန်းငွေကို ဒေါ်လာသို့ လဲလှယ်သည်။"
                ),
                HomophoneEntry(
                    kanji = "返る",
                    reading = "かえる",
                    burmeseMeaning = "မူလနေရာ၊ မူလအခြေအနေသို့ ပြန်လည်ရောက်ရှိသည်",
                    nuance = "မူလပိုင်ရှင်ထံသို့ သို့မဟုတ် စတင်ခဲ့သော အခြေအနေသို့ ပြန်ရောက်ခြင်း",
                    example = "初心に返って頑張る。",
                    exampleBurmese = "စတင်ခဲ့စဉ်က မူလစိတ်ထားအတိုင်း ပြန်လည်ကြိုးစားသည်။"
                )
            )
        ),

        // 4. かがく
        HomophoneGroup(
            reading = "かがく",
            title = "かがく (Science / Chemistry)",
            burmeseCategory = "သိပ္ပံပညာ နှင့် ဓါတုဗေဒ",
            entries = listOf(
                HomophoneEntry(
                    kanji = "科学",
                    reading = "かがく",
                    burmeseMeaning = "သိပ္ပံပညာရပ် (Science)",
                    nuance = "သဘာဝတရားနှင့် စကြဝဠာဆိုင်ရာ စနစ်တကျလေ့လာသော သိပ္ပံပညာရပ်ကြီး",
                    example = "科学の進歩は素晴らしい。",
                    exampleBurmese = "သိပ္ပံပညာ တိုးတက်လာမှုမှာ အံ့မခန်းဖွယ်ဖြစ်သည်။"
                ),
                HomophoneEntry(
                    kanji = "化学",
                    reading = "かがく",
                    burmeseMeaning = "ဓါတုဗေဒဘာသာရပ် (Chemistry)",
                    nuance = "ဒြပ်ပစ္စည်းများ၏ ဓာတ်ပြုမှုဆိုင်ရာ ဓါတုဗေဒဘာသာရပ် (ခွဲခြားရန် 'ばけがく' ဟုလည်း ခေါ်ကြသည်)",
                    example = "高校で化学の実験をした。",
                    exampleBurmese = "အထက်တန်းကျောင်းတွင် ဓါတုဗေဒလက်တွေ့စမ်းသပ်မှု ပြုလုပ်ခဲ့သည်။"
                )
            )
        ),

        // 5. じしん
        HomophoneGroup(
            reading = "じしん",
            title = "じしん (Confidence / Earthquake / Oneself)",
            burmeseCategory = "ယုံကြည်မှု / ငလျင် / မိမိကိုယ်တိုင်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "自信",
                    reading = "じしん",
                    burmeseMeaning = "ကိုယ့်ကိုယ်ကိုယ် ယုံကြည်မှု (Confidence)",
                    nuance = "မိမိ၏ အရည်အချင်းအပေါ် ယုံကြည်စိတ်ချသော ခံစားချက်",
                    example = "試験に合格する自信がある。",
                    exampleBurmese = "စာမေးပွဲအောင်မည်ဟု ကိုယ့်ကိုယ်ကိုယ် ယုံကြည်မှုရှိသည်။"
                ),
                HomophoneEntry(
                    kanji = "地震",
                    reading = "じしん",
                    burmeseMeaning = "ငလျင်လှုပ်ခြင်း (Earthquake)",
                    nuance = "မြေပြင်တုန်ခါ လှုပ်ခတ်သော သဘာဝဘေးအန္တရာယ်",
                    example = "昨日強い地震があった。",
                    exampleBurmese = "မနေ့က ပြင်းထန်သောငလျင် လှုပ်ခတ်ခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "自身",
                    reading = "じしん",
                    burmeseMeaning = "မိမိကိုယ်တိုင် (Oneself)",
                    nuance = "သူတစ်ပါးမဟုတ်ဘဲ မိမိကိုယ်တိုင်ကိုယ်ကျ ဖြစ်ကြောင်း ညွှန်းဆိုသည်",
                    example = "自分自身の力でやり遂げた。",
                    exampleBurmese = "မိမိကိုယ်တိုင်၏ စွမ်းအားဖြင့် ပြီးမြောက်အောင် ဆောင်ရွက်ခဲ့သည်။"
                )
            )
        ),

        // 6. かんしん
        HomophoneGroup(
            reading = "かんしん",
            title = "かんしん (Interest / Admiration)",
            burmeseCategory = "စိတ်ဝင်စားမှု / ချီးကျူးအထင်ကြီးဖွယ်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "関心",
                    reading = "かんしん",
                    burmeseMeaning = "စိတ်ဝင်စားမှု (Interest)",
                    nuance = "ကိစ္စတစ်ခုခုအပေါ် အာရုံစိုက် စိတ်ဝင်စားသော သဘောထား",
                    example = "最近の政治に高い関心を持つ。",
                    exampleBurmese = "မကြာသေးမီက နိုင်ငံရေးအပေါ် အထူးစိတ်ဝင်စားမှုရှိသည်။"
                ),
                HomophoneEntry(
                    kanji = "感心",
                    reading = "かんしん",
                    burmeseMeaning = "သဘောကျချီးကျူးဖွယ် ဖြစ်ခြင်း (Admiration)",
                    nuance = "သူတစ်ပါး၏ ကောင်းမွန်သောလုပ်ရပ်အပေါ် အထင်ကြီး လေးစားချီးကျူးခြင်း",
                    example = "彼の真面目な態度に感心した。",
                    exampleBurmese = "သူ၏ ရိုးသားကြိုးစားသောသဘောထားကို သဘောကျချီးကျူးမိသည်။"
                )
            )
        ),

        // 7. きかい
        HomophoneGroup(
            reading = "きかい",
            title = "きかい (Opportunity / Machine)",
            burmeseCategory = "အခွင့်အရေး / စက်ပစ္စည်း",
            entries = listOf(
                HomophoneEntry(
                    kanji = "機会",
                    reading = "きかい",
                    burmeseMeaning = "အခွင့်အရေး၊ အခါအခွင့် (Opportunity)",
                    nuance = "အဆင်သင့်ကြုံကြိုက်သော အခွင့်အလမ်း၊ အခိုက်အတန့်",
                    example = "日本へ行く良い機会を得た。",
                    exampleBurmese = "ဂျပန်သို့သွားရောက်ရန် အခွင့်အရေးကောင်း ရရှိခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "機械",
                    reading = "きかい",
                    burmeseMeaning = "စက်ပစ္စည်း၊ စက်ယန္တရား (Machine)",
                    nuance = "စွမ်းအင်သုံး၍ လည်ပတ်သော စက်တပ်ဆင်မှု",
                    example = "工場の新しい機械を操作する。",
                    exampleBurmese = "စက်ရုံ၏ စက်ပစ္စည်းအသစ်ကို မောင်းနှင်လည်ပတ်သည်။"
                )
            )
        ),

        // 8. しめる
        HomophoneGroup(
            reading = "しめる",
            title = "しめる (Close / Fasten / Occupy)",
            burmeseCategory = "ပိတ်သည် / ချည်နှောင်သည် / နေရာယူသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "閉める",
                    reading = "しめる",
                    burmeseMeaning = "တံခါး၊ ပြတင်းပေါက် ပိတ်သည်",
                    nuance = "ဖွင့်ထားသော အပေါက်၊ တံခါးကို ပိတ်ရာတွင် သုံးသည်",
                    example = "寒いので窓を閉めてください。",
                    exampleBurmese = "ချမ်းသဖြင့် ပြတင်းပေါက်ကို ပိတ်ပေးပါ။"
                ),
                HomophoneEntry(
                    kanji = "締める",
                    reading = "しめる",
                    burmeseMeaning = "ကြိုး၊ ခါးပတ်၊ လည်စည်း ချည်နှောင်တင်းကျပ်သည်",
                    nuance = "ကြိုး၊ ခါးပတ် စသည်တို့ကို လျော့မနေစေဘဲ တင်းကျပ်စွာ ချည်နှောင်ရာတွင် သုံးသည်",
                    example = "車のシートベルトを締める。",
                    exampleBurmese = "ကားထိုင်ခုံခါးပတ်ကို ပတ်ချည်သည်။"
                ),
                HomophoneEntry(
                    kanji = "占める",
                    reading = "しめる",
                    burmeseMeaning = "ရာခိုင်နှုန်း၊ နေရာ အချိုးအစား နေရာယူထားသည်",
                    nuance = "စုစုပေါင်းထဲမှ အချိုးအစားတစ်ခုခုကို သိမ်းပိုက်ရယူထားခြင်း",
                    example = "女性の割合が全体の半数を占めている。",
                    exampleBurmese = "အမျိုးသမီးအချိုးအစားမှာ တစ်ခုလုံး၏ ထက်ဝက်ကို နေရာယူထားသည်။"
                )
            )
        ),

        // 9. すすめる
        HomophoneGroup(
            reading = "すすめる",
            title = "すすめる (Advise / Recommend / Advance)",
            burmeseCategory = "တိုက်တွန်းသည် / ထောက်ခံအကြံပြုသည် / ရှေ့သို့တိုးသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "勧める",
                    reading = "すすめる",
                    burmeseMeaning = "တစ်စုံတစ်ခု ပြုလုပ်ရန် တိုက်တွန်းအားပေးသည်",
                    nuance = "အာမခံထားရှိရန်၊ စာဖတ်ရန် စသည်ဖြင့် အခြားသူအား ပြုလုပ်ရန် တိုက်တွန်းခြင်း",
                    example = "先生に読書を勧められた。",
                    exampleBurmese = "ဆရာက စာဖတ်ရန် တိုက်တွန်းအားပေးခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "薦める",
                    reading = "すすめる",
                    burmeseMeaning = "လူ သို့မဟုတ် ကုန်ပစ္စည်းကို ထောက်ခံညွှန်းဆိုသည်",
                    nuance = "ထောက်ခံချက်ပေး၍ အကြံပြုညွှန်းဆိုခြင်း (ဥပမာ- စာအုပ်ကောင်းညွှန်းခြင်း၊ လူရွေးချယ်ရန် ထောက်ခံခြင်း)",
                    example = "友人にこの本を薦めた。",
                    exampleBurmese = "သူငယ်ချင်းအား ဤစာအုပ်ကို အကြံပြုထောက်ခံညွှန်းဆိုခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "進める",
                    reading = "すすめる",
                    burmeseMeaning = "လုပ်ငန်းစဉ်၊ အစီအစဉ်ကို ရှေ့သို့ ဆက်လက်တိုးတက်စေသည်",
                    nuance = "ရှေ့သို့ ရွေ့လျားတိုးတက်အောင် ပြုလုပ်ခြင်း (ဥပမာ- နာရီလက်တံရှေ့ရွှေ့ခြင်း၊ စီမံကိန်းရှေ့ဆက်ခြင်း)",
                    example = "計画を予定通り進める。",
                    exampleBurmese = "စီမံကိန်းကို လျာထားချက်အတိုင်း ရှေ့ဆက်ဆောင်ရွက်သည်။"
                )
            )
        ),

        // 10. たずねる
        HomophoneGroup(
            reading = "たずねる",
            title = "たずねる (Visit / Ask)",
            burmeseCategory = "အလည်သွားသည် / မေးမြန်းစုံစမ်းသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "訪ねる",
                    reading = "たずねる",
                    burmeseMeaning = "တစ်နေရာရာ သို့မဟုတ် လူထံသို့ သွားရောက်လည်ပတ်သည်",
                    nuance = "နေရာ သို့မဟုတ် နေအိမ်သို့ ကိုယ်တိုင်ကိုယ်ကျ သွားရောက်လည်ပတ်တွေ့ဆုံခြင်း",
                    example = "休日に恩師の家を訪ねた。",
                    exampleBurmese = "အားလပ်ရက်တွင် ကျေးဇူးရှင်ဆရာ၏ အိမ်သို့ သွားရောက်လည်ပတ်ခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "尋ねる",
                    reading = "たずねる",
                    burmeseMeaning = "မသိသောအရာကို မေးမြန်းစုံစမ်းသည်",
                    nuance = "မေးခွန်းမေးခြင်း၊ လမ်းမေးခြင်း စသည်ဖြင့် အဖြေသိလို၍ စုံစမ်းမေးမြန်းခြင်း",
                    example = "駅員に道順を尋ねる。",
                    exampleBurmese = "ဘူတာရုံဝန်ထမ်းအား သွားရမည့်လမ်းကြောင်းကို မေးမြန်းသည်။"
                )
            )
        ),

        // 11. うつる
        HomophoneGroup(
            reading = "うつる",
            title = "うつる (Move / Reflect / Photograph)",
            burmeseCategory = "ရွှေ့ပြောင်းသည် / အရိပ်ထင်သည် / ဓာတ်ပုံပေါ်သည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "移る",
                    reading = "うつる",
                    burmeseMeaning = "နေရာ၊ ဌာန၊ အိမ် ပြောင်းရွှေ့သည်",
                    nuance = "တစ်နေရာမှ တစ်နေရာသို့ ရွှေ့ပြောင်းသွားလာခြင်း",
                    example = "東京から大阪へ移る。",
                    exampleBurmese = "တိုကျိုမှ အိုဆာကာသို့ ပြောင်းရွှေ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "映る",
                    reading = "うつる",
                    burmeseMeaning = "မှန် သို့မဟုတ် ရေပြင်တွင် အရိပ်ထင်ဟပ်သည်",
                    nuance = "အလင်းပြန်ခြင်း သို့မဟုတ် ဖန်သားပြင်ပေါ်တွင် ပုံရိပ်ပေါ်ထင်ခြင်း",
                    example = "水面に月が綺麗に映る。",
                    exampleBurmese = "ရေပြင်ထက်တွင် လရိပ် လှပစွာထင်ဟပ်နေသည်။"
                ),
                HomophoneEntry(
                    kanji = "写る",
                    reading = "うつる",
                    burmeseMeaning = "ဓာတ်ပုံ၊ ရုပ်ရှင်ထဲတွင် ထင်ရှားစွာ ပေါ်သည်",
                    nuance = "ကင်မရာ သို့မဟုတ် မှတ်တမ်းထဲတွင် ပုံရိပ်ဖမ်းယူရရှိခြင်း",
                    example = "このカメラは暗い所でもよく写る。",
                    exampleBurmese = "ဤကင်မရာသည် မှောင်သောနေရာ၌ပင် ပုံကောင်းစွာပေါ်သည်။"
                )
            )
        ),

        // 12. あう
        HomophoneGroup(
            reading = "あう",
            title = "あう (Meet / Match / Encounter Accident)",
            burmeseCategory = "တွေ့ဆုံသည် / ကိုက်ညီသည် / မတော်တဆကြုံတွေ့ရသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "会う",
                    reading = "あう",
                    burmeseMeaning = "လူနှင့် တွေ့ဆုံသည်",
                    nuance = "လူတစ်ဦးနှင့်တစ်ဦး လူချင်းမျက်နှာချင်းဆိုင် တွေ့ဆုံခြင်း",
                    example = "明日久しぶりに友達と会う。",
                    exampleBurmese = "မနက်ဖြန် မတွေ့ရသည်မှာကြာပြီဖြစ်သော သူငယ်ချင်းနှင့် တွေ့ဆုံမည်။"
                ),
                HomophoneEntry(
                    kanji = "合う",
                    reading = "あう",
                    burmeseMeaning = "အရွယ်အစား၊ စိတ်သဘော ကိုက်ညီသည်",
                    nuance = "တူညီကိုက်ညီခြင်း၊ သဟဇာတဖြစ်ခြင်း (ဥပမာ- အဝတ်အစားတော်သည်)",
                    example = "この靴はサイズがぴったり合う。",
                    exampleBurmese = "ဤဖိနပ်သည် အရွယ်အစား ကွက်တိကိုက်ညီသည်။"
                ),
                HomophoneEntry(
                    kanji = "遭う",
                    reading = "あう",
                    burmeseMeaning = "မတော်တဆမှု၊ ဘေးဒုက္ခနှင့် ကြုံတွေ့ရသည်",
                    nuance = "မလိုလားအပ်သော အန္တရာယ်၊ မတော်တဆမှု (accident/disaster) ကြုံတွေ့ရခြင်း",
                    example = "旅行中に思わぬ事故に遭った。",
                    exampleBurmese = "ခရီးသွားနေစဉ် မထင်မှတ်ထားသော မတော်တဆမှုနှင့် ကြုံတွေ့ခဲ့ရသည်။"
                )
            )
        ),

        // 13. おさめる
        HomophoneGroup(
            reading = "おさめる",
            title = "おさめる (Obtain / Pay / Govern)",
            burmeseCategory = "ရယူသိမ်းဆည်းသည် / ပေးသွင်းသည် / အုပ်ချုပ်သည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "収める",
                    reading = "おさめる",
                    burmeseMeaning = "အောင်မြင်မှု၊ ရလဒ် သိမ်းဆည်းရယူသည်",
                    nuance = "ရလဒ်ကောင်း၊ အောင်မြင်မှုများကို မိမိလက်ထဲ သိမ်းဆည်းရရှိခြင်း",
                    example = "努力の末に大成功を収めた。",
                    exampleBurmese = "ကြိုးစားမှုအဆုံးတွင် ကြီးမားသောအကြံအောင်မြင်မှု ရရှိခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "納める",
                    reading = "おさめる",
                    burmeseMeaning = "အခွန်၊ အခကြေးငွေ ပေးသွင်းသည်",
                    nuance = "ပေးဆောင်ရမည့် ငွေကြေး သို့မဟုတ် ပစ္စည်းကို သက်ဆိုင်ရာသို့ ပေးသွင်းခြင်း",
                    example = "期限までに税金を納める。",
                    exampleBurmese = "သတ်မှတ်ရက်မတိုင်မီ အခွန်ပေးသွင်းသည်။"
                ),
                HomophoneEntry(
                    kanji = "治める",
                    reading = "おさめる",
                    burmeseMeaning = "တိုင်းပြည်၊ ဒေသကို အေးချမ်းစွာ အုပ်ချုပ်သည်",
                    nuance = "အချုပ်အခြာအာဏာဖြင့် ငြိမ်းချမ်းအောင် စီရင်အုပ်ချုပ်ခြင်း",
                    example = "王が国を平和に治めた。",
                    exampleBurmese = "ဘုရင်က တိုင်းပြည်ကို ငြိမ်းချမ်းစွာ အုပ်ချုပ်ခဲ့သည်။"
                )
            )
        ),

        // 14. こす
        HomophoneGroup(
            reading = "こす",
            title = "こす (Cross Over / Exceed)",
            burmeseCategory = "ဖြတ်ကျော်သည် / ကိန်းဂဏန်းကျော်လွန်သည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "越す",
                    reading = "こす",
                    burmeseMeaning = "တောင်၊ နယ်စပ်ကို ဖြတ်ကျော်သည်",
                    nuance = "နေရာအတားအဆီး သို့မဟုတ် အချိန်ကာလကို ကျော်ဖြတ်ခြင်း (ဥပမာ- နှစ်သစ်ကူးသည်)",
                    example = "険しい山を越して町へ行く。",
                    exampleBurmese = "မတ်စောက်သောတောင်ကို ဖြတ်ကျော်ပြီး မြို့သို့သွားသည်။"
                ),
                HomophoneEntry(
                    kanji = "超す",
                    reading = "こす",
                    burmeseMeaning = "သတ်မှတ်ကိန်းဂဏန်း၊ စံနှုန်းထက် ကျော်လွန်သည်",
                    nuance = "အရေအတွက်၊ အတိုင်းအတာ၊ ကန့်သတ်ချက်ထက် သာလွန်ကျော်လွန်ခြင်း",
                    example = "参加者が100人を超した。",
                    exampleBurmese = "ပါဝင်တက်ရောက်သူဦးရေ ၁၀၀ ကျော်လွန်သွားခဲ့သည်။"
                )
            )
        ),

        // 15. せいさく
        HomophoneGroup(
            reading = "せいさく",
            title = "せいさく (Artwork / Product / Policy)",
            burmeseCategory = "အနုပညာဖန်တီးခြင်း / ပစ္စည်းထုတ်လုပ်ခြင်း / မူဝါဒ",
            entries = listOf(
                HomophoneEntry(
                    kanji = "制作",
                    reading = "せいさく",
                    burmeseMeaning = "အနုပညာလက်ရာ၊ ရုပ်ရှင်၊ ပန်းချီ ဖန်တီးပြုလုပ်ခြင်း",
                    nuance = "စိတ်ကူးစိတ်သန်းသုံး၍ အနုပညာလက်ရာ (art, movie, animation) ဖန်တီးခြင်း",
                    example = "新しいアニメを制作する。",
                    exampleBurmese = "အန်နီမေးရှင်းအသစ်တစ်ခု ဖန်တီးထုတ်လုပ်သည်။"
                ),
                HomophoneEntry(
                    kanji = "製作",
                    reading = "せいさく",
                    burmeseMeaning = "စက်မှုပစ္စည်း၊ လက်တွေ့သုံးပစ္စည်း ထုတ်လုပ်ခြင်း",
                    nuance = "စက်ရုံတွင် ပစ္စည်းကိရိယာ၊ ကုန်ပစ္စည်း (manufacturing) ထုတ်လုပ်ခြင်း",
                    example = "工場で精密機器を製作する。",
                    exampleBurmese = "စက်ရုံတွင် တိကျသောအဆင့်မြင့်စက်ကိရိယာများကို ထုတ်လုပ်သည်။"
                ),
                HomophoneEntry(
                    kanji = "政策",
                    reading = "せいさく",
                    burmeseMeaning = "အစိုးရမူဝါဒ၊ နိုင်ငံရေးမူဝါဒ (Policy)",
                    nuance = "တိုင်းပြည် သို့မဟုတ် အဖွဲ့အစည်း၏ စီမံခန့်ခွဲရေးဆိုင်ရာ မူဝါဒလမ်းစဉ်",
                    example = "政府が新しい経済政策を発表した。",
                    exampleBurmese = "အစိုးရက စီးပွားရေးမူဝါဒအသစ်ကို ကြေညာခဲ့သည်။"
                )
            )
        ),

        // 16. そうぞう
        HomophoneGroup(
            reading = "そうぞう",
            title = "そうぞう (Imagination / Creation)",
            burmeseCategory = "စိတ်ကူးမှန်းဆခြင်း / အသစ်တီထွင်ခြင်း",
            entries = listOf(
                HomophoneEntry(
                    kanji = "想像",
                    reading = "そうぞう",
                    burmeseMeaning = "စိတ်ကူးစိတ်သန်း၊ မှန်းဆခြင်း (Imagination)",
                    nuance = "လက်တွေ့မမြင်ရသေးသောအရာကို စိတ်ထဲတွင် ပုံဖော်တွေးတောခြင်း",
                    example = "未来の生活を想像する。",
                    exampleBurmese = "အနာဂတ်လူနေမှုဘဝကို စိတ်ကူးပုံဖော်ကြည့်သည်။"
                ),
                HomophoneEntry(
                    kanji = "創造",
                    reading = "そうぞう",
                    burmeseMeaning = "အသစ်အဆန်း တီထွင်ဖန်တီးခြင်း (Creation)",
                    nuance = "ယခင်ကမရှိခဲ့ဖူးသော အရာတစ်ခုကို အသစ်အဆန်းအဖြစ် ဖန်တီးပြုလုပ်ခြင်း",
                    example = "新しい価値を創造する企業。",
                    exampleBurmese = "တန်ဖိုးအသစ်များကို တီထွင်ဖန်တီးပေးသော ကုမ္ပဏီ။"
                )
            )
        ),

        // 17. たいしょう
        HomophoneGroup(
            reading = "たいしょう",
            title = "たいしょう (Target / Symmetry / Contrast)",
            burmeseCategory = "ဦးတည်ပစ်မှတ် / အချိုးညီခြင်း / နှိုင်းယှဉ်ယှဉ်တွဲပြခြင်း",
            entries = listOf(
                HomophoneEntry(
                    kanji = "対象",
                    reading = "たいしょう",
                    burmeseMeaning = "ဦးတည်ရာပစ်မှတ်၊ သတ်မှတ်ခံရသူ (Target)",
                    nuance = "စစ်တမ်း သို့မဟုတ် စည်းမျဉ်း၏ အကျုံးဝင်သော ဦးတည်ပစ်မှတ်",
                    example = "この奨学金は留学生が対象だ。",
                    exampleBurmese = "ဤပညာသင်ဆုသည် နိုင်ငံတကာကျောင်းသားများကို ဦးတည်သည်။"
                ),
                HomophoneEntry(
                    kanji = "対称",
                    reading = "たいしょう",
                    burmeseMeaning = "ဘယ်ညာအချိုးညီခြင်း (Symmetry)",
                    nuance = "ဗဟိုမျဉ်းကို အခြေပြု၍ နှစ်ဖက်စလုံး တူညီသောအချိုးအစားဖြစ်ခြင်း",
                    example = "左右対称の美しいデザイン。",
                    exampleBurmese = "ဘယ်ညာအချိုးညီသော လှပသည့်ဒီဇိုင်း။"
                ),
                HomophoneEntry(
                    kanji = "対照",
                    reading = "たいしょう",
                    burmeseMeaning = "နှိုင်းယှဉ်ယှဉ်တွဲပြခြင်း (Contrast)",
                    nuance = "ခြားနားချက်ထင်ရှားစေရန် အရာနှစ်ခုကို နှိုင်းယှဉ်ပြသခြင်း",
                    example = "二人の性格は実に対照的だ。",
                    exampleBurmese = "ထိုနှစ်ဦး၏ စိတ်နေစိတ်ထားမှာ အမှန်ပင် ဆန့်ကျင်ဘက်ဖြစ်နေသည်။"
                )
            )
        ),

        // 18. こうか
        HomophoneGroup(
            reading = "こうか",
            title = "こうか (Effect / Coin / Expensive)",
            burmeseCategory = "အကျိုးသက်ရောက်မှု / အကြွေစေ့ / တန်ဖိုးကြီးခြင်း",
            entries = listOf(
                HomophoneEntry(
                    kanji = "効果",
                    reading = "こうか",
                    burmeseMeaning = "အကျိုးသက်ရောက်မှု၊ အာနိသင် (Effect)",
                    nuance = "ဆေးဝါး သို့မဟုတ် လုပ်ဆောင်ချက်တစ်ခုမှ ရရှိလာသော အကျိုးရလဒ်",
                    example = "この薬は効果が高い。",
                    exampleBurmese = "ဤဆေးသည် အာနိသင်အကျိုးသက်ရောက်မှု အလွန်မြင့်မားသည်။"
                ),
                HomophoneEntry(
                    kanji = "硬貨",
                    reading = "こうか",
                    burmeseMeaning = "သတ္တုဒင်္ဂါးပြား၊ အကြွေစေ့ (Coin)",
                    nuance = "စက္ကူငွေမဟုတ်သော သတ္တုငွေကြေးဒင်္ဂါးပြား",
                    example = "自動販売機に硬貨を入れる。",
                    exampleBurmese = "အလိုအလျောက်အရောင်းစက်ထဲသို့ အကြွေစေ့ထည့်သည်။"
                ),
                HomophoneEntry(
                    kanji = "高価",
                    reading = "こうか",
                    burmeseMeaning = "တန်ဖိုးကြီးမြင့်သော၊ စျေးကြီးသော (Expensive)",
                    nuance = "ကုန်ကျစရိတ် သို့မဟုတ် တန်ဖိုးအလွန်ကြီးမားခြင်း",
                    example = "高価な腕時計を購入した。",
                    exampleBurmese = "တန်ဖိုးကြီးမားသော လက်ပတ်နာရီကို ဝယ်ယူခဲ့သည်။"
                )
            )
        ),

        // 19. かくしん
        HomophoneGroup(
            reading = "かくしん",
            title = "かくしん (Conviction / Core / Innovation)",
            burmeseCategory = "ခိုင်မာသောယုံကြည်ချက် / အဓိကအချက်အချာ / ဆန်းသစ်တီထွင်မှု",
            entries = listOf(
                HomophoneEntry(
                    kanji = "確信",
                    reading = "かくしん",
                    burmeseMeaning = "ခိုင်မာစွာ ယုံကြည်ခြင်း (Conviction)",
                    nuance = "သံသယအလျဉ်းမရှိဘဲ လုံးဝယုံကြည်စိတ်ချခြင်း",
                    example = "チームの勝利を確信している。",
                    exampleBurmese = "မိမိအသင်း အနိုင်ရမည်ဟု အပြည့်အဝယုံကြည်ထားသည်။"
                ),
                HomophoneEntry(
                    kanji = "核心",
                    reading = "かくしん",
                    burmeseMeaning = "ပြဿနာ၏ အဓိကဗဟိုအချက်အချာ (Core)",
                    nuance = "ကိစ္စတစ်ခု၏ အရေးအကြီးဆုံးသော ဗဟိုအချက်",
                    example = "議論が核心に触れる。",
                    exampleBurmese = "ဆွေးနွေးမှုသည် အဓိကအချက်အချာသို့ ရောက်ရှိသွားသည်။"
                ),
                HomophoneEntry(
                    kanji = "革新",
                    reading = "かくしん",
                    burmeseMeaning = "ဆန်းသစ်တီထွင် ပြုပြင်ပြောင်းလဲခြင်း (Innovation)",
                    nuance = "ဟောင်းနွမ်းသောအရာကို စွန့်ခွာ၍ အသစ်စက်စက် တိုးတက်စေခြင်း",
                    example = "技術革新で暮らしが便利になる。",
                    exampleBurmese = "နည်းပညာဆန်းသစ်မှုကြောင့် လူနေမှုဘဝ ပိုမိုအဆင်ပြေလာသည်။"
                )
            )
        ),

        // 20. いがい
        HomophoneGroup(
            reading = "いがい",
            title = "いがい (Unexpected / Except)",
            burmeseCategory = "မထင်မှတ်သော / မှလွဲ၍",
            entries = listOf(
                HomophoneEntry(
                    kanji = "意外",
                    reading = "いがい",
                    burmeseMeaning = "မထင်မှတ်သော၊ အံ့အားသင့်ဖွယ် (Unexpected)",
                    nuance = "ထင်ထားသည်နှင့် လုံးဝမတူဘဲ မမျှော်လင့်ဘဲ ဖြစ်ပေါ်လာသောအရာ",
                    example = "意外な事実が判明した。",
                    exampleBurmese = "မထင်မှတ်ထားသော အမှန်တရားတစ်ခု ပေါ်ပေါက်လာခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "以外",
                    reading = "いがい",
                    burmeseMeaning = "မှလွဲ၍၊ အပ (Except)",
                    nuance = "ဖော်ပြပါအရာမှအပ ကျန်ရှိသောအရာများကို ညွှန်းဆိုသည်",
                    example = "日曜以外は毎日仕事がある。",
                    exampleBurmese = "တနင်္ဂနွေနေ့မှလွဲ၍ နေ့တိုင်း အလုပ်ရှိသည်။"
                )
            )
        ),

        // 21. きく
        HomophoneGroup(
            reading = "きく",
            title = "きく (Hear / Effective / Function)",
            burmeseCategory = "နားထောင်သည် / ဆေးအာနိသင်ထိရောက်သည် / လုပ်ဆောင်နိုင်သည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "聞く",
                    reading = "きく",
                    burmeseMeaning = "အသံကို နားထောင်သည်၊ မေးမြန်းသည်",
                    nuance = "နားဖြင့် အသံကို ကြားနာနားထောင်ခြင်း",
                    example = "音楽を聴く／聞く。",
                    exampleBurmese = "သီချင်းနားထောင်သည်။"
                ),
                HomophoneEntry(
                    kanji = "効く",
                    reading = "きく",
                    burmeseMeaning = "ဆေးဝါးအာနိသင် ထိရောက်သည် (Effective)",
                    nuance = "ဆေး သို့မဟုတ် ကုသမှုကြောင့် ရောဂါသက်သာသွားခြင်း",
                    example = "頭痛薬がよく効いた。",
                    exampleBurmese = "ခေါင်းကိုက်ပျောက်ဆေး အာနိသင်ကောင်းစွာ ထိရောက်ခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "利く",
                    reading = "きく",
                    burmeseMeaning = "စွမ်းဆောင်နိုင်စွမ်းရှိသည်၊ ဘရိတ်ပေါက်သည်",
                    nuance = "လုပ်ဆောင်ချက် ပုံမှန်အလုပ်လုပ်နိုင်စွမ်းရှိခြင်း (ဥပမာ- ဘရိတ်မိသည်)",
                    example = "車のブレーキがよく利く。",
                    exampleBurmese = "ကားဘရိတ် ကောင်းစွာမိသည်။"
                )
            )
        ),

        // 22. とる
        HomophoneGroup(
            reading = "とる",
            title = "とる (Take / Photograph / Employ / Catch)",
            burmeseCategory = "ယူသည် / ဓာတ်ပုံရိုက်သည် / ရွေးချယ်ခန့်ထားသည် / ဖမ်းဆီးသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "取る",
                    reading = "とる",
                    burmeseMeaning = "လက်ဖြင့် ယူသည်၊ ရရှိသည်",
                    nuance = "ပစ္စည်းတစ်ခုကို လက်ဖြင့်ယူခြင်း သို့မဟုတ် အမှတ်ရယူခြင်း",
                    example = "手に本を取って読む。",
                    exampleBurmese = "စာအုပ်ကို လက်ထဲယူ၍ ဖတ်သည်။"
                ),
                HomophoneEntry(
                    kanji = "撮る",
                    reading = "とる",
                    burmeseMeaning = "ဓာတ်ပုံ၊ ရုပ်ရှင် ရိုက်ကူးသည်",
                    nuance = "ကင်မရာသုံး၍ ပုံရိပ်ဖမ်းယူရိုက်ကူးခြင်း",
                    example = "記念にみんなで写真を撮る。",
                    exampleBurmese = "အမှတ်တရအဖြစ် အားလုံးဓာတ်ပုံရိုက်ကြသည်။"
                ),
                HomophoneEntry(
                    kanji = "採る",
                    reading = "とる",
                    burmeseMeaning = "လူရွေးချယ်ခန့်ထားသည်၊ ရွေးချယ်ကျင့်သုံးသည်",
                    nuance = "ဝန်ထမ်းသစ်ရွေးချယ်ခြင်း သို့မဟုတ် နည်းလမ်းတစ်ခုကို ရွေးချယ်အသုံးချခြင်း",
                    example = "今年の新入社員を多く採る。",
                    exampleBurmese = "ယခုနှစ်တွင် ဝန်ထမ်းအသစ် အများအပြား ခန့်ထားသည်။"
                ),
                HomophoneEntry(
                    kanji = "捕る",
                    reading = "とる",
                    burmeseMeaning = "ငါး၊ အင်းဆက်၊ တိရစ္ဆာန်ကို ဖမ်းဆီးသည်",
                    nuance = "လှုပ်ရှားနေသော သတ္တဝါကို လိုက်လံဖမ်းယူခြင်း",
                    example = "川で魚を捕る。",
                    exampleBurmese = "မြစ်ထဲတွင် ငါးဖမ်းသည်။"
                )
            )
        ),

        // 23. かわく
        HomophoneGroup(
            reading = "かわく",
            title = "かわく (Dry / Thirsty)",
            burmeseCategory = "ခြောက်သွေ့သည် / ရေဆာလောင်သည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "乾く",
                    reading = "かわく",
                    burmeseMeaning = "အဝတ်အထည်၊ လေထု အစိုဓာတ်ခန်းခြောက်သည်",
                    nuance = "ရေငွေ့ သို့မဟုတ် အစိုဓာတ် ကုန်ဆုံးသွားခြင်း",
                    example = "天気が良くて洗濯物がよく乾く。",
                    exampleBurmese = "ရာသီဥတုသာယာ၍ လျှော်ထားသောအဝတ်များ ကောင်းစွာခြောက်သည်။"
                ),
                HomophoneEntry(
                    kanji = "渇く",
                    reading = "かわく",
                    burmeseMeaning = "လည်ချောင်း၊ ရေငတ်မွတ်သည် (Thirsty)",
                    nuance = "ရေသောက်လိုသော ဆာလောင်ငတ်မွတ်မှု ခံစားရခြင်း",
                    example = "運動して喉がカラカラに渇いた。",
                    exampleBurmese = "အားကစားလုပ်ပြီး လည်ချောင်း အလွန်ရေငတ်ခဲ့သည်။"
                )
            )
        ),

        // 24. にる
        HomophoneGroup(
            reading = "にる",
            title = "にる (Resemble / Boil)",
            burmeseCategory = "ဆင်တူသည် / ဟင်းလျာပြုတ်သည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "似る",
                    reading = "にる",
                    burmeseMeaning = "ရုပ်ရည်၊ သွင်ပြင် ဆင်တူသည် (Resemble)",
                    nuance = "မိဘ သို့မဟုတ် သူတစ်ပါးနှင့် ပုံပန်းသဏ္ဌာန် တူညီခြင်း",
                    example = "息子は父親によく似ている。",
                    exampleBurmese = "သားဖြစ်သူသည် ဖခင်နှင့် အလွန်တူသည်။"
                ),
                HomophoneEntry(
                    kanji = "煮る",
                    reading = "にる",
                    burmeseMeaning = "အရည်ထဲတွင် ဟင်းလျာ၊ အသား ပြုတ်သည်/ချက်သည်",
                    nuance = "ရေနွေး သို့မဟုတ် အရည်ထဲတွင် အပူပေး၍ ပြုတ်ချက်ခြင်း",
                    example = "鍋で野菜と肉を煮る。",
                    exampleBurmese = "အိုးထဲတွင် အသီးအရွက်နှင့် အသားကို ပြုတ်ချက်သည်။"
                )
            )
        ),

        // 25. ひく
        HomophoneGroup(
            reading = "ひく",
            title = "ひく (Pull / Play Music)",
            burmeseCategory = "ဆွဲသည်၊နှုတ်သည် / တူရိယာတီးခတ်သည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "引く",
                    reading = "ひく",
                    burmeseMeaning = "ဆွဲသည်၊ မျဉ်းဆွဲသည်၊ အဘိဓာန်လှန်သည်",
                    nuance = "အရာဝတ္ထုကို မိမိဘက်သို့ ဆွဲငင်ခြင်း သို့မဟုတ် စာရင်းနှုတ်ခြင်း",
                    example = "ドアを手前に引く／辞書を引く。",
                    exampleBurmese = "တံခါးကို မိမိဘက်သို့ဆွဲသည် / အဘိဓာန်လှန်သည်။"
                ),
                HomophoneEntry(
                    kanji = "弾く",
                    reading = "ひく",
                    burmeseMeaning = "စန္ဒရား၊ ဂစ်တာ တီးခတ်သည် (Play Music)",
                    nuance = "ကြိုးတပ်တူရိယာ သို့မဟုတ် ခလုတ်တပ်တူရိယာကို လက်ချောင်းဖြင့် တီးခတ်ခြင်း",
                    example = "休日にピアノを弾くのが好きだ。",
                    exampleBurmese = "အားလပ်ရက်တွင် စန္ဒရားတီးခတ်ရသည်ကို နှစ်သက်သည်။"
                )
            )
        ),

        // 26. とける
        HomophoneGroup(
            reading = "とける",
            title = "とける (Solve / Melt)",
            burmeseCategory = "ပုစ္ဆာပြေလည်သည် / အရည်ပျော်သည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "解ける",
                    reading = "とける",
                    burmeseMeaning = "ပုစ္ဆာ၊ နားမလည်သောပြဿနာ ပြေလည်သည်",
                    nuance = "ခက်ခဲသော မေးခွန်း သို့မဟုတ် သံသယ ရှင်းလင်းပြေလည်သွားခြင်း",
                    example = "難しかった数学の問題が解けた。",
                    exampleBurmese = "ခက်ခဲခဲ့သော သင်္ချာပုစ္ဆာ ပြေလည်သွားခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "溶ける",
                    reading = "とける",
                    burmeseMeaning = "ရေခဲ၊ သကြား အရည်ပျော်သွားသည် (Melt)",
                    nuance = "အစိုင်အခဲမှ အရည်အဖြစ်သို့ ပြောင်းလဲပျော်ဝင်ခြင်း",
                    example = "暖かい日差しで雪が溶けた。",
                    exampleBurmese = "နွေးထွေးသောနေရောင်ခြည်ကြောင့် နှင်းများ အရည်ပျော်သွားခဲ့သည်။"
                )
            )
        ),

        // 27. あらわれる
        HomophoneGroup(
            reading = "あらわれる",
            title = "あらわれる (Appear / Reveal Emotion)",
            burmeseCategory = "ပေါ်လာသည် / စိတ်ခံစားချက်ပေါ်လွင်သည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "現れる",
                    reading = "あらわれる",
                    burmeseMeaning = "ကိုယ်ထင်ပြပေါ်လာသည်၊ ရှေ့မှောက်ရောက်လာသည်",
                    nuance = "မမြင်ရရာမှ မျက်စိရှေ့သို့ လူ သို့မဟုတ် အရာဝတ္ထု ပေါ်ထွက်လာခြင်း",
                    example = "霧の向こうから船が現れた。",
                    exampleBurmese = "မြူခိုးများနောက်ကွယ်မှ သင်္ဘောတစ်စင်း ပေါ်ထွက်လာခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "表れる",
                    reading = "あらわれる",
                    burmeseMeaning = "မျက်နှာ သို့မဟုတ် သဘောထားတွင် ခံစားချက် ပေါ်လွင်သည်",
                    nuance = "စိတ်ခံစားချက် သို့မဟုတ် သဘောထား အပြင်ပန်းတွင် ပေါ်လွင်ထင်ရှားလာခြင်း",
                    example = "彼の言葉に喜びが表れている。",
                    exampleBurmese = "သူ၏ စကားလုံးများတွင် ဝမ်းသာပျော်ရွှင်မှု ပေါ်လွင်နေသည်။"
                )
            )
        ),

        // 28. つく
        HomophoneGroup(
            reading = "つく",
            title = "つく (Arrive / Attach / Stab)",
            burmeseCategory = "ရောက်ရှိသည် / ကပ်ညှိသည် / ထိုးသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "着く",
                    reading = "つく",
                    burmeseMeaning = "ခရီးဆုံးသို့ ရောက်ရှိသည် (Arrive)",
                    nuance = "ဦးတည်ရာနေရာသို့ အရောက်လှမ်းခြင်း",
                    example = "定刻通りに駅に着いた。",
                    exampleBurmese = "သတ်မှတ်အချိန်အတိုင်း ဘူတာရုံသို့ ရောက်ရှိခဲ့သည်။"
                ),
                HomophoneEntry(
                    kanji = "付く",
                    reading = "つく",
                    burmeseMeaning = "ကပ်ညှိသည်၊ တွဲဆက်သည်၊ အစွန်းအထင်းပေသည်",
                    nuance = "အရာဝတ္ထုတစ်ခုပေါ်တွင် အခြားတစ်ခု ကပ်ငြိနေခြင်း",
                    example = "服にコーヒーの染みが付いた。",
                    exampleBurmese = "အဝတ်တွင် ကော်ဖီစွန်းကွက် ပေသွားသည်။"
                ),
                HomophoneEntry(
                    kanji = "突く",
                    reading = "つく",
                    burmeseMeaning = "တုတ်ထောက်သည်၊ တံတောင်ဖြင့်ထိုးသည်",
                    nuance = "ချွန်ထက်သောအရာ သို့မဟုတ် အားဖြင့် ထိုးနှက်ထောက်ကန်ခြင်း",
                    example = "杖を突いてゆっくり歩く。",
                    exampleBurmese = "တောင်ဝှေးထောက်ပြီး ဖြည်းဖြည်းချင်း လမ်းလျှောက်သည်။"
                )
            )
        ),

        // 29. とめる
        HomophoneGroup(
            reading = "とめる",
            title = "とめる (Stop / Lodge)",
            burmeseCategory = "ရပ်တန့်သည် / တည်းခိုစေသည်",
            entries = listOf(
                HomophoneEntry(
                    kanji = "止める",
                    reading = "とめる",
                    burmeseMeaning = "လှုပ်ရှားမှုကို ရပ်တန့်သည်၊ ကားရပ်သည်",
                    nuance = "ရွေ့လျားနေသောအရာကို မရွေ့အောင် ရပ်တန့်စေခြင်း",
                    example = "駐車禁止の場所に車を止めるな。",
                    exampleBurmese = "ကားရပ်နားခွင့်မရှိသောနေရာတွင် ကားမရပ်ပါနှင့်။"
                ),
                HomophoneEntry(
                    kanji = "泊める",
                    reading = "とめる",
                    burmeseMeaning = "ဧည့်သည်ကို အိမ်တွင် တည်းခိုခွင့်ပြုသည်",
                    nuance = "အခြားသူအား ညအိပ်တည်းခိုရန် နေရာပေးခြင်း",
                    example = "泊まる場所がない友人を家に泊める。",
                    exampleBurmese = "တည်းခိုစရာနေရာမရှိသော သူငယ်ချင်းအား အိမ်တွင် တည်းခိုစေသည်။"
                )
            )
        ),

        // 30. いどう
        HomophoneGroup(
            reading = "いどう",
            title = "いどう (Movement / Personnel Change)",
            burmeseCategory = "ရွှေ့ပြောင်းခြင်း / ရာထူးဌာနအပြောင်းအလဲ",
            entries = listOf(
                HomophoneEntry(
                    kanji = "移動",
                    reading = "いどう",
                    burmeseMeaning = "တစ်နေရာမှ တစ်နေရာသို့ ရွှေ့ပြောင်းခြင်း (Movement)",
                    nuance = "ရုပ်ပိုင်းဆိုင်ရာအရ တစ်နေရာမှ တစ်နေရာသို့ သွားလာရွှေ့ပြောင်းခြင်း",
                    example = "電車で次の目的地へ移動する。",
                    exampleBurmese = "ရထားဖြင့် နောက်ထပ်ဦးတည်ရာနေရာသို့ ရွှေ့ပြောင်းသွားရောက်သည်။"
                ),
                HomophoneEntry(
                    kanji = "異動",
                    reading = "いどう",
                    burmeseMeaning = "ကုမ္ပဏီအတွင်း ရာထူး၊ ဌာန အပြောင်းအလဲ (Transfer)",
                    nuance = "ဝန်ထမ်းများ၏ လုပ်ငန်းခွင်ဌာန သို့မဟုတ် တာဝန်နေရာ အပြောင်းအလဲဖြစ်ခြင်း",
                    example = "来月から営業部への人事異動が決まった。",
                    exampleBurmese = "နောက်လမှစ၍ အရောင်းဌာနသို့ ဝန်ထမ်းအပြောင်းအလဲ သတ်မှတ်လိုက်သည်။"
                )
            )
        )
    )

    /**
     * Finds a curated homophone group matching the given reading or kanji.
     */
    fun findCuratedGroup(card: VocabCard): HomophoneGroup? {
        return JLPT_N3_HOMOPHONES.firstOrNull { group ->
            group.reading == card.reading || group.entries.any { it.kanji == card.kanji }
        }
    }

    /**
     * Builds comparison text summarizing all homophones in the group.
     */
    fun formatComparisonText(group: HomophoneGroup): String {
        val sb = StringBuilder()
        sb.append("💡 同音語・ဆင်တူရိုးမှား နှိုင်းယှဉ်ချက် 【${group.reading}】:\n")
        group.entries.forEach { entry ->
            sb.append("• ${entry.kanji} (${entry.reading}): ${entry.burmeseMeaning}\n  ↳ ${entry.nuance}\n")
        }
        return sb.toString().trimEnd()
    }
}
