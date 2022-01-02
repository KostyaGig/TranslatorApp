package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.data.words.cache.DataBaseOperationLanguage
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [com.zinoview.translatorapp.data.words.cache.DataBaseOperationLanguage.Test.Base]
 * */

class SaveLanguageTest {

    @Test
    fun test_save_words() = runBlocking {
        val saveLanguage = DataBaseOperationLanguage.Test.Base

        var srcWord = "Дом"
        var translatedWord = "House"

        saveLanguage.saveToDb(translatedWord, srcWord)

        var expected = listOf(
            Pair("House","Дом")
        )
        var actual = saveLanguage.read()
        assertEquals(expected, actual)


        srcWord = "Телефон"
        translatedWord = "Phone"

        saveLanguage.saveToDb(translatedWord, srcWord)

        srcWord = "Ноутбук"
        translatedWord = "Laptop"

        saveLanguage.saveToDb(translatedWord, srcWord)

        expected = listOf(
            Pair("House","Дом"),
            Pair("Phone","Телефон"),
            Pair("Laptop","Ноутбук")
        )

        actual = saveLanguage.read()
        assertEquals(expected, actual)
    }
}