package com.example.data.db

import androidx.room.*
import com.example.data.model.CustomFlashcardEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for offline custom user flashcard CRUD operations.
 */
@Dao
interface CustomFlashcardDao {

    // ==========================================
    // CREATE (Insert)
    // ==========================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CustomFlashcardEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<CustomFlashcardEntity>): List<Long>

    // ==========================================
    // READ (Queries)
    // ==========================================

    @Query("SELECT * FROM custom_flashcards ORDER BY updatedAt DESC, id DESC")
    fun getAllCards(): Flow<List<CustomFlashcardEntity>>

    @Query("SELECT * FROM custom_flashcards ORDER BY updatedAt DESC, id DESC")
    suspend fun getAllCardsDirect(): List<CustomFlashcardEntity>

    @Query("SELECT * FROM custom_flashcards WHERE id = :id LIMIT 1")
    fun getCardById(id: Long): Flow<CustomFlashcardEntity?>

    @Query("SELECT * FROM custom_flashcards WHERE id = :id LIMIT 1")
    suspend fun getCardByIdDirect(id: Long): CustomFlashcardEntity?

    @Query("SELECT * FROM custom_flashcards WHERE isBookmarked = 1 ORDER BY updatedAt DESC")
    fun getBookmarkedCards(): Flow<List<CustomFlashcardEntity>>

    @Query("SELECT * FROM custom_flashcards WHERE category = :category ORDER BY id DESC")
    fun getCardsByCategory(category: String): Flow<List<CustomFlashcardEntity>>

    @Query("SELECT * FROM custom_flashcards WHERE cardType = :cardType ORDER BY id DESC")
    fun getCardsByType(cardType: String): Flow<List<CustomFlashcardEntity>>

    @Query("""
        SELECT * FROM custom_flashcards 
        WHERE front LIKE '%' || :query || '%' 
           OR reading LIKE '%' || :query || '%' 
           OR back LIKE '%' || :query || '%' 
           OR exampleSentence LIKE '%' || :query || '%' 
           OR exampleMeaning LIKE '%' || :query || '%' 
           OR tags LIKE '%' || :query || '%'
           OR personalNotes LIKE '%' || :query || '%'
        ORDER BY updatedAt DESC
    """)
    fun searchCards(query: String): Flow<List<CustomFlashcardEntity>>

    @Query("SELECT * FROM custom_flashcards WHERE tags LIKE '%' || :tag || '%' ORDER BY updatedAt DESC")
    fun getCardsByTag(tag: String): Flow<List<CustomFlashcardEntity>>

    @Query("SELECT DISTINCT category FROM custom_flashcards WHERE category != '' ORDER BY category ASC")
    fun getAllCategories(): Flow<List<String>>

    @Query("SELECT * FROM custom_flashcards WHERE nextReviewTimestamp <= :currentTimeMs OR repetitions = 0 ORDER BY nextReviewTimestamp ASC")
    fun getDueCards(currentTimeMs: Long): Flow<List<CustomFlashcardEntity>>

    @Query("SELECT * FROM custom_flashcards WHERE nextReviewTimestamp <= :currentTimeMs OR repetitions = 0 ORDER BY nextReviewTimestamp ASC")
    suspend fun getDueCardsDirect(currentTimeMs: Long): List<CustomFlashcardEntity>

    @Query("SELECT COUNT(*) FROM custom_flashcards")
    fun getTotalCardCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM custom_flashcards")
    suspend fun getTotalCardCountDirect(): Int

    // ==========================================
    // UPDATE
    // ==========================================

    @Update
    suspend fun updateCard(card: CustomFlashcardEntity)

    @Query("UPDATE custom_flashcards SET isBookmarked = :isBookmarked, updatedAt = :updatedAt WHERE id = :id")
    suspend fun setBookmark(id: Long, isBookmarked: Boolean, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE custom_flashcards SET personalNotes = :notes, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updatePersonalNotes(id: Long, notes: String, updatedAt: Long = System.currentTimeMillis())

    @Query("""
        UPDATE custom_flashcards 
        SET repetitions = :repetitions,
            intervalDays = :intervalDays,
            easeFactor = :easeFactor,
            nextReviewTimestamp = :nextReview,
            lastReviewedTimestamp = :lastReviewed,
            masteryLevel = :masteryLevel,
            timesCorrect = timesCorrect + :correctDelta,
            timesIncorrect = timesIncorrect + :incorrectDelta,
            updatedAt = :lastReviewed
        WHERE id = :id
    """)
    suspend fun updateReviewStats(
        id: Long,
        repetitions: Int,
        intervalDays: Int,
        easeFactor: Float,
        nextReview: Long,
        lastReviewed: Long,
        masteryLevel: Int,
        correctDelta: Int,
        incorrectDelta: Int
    )

    // ==========================================
    // DELETE
    // ==========================================

    @Delete
    suspend fun deleteCard(card: CustomFlashcardEntity)

    @Query("DELETE FROM custom_flashcards WHERE id = :id")
    suspend fun deleteCardById(id: Long): Int

    @Query("DELETE FROM custom_flashcards")
    suspend fun deleteAllCards()
}
