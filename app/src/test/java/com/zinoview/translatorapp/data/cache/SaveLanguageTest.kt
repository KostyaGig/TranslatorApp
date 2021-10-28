package com.zinoview.translatorapp.data.cache

import org.junit.Assert.*
import org.junit.Test

class SaveLanguageTest {

    @Test
    fun test_save_words() {
        val realmProvider = RealmProvider.Test()
        val saveLanguage = SaveLanguage.Test

        var srcWord = "Дом"
        var translatedWord = "House"

        saveLanguage.saveToDb(realmProvider, translatedWord, srcWord)

        var expected = listOf(
            Pair("House","Дом")
        )
        var actual = saveLanguage.read()
        assertEquals(expected, actual)


        srcWord = "Телефон"
        translatedWord = "Phone"

        saveLanguage.saveToDb(realmProvider, translatedWord, srcWord)

        srcWord = "Ноутбук"
        translatedWord = "Laptop"

        saveLanguage.saveToDb(realmProvider, translatedWord, srcWord)

        expected = listOf(
            Pair("House","Дом"),
            Pair("Phone","Телефон"),
            Pair("Laptop","Ноутбук")
        )

        actual = saveLanguage.read()
        assertEquals(expected, actual)
    }
}