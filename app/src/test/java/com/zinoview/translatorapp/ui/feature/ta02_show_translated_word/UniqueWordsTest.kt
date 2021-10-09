package com.zinoview.translatorapp.ui.feature.ta02_show_translated_word

import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordState
import org.junit.Assert.*
import org.junit.Test

class UniqueWordsTest {

    @Test
    fun test_add_items() {
        val uniqueWordsTest = UniqueWords.Test()
        val firstWord = UiWordState.Test(
            "Home","Дом"
        )
        val secondWord = UiWordState.Test(
            "Fish","Рыба"
        )

        uniqueWordsTest.addItem(firstWord)
        uniqueWordsTest.addItem(secondWord)

        val expected = listOf<UiWordState>(
            UiWordState.Test(
                "Home","Дом"
            ),
            UiWordState.Test(
                "Fish","Рыба"
            )
        )
        val actual = uniqueWordsTest.uniques()
        assertEquals(expected, actual)
    }

    @Test
    fun test_add_unique_items() {
        val uniqueWordsTest = UniqueWords.Test()
        val firstWord = UiWordState.Test(
            "Home","Дом"
        )
        val secondWord = UiWordState.Test(
            "Fish","Рыба"
        )
        val thirdWord = UiWordState.Test(
            "Fish","Рыба"
        )

        uniqueWordsTest.addItem(firstWord)
        uniqueWordsTest.addItem(secondWord)
        uniqueWordsTest.addItem(thirdWord)

        val expected = listOf<UiWordState>(
            UiWordState.Test(
                "Home","Дом"
            ),
            UiWordState.Test(
                "Fish","Рыба"
            )
        )
        val actual = uniqueWordsTest.uniques()
        assertEquals(expected, actual)
    }
}