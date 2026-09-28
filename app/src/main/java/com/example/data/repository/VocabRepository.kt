package com.example.data.repository

import com.example.data.db.QuizDao
import com.example.data.db.SrsReviewDao
import com.example.data.db.UserProfileDao
import com.example.data.db.VocabDao
import com.example.data.model.LessonProgress
import com.example.data.model.QuizHistory
import com.example.data.model.SrsReviewLog
import com.example.data.model.SrsScheduleSummary
import com.example.data.model.UserProfile
import com.example.data.model.VocabCard
import com.example.data.srs.ReviewRating
import com.example.data.srs.SpacedRepetitionEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Calendar

class VocabRepository(
    private val vocabDao: VocabDao,
    private val userProfileDao: UserProfileDao,
    private val quizDao: QuizDao,
    private val srsReviewDao: SrsReviewDao? = null
) {
    suspend fun ensureDataSeeded(forceRefresh: Boolean = false) {
        val count = vocabDao.getCardCount()
        val allCards = com.example.data.seed.VocabSeedDataApplied.getAllSeedCards()
        if (forceRefresh) {
            vocabDao.clearNonCustomCards()
            vocabDao.insertCards(allCards)
        } else if (count < allCards.size) {
            // Safe non-destructive insert: OnConflictStrategy.IGNORE retains all existing cards and user progress
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
                    bestStreak = 1,
                    lastStudyDate = System.currentTimeMillis()
                )
            )
        } else {
            syncDailyStreak()
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

    suspend fun toggleBookmark(card: VocabCard): Boolean {
        val freshCard = vocabDao.getCardById(card.id) ?: card
        val targetState = !freshCard.isBookmarked
        vocabDao.setBookmark(card.id, targetState)
        return targetState
    }

    suspend fun updateCardNotes(cardId: Long, personalNote: String) {
        vocabDao.updatePersonalNote(cardId, personalNote.trim())
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

    // SRS Review Flow (SM-2 Spaced Repetition Algorithm & Room Tracking)
    suspend fun processCardReview(
        card: VocabCard,
        rating: ReviewRating,
        timeSpentSeconds: Int = 0,
        currentTimeMs: Long = System.currentTimeMillis()
    ) {
        val freshCard = vocabDao.getCardById(card.id) ?: card
        val srsResult = SpacedRepetitionEngine.calculateSrsParameters(freshCard, rating, currentTimeMs)
        
        val updatedCard = freshCard.copy(
            repetitions = srsResult.repetitions,
            intervalDays = srsResult.intervalDays,
            easeFactor = srsResult.easeFactor,
            nextReviewTimestamp = srsResult.nextReviewTimestamp,
            lastReviewedTimestamp = currentTimeMs,
            masteryLevel = srsResult.masteryLevel,
            timesCorrect = freshCard.timesCorrect + if (srsResult.isRetentionSuccess) 1 else 0,
            timesIncorrect = freshCard.timesIncorrect + if (!srsResult.isRetentionSuccess) 1 else 0
        )
        vocabDao.updateCard(updatedCard)

        // Persist review log into Room database for tracking and analytics
        srsReviewDao?.insertLog(
            SrsReviewLog(
                cardId = freshCard.id,
                kanji = freshCard.kanji,
                reading = freshCard.reading,
                meaningBurmese = freshCard.meaningBurmese,
                rating = rating.name,
                ratingQuality = rating.quality,
                repetitionsBefore = freshCard.repetitions,
                repetitionsAfter = srsResult.repetitions,
                intervalDaysBefore = freshCard.intervalDays,
                intervalDaysAfter = srsResult.intervalDays,
                easeFactorBefore = freshCard.easeFactor,
                easeFactorAfter = srsResult.easeFactor,
                reviewTimestamp = currentTimeMs,
                scheduledNextReviewTimestamp = srsResult.nextReviewTimestamp,
                isRetentionSuccess = srsResult.isRetentionSuccess,
                timeSpentSeconds = timeSpentSeconds
            )
        )

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

    fun getRecentSrsLogs(limit: Int = 50): Flow<List<SrsReviewLog>> =
        srsReviewDao?.getRecentLogs(limit) ?: flowOf(emptyList())

    fun getLogsForCard(cardId: Long): Flow<List<SrsReviewLog>> =
        srsReviewDao?.getLogsForCard(cardId) ?: flowOf(emptyList())

    fun getTotalSrsReviewsCount(): Flow<Int> =
        srsReviewDao?.getTotalReviewCount() ?: flowOf(0)

    fun getSrsRetentionRate(): Flow<Float?> =
        srsReviewDao?.getRetentionRatePercent() ?: flowOf(null)

    fun getSrsScheduleSummary(): Flow<SrsScheduleSummary> {
        val cardsFlow = vocabDao.getAllCards()
        val totalReviewsFlow = srsReviewDao?.getTotalReviewCount() ?: flowOf(0)
        val successfulReviewsFlow = srsReviewDao?.getSuccessfulReviewCount() ?: flowOf(0)

        return combine(cardsFlow, totalReviewsFlow, successfulReviewsFlow) { cards, totalReviews, successReviews ->
            SpacedRepetitionEngine.buildScheduleSummary(
                allCards = cards,
                totalReviewsLogged = totalReviews,
                successfulReviewsCount = successReviews
            )
        }
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

    suspend fun updateFullProfile(
        name: String,
        dailyGoal: Int,
        targetJlpt: String,
        avatarIndex: Int,
        customAvatarUri: String?
    ) {
        val current = userProfileDao.getProfile().firstOrNull() ?: UserProfile(id = 1)
        userProfileDao.updateProfile(
            current.copy(
                name = name,
                dailyGoal = dailyGoal,
                targetJlptLevel = targetJlpt,
                avatarIndex = avatarIndex,
                customAvatarUri = customAvatarUri
            )
        )
    }

    suspend fun updateAvatar(avatarIndex: Int, customAvatarUri: String?) {
        val current = userProfileDao.getProfile().firstOrNull() ?: UserProfile(id = 1)
        userProfileDao.updateProfile(
            current.copy(
                avatarIndex = avatarIndex,
                customAvatarUri = customAvatarUri
            )
        )
    }

    suspend fun addXp(amount: Int) {
        val current = userProfileDao.getProfile().firstOrNull() ?: UserProfile(id = 1)
        val newXp = current.totalXp + amount
        userProfileDao.updateProfile(current.copy(totalXp = newXp))
    }

    suspend fun updateDailyGoal(dailyGoal: Int) {
        val current = userProfileDao.getProfile().firstOrNull() ?: UserProfile(id = 1)
        userProfileDao.updateProfile(
            current.copy(dailyGoal = dailyGoal)
        )
    }

    suspend fun syncDailyStreak() {
        val current = userProfileDao.getProfile().firstOrNull() ?: return
        val now = System.currentTimeMillis()
        val lastDate = current.lastStudyDate
        if (lastDate == 0L) return

        val calNow = Calendar.getInstance().apply { timeInMillis = now }
        val calLast = Calendar.getInstance().apply { timeInMillis = lastDate }

        val isSameDay = calNow.get(Calendar.YEAR) == calLast.get(Calendar.YEAR) &&
                calNow.get(Calendar.DAY_OF_YEAR) == calLast.get(Calendar.DAY_OF_YEAR)

        if (isSameDay) return

        calNow.add(Calendar.DAY_OF_YEAR, -1)
        val isYesterday = calNow.get(Calendar.YEAR) == calLast.get(Calendar.YEAR) &&
                calNow.get(Calendar.DAY_OF_YEAR) == calLast.get(Calendar.DAY_OF_YEAR)

        if (!isYesterday && current.currentStreak > 0) {
            userProfileDao.updateProfile(
                current.copy(currentStreak = 0)
            )
        }
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
        val newBestStreak = maxOf(newStreak, current.bestStreak)
        userProfileDao.updateProfile(
            current.copy(
                currentStreak = newStreak,
                bestStreak = newBestStreak,
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
