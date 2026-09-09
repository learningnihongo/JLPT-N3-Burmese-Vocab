package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_histories")
data class QuizHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val quizType: String,       // "Kanji to Meaning", "Meaning to Kanji", etc.
    val score: Int,
    val totalQuestions: Int,
    val lessonFilter: String = "All Lessons",
    val lessonScope: String = "General N3",
    val xpEarned: Int = 0,
    val timeSpentSeconds: Int = 0,
    val accuracyPercentage: Int = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
)
