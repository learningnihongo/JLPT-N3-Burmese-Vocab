package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Int = 1,
    val name: String = "Sensei Student",
    val avatarIndex: Int = 0,
    val targetJlptLevel: String = "N3",
    val dailyGoal: Int = 15,
    val totalXp: Int = 0,
    val currentStreak: Int = 1,
    val bestStreak: Int = 1,
    val lastStudyDate: Long = System.currentTimeMillis()
) {
    val level: Int
        get() = (totalXp / 150) + 1

    val levelTitle: String
        get() = when {
            level >= 25 -> "JLPT Shogun (将軍)"
            level >= 15 -> "Kanji Sensei (先生)"
            level >= 10 -> "Kotoba Master (達人)"
            level >= 5 -> "Samurai Scholar (侍)"
            level >= 2 -> "Apprentice (弟子)"
            else -> "Novice Learner (初心者)"
        }

    val xpToNextLevel: Int
        get() {
            val currentLevelBase = (level - 1) * 150
            return (currentLevelBase + 150) - totalXp
        }

    val levelProgress: Float
        get() {
            val xpInCurrentLevel = totalXp % 150
            return (xpInCurrentLevel / 150f).coerceIn(0f, 1f)
        }
}
