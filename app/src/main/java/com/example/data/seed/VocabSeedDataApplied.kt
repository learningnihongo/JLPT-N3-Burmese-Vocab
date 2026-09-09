package com.example.data.seed

import com.example.data.model.VocabCard

object VocabSeedDataApplied {
    fun getAllSeedCards(): List<VocabCard> {
        return VocabSeedDataPart1.getCards() +
               VocabSeedDataPart2.getCards() +
               VocabSeedDataPart3.getCards() +
               VocabSeedDataPart4.getCards() +
               VocabSeedDataPart5.getCards() +
               VocabSeedDataPart6.getCards() +
               VocabSeedDataPart7.getCards() +
               VocabSeedDataPart8.getCards() +
               VocabSeedDataPart9.getCards() +
               VocabSeedDataShinkanzen1.getCards() +
               VocabSeedDataShinkanzen2.getCards() +
               VocabSeedDataShinkanzen3.getCards() +
               VocabSeedDataShinkanzen4.getCards() +
               VocabSeedDataShinkanzen5.getCards() +
               VocabSeedDataShinkanzen6.getCards() +
               VocabSeedDataShinkanzen7.getCards() +
               VocabSeedDataShinkanzen8.getCards() +
               VocabSeedDataHnin1.getCards() +
               VocabSeedDataHnin2.getCards() +
               VocabSeedDataHnin3.getCards() +
               VocabSeedDataHnin4.getCards() +
               VocabSeedDataHnin5.getCards() +
               VocabSeedDataHnin6.getCards() +
               VocabSeedDataHnin7.getCards() +
               VocabSeedDataHnin8.getCards() +
               VocabSeedDataHnin9.getCards() +
               VocabSeedDataHnin10.getCards() +
               VocabSeedDataEssential1.getCards() +
               VocabSeedDataEssential2.getCards() +
               VocabSeedDataEssential3.getCards() +
               VocabSeedDataEssential4.getCards() +
               VocabSeedDataEssential5.getCards() +
               VocabSeedDataEssential6.getCards() +
               VocabSeedDataEssential7.getCards() +
               VocabSeedDataEssential8.getCards() +
               VocabSeedDataMastery1.getCards() +
               VocabSeedDataMastery2.getCards() +
               VocabSeedDataMastery3.getCards() +
               VocabSeedDataMastery4.getCards()
    }
}

