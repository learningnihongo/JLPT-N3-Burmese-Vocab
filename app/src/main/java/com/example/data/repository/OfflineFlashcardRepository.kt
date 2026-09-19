package com.example.data.repository

import com.example.data.db.CustomFlashcardDao
import com.example.data.db.KanjiDao
import com.example.data.db.KotobaDao
import com.example.data.model.CustomFlashcardEntity
import com.example.data.model.KanjiEntity
import com.example.data.model.KotobaEntity
import kotlinx.coroutines.flow.Flow

/**
 * Offline Flashcard Repository providing a clean API for CRUD operations
 * on Kanji, Kotoba, and Custom user flashcards stored in local Room Database.
 */
class OfflineFlashcardRepository(
    private val kanjiDao: KanjiDao,
    private val kotobaDao: KotobaDao,
    private val customFlashcardDao: CustomFlashcardDao
) {
    // ====================================================================
    // KANJI CRUD OPERATIONS
    // ====================================================================

    fun getAllKanji(): Flow<List<KanjiEntity>> = kanjiDao.getAllKanji()

    suspend fun getAllKanjiDirect(): List<KanjiEntity> = kanjiDao.getAllKanjiDirect()

    fun getKanjiById(id: Long): Flow<KanjiEntity?> = kanjiDao.getKanjiById(id)

    suspend fun getKanjiByIdDirect(id: Long): KanjiEntity? = kanjiDao.getKanjiByIdDirect(id)

    suspend fun getKanjiByCharacter(character: String): KanjiEntity? = kanjiDao.getKanjiByCharacter(character)

    fun getBookmarkedKanji(): Flow<List<KanjiEntity>> = kanjiDao.getBookmarkedKanji()

    fun searchKanji(query: String): Flow<List<KanjiEntity>> = kanjiDao.searchKanji(query)

    fun getDueKanji(currentTimeMs: Long = System.currentTimeMillis()): Flow<List<KanjiEntity>> =
        kanjiDao.getDueKanji(currentTimeMs)

    suspend fun insertKanji(kanji: KanjiEntity): Long = kanjiDao.insertKanji(kanji)

    suspend fun insertAllKanji(list: List<KanjiEntity>): List<Long> = kanjiDao.insertAllKanji(list)

    suspend fun updateKanji(kanji: KanjiEntity) = kanjiDao.updateKanji(kanji)

    suspend fun setKanjiBookmark(id: Long, isBookmarked: Boolean) = kanjiDao.setBookmark(id, isBookmarked)

    suspend fun updateKanjiNotes(id: Long, notes: String) = kanjiDao.updatePersonalNotes(id, notes)

    suspend fun deleteKanji(kanji: KanjiEntity) = kanjiDao.deleteKanji(kanji)

    suspend fun deleteKanjiById(id: Long): Int = kanjiDao.deleteKanjiById(id)

    // ====================================================================
    // KOTOBA CRUD OPERATIONS
    // ====================================================================

    fun getAllKotoba(): Flow<List<KotobaEntity>> = kotobaDao.getAllKotoba()

    suspend fun getAllKotobaDirect(): List<KotobaEntity> = kotobaDao.getAllKotobaDirect()

    fun getKotobaById(id: Long): Flow<KotobaEntity?> = kotobaDao.getKotobaById(id)

    suspend fun getKotobaByIdDirect(id: Long): KotobaEntity? = kotobaDao.getKotobaByIdDirect(id)

    fun getBookmarkedKotoba(): Flow<List<KotobaEntity>> = kotobaDao.getBookmarkedKotoba()

    fun getKotobaByLesson(lessonNumber: Int): Flow<List<KotobaEntity>> = kotobaDao.getKotobaByLesson(lessonNumber)

    fun getKotobaByCategory(category: String): Flow<List<KotobaEntity>> = kotobaDao.getKotobaByCategory(category)

    fun searchKotoba(query: String): Flow<List<KotobaEntity>> = kotobaDao.searchKotoba(query)

    fun getDueKotoba(currentTimeMs: Long = System.currentTimeMillis()): Flow<List<KotobaEntity>> =
        kotobaDao.getDueKotoba(currentTimeMs)

    suspend fun insertKotoba(kotoba: KotobaEntity): Long = kotobaDao.insertKotoba(kotoba)

    suspend fun insertAllKotoba(list: List<KotobaEntity>): List<Long> = kotobaDao.insertAllKotoba(list)

    suspend fun updateKotoba(kotoba: KotobaEntity) = kotobaDao.updateKotoba(kotoba)

    suspend fun setKotobaBookmark(id: Long, isBookmarked: Boolean) = kotobaDao.setBookmark(id, isBookmarked)

    suspend fun updateKotobaNotes(id: Long, notes: String) = kotobaDao.updatePersonalNotes(id, notes)

    suspend fun deleteKotoba(kotoba: KotobaEntity) = kotobaDao.deleteKotoba(kotoba)

    suspend fun deleteKotobaById(id: Long): Int = kotobaDao.deleteKotobaById(id)

    // ====================================================================
    // CUSTOM USER FLASHCARD CRUD OPERATIONS
    // ====================================================================

    fun getAllCustomCards(): Flow<List<CustomFlashcardEntity>> = customFlashcardDao.getAllCards()

    suspend fun getAllCustomCardsDirect(): List<CustomFlashcardEntity> = customFlashcardDao.getAllCardsDirect()

    fun getCustomCardById(id: Long): Flow<CustomFlashcardEntity?> = customFlashcardDao.getCardById(id)

    suspend fun getCustomCardByIdDirect(id: Long): CustomFlashcardEntity? = customFlashcardDao.getCardByIdDirect(id)

    fun getBookmarkedCustomCards(): Flow<List<CustomFlashcardEntity>> = customFlashcardDao.getBookmarkedCards()

    fun getCustomCardsByCategory(category: String): Flow<List<CustomFlashcardEntity>> =
        customFlashcardDao.getCardsByCategory(category)

    fun getCustomCardsByType(cardType: String): Flow<List<CustomFlashcardEntity>> =
        customFlashcardDao.getCardsByType(cardType)

    fun searchCustomCards(query: String): Flow<List<CustomFlashcardEntity>> = customFlashcardDao.searchCards(query)

    fun getCustomCardsByTag(tag: String): Flow<List<CustomFlashcardEntity>> = customFlashcardDao.getCardsByTag(tag)

    fun getAllCategories(): Flow<List<String>> = customFlashcardDao.getAllCategories()

    fun getDueCustomCards(currentTimeMs: Long = System.currentTimeMillis()): Flow<List<CustomFlashcardEntity>> =
        customFlashcardDao.getDueCards(currentTimeMs)

    fun getTotalCustomCardCount(): Flow<Int> = customFlashcardDao.getTotalCardCount()

    suspend fun insertCustomCard(card: CustomFlashcardEntity): Long = customFlashcardDao.insertCard(card)

    suspend fun insertCustomCards(cards: List<CustomFlashcardEntity>): List<Long> =
        customFlashcardDao.insertCards(cards)

    suspend fun updateCustomCard(card: CustomFlashcardEntity) = customFlashcardDao.updateCard(card)

    suspend fun setCustomCardBookmark(id: Long, isBookmarked: Boolean) =
        customFlashcardDao.setBookmark(id, isBookmarked)

    suspend fun updateCustomCardNotes(id: Long, notes: String) =
        customFlashcardDao.updatePersonalNotes(id, notes)

    suspend fun deleteCustomCard(card: CustomFlashcardEntity) = customFlashcardDao.deleteCard(card)

    suspend fun deleteCustomCardById(id: Long): Int = customFlashcardDao.deleteCardById(id)

    suspend fun deleteAllCustomCards() = customFlashcardDao.deleteAllCards()
}
