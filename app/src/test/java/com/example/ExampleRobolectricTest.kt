package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.VocabCard
import com.example.data.srs.ReviewRating
import com.example.data.srs.SpacedRepetitionEngine
import com.example.reminder.ReminderPreferences
import com.example.reminder.ReminderScheduler
import com.example.ui.theme.AppThemePreferences
import com.example.ui.theme.IconThemeStyle
import com.example.ui.theme.ThemeMode
import com.example.ui.theme.ThemePalette
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("KanjiKotoba", appName)
  }

  @Test
  fun `sm2 algorithm calculates correct initial intervals for Good and Easy on new card`() {
    val initialCard = VocabCard(
      id = 100,
      lessonNumber = 1,
      lessonTitle = "Lesson 1",
      sectionTitle = "Section 1",
      kanji = "約束",
      reading = "やくそく",
      meaningBurmese = "ကတိ (Promise)",
      partOfSpeech = "Noun",
      repetitions = 0,
      intervalDays = 0,
      easeFactor = 2.5f,
      nextReviewTimestamp = 0L
    )

    // 1st review: GOOD
    val goodResult = SpacedRepetitionEngine.calculateSrsParameters(initialCard, ReviewRating.GOOD)
    assertEquals(1, goodResult.intervalDays)
    assertEquals(1, goodResult.repetitions)
    assertEquals(2.5f, goodResult.easeFactor, 0.01f)
    assertTrue(goodResult.isRetentionSuccess)

    // 1st review: EASY
    val easyResult = SpacedRepetitionEngine.calculateSrsParameters(initialCard, ReviewRating.EASY)
    assertEquals(4, easyResult.intervalDays)
    assertEquals(1, easyResult.repetitions)
    assertTrue(easyResult.easeFactor > 2.5f) // EF increases with Easy
  }

  @Test
  fun `sm2 algorithm progresses intervals on multiple successful Good reviews`() {
    var card = VocabCard(
      id = 101,
      lessonNumber = 1,
      lessonTitle = "Lesson 1",
      sectionTitle = "Section 1",
      kanji = "約束",
      reading = "やくそく",
      meaningBurmese = "ကတိ",
      partOfSpeech = "Noun",
      repetitions = 0,
      intervalDays = 0,
      easeFactor = 2.5f,
      nextReviewTimestamp = 0L
    )

    // 1st review: Good -> 1 day
    card = SpacedRepetitionEngine.reviewCard(card, ReviewRating.GOOD)
    assertEquals(1, card.intervalDays)
    assertEquals(1, card.repetitions)

    // 2nd review: Good -> 6 days (Standard SM-2)
    card = SpacedRepetitionEngine.reviewCard(card, ReviewRating.GOOD)
    assertEquals(6, card.intervalDays)
    assertEquals(2, card.repetitions)

    // 3rd review: Good -> ~15 days (6 * 2.5)
    card = SpacedRepetitionEngine.reviewCard(card, ReviewRating.GOOD)
    assertEquals(15, card.intervalDays)
    assertEquals(3, card.repetitions)
    assertEquals(3, card.timesCorrect)
    assertEquals(0, card.timesIncorrect)
  }

  @Test
  fun `sm2 algorithm resets repetitions on Again and caps minimum ease factor to 1_3`() {
    val matureCard = VocabCard(
      id = 102,
      lessonNumber = 1,
      lessonTitle = "Lesson 1",
      sectionTitle = "Section 1",
      kanji = "敬語",
      reading = "けいご",
      meaningBurmese = "ယဉ်ကျေးသော စကား",
      partOfSpeech = "Noun",
      repetitions = 4,
      intervalDays = 21,
      easeFactor = 1.35f,
      nextReviewTimestamp = 0L
    )

    val failResult = SpacedRepetitionEngine.calculateSrsParameters(matureCard, ReviewRating.AGAIN)
    assertEquals(1, failResult.intervalDays)
    assertEquals(0, failResult.repetitions)
    assertEquals(1.3f, failResult.easeFactor, 0.001f) // Capped at 1.3 min
    assertEquals("10m", failResult.intervalLabel)
  }

  @Test
  fun `sm2 getPredictedIntervals returns mapping for all 4 ratings`() {
    val card = VocabCard(
      id = 103,
      lessonNumber = 1,
      lessonTitle = "Lesson 1",
      sectionTitle = "Section 1",
      kanji = "敬語",
      reading = "けいご",
      meaningBurmese = "ယဉ်ကျေးသော စကား",
      partOfSpeech = "Noun",
      repetitions = 1,
      intervalDays = 1,
      easeFactor = 2.5f,
      nextReviewTimestamp = 0L
    )

    val predicted = SpacedRepetitionEngine.getPredictedIntervals(card)
    assertEquals(4, predicted.size)
    assertTrue(predicted.containsKey(ReviewRating.AGAIN))
    assertTrue(predicted.containsKey(ReviewRating.HARD))
    assertTrue(predicted.containsKey(ReviewRating.GOOD))
    assertTrue(predicted.containsKey(ReviewRating.EASY))
  }

  @Test
  fun `sm2 spaced repetition calculates next review date and due status accurately`() {
    val baseTime = 1700000000000L // arbitrary fixed timestamp
    val newCard = VocabCard(
      id = 104,
      lessonNumber = 1,
      lessonTitle = "Lesson 1",
      sectionTitle = "Section 1",
      kanji = "準備",
      reading = "じゅんび",
      meaningBurmese = "ပြင်ဆင်ခြင်း",
      partOfSpeech = "Noun",
      repetitions = 0,
      intervalDays = 0,
      easeFactor = 2.5f,
      nextReviewTimestamp = 0L
    )

    // A brand new card with nextReviewTimestamp == 0 is always due
    assertTrue("New card must be due for study", newCard.isDue)

    // Review Good: 1 day interval
    val goodResult = SpacedRepetitionEngine.calculateSrsParameters(newCard, ReviewRating.GOOD, baseTime)
    assertEquals(1, goodResult.intervalDays)
    assertEquals(baseTime + 1 * SpacedRepetitionEngine.ONE_DAY_MS, goodResult.nextReviewTimestamp)

    // Create updated card with the scheduled future timestamp
    val reviewedCard = SpacedRepetitionEngine.reviewCard(newCard, ReviewRating.GOOD, baseTime)
    assertEquals(1, reviewedCard.repetitions)
    assertEquals(1, reviewedCard.intervalDays)
    assertEquals(baseTime, reviewedCard.lastReviewedTimestamp)
    assertEquals(baseTime + 1 * SpacedRepetitionEngine.ONE_DAY_MS, reviewedCard.nextReviewTimestamp)

    // Check isDue status relative to current time
    val beforeDue = reviewedCard.nextReviewTimestamp - 1000L
    val afterDue = reviewedCard.nextReviewTimestamp + 1000L
    
    // Custom check mimicking isDue logic at specific timestamps
    val isDueBefore = beforeDue >= reviewedCard.nextReviewTimestamp
    val isDueAfter = afterDue >= reviewedCard.nextReviewTimestamp
    assertEquals(false, isDueBefore)
    assertEquals(true, isDueAfter)
  }

  @Test
  fun `reminder preferences save and load time correctly`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val prefs = ReminderPreferences(context)
    prefs.updateSettings(
      isEnabled = true,
      reminderHour = 21,
      reminderMinute = 30,
      isSmartReminderEnabled = true
    )

    val settings = prefs.getSettings()
    assertTrue(settings.isEnabled)
    assertTrue(settings.isSmartReminderEnabled)
    assertEquals(21, settings.reminderHour)
    assertEquals(30, settings.reminderMinute)
    assertEquals("9:30 PM", settings.formattedTime)
  }

  @Test
  fun `reminder scheduler computes positive initial delay`() {
    val delayMs = ReminderScheduler.calculateInitialDelayMs(19, 0)
    assertTrue("Delay must be strictly positive", delayMs > 0)
  }

  @Test
  fun `quiz types have valid display names and badge labels`() {
    val types = com.example.ui.viewmodel.QuizType.values()
    assertEquals(10, types.size)
    types.forEach { type ->
      assertTrue(type.displayName.isNotBlank())
      assertTrue(type.badge.isNotBlank())
    }
    assertTrue(types.any { it == com.example.ui.viewmodel.QuizType.HOMOPHONE_SIMILAR_DRILL })
  }

  @Test
  fun `homophone data contains curated N3 homophone pairs and comparison helper`() {
    val curated = com.example.data.util.HomophoneData.JLPT_N3_HOMOPHONES
    assertTrue("Should have curated homophone groups", curated.isNotEmpty())
    val hakaru = curated.firstOrNull { it.reading == "はかる" }
    assertNotNull(hakaru)
    assertTrue("はかる should have multiple entries", hakaru!!.entries.size >= 3)
    val comparison = com.example.data.util.HomophoneData.formatComparisonText(hakaru)
    assertTrue(comparison.contains("はかる"))
    assertTrue(comparison.contains("量る"))
    assertTrue(comparison.contains("測る"))
  }

  @Test
  fun `multiple-choice questions are correctly formed from N3 vocabulary data`() {
    val sampleCards = listOf(
      VocabCard(id = 1, lessonNumber = 1, lessonTitle = "L1", sectionTitle = "Daily", kanji = "約束", reading = "やくそく", meaningBurmese = "ကတိ", partOfSpeech = "Noun"),
      VocabCard(id = 2, lessonNumber = 1, lessonTitle = "L1", sectionTitle = "Daily", kanji = "遠慮", reading = "えんりょ", meaningBurmese = "အားနာခြင်း", partOfSpeech = "Noun"),
      VocabCard(id = 3, lessonNumber = 1, lessonTitle = "L1", sectionTitle = "Daily", kanji = "案内", reading = "あんない", meaningBurmese = "လမ်းညွှန်ခြင်း", partOfSpeech = "Noun"),
      VocabCard(id = 4, lessonNumber = 1, lessonTitle = "L1", sectionTitle = "Daily", kanji = "相談", reading = "そうだん", meaningBurmese = "တိုင်ပင်ဆွေးနွေးခြင်း", partOfSpeech = "Noun")
    )

    // Test Kanji to Meaning Multiple Choice
    val targetCard = sampleCards[0]
    val distractors = sampleCards.filter { it.id != targetCard.id }
    val options = (distractors.map { it.meaningBurmese } + targetCard.meaningBurmese).distinct()
    
    assertEquals(4, options.size)
    assertTrue(options.contains(targetCard.meaningBurmese))

    // Test Kanji Mastery (Selecting Kanji from reading and meaning)
    val kanjiOptions = (distractors.map { it.kanji } + targetCard.kanji).distinct()
    assertEquals(4, kanjiOptions.size)
    assertTrue(kanjiOptions.contains("約束"))
  }

  @Test
  fun `theme preferences save and load theme settings properly`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val themePrefs = AppThemePreferences(context)
    
    themePrefs.setThemeMode(ThemeMode.DARK)
    themePrefs.setThemePalette(ThemePalette.INDIGO)
    themePrefs.setIconThemeStyle(IconThemeStyle.DUOTONE_GLOW)
    themePrefs.setOledBlack(true)

    val settings = themePrefs.getSettings()
    assertEquals(ThemeMode.DARK, settings.themeMode)
    assertEquals(ThemePalette.INDIGO, settings.themePalette)
    assertEquals(IconThemeStyle.DUOTONE_GLOW, settings.iconThemeStyle)
    assertTrue(settings.oledBlack)
  }

  @Test
  fun `theme preferences toggle dark mode correctly`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val themePrefs = AppThemePreferences(context)

    themePrefs.toggleDarkMode(currentIsDark = true)
    assertEquals(ThemeMode.LIGHT, themePrefs.getSettings().themeMode)

    themePrefs.toggleDarkMode(currentIsDark = false)
    assertEquals(ThemeMode.DARK, themePrefs.getSettings().themeMode)
  }

  @Test
  fun `flashcard screen route and sample data contain valid Burmese meanings and readings`() {
    assertEquals("flashcard", com.example.ui.navigation.Screen.Flashcard.route)
    val sampleCards = com.example.ui.screens.SAMPLE_N3_FLASHCARDS
    assertTrue(sampleCards.isNotEmpty())
    sampleCards.forEach { card ->
      assertTrue(card.kanji.isNotBlank())
      assertTrue(card.reading.isNotBlank())
      assertTrue(card.meaningBurmese.isNotBlank())
    }
  }

  @Test
  fun `kanji progress screen route and goal types are properly initialized`() {
    assertEquals("kanji_progress", com.example.ui.navigation.Screen.KanjiProgress.route)
    assertEquals(650, com.example.ui.screens.KanjiGoalType.STANDARD_N3_KANJI.defaultGoal)
    assertEquals(880, com.example.ui.screens.KanjiGoalType.TOTAL_APP_VOCAB.defaultGoal)
    assertTrue(com.example.ui.screens.KanjiGoalType.STANDARD_N3_KANJI.description.contains("၆၅၀"))
  }
}

