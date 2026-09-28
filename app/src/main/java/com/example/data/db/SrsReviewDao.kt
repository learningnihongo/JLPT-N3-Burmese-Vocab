package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.SrsReviewLog
import kotlinx.coroutines.flow.Flow

@Dao
interface SrsReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: SrsReviewLog): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogs(logs: List<SrsReviewLog>)

    @Query("SELECT * FROM srs_review_logs ORDER BY reviewTimestamp DESC LIMIT :limit")
    fun getRecentLogs(limit: Int = 50): Flow<List<SrsReviewLog>>

    @Query("SELECT * FROM srs_review_logs WHERE cardId = :cardId ORDER BY reviewTimestamp DESC")
    fun getLogsForCard(cardId: Long): Flow<List<SrsReviewLog>>

    @Query("SELECT COUNT(*) FROM srs_review_logs")
    fun getTotalReviewCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM srs_review_logs")
    suspend fun getTotalReviewCountDirect(): Int

    @Query("SELECT COUNT(*) FROM srs_review_logs WHERE isRetentionSuccess = 1")
    fun getSuccessfulReviewCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM srs_review_logs WHERE isRetentionSuccess = 1")
    suspend fun getSuccessfulReviewCountDirect(): Int

    @Query("SELECT COUNT(*) FROM srs_review_logs WHERE reviewTimestamp >= :startOfDayMs")
    fun getTodayReviewCount(startOfDayMs: Long): Flow<Int>

    @Query("SELECT * FROM srs_review_logs WHERE reviewTimestamp >= :startTimeMs AND reviewTimestamp <= :endTimeMs ORDER BY reviewTimestamp ASC")
    suspend fun getLogsInRange(startTimeMs: Long, endTimeMs: Long): List<SrsReviewLog>

    @Query("SELECT AVG(CASE WHEN isRetentionSuccess = 1 THEN 1.0 ELSE 0.0 END) * 100 FROM srs_review_logs")
    fun getRetentionRatePercent(): Flow<Float?>

    @Query("DELETE FROM srs_review_logs")
    suspend fun clearAllLogs()
}
