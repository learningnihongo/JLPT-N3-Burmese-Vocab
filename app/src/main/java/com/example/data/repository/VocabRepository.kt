package com.example.data.repository

import com.example.data.db.QuizDao
import com.example.data.db.UserProfileDao
import com.example.data.db.VocabDao
import com.example.data.model.LessonProgress
import com.example.data.model.QuizHistory
import com.example.data.model.UserProfile
import com.example.data.model.VocabCard
import com.example.data.srs.ReviewRating
import com.example.data.srs.SpacedRepetitionEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.util.Calendar

class VocabRepository(
    private val vocabDao: VocabDao,
    private val userProfileDao: UserProfileDao,
    private val quizDao: QuizDao
) {
    suspend fun ensureDataSeeded() {
        val count = vocabDao.getCardCount()
        if (count < 1101) {
            val allCards = com.example.data.seed.VocabSeedDataApplied.getAllSeedCards()
            vocabDao.clearNonCustomCards()
            vocabDao.insertCards(allCards)
        }
        val profile = userProfileDao.getProfile().firstOrNull()
        if (profile == null) {
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
        }
    }

    // Vocab Flow queries
    fun getAllCards(): Flow<List<VocabCard>> = vocabDao.getAllCards()

    fun getCardsByLesson(lesson: Int): Flow<List<VocabCard>> = vocabDao.getCardsByLesson(lesson)

    fun getBookmarkedCards(): Flow<List<VocabCard>> = vocabDao.getBookmarkedCards()

    fun getCustomPersonalCards(): Flow<List<VocabCard>> = vocabDao.getCustomCards()

    fun getDueCards(currentTimeMs: Long = System.currentTimeMillis()): Flow<List<VocabCard>> =
        vocabDao.getDueCards(currentTimeMs)

    suspend fun getDueCardsDirect(currentTimeMs: Long = System.currentTimeMillis()): List<VocabCard> =
        vocabDao.getDueCardsDirect(currentTimeMs)

    suspend fun getAllCardsDirect(): List<VocabCard> =
        vocabDao.getAllCardsDirect()

    fun getMasteredCards(): Flow<List<VocabCard>> = vocabDao.getMasteredCards()

    fun getWeakCards(): Flow<List<VocabCard>> = vocabDao.getWeakCards()

    fun getCardsByTag(tag: String): Flow<List<VocabCard>> = vocabDao.getCardsByTag(tag)

    fun getAllCustomTags(): Flow<List<String>> = vocabDao.getAllTagsRaw().map { rawTagsList ->
        rawTagsList.flatMap { raw ->
            raw.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        }.distinct().sorted()
    }

    fun searchCards(query: String): Flow<List<VocabCard>> = vocabDao.searchCards(query)

    fun getLessonProgress(): Flow<List<LessonProgress>> = vocabDao.getAllCards().map { cards ->
        cards.groupBy { it.lessonNumber }.map { (lessonNum, cardList) ->
            val firstCard = cardList.firstOrNull()
            LessonProgress(
                lessonNumber = lessonNum,
                lessonTitle = firstCard?.lessonTitle ?: "Lesson $lessonNum",
                totalCards = cardList.size,
                masteredCards = cardList.count { it.masteryLevel >= 3 },
                dueCards = cardList.count { it.isDue }
            )
        }.sortedBy { it.lessonNumber }
    }

    fun getTotalCardCount(): Flow<Int> = vocabDao.getTotalCardCount()

    fun getMasteredCardCount(): Flow<Int> = vocabDao.getMasteredCardCount()

    fun getDueCardCount(currentTimeMs: Long = System.currentTimeMillis()): Flow<Int> =
        vocabDao.getDueCardCount(currentTimeMs)

    suspend fun getCardById(id: Long): VocabCard? = vocabDao.getCardById(id)

    suspend fun getRandomCardsForQuiz(limit: Int): List<VocabCard> = vocabDao.getRandomCards(limit)

    suspend fun getRandomCardsForLessonQuiz(lesson: Int, limit: Int): List<VocabCard> =
        vocabDao.getRandomCardsForLesson(lesson, limit)

    suspend fun getRandomWeakCardsForQuiz(limit: Int): List<VocabCard> =
        vocabDao.getRandomWeakCards(limit)

    suspend fun getRandomBookmarkedCardsForQuiz(limit: Int): List<VocabCard> =
        vocabDao.getRandomBookmarkedCards(limit)

    suspend fun getRandomCardsWithSentenceForQuiz(limit: Int): List<VocabCard> =
        vocabDao.getRandomCardsWithSentence(limit)

    suspend fun getCardsByIds(ids: List<Long>): List<VocabCard> =
        vocabDao.getCardsByIds(ids)

    suspend fun recordCardQuizOutcome(cardId: Long, wasCorrect: Boolean) {
        val now = System.currentTimeMillis()
        if (wasCorrect) {
            vocabDao.incrementCorrect(cardId, now)
        } else {
            vocabDao.incrementIncorrect(cardId, now)
        }
    }

    suspend fun bookmarkAllCards(cardIds: List<Long>, isBookmarked: Boolean = true) {
        cardIds.forEach { id ->
            vocabDao.setBookmark(id, isBookmarked)
        }
    }

    suspend fun setBookmarkById(cardId: Long, isBookmarked: Boolean) {
        vocabDao.setBookmark(cardId, isBookmarked)
    }

    suspend fun toggleBookmark(card: VocabCard) {
        vocabDao.setBookmark(card.id, !card.isBookmarked)
    }

    suspend fun updateCardNotes(cardId: Long, personalNote: String) {
        vocabDao.updatePersonalNote(cardId, personalNote)
    }

    suspend fun updateCardTags(cardId: Long, tags: List<String>) {
        val cleanString = tags.map { it.trim() }.filter { it.isNotEmpty() }.distinct().joinToString(",")
        vocabDao.updateCardTags(cardId, cleanString)
    }

    suspend fun addTagToCard(cardId: Long, tag: String) {
        val card = vocabDao.getCardById(cardId) ?: return
        val cleanTag = tag.trim()
        if (cleanTag.isEmpty()) return
        val currentTags = card.tagList.toMutableList()
        if (!currentTags.any { it.equals(cleanTag, ignoreCase = true) }) {
            currentTags.add(cleanTag)
            updateCardTags(cardId, currentTags)
        }
    }

    suspend fun removeTagFromCard(cardId: Long, tag: String) {
        val card = vocabDao.getCardById(cardId) ?: return
        val cleanTag = tag.trim()
        val currentTags = card.tagList.filterNot { it.equals(cleanTag, ignoreCase = true) }
        updateCardTags(cardId, currentTags)
    }

    suspend fun insertCustomCard(
        kanji: String,
        reading: String,
        meaningBurmese: String,
        partOfSpeech: String,
        exampleSentence: String,
        exampleMeaningBurmese: String,
        personalNote: String,
        tags: String = ""
    ): Long {
        val newCard = VocabCard(
            lessonNumber = 999, // Custom personalized lesson
            lessonTitle = "Personalized Flashcards",
            sectionTitle = "【My Custom Vocab】",
            kanji = kanji,
            reading = reading,
            meaningBurmese = meaningBurmese,
            partOfSpeech = partOfSpeech,
            exampleSentence = exampleSentence,
            exampleMeaningBurmese = exampleMeaningBurmese,
            personalNote = personalNote,
            isCustom = true,
            tags = tags.trim()
        )
        val id = vocabDao.insertCard(newCard)
        addXp(15) // Reward for creating flashcard
        return id
    }

    suspend fun updateCard(card: VocabCard) {
        vocabDao.updateCard(card)
    }

    suspend fun deleteCard(card: VocabCard) {
        vocabDao.deleteCard(card)
    }

    // SRS Review Flow
    suspend fun processCardReview(card: VocabCard, rating: ReviewRating) {
        val updatedCard = SpacedRepetitionEngine.reviewCard(card, rating)
        vocabDao.updateCard(updatedCard)

        // Award XP and check streaks
        val earnedXp = when (rating) {
            ReviewRating.EASY -> 10
            ReviewRating.GOOD -> 8
            ReviewRating.HARD -> 5
            ReviewRating.AGAIN -> 2
        }
        addXp(earnedXp)
        checkAndUpdateStreak()
    }

    // User Profile & Streak
    fun getUserProfile(): Flow<UserProfile?> = userProfileDao.getProfile()

    suspend fun updateProfileName(name: String, dailyGoal: Int, targetJlpt: String) {
        val current = userProfileDao.getProfile().firstOrNull() ?: UserProfile(id = 1)
        userProfileDao.updateProfile(
            current.copy(
                name = name,
                dailyGoal = dailyGoal,
                targetJlptLevel = targetJlpt
            )
        )
    }

    suspend fun addXp(amount: Int) {
        val current = userProfileDao.getProfile().firstOrNull() ?: UserProfile(id = 1)
        val newXp = current.totalXp + amount
        userProfileDao.updateProfile(current.copy(totalXp = newXp))
    }

    suspend fun checkAndUpdateStreak() {
        val current = userProfileDao.getProfile().firstOrNull() ?: UserProfile(id = 1)
        val now = System.currentTimeMillis()
        val lastDate = current.lastStudyDate

        val calNow = Calendar.getInstance().apply { timeInMillis = now }
        val calLast = Calendar.getInstance().apply { timeInMillis = lastDate }

        val isSameDay = calNow.get(Calendar.YEAR) == calLast.get(Calendar.YEAR) &&
                calNow.get(Calendar.DAY_OF_YEAR) == calLast.get(Calendar.DAY_OF_YEAR)

        if (isSameDay) {
            return
        }

        calNow.add(Calendar.DAY_OF_YEAR, -1)
        val isYesterday = calNow.get(Calendar.YEAR) == calLast.get(Calendar.YEAR) &&
                calNow.get(Calendar.DAY_OF_YEAR) == calLast.get(Calendar.DAY_OF_YEAR)

        val newStreak = if (isYesterday) current.currentStreak + 1 else 1
        userProfileDao.updateProfile(
            current.copy(
                currentStreak = newStreak,
                lastStudyDate = now
            )
        )
    }

    // Quiz History
    fun getRecentQuizHistory(limit: Int = 20): Flow<List<QuizHistory>> =
        quizDao.getRecentQuizHistory(limit)

    suspend fun recordQuizResult(
        quizType: String,
        lessonFilter: String,
        score: Int,
        totalQuestions: Int,
        durationSeconds: Int
    ) {
        val xpGain = (score * 10) + if (score == totalQuestions && totalQuestions > 0) 50 else 0
        val history = QuizHistory(
            quizType = quizType,
            lessonFilter = lessonFilter,
            score = score,
            totalQuestions = totalQuestions,
            timeSpentSeconds = durationSeconds,
            xpEarned = xpGain
        )
        quizDao.insertQuizHistory(history)

        addXp(xpGain)
        checkAndUpdateStreak()
    }
}
