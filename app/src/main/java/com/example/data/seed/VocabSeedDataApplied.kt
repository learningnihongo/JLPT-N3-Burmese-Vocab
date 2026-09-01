package com.example.data.seed

import com.example.data.model.VocabCard

object VocabSeedDataApplied {
    fun getAllSeedCards(): List<VocabCard> {
        return VocabSeedData1To300.getCards1To300() + 
               VocabSeedData301To600.getCards301To600() + 
               VocabSeedData601To880.getCards601To880() +
               VocabSeedData901To1101.getCards901To1101()
    }
}
