package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.reminder.ReminderPreferences
import com.example.reminder.ReminderScheduler
import org.junit.Assert.assertEquals
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
    assertEquals(6, types.size)
    types.forEach { type ->
      assertTrue(type.displayName.isNotBlank())
      assertTrue(type.badge.isNotBlank())
    }
  }
}

