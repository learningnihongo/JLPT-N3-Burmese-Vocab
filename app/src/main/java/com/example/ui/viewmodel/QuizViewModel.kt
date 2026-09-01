package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.QuizHistory
import com.example.data.model.VocabCard
import com.example.data.repository.VocabRepository
import com.example.ui.util.TtsHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class QuizType(val displayName: String, val description: String, val badge: String) {
    KANJI_TO_MEANING("Kanji → Burmese", "Identify the Burmese meaning for each Japanese word", "Standard"),
    MEANING_TO_KANJI("Burmese → Kanji", "Pick the correct Kanji/Kotoba for the Burmese meaning", "Recall"),
    READING_CHALLENGE("Furigana Reading", "Select the correct Hiragana/Furigana pronunciation", "Phonetics"),
    LISTENING_CHALLENGE("Listening Audio", "Train your ear: listen to Japanese audio and pick the meaning", "Audio 聴解"),
    SENTENCE_CLOZE("Sentence Fill-in", "Complete authentic JLPT N3 sentences 【 ? 】 in context", "Cloze 穴埋め"),
    SPEED_TEST("Speed Rush (10s)", "Rapid-fire 10s countdown testing lightning reflexes", "10s Rush")
}

data class QuizQuestion(
    val card: VocabCard,
    val prompt: String,
    val promptSub: String,
    val correctAnswer: String,
    val options: List<String>,
    var selectedAnswer: String? = null,
    var isCorrect: Boolean = false,
    val isListening: Boolean = false,
    val clozeSentence: String = "",
    val sentenceMeaning: String = ""
)

data class QuizState(
    val isQuizActive: Boolean = false,
    val isFinished: Boolean = false,
    val quizType: QuizType = QuizType.KANJI_TO_MEANING,
    val lessonFilter: Int? = null, // null = all, -1 = weak cards, 1..10 = specific lesson
    val lessonTitle: String = "All Lessons",
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val currentStreak: Int = 0,
    val maxStreak: Int = 0,
    val remainingSeconds: Int = 15,
    val xpEarned: Int = 0,
    val startTime: Long = 0L,
    val durationSeconds: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerSubmitted: Boolean = false,
    val eliminatedOptionIndices: Set<Int> = emptySet(),
    val hasUsedFiftyFifty: Boolean = false,
    val showClue: Boolean = false,
    val isCurrentCardBookmarked: Boolean = false
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    val repository = VocabRepository(db.vocabDao(), db.userProfileDao(), db.quizDao())
    val ttsHelper = TtsHelper(application)

    private val _quizState = MutableStateFlow(QuizState())
    val quizState = _quizState.asStateFlow()

    val quizHistory: StateFlow<List<QuizHistory>> = repository.getRecentQuizHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var timerJob: Job? = null

    fun startQuiz(
        quizType: QuizType,
        lessonFilter: Int? = null,
        questionCount: Int = 10,
        customCardsList: List<VocabCard>? = null
    ) {
        viewModelScope.launch {
            val cards: List<VocabCard> = when {
                customCardsList != null && customCardsList.isNotEmpty() -> {
                    val pool = repository.getRandomCardsForQuiz(30)
                    (customCardsList + pool).distinctBy { it.id }
                }
                lessonFilter == -1 -> {
                    // Weak cards
                    val weak = repository.getRandomWeakCardsForQuiz(questionCount + 10)
                    if (weak.size >= 4) weak else repository.getRandomCardsForQuiz(questionCount + 15)
                }
                lessonFilter != null && lessonFilter > 0 -> {
                    repository.getRandomCardsForLessonQuiz(lessonFilter, questionCount + 15)
                }
                quizType == QuizType.SENTENCE_CLOZE -> {
                    repository.getRandomCardsWithSentenceForQuiz(questionCount + 15)
                }
                else -> {
                    repository.getRandomCardsForQuiz(questionCount + 15)
                }
            }

            if (cards.size < 4) {
                // Fallback to general random if category has fewer than 4 cards
                val fallbackCards = repository.getRandomCardsForQuiz(questionCount + 15)
                if (fallbackCards.size < 4) return@launch
                generateQuestionsAndLaunch(quizType, lessonFilter, questionCount, fallbackCards)
                return@launch
            }

            generateQuestionsAndLaunch(quizType, lessonFilter, questionCount, cards, customCardsList)
        }
    }

    private fun generateQuestionsAndLaunch(
        quizType: QuizType,
        lessonFilter: Int?,
        questionCount: Int,
        cards: List<VocabCard>,
        customCardsList: List<VocabCard>? = null
    ) {
        val quizQuestions = mutableListOf<QuizQuestion>()
        val targetCards = (customCardsList ?: cards).take(questionCount)

        for (card in targetCards) {
            val distractors = cards.filter { it.id != card.id }.shuffled()
            val question = when (quizType) {
                QuizType.KANJI_TO_MEANING -> {
                    val correct = card.meaningBurmese
                    val wrongChoices = distractors.map { it.meaningBurmese }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = card.kanji,
                        promptSub = card.reading,
                        correctAnswer = correct,
                        options = allOptions
                    )
                }
                QuizType.MEANING_TO_KANJI -> {
                    val correct = "${card.kanji} (${card.reading})"
                    val wrongChoices = distractors.map { "${it.kanji} (${it.reading})" }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = card.meaningBurmese,
                        promptSub = "Section: ${card.sectionTitle}",
                        correctAnswer = correct,
                        options = allOptions
                    )
                }
                QuizType.READING_CHALLENGE -> {
                    val correct = card.reading
                    val wrongChoices = distractors.map { it.reading }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = card.kanji,
                        promptSub = card.meaningBurmese,
                        correctAnswer = correct,
                        options = allOptions
                    )
                }
                QuizType.LISTENING_CHALLENGE -> {
                    val correct = "${card.meaningBurmese} — ${card.kanji}"
                    val wrongChoices = distractors.map { "${it.meaningBurmese} — ${it.kanji}" }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = "🎧 Tap Audio to Listen",
                        promptSub = "Choose the matching Burmese meaning & Kanji",
                        correctAnswer = correct,
                        options = allOptions,
                        isListening = true
                    )
                }
                QuizType.SENTENCE_CLOZE -> {
                    val sentence = if (card.exampleSentence.isNotBlank()) {
                        card.exampleSentence.replace(card.kanji, "【 ? 】")
                    } else {
                        "この【 ? 】の意味を答えてください。"
                    }
                    val correct = "${card.kanji} (${card.reading})"
                    val wrongChoices = distractors.map { "${it.kanji} (${it.reading})" }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = sentence,
                        promptSub = card.meaningBurmese,
                        correctAnswer = correct,
                        options = allOptions,
                        clozeSentence = sentence,
                        sentenceMeaning = card.exampleMeaningBurmese
                    )
                }
                QuizType.SPEED_TEST -> {
                    val correct = card.meaningBurmese
                    val wrongChoices = distractors.map { it.meaningBurmese }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = card.kanji,
                        promptSub = card.reading,
                        correctAnswer = correct,
                        options = allOptions
                    )
                }
            }
            quizQuestions.add(question)
        }

        val initialSeconds = if (quizType == QuizType.SPEED_TEST) 10 else 15

        val lessonLabel = when {
            customCardsList != null -> "Missed Words Drill"
            lessonFilter == -1 -> "Weak Cards Arena"
            lessonFilter != null && lessonFilter > 0 -> "Lesson $lessonFilter"
            else -> "General JLPT N3"
        }

        _quizState.value = QuizState(
            isQuizActive = true,
            isFinished = false,
            quizType = quizType,
            lessonFilter = lessonFilter,
            lessonTitle = lessonLabel,
            questions = quizQuestions,
            currentIndex = 0,
            score = 0,
            currentStreak = 0,
            maxStreak = 0,
            remainingSeconds = initialSeconds,
            startTime = System.currentTimeMillis(),
            isCurrentCardBookmarked = quizQuestions.firstOrNull()?.card?.isBookmarked ?: false
        )

        // Auto play audio if listening challenge
        if (quizType == QuizType.LISTENING_CHALLENGE && quizQuestions.isNotEmpty()) {
            ttsHelper.speak(quizQuestions[0].card.kanji)
        }

        startTimer(initialSeconds)
    }

    private fun startTimer(seconds: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (sec in seconds downTo 0) {
                _quizState.value = _quizState.value.copy(remainingSeconds = sec)
                if (sec == 0) {
                    if (!_quizState.value.isAnswerSubmitted) {
                        selectOption(-1) // Timeout
                    }
                    break
                }
                delay(1000)
            }
        }
    }

    fun selectOption(optionIndex: Int) {
        val state = _quizState.value
        if (state.isAnswerSubmitted || state.currentIndex >= state.questions.size) return

        timerJob?.cancel()
        val currentQuestion = state.questions[state.currentIndex]
        val chosenAnswer = if (optionIndex in currentQuestion.options.indices) {
            currentQuestion.options[optionIndex]
        } else {
            ""
        }

        val isCorrect = chosenAnswer == currentQuestion.correctAnswer
        val newScore = if (isCorrect) state.score + 1 else state.score
        val newStreak = if (isCorrect) state.currentStreak + 1 else 0
        val maxStreak = maxOf(state.maxStreak, newStreak)

        currentQuestion.selectedAnswer = chosenAnswer
        currentQuestion.isCorrect = isCorrect

        _quizState.value = state.copy(
            selectedOptionIndex = optionIndex,
            isAnswerSubmitted = true,
            score = newScore,
            currentStreak = newStreak,
            maxStreak = maxStreak,
            isCurrentCardBookmarked = currentQuestion.card.isBookmarked
        )

        // Pronounce Japanese word via TTS
        ttsHelper.speak(currentQuestion.card.kanji)
    }

    fun useFiftyFifty() {
        val state = _quizState.value
        if (state.hasUsedFiftyFifty || state.isAnswerSubmitted || state.currentIndex >= state.questions.size) return

        val currentQuestion = state.questions[state.currentIndex]
        val wrongIndices = currentQuestion.options.indices
            .filter { currentQuestion.options[it] != currentQuestion.correctAnswer }
            .shuffled()
            .take(2)
            .toSet()

        _quizState.value = state.copy(
            hasUsedFiftyFifty = true,
            eliminatedOptionIndices = wrongIndices
        )
    }

    fun toggleClue() {
        _quizState.value = _quizState.value.copy(showClue = !_quizState.value.showClue)
    }

    fun toggleCurrentCardBookmark() {
        val state = _quizState.value
        val currentQuestion = state.questions.getOrNull(state.currentIndex) ?: return
        viewModelScope.launch {
            val newBookmarkState = !state.isCurrentCardBookmarked
            repository.setBookmarkById(currentQuestion.card.id, newBookmarkState)
            _quizState.value = state.copy(isCurrentCardBookmarked = newBookmarkState)
        }
    }

    fun toggleBookmarkInReview(cardId: Long, currentBookmarked: Boolean) {
        viewModelScope.launch {
            repository.setBookmarkById(cardId, !currentBookmarked)
        }
    }

    fun nextQuestion() {
        val state = _quizState.value
        val nextIndex = state.currentIndex + 1

        if (nextIndex < state.questions.size) {
            val nextSeconds = if (state.quizType == QuizType.SPEED_TEST) 10 else 15
            val nextCard = state.questions[nextIndex].card
            _quizState.value = state.copy(
                currentIndex = nextIndex,
                selectedOptionIndex = null,
                isAnswerSubmitted = false,
                remainingSeconds = nextSeconds,
                eliminatedOptionIndices = emptySet(),
                showClue = false,
                isCurrentCardBookmarked = nextCard.isBookmarked
            )

            // Auto-play audio if listening mode
            if (state.quizType == QuizType.LISTENING_CHALLENGE) {
                ttsHelper.speak(nextCard.kanji)
            }

            startTimer(nextSeconds)
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        timerJob?.cancel()
        val state = _quizState.value
        val duration = ((System.currentTimeMillis() - state.startTime) / 1000).toInt()
        val baseScoreXp = state.score * 10
        val streakBonusXp = state.maxStreak * 5
        val perfectBonusXp = if (state.score == state.questions.size && state.questions.isNotEmpty()) 50 else 0
        val xp = baseScoreXp + streakBonusXp + perfectBonusXp

        _quizState.value = state.copy(
            isQuizActive = false,
            isFinished = true,
            durationSeconds = duration,
            xpEarned = xp
        )

        viewModelScope.launch {
            repository.recordQuizResult(
                quizType = state.quizType.displayName,
                lessonFilter = state.lessonTitle,
                score = state.score,
                totalQuestions = state.questions.size,
                durationSeconds = duration
            )
        }
    }

    fun retryMissedQuestions() {
        val state = _quizState.value
        val missedCards = state.questions.filter { !it.isCorrect }.map { it.card }
        if (missedCards.isNotEmpty()) {
            startQuiz(
                quizType = state.quizType,
                lessonFilter = state.lessonFilter,
                questionCount = missedCards.size,
                customCardsList = missedCards
            )
        }
    }

    fun exitQuiz() {
        timerJob?.cancel()
        _quizState.value = QuizState()
    }

    fun speak(text: String) {
        ttsHelper.speak(text)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        ttsHelper.shutdown()
    }
}
