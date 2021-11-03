package com.zinoview.translatorapp.data.words.cache

import com.zinoview.translatorapp.data.words.DataWords
import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import com.zinoview.translatorapp.data.words.cache.db.Database
import java.lang.IllegalStateException

interface CacheDataSource<T> {

    suspend fun saveWord(word: DataWords)

    //param isFavorite need for tests
    suspend fun updateWord(translatedWord: String,isFavorite: Boolean = false) : T

    suspend fun words() : List<T>

    suspend fun contains(word: DataWords) : Boolean

    suspend fun isFavorite(dataWord: DataWords): Boolean

    class Base(
        private val database: Database.Room
    ) : CacheDataSource<CacheWord> {

        override suspend fun saveWord(word: DataWords) {
            word.save(database)
        }

        override suspend fun words(): List<CacheWord> {
            return database.objects()
        }

        override suspend fun updateWord(translatedWord: String,isFavorite: Boolean) : CacheWord {
            return database.updateObject(translatedWord)
        }

        override suspend fun contains(word: DataWords): Boolean {
            return word.contains(database)
        }

        override suspend fun isFavorite(dataWord: DataWords): Boolean {
            return dataWord.isFavorite(database)
        }
    }

    class Test : CacheDataSource<Test.TestWord> {

        class TestWord(
            val srcWord: String,
            val translatedWord: String,
            val isFavorite: Boolean = false,
        ) {

            fun contains(translatedWord: String)
                = this.translatedWord == translatedWord
        }

        private val data = ArrayList<TestWord>()

        override suspend fun saveWord(word: DataWords) {
            val testWord = word as DataWords.Test
            data.add(
                TestWord(testWord.srcWord,testWord.translatedWord)
            )
        }

        override suspend fun words(): List<TestWord>
            = data

        override suspend fun updateWord(translatedWord: String,isFavorite: Boolean) : TestWord {
            var indexChangedElement = 0
            data.forEachIndexed {index, word ->
                if (word.contains(translatedWord)) {
                    data[index] = TestWord(word.srcWord,word.translatedWord,isFavorite)
                    indexChangedElement = index
                }
            }
            return data[indexChangedElement]
        }

        override suspend fun contains(word: DataWords): Boolean {
            var contains = false
            val testWord = word as DataWords.Test
            data.forEach { currentWord ->
                if (currentWord.contains(testWord.translatedWord)) {
                    contains = true
                }
            }
            return contains
        }

        override suspend fun isFavorite(dataWord: DataWords): Boolean {
            val testWord = dataWord as DataWords.Test
            return testWord.isFavorite
        }
    }

    class TestPair : CacheDataSource<Pair<String, String>> {

            private val data = ArrayList<Pair<String,String>>()

            override suspend fun saveWord(word: DataWords) {
                val testWord = word as DataWords.Test
                data.add(
                    Pair(
                        testWord.translatedWord,
                        testWord.srcWord
                    )
                )
            }

            override suspend fun words(): List<Pair<String,String>>
                    = data

        override suspend fun updateWord(
            translatedWord: String,
            isFavorite: Boolean
        ): Pair<String, String> {
            var element: Pair<String, String>? = null
            data.forEach {pair ->
                if (pair.second == translatedWord) {
                    element = pair
                }
            }
            return Pair(element!!.first, element!!.second + " " + isFavorite)
        }

        override suspend fun contains(word: DataWords): Boolean
                = throw IllegalStateException("TestPair not use contains()")

        override suspend fun isFavorite(dataWord: DataWords): Boolean
            = throw IllegalStateException("TestPair not use isFavorite()")

    }
}
