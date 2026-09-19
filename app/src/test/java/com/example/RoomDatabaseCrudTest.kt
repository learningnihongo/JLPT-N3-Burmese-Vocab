package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.AppDatabase
import com.example.data.db.CustomFlashcardDao
import com.example.data.db.KanjiDao
import com.example.data.db.KotobaDao
import com.example.data.model.CustomFlashcardEntity
import com.example.data.model.KanjiEntity
import com.example.data.model.KotobaEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class RoomDatabaseCrudTest {

    private lateinit var db: AppDatabase
    private lateinit var kanjiDao: KanjiDao
    private lateinit var kotobaDao: KotobaDao
    private lateinit var customFlashcardDao: CustomFlashcardDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        kanjiDao = db.kanjiDao()
        kotobaDao = db.kotobaDao()
        customFlashcardDao = db.customFlashcardDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testKanjiCrudOperations() = runBlocking {
        // 1. Create (Insert)
        val kanji = KanjiEntity(
            kanji = "日",
            onyomi = "ニチ, ジツ",
            kunyomi = "ひ, -び, -か",
            meaningBurmese = "နေ၊ နေ့ရက်",
            meaningEnglish = "Day, Sun",
            strokeCount = 4,
            jlptLevel = "N5"
        )
        val insertedId = kanjiDao.insertKanji(kanji)
        assertTrue(insertedId > 0)

        // 2. Read
        val fetched = kanjiDao.getKanjiByIdDirect(insertedId)
        assertNotNull(fetched)
        assertEquals("日", fetched?.kanji)
        assertEquals("ニチ, ジツ", fetched?.onyomi)
        assertEquals("နေ၊ နေ့ရက်", fetched?.meaningBurmese)

        // 3. Update
        val updated = fetched!!.copy(meaningEnglish = "Sun, Day, Japan", isBookmarked = true)
        kanjiDao.updateKanji(updated)

        val fetchedUpdated = kanjiDao.getKanjiByIdDirect(insertedId)
        assertEquals("Sun, Day, Japan", fetchedUpdated?.meaningEnglish)
        assertTrue(fetchedUpdated?.isBookmarked == true)

        // Search
        val searchResults = kanjiDao.searchKanji("နေ").first()
        assertEquals(1, searchResults.size)

        // 4. Delete
        kanjiDao.deleteKanjiById(insertedId)
        val deleted = kanjiDao.getKanjiByIdDirect(insertedId)
        assertNull(deleted)
    }

    @Test
    fun testKotobaCrudOperations() = runBlocking {
        // 1. Create (Insert)
        val kotoba = KotobaEntity(
            word = "勉強する",
            reading = "べんきょうする",
            romaji = "benkyousuru",
            meaningBurmese = "စာကျက်သည်၊ လေ့လာသည်",
            meaningEnglish = "To study",
            partOfSpeech = "Verb Group 3",
            exampleSentenceJapanese = "毎日日本語を勉強します。",
            exampleSentenceBurmese = "နေ့တိုင်း ဂျပန်စာ လေ့လာပါတယ်။",
            lessonNumber = 1
        )
        val insertedId = kotobaDao.insertKotoba(kotoba)
        assertTrue(insertedId > 0)

        // 2. Read
        val fetched = kotobaDao.getKotobaByIdDirect(insertedId)
        assertNotNull(fetched)
        assertEquals("勉強する", fetched?.word)
        assertEquals("べんきょうする", fetched?.reading)

        // 3. Update
        val updated = fetched!!.copy(personalNotes = "Very important core verb")
        kotobaDao.updateKotoba(updated)

        val fetchedUpdated = kotobaDao.getKotobaByIdDirect(insertedId)
        assertEquals("Very important core verb", fetchedUpdated?.personalNotes)

        // 4. Delete
        kotobaDao.deleteKotobaById(insertedId)
        val deleted = kotobaDao.getKotobaByIdDirect(insertedId)
        assertNull(deleted)
    }

    @Test
    fun testCustomFlashcardCrudOperations() = runBlocking {
        // 1. Create (Insert)
        val customCard = CustomFlashcardEntity(
            front = "一期一会",
            reading = "いちごいちえ",
            back = "တစ်သက်မှာ တစ်ကြိမ်သာ ကြုံရသော တွေ့ဆုံခြင်း (Treasure every encounter)",
            cardType = "KANJI",
            category = "Proverbs",
            tags = "Proverb,Wisdom"
        )
        val insertedId = customFlashcardDao.insertCard(customCard)
        assertTrue(insertedId > 0)

        // 2. Read
        val fetched = customFlashcardDao.getCardByIdDirect(insertedId)
        assertNotNull(fetched)
        assertEquals("一期一会", fetched?.front)
        assertEquals("いちごいちえ", fetched?.reading)
        assertTrue(fetched?.hasTag("Proverb") == true)

        // 3. Update (bookmark, notes, review stats)
        customFlashcardDao.setBookmark(insertedId, true)
        val bookmarked = customFlashcardDao.getBookmarkedCards().first()
        assertEquals(1, bookmarked.size)
        assertEquals(insertedId, bookmarked[0].id)

        // 4. Delete
        val deleteCount = customFlashcardDao.deleteCardById(insertedId)
        assertEquals(1, deleteCount)
        val deleted = customFlashcardDao.getCardByIdDirect(insertedId)
        assertNull(deleted)
    }
}
