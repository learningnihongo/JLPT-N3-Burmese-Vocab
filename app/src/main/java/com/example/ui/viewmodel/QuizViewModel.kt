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

enum class QuizType(
    val displayName: String,
    val description: String,
    val badge: String,
    val isSpeed: Boolean = false
) {
    KANJI_MASTERY("Kanji Mastery Drill", "Identify exact N3 Kanji characters from reading and Burmese meaning", "Kanji 漢字"),
    KANJI_TO_MEANING("Kanji → Burmese", "Identify the correct Burmese translation for each Japanese word", "Standard"),
    MEANING_TO_KANJI("Burmese → Kanji", "Pick the matching Japanese Kanji and Kotoba from Burmese meaning", "Recall"),
    READING_CHALLENGE("Furigana Reading", "Test phonetics: select accurate Hiragana & Furigana pronunciation", "Phonetics"),
    LISTENING_CHALLENGE("Audio Comprehension", "Train your ear: listen to natural pronunciation with Slow/Normal audio", "Audio 聴解"),
    SENTENCE_CLOZE("Sentence Fill-in", "Complete real JLPT N3 sentences 【 ? 】 in authentic context", "Cloze 穴埋め"),
    TRUE_FALSE_DRILL("True / False Blitz", "Lightning judgment: verify if the Kanji & Burmese meaning match (○/×)", "Blitz ○/×", isSpeed = true),
    SPEED_TEST("Speed Rush (10s)", "Rapid-fire 10-second countdown testing split-second recall", "10s Rush", isSpeed = true),
    BOOKMARKED_DRILL("Bookmarked Review", "Focus exclusively on your saved & starred personal flashcards", "Starred ⭐")
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
    val isTrueFalse: Boolean = false,
    val clozeSentence: String = "",
    val sentenceMeaning: String = "",
    val clueHint: String = ""
)

data class QuizState(
    val isQuizActive: Boolean = false,
    val isFinished: Boolean = false,
    val isPaused: Boolean = false,
    val quizType: QuizType = QuizType.KANJI_TO_MEANING,
    val lessonFilter: Int? = null, // null = all, -2 = bookmarked, -1 = weak cards, 1..29 = specific lesson
    val lessonTitle: String = "All Lessons",
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val currentStreak: Int = 0,
    val maxStreak: Int = 0,
    val comboMultiplier: Int = 1,
    val remainingSeconds: Int = 15,
    val xpEarned: Int = 0,
    val speedBonusXp: Int = 0,
    val streakBonusXp: Int = 0,
    val startTime: Long = 0L,
    val durationSeconds: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerSubmitted: Boolean = false,
    val eliminatedOptionIndices: Set<Int> = emptySet(),
    val hasUsedFiftyFifty: Boolean = false,
    val hasUsedSwap: Boolean = false,
    val showClue: Boolean = false,
    val isCurrentCardBookmarked: Boolean = false,
    val speechRate: Float = 1.0f,
    val cardsUpdatedCount: Int = 0
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    val repository = VocabRepository(db.vocabDao(), db.userProfileDao(), db.quizDao())
    val ttsHelper = TtsHelper(application)

    private val _quizState = MutableStateFlow(QuizState())
    val quizState = _quizState.asStateFlow()

    val quizHistory: StateFlow<List<QuizHistory>> = repository.getRecentQuizHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val isTtsSpeaking: StateFlow<Boolean> = ttsHelper.isSpeaking

    private var timerJob: Job? = null
    private var reserveCards: List<VocabCard> = emptyList()

    fun startQuiz(
        quizType: QuizType,
        lessonFilter: Int? = null,
        questionCount: Int = 10,
        customCardsList: List<VocabCard>? = null
    ) {
        viewModelScope.launch {
            val cards: List<VocabCard> = when {
                customCardsList != null && customCardsList.isNotEmpty() -> {
                    val pool = repository.getRandomCardsForQuiz(40)
                    (customCardsList + pool).distinctBy { it.id }
                }
                quizType == QuizType.BOOKMARKED_DRILL || lessonFilter == -2 -> {
                    val starred = repository.getRandomBookmarkedCardsForQuiz(questionCount + 10)
                    if (starred.size >= 4) starred else repository.getRandomCardsForQuiz(questionCount + 15)
                }
                lessonFilter == -1 -> {
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
                    repository.getRandomCardsForQuiz(questionCount + 20)
                }
            }

            if (cards.size < 2) {
                val fallbackCards = repository.getRandomCardsForQuiz(questionCount + 15)
                if (fallbackCards.isEmpty()) return@launch
                generateQuestionsAndLaunch(quizType, lessonFilter, questionCount, fallbackCards, customCardsList)
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
        val targetCards = (customCardsList ?: cards).take(questionCount)
        reserveCards = cards.drop(questionCount)

        val quizQuestions = mutableListOf<QuizQuestion>()

        for (card in targetCards) {
            val distractors = cards.filter { it.id != card.id }.shuffled()
            val clue = "Part of Speech: ${card.partOfSpeech.ifBlank { "Noun / Expression" }} • Lesson ${card.lessonNumber}"

            val question = when (quizType) {
                QuizType.KANJI_MASTERY -> {
                    val correct = card.kanji
                    val wrongChoices = distractors.map { it.kanji }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = "${card.reading}\n「${card.meaningBurmese}」",
                        promptSub = "Select the exact N3 Kanji spelling • ${card.sectionTitle}",
                        correctAnswer = correct,
                        options = allOptions,
                        clueHint = clue
                    )
                }
                QuizType.KANJI_TO_MEANING -> {
                    val correct = card.meaningBurmese
                    val wrongChoices = distractors.map { it.meaningBurmese }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = card.kanji,
                        promptSub = "Furigana: ${card.reading}",
                        correctAnswer = correct,
                        options = allOptions,
                        clueHint = clue
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
                        options = allOptions,
                        clueHint = clue
                    )
                }
                QuizType.READING_CHALLENGE -> {
                    val correct = card.reading
                    val wrongChoices = distractors.map { it.reading }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = card.kanji,
                        promptSub = "Meaning: ${card.meaningBurmese}",
                        correctAnswer = correct,
                        options = allOptions,
                        clueHint = clue
                    )
                }
                QuizType.LISTENING_CHALLENGE -> {
                    val correct = "${card.meaningBurmese} — ${card.kanji}"
                    val wrongChoices = distractors.map { "${it.meaningBurmese} — ${it.kanji}" }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = "🎧 Listen & Match",
                        promptSub = "Select the correct Burmese translation & Kanji",
                        correctAnswer = correct,
                        options = allOptions,
                        isListening = true,
                        clueHint = clue
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
                        sentenceMeaning = card.exampleMeaningBurmese,
                        clueHint = clue
                    )
                }
                QuizType.TRUE_FALSE_DRILL -> {
                    val isTrue = (0..1).random() == 1 || distractors.isEmpty()
                    val displayedMeaning = if (isTrue) card.meaningBurmese else distractors.first().meaningBurmese
                    val correct = if (isTrue) "⭕ True (မှန်သည်)" else "❌ False (မှားသည်)"
                    val options = listOf("⭕ True (မှန်သည်)", "❌ False (မှားသည်)")

                    QuizQuestion(
                        card = card,
                        prompt = "${card.kanji} 【${card.reading}】\n=\n「$displayedMeaning」",
                        promptSub = "Does this Japanese word match the Burmese meaning?",
                        correctAnswer = correct,
                        options = options,
                        isTrueFalse = true,
                        clueHint = clue
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
                        options = allOptions,
                        clueHint = clue
                    )
                }
                QuizType.BOOKMARKED_DRILL -> {
                    val correct = card.meaningBurmese
                    val wrongChoices = distractors.map { it.meaningBurmese }.distinct().filter { it != correct }.take(3)
                    val allOptions = (wrongChoices + correct).shuffled()
                    QuizQuestion(
                        card = card,
                        prompt = card.kanji,
                        promptSub = "⭐ Starred Card • ${card.reading}",
                        correctAnswer = correct,
                        options = allOptions,
                        clueHint = clue
                    )
                }
            }
            quizQuestions.add(question)
        }

        val initialSeconds = if (quizType.isSpeed) 10 else 15

        val lessonLabel = when {
            customCardsList != null -> "Missed Words Drill"
            quizType == QuizType.BOOKMARKED_DRILL || lessonFilter == -2 -> "Starred Vocab Drill"
            lessonFilter == -1 -> "Weak Cards Arena"
            lessonFilter != null && lessonFilter > 0 -> "Lesson $lessonFilter"
            else -> "General JLPT N3"
        }

        _quizState.value = QuizState(
            isQuizActive = true,
            isFinished = false,
            isPaused = false,
            quizType = quizType,
            lessonFilter = lessonFilter,
            lessonTitle = lessonLabel,
            questions = quizQuestions,
            currentIndex = 0,
            score = 0,
            currentStreak = 0,
            maxStreak = 0,
            comboMultiplier = 1,
            remainingSeconds = initialSeconds,
            startTime = System.currentTimeMillis(),
            isCurrentCardBookmarked = quizQuestions.firstOrNull()?.card?.isBookmarked ?: false,
            speechRate = 1.0f
        )

        // Auto play audio for listening challenge
        if (quizType == QuizType.LISTENING_CHALLENGE && quizQuestions.isNotEmpty()) {
            ttsHelper.speak(quizQuestions[0].card.kanji, 1.0f)
        }

        startTimer(initialSeconds)
    }

    private fun startTimer(seconds: Int) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            for (sec in seconds downTo 0) {
                if (_quizState.value.isPaused) {
                    while (_quizState.value.isPaused) {
                        delay(200)
                    }
                }
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
        val maxTimer = if (state.quizType.isSpeed) 10 else 15
        val answeredFast = (maxTimer - state.remainingSeconds) <= 3 && isCorrect

        val newStreak = if (isCorrect) state.currentStreak + 1 else 0
        val maxStreak = maxOf(state.maxStreak, newStreak)

        val multiplier = when {
            newStreak >= 8 -> 5
            newStreak >= 5 -> 3
            newStreak >= 3 -> 2
            else -> 1
        }

        val baseEarned = if (isCorrect) 10 * multiplier else 0
        val speedEarned = if (answeredFast) 5 else 0
        val streakBonus = if (newStreak in listOf(3, 5, 8, 10, 15)) 15 else 0

        val totalXpThisRound = state.xpEarned + baseEarned + speedEarned + streakBonus

        currentQuestion.selectedAnswer = chosenAnswer
        currentQuestion.isCorrect = isCorrect

        _quizState.value = state.copy(
            selectedOptionIndex = optionIndex,
            isAnswerSubmitted = true,
            score = if (isCorrect) state.score + 1 else state.score,
            currentStreak = newStreak,
            maxStreak = maxStreak,
            comboMultiplier = multiplier,
            xpEarned = totalXpThisRound,
            speedBonusXp = state.speedBonusXp + speedEarned,
            streakBonusXp = state.streakBonusXp + streakBonus,
            isCurrentCardBookmarked = currentQuestion.card.isBookmarked,
            cardsUpdatedCount = state.cardsUpdatedCount + 1
        )

        // Asynchronously update SQLite card review metrics
        viewModelScope.launch {
            repository.recordCardQuizOutcome(currentQuestion.card.id, isCorrect)
        }

        // Pronounce Japanese word via TTS
        ttsHelper.speak(currentQuestion.card.kanji, state.speechRate)
    }

    fun useFiftyFifty() {
        val state = _quizState.value
        if (state.hasUsedFiftyFifty || state.isAnswerSubmitted || state.currentIndex >= state.questions.size) return

        val currentQuestion = state.questions[state.currentIndex]
        if (currentQuestion.isTrueFalse || currentQuestion.options.size <= 2) return

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

    fun swapCurrentQuestion() {
        val state = _quizState.value
        if (state.hasUsedSwap || state.isAnswerSubmitted || reserveCards.isEmpty()) return

        val freshCard = reserveCards.first()
        reserveCards = reserveCards.drop(1)

        val updatedQuestions = state.questions.toMutableList()
        val oldQuestion = updatedQuestions[state.currentIndex]

        // Create replacement question of same type
        val distractors = (reserveCards + state.questions.map { it.card }).filter { it.id != freshCard.id }.shuffled()
        val clue = "Part of Speech: ${freshCard.partOfSpeech.ifBlank { "Noun / Expression" }} • Lesson ${freshCard.lessonNumber}"

        val replacementQuestion = when (state.quizType) {
            QuizType.KANJI_MASTERY -> {
                val correct = freshCard.kanji
                val wrongChoices = distractors.map { it.kanji }.distinct().filter { it != correct }.take(3)
                QuizQuestion(
                    card = freshCard,
                    prompt = "${freshCard.reading}\n「${freshCard.meaningBurmese}」",
                    promptSub = "Select the exact N3 Kanji spelling • ${freshCard.sectionTitle}",
                    correctAnswer = correct,
                    options = (wrongChoices + correct).shuffled(),
                    clueHint = clue
                )
            }
            QuizType.READING_CHALLENGE -> {
                val correct = freshCard.reading
                val wrongChoices = distractors.map { it.reading }.distinct().filter { it != correct }.take(3)
                QuizQuestion(
                    card = freshCard,
                    prompt = freshCard.kanji,
                    promptSub = "Meaning: ${freshCard.meaningBurmese}",
                    correctAnswer = correct,
                    options = (wrongChoices + correct).shuffled(),
                    clueHint = clue
                )
            }
            QuizType.LISTENING_CHALLENGE -> {
                val correct = "${freshCard.meaningBurmese} — ${freshCard.kanji}"
                val wrongChoices = distractors.map { "${it.meaningBurmese} — ${it.kanji}" }.distinct().filter { it != correct }.take(3)
                QuizQuestion(
                    card = freshCard,
                    prompt = "🎧 Listen & Match",
                    promptSub = "Select the correct Burmese translation & Kanji",
                    correctAnswer = correct,
                    options = (wrongChoices + correct).shuffled(),
                    isListening = true,
                    clueHint = clue
                )
            }
            else -> {
                val correct = freshCard.meaningBurmese
                val wrongChoices = distractors.map { it.meaningBurmese }.distinct().filter { it != correct }.take(3)
                QuizQuestion(
                    card = freshCard,
                    prompt = freshCard.kanji,
                    promptSub = "Furigana: ${freshCard.reading}",
                    correctAnswer = correct,
                    options = (wrongChoices + correct).shuffled(),
                    clueHint = clue
                )
            }
        }

        updatedQuestions[state.currentIndex] = replacementQuestion

        val resetSeconds = if (state.quizType.isSpeed) 10 else 15
        _quizState.value = state.copy(
            questions = updatedQuestions,
            hasUsedSwap = true,
            eliminatedOptionIndices = emptySet(),
            showClue = false,
            remainingSeconds = resetSeconds,
            isCurrentCardBookmarked = freshCard.isBookmarked
        )

        if (state.quizType == QuizType.LISTENING_CHALLENGE) {
            ttsHelper.speak(freshCard.kanji, state.speechRate)
        }

        startTimer(resetSeconds)
    }

    fun toggleClue() {
        _quizState.value = _quizState.value.copy(showClue = !_quizState.value.showClue)
    }

    fun toggleAudioSpeed() {
        val newRate = if (_quizState.value.speechRate == 1.0f) 0.75f else 1.0f
        _quizState.value = _quizState.value.copy(speechRate = newRate)
        val currentQuestion = _quizState.value.questions.getOrNull(_quizState.value.currentIndex)
        if (currentQuestion != null) {
            ttsHelper.speak(currentQuestion.card.kanji, newRate)
        }
    }

    fun togglePause() {
        _quizState.value = _quizState.value.copy(isPaused = !_quizState.value.isPaused)
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

    fun bookmarkAllMissedCards() {
        val state = _quizState.value
        val missedCardIds = state.questions.filter { !it.isCorrect }.map { it.card.id }
        if (missedCardIds.isNotEmpty()) {
            viewModelScope.launch {
                repository.bookmarkAllCards(missedCardIds, true)
            }
        }
    }

    fun nextQuestion() {
        val state = _quizState.value
        val nextIndex = state.currentIndex + 1

        if (nextIndex < state.questions.size) {
            val nextSeconds = if (state.quizType.isSpeed) 10 else 15
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
                ttsHelper.speak(nextCard.kanji, state.speechRate)
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
        val perfectBonusXp = if (state.score == state.questions.size && state.questions.isNotEmpty()) 50 else 0
        val finalXp = state.xpEarned + perfectBonusXp

        _quizState.value = state.copy(
            isQuizActive = false,
            isFinished = true,
            durationSeconds = duration,
            xpEarned = finalXp
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

    fun speak(text: String, rate: Float? = null) {
        ttsHelper.speak(text, rate ?: _quizState.value.speechRate)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        ttsHelper.shutdown()
    }
}
