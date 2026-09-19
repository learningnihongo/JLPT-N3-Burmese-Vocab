package com.example.data.db

import androidx.room.*
import com.example.data.model.KotobaEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for offline Kotoba (Vocabulary) CRUD operations.
 */
@Dao
interface KotobaDao {

    // ==========================================
    // CREATE (Insert)
    // ==========================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKotoba(kotoba: KotobaEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKotoba(kotobaList: List<KotobaEntity>): List<Long>

    // ==========================================
    // READ (Queries)
    // ==========================================

    @Query("SELECT * FROM kotoba_entries ORDER BY lessonNumber ASC, id ASC")
    fun getAllKotoba(): Flow<List<KotobaEntity>>

    @Query("SELECT * FROM kotoba_entries ORDER BY lessonNumber ASC, id ASC")
    suspend fun getAllKotobaDirect(): List<KotobaEntity>

    @Query("SELECT * FROM kotoba_entries WHERE id = :id LIMIT 1")
    fun getKotobaById(id: Long): Flow<KotobaEntity?>

    @Query("SELECT * FROM kotoba_entries WHERE id = :id LIMIT 1")
    suspend fun getKotobaByIdDirect(id: Long): KotobaEntity?

    @Query("SELECT * FROM kotoba_entries WHERE isBookmarked = 1 ORDER BY id DESC")
    fun getBookmarkedKotoba(): Flow<List<KotobaEntity>>

    @Query("SELECT * FROM kotoba_entries WHERE lessonNumber = :lessonNumber ORDER BY id ASC")
    fun getKotobaByLesson(lessonNumber: Int): Flow<List<KotobaEntity>>

    @Query("SELECT * FROM kotoba_entries WHERE category = :category ORDER BY id ASC")
    fun getKotobaByCategory(category: String): Flow<List<KotobaEntity>>

    @Query("""
        SELECT * FROM kotoba_entries 
        WHERE word LIKE '%' || :query || '%' 
           OR reading LIKE '%' || :query || '%' 
           OR romaji LIKE '%' || :query || '%' 
           OR meaningBurmese LIKE '%' || :query || '%' 
           OR meaningEnglish LIKE '%' || :query || '%'
           OR exampleSentenceJapanese LIKE '%' || :query || '%'
        ORDER BY lessonNumber ASC, id ASC
    """)
    fun searchKotoba(query: String): Flow<List<KotobaEntity>>

    @Query("SELECT * FROM kotoba_entries WHERE nextReviewTimestamp <= :currentTimeMs OR repetitions = 0 ORDER BY nextReviewTimestamp ASC")
    fun getDueKotoba(currentTimeMs: Long): Flow<List<KotobaEntity>>

    @Query("SELECT * FROM kotoba_entries WHERE nextReviewTimestamp <= :currentTimeMs OR repetitions = 0 ORDER BY nextReviewTimestamp ASC")
    suspend fun getDueKotobaDirect(currentTimeMs: Long): List<KotobaEntity>

    @Query("SELECT COUNT(*) FROM kotoba_entries")
    fun getTotalKotobaCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM kotoba_entries")
    suspend fun getTotalKotobaCountDirect(): Int

    // ==========================================
    // UPDATE
    // ==========================================

    @Update
    suspend fun updateKotoba(kotoba: KotobaEntity)

    @Query("UPDATE kotoba_entries SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun setBookmark(id: Long, isBookmarked: Boolean)

    @Query("UPDATE kotoba_entries SET personalNotes = :notes WHERE id = :id")
    suspend fun updatePersonalNotes(id: Long, notes: String)

    @Query("""
        UPDATE kotoba_entries 
        SET repetitions = :repetitions,
            intervalDays = :intervalDays,
            easeFactor = :easeFactor,
            nextReviewTimestamp = :nextReview,
            lastReviewedTimestamp = :lastReviewed,
            masteryLevel = :masteryLevel,
            timesCorrect = timesCorrect + :correctDelta,
            timesIncorrect = timesIncorrect + :incorrectDelta
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
    suspend fun deleteKotoba(kotoba: KotobaEntity)

    @Query("DELETE FROM kotoba_entries WHERE id = :id")
    suspend fun deleteKotobaById(id: Long): Int

    @Query("DELETE FROM kotoba_entries")
    suspend fun deleteAllKotoba()
}
