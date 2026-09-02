package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.QuizHistory
import com.example.data.model.UserProfile
import com.example.data.model.VocabCard
import com.example.data.seed.VocabSeedDataApplied
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [VocabCard::class, UserProfile::class, QuizHistory::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vocabDao(): VocabDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun quizDao(): QuizDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kanjikotoba_database"
                )
                    .addCallback(DatabaseCallback(context))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val database = getInstance(context)
                    // Insert Default User Profile
                    val userProfileDao = database.userProfileDao()
                    userProfileDao.insertProfile(
                        UserProfile(
                            id = 1,
                            name = "JLPT N3 Scholar",
                            targetJlptLevel = "N3",
                            dailyGoal = 15,
                            totalXp = 0,
                            currentStreak = 1,
                            lastStudyDate = System.currentTimeMillis()
                        )
                    )

                    // Pre-populate all vocabulary items
                    val vocabDao = database.vocabDao()
                    val allCards = VocabSeedDataApplied.getAllSeedCards()
                    vocabDao.insertCards(allCards)
                }
            }
        }
    }
}
