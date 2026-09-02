package com.example.data.seed

import com.example.data.model.VocabCard

object VocabSeedDataApplied {
    fun getAllSeedCards(): List<VocabCard> {
        return VocabSeedDataPart1.getCards() +
               VocabSeedDataPart2.getCards() +
               VocabSeedDataPart3A.getCards() +
               VocabSeedDataPart3B.getCards() +
               VocabSeedDataPart4.getCards() +
               VocabSeedDataPart5.getCards() +
               VocabSeedDataPart6A.getCards() +
               VocabSeedDataPart6B.getCards() +
               VocabSeedDataShinkanzen1.getCards() +
               VocabSeedDataShinkanzen2.getCards() +
               VocabSeedDataShinkanzen3.getCards() +
               VocabSeedDataShinkanzen4.getCards() +
               VocabSeedDataExtra1.getCards() +
               VocabSeedDataExtra2.getCards()
    }
}

