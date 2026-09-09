package com.example.data.db

import androidx.room.*
import com.example.data.model.KanjiEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for offline Kanji flashcard CRUD operations.
 */
@Dao
interface KanjiDao {

    // ==========================================
    // CREATE (Insert)
    // ==========================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKanji(kanji: KanjiEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKanji(kanjiList: List<KanjiEntity>): List<Long>

    // ==========================================
    // READ (Queries)
    // ==========================================

    @Query("SELECT * FROM kanji_entries ORDER BY id ASC")
    fun getAllKanji(): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM kanji_entries ORDER BY id ASC")
    suspend fun getAllKanjiDirect(): List<KanjiEntity>

    @Query("SELECT * FROM kanji_entries WHERE id = :id LIMIT 1")
    fun getKanjiById(id: Long): Flow<KanjiEntity?>

    @Query("SELECT * FROM kanji_entries WHERE id = :id LIMIT 1")
    suspend fun getKanjiByIdDirect(id: Long): KanjiEntity?

    @Query("SELECT * FROM kanji_entries WHERE kanji = :character LIMIT 1")
    suspend fun getKanjiByCharacter(character: String): KanjiEntity?

    @Query("SELECT * FROM kanji_entries WHERE isBookmarked = 1 ORDER BY id DESC")
    fun getBookmarkedKanji(): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM kanji_entries WHERE jlptLevel = :level ORDER BY id ASC")
    fun getKanjiByJlpt(level: String): Flow<List<KanjiEntity>>

    @Query("""
        SELECT * FROM kanji_entries 
        WHERE kanji LIKE '%' || :query || '%' 
           OR onyomi LIKE '%' || :query || '%' 
           OR kunyomi LIKE '%' || :query || '%' 
           OR meaningBurmese LIKE '%' || :query || '%' 
           OR meaningEnglish LIKE '%' || :query || '%'
           OR exampleCompounds LIKE '%' || :query || '%'
        ORDER BY id ASC
    """)
    fun searchKanji(query: String): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM kanji_entries WHERE nextReviewTimestamp <= :currentTimeMs OR repetitions = 0 ORDER BY nextReviewTimestamp ASC")
    fun getDueKanji(currentTimeMs: Long): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM kanji_entries WHERE nextReviewTimestamp <= :currentTimeMs OR repetitions = 0 ORDER BY nextReviewTimestamp ASC")
    suspend fun getDueKanjiDirect(currentTimeMs: Long): List<KanjiEntity>

    @Query("SELECT COUNT(*) FROM kanji_entries")
    fun getTotalKanjiCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM kanji_entries")
    suspend fun getTotalKanjiCountDirect(): Int

    // ==========================================
    // UPDATE
    // ==========================================

    @Update
    suspend fun updateKanji(kanji: KanjiEntity)

    @Query("UPDATE kanji_entries SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun setBookmark(id: Long, isBookmarked: Boolean)

    @Query("UPDATE kanji_entries SET personalNotes = :notes WHERE id = :id")
    suspend fun updatePersonalNotes(id: Long, notes: String)

    @Query("""
        UPDATE kanji_entries 
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
    suspend fun deleteKanji(kanji: KanjiEntity)

    @Query("DELETE FROM kanji_entries WHERE id = :id")
    suspend fun deleteKanjiById(id: Long): Int

    @Query("DELETE FROM kanji_entries")
    suspend fun deleteAllKanji()
}
