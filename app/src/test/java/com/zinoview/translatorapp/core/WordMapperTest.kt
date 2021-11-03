package com.zinoview.translatorapp.core

import com.zinoview.translatorapp.data.cache.db.CacheWord
import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [TestWordMapper]
 */

class WordMapperTest {

    @Test
    fun test_success_map() {
        val mapper = TestWordMapper()
        val language = Language.Test()
        val dataWord = TestDataWord.Success(
            "House","Дом",language
        )
        val expected = TestDomainWord.Success(
            "House","Дом",language
        )
        val actual = dataWord.map(mapper)
        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_map() {
        val mapper = TestWordMapper()
        val dataWord = TestDataWord.Failure(
           "No connection"
        )
        val expected = TestDomainWord.Failure(
            "No connection"
        )
        val actual = dataWord.map(mapper)
        assertEquals(expected, actual)
        assertEquals(actual is TestDomainWord.Failure,true)
    }

    private inner class TestWordMapper : Abstract.WordsMapper<TestDomainWord> {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): TestDomainWord
                = TestDomainWord.Success(translatedWord,srcWord, language)

        override fun cachedMap(
            translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
        ): TestDomainWord = TestDomainWord.Failure("")

        override fun map(message: String): TestDomainWord
                = TestDomainWord.Failure(message)

        override fun map(cachedWords: List<CacheWord>, position: Int): TestDomainWord {
            return TestDomainWord.Success("","",Language.Test())
        }

    }

    private interface TestDataWord : Abstract.Words {

        data class Success(private val translatedWord: String,
                      private val srcWord: String,
                      private val language: Language) : TestDataWord {

            override fun <T> map(mapper: Abstract.WordsMapper<T>): T
                    = mapper.map(translatedWord, srcWord, language)
        }

        data class Failure(private val message: String) : TestDataWord {
            override fun <T> map(mapper: Abstract.WordsMapper<T>): T
                    = mapper.map(message)
        }
    }

    private interface TestDomainWord: Abstract.Words {

        data class Success(private val translatedWord: String,
                      private val srcWord: String,
                      private val language: Language) : TestDomainWord {

            override fun <T> map(mapper: Abstract.WordsMapper<T>): T
                    = mapper.map(translatedWord, srcWord, language)
        }

        data class Failure(private val message: String) : TestDomainWord {
            override fun <T> map(mapper: Abstract.WordsMapper<T>): T
                    = mapper.map(message)
        }
    }
}