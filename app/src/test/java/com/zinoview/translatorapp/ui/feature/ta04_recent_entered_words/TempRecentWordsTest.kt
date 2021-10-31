package com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words.TempRecentWords.Test]
 * */

class TempRecentWordsTest {

    private lateinit var tempRecentWords: TempRecentWords

    @Before
    fun setUp() {
        tempRecentWords = TempRecentWords.Test()
    }


    @Test
    fun test_unique_words() {
        val expected = listOf<String>("Дом","Ноутбук","Квартира")

        tempRecentWords.addNewWord("Квартира")
        tempRecentWords.addNewWord("Ноутбук")
        tempRecentWords.addNewWord("Ноутбук")
        tempRecentWords.addNewWord("Дом")
        tempRecentWords.addNewWord("Квартира")

        val actual = tempRecentWords.read()

        assertEquals(expected, actual)
    }

    @Test
    fun test_add_recent_word_size_less_six() {
        val expected = listOf<String>("Футболка","Андроид","Квартира","Игра","Дом")

        tempRecentWords.addNewWord("Дом")
        tempRecentWords.addNewWord("Игра")
        tempRecentWords.addNewWord("Квартира")
        tempRecentWords.addNewWord("Андроид")
        tempRecentWords.addNewWord("Футболка")

        val actual = tempRecentWords.read()

        assertEquals(expected, actual)
    }

    @Test
    fun test_add_recent_word_size_more_six() {
        val expected = listOf<String>("Фигурка","Книга","Жилье","Учеба","Кнопка","Индиктор","Состояние")

        tempRecentWords.addNewWord("Пенал")
        tempRecentWords.addNewWord("Загрузка")
        tempRecentWords.addNewWord("Состояние")
        tempRecentWords.addNewWord("Индиктор")
        tempRecentWords.addNewWord("Кнопка")
        tempRecentWords.addNewWord("Учеба")
        tempRecentWords.addNewWord("Жилье")
        tempRecentWords.addNewWord("Книга")
        tempRecentWords.addNewWord("Фигурка")

        val actual = tempRecentWords.read()
        assertEquals(expected,actual)
    }

}