package com.example.data.db

import androidx.room.*
import com.example.data.model.QuizHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM quiz_histories ORDER BY timestamp DESC")
    fun getAllQuizHistories(): Flow<List<QuizHistory>>

    @Query("SELECT * FROM quiz_histories ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentQuizHistory(limit: Int): Flow<List<QuizHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizHistory(history: QuizHistory): Long

    @Query("SELECT COUNT(*) FROM quiz_histories")
    suspend fun getTotalQuizzesTaken(): Int

    @Query("SELECT COALESCE(SUM(totalQuestions), 0) FROM quiz_histories WHERE timestamp >= :startOfDayMs AND timestamp <= :endOfDayMs")
    suspend fun getQuestionsAnsweredInRange(startOfDayMs: Long, endOfDayMs: Long): Int
}
