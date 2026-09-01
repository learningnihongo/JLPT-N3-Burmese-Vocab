package com.example.data.db

import androidx.room.*
import com.example.data.model.VocabCard
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabDao {
    @Query("SELECT * FROM vocab_cards ORDER BY lessonNumber ASC, id ASC")
    fun getAllCards(): Flow<List<VocabCard>>

    @Query("SELECT * FROM vocab_cards WHERE isBookmarked = 1 ORDER BY id DESC")
    fun getBookmarkedCards(): Flow<List<VocabCard>>

    @Query("SELECT * FROM vocab_cards WHERE lessonNumber = :lessonNumber ORDER BY id ASC")
    fun getCardsByLesson(lessonNumber: Int): Flow<List<VocabCard>>

    @Query("SELECT * FROM vocab_cards WHERE isCustom = 1 ORDER BY id DESC")
    fun getCustomCards(): Flow<List<VocabCard>>

    @Query("SELECT * FROM vocab_cards WHERE masteryLevel >= 3 ORDER BY id ASC")
    fun getMasteredCards(): Flow<List<VocabCard>>

    @Query("SELECT * FROM vocab_cards WHERE timesIncorrect > timesCorrect AND (timesCorrect + timesIncorrect) > 0 ORDER BY timesIncorrect DESC")
    fun getWeakCards(): Flow<List<VocabCard>>

    @Query("SELECT * FROM vocab_cards WHERE nextReviewTimestamp <= :currentTimeMs OR repetitions = 0 ORDER BY nextReviewTimestamp ASC")
    fun getDueCards(currentTimeMs: Long): Flow<List<VocabCard>>

    @Query("""
        SELECT * FROM vocab_cards 
        WHERE kanji LIKE '%' || :query || '%' 
           OR reading LIKE '%' || :query || '%' 
           OR meaningBurmese LIKE '%' || :query || '%' 
           OR personalNote LIKE '%' || :query || '%'
        ORDER BY lessonNumber ASC, id ASC
    """)
    fun searchCards(query: String): Flow<List<VocabCard>>

    @Query("SELECT * FROM vocab_cards WHERE id = :id LIMIT 1")
    suspend fun getCardById(id: Long): VocabCard?

    @Query("SELECT * FROM vocab_cards ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomCards(limit: Int): List<VocabCard>

    @Query("SELECT * FROM vocab_cards WHERE lessonNumber = :lessonNumber ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomCardsForLesson(lessonNumber: Int, limit: Int): List<VocabCard>

    @Query("SELECT COUNT(*) FROM vocab_cards")
    suspend fun getCardCount(): Int

    @Query("SELECT COUNT(*) FROM vocab_cards")
    fun getTotalCardCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM vocab_cards WHERE masteryLevel >= 3")
    fun getMasteredCardCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM vocab_cards WHERE nextReviewTimestamp <= :currentTimeMs OR repetitions = 0")
    fun getDueCardCount(currentTimeMs: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM vocab_cards WHERE nextReviewTimestamp <= :currentTimeMs OR repetitions = 0")
    suspend fun getDueCardCountDirect(currentTimeMs: Long): Int

    @Query("SELECT * FROM vocab_cards WHERE timesIncorrect > timesCorrect AND (timesCorrect + timesIncorrect) > 0 ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomWeakCards(limit: Int): List<VocabCard>

    @Query("SELECT * FROM vocab_cards WHERE exampleSentence != '' ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomCardsWithSentence(limit: Int): List<VocabCard>

    @Query("SELECT * FROM vocab_cards WHERE id IN (:ids)")
    suspend fun getCardsByIds(ids: List<Long>): List<VocabCard>

    @Query("SELECT COUNT(*) FROM vocab_cards WHERE lastReviewedTimestamp >= :startOfDayMs AND lastReviewedTimestamp <= :endOfDayMs")
    suspend fun getCardsReviewedCountInRange(startOfDayMs: Long, endOfDayMs: Long): Int

    @Query("DELETE FROM vocab_cards WHERE isCustom = 0")
    suspend fun clearNonCustomCards()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: VocabCard): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCards(cards: List<VocabCard>)

    @Update
    suspend fun updateCard(card: VocabCard)

    @Delete
    suspend fun deleteCard(card: VocabCard)

    @Query("UPDATE vocab_cards SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun setBookmark(id: Long, isBookmarked: Boolean)

    @Query("UPDATE vocab_cards SET personalNote = :note WHERE id = :id")
    suspend fun updatePersonalNote(id: Long, note: String)
}
