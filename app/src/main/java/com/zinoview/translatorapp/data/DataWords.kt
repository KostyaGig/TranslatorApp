package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.data.cache.Database
import com.zinoview.translatorapp.data.cache.DataBaseOperationLanguage
import com.zinoview.translatorapp.ui.core.log

sealed class DataWords : Abstract.Words {

    open suspend fun save(realm: Database.Room) = Unit

    //remove later
    open fun printUpdatedWord() = Unit

    data class Success(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: DataBaseOperationLanguage
    ) : DataWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(translatedWord, srcWord, language)

        override suspend fun save(realm: Database.Room) {
            realm.insertObject(
                Triple(translatedWord,srcWord,language)
            )
        }
    }

    data class Cache(
        private val words: List<CacheWord>,
        private val position: Int = -1
    ) : DataWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(words,position)

        override fun printUpdatedWord() {
            log("Updated word ${words[0].src} is favorite ${words[0].isFavorite}")
        }
    }

    data class Failure(
        private val message: String,
    ) : DataWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(message)
    }

    data class Test(
        val srcWord: String,
        val translatedWord: String
    ) : DataWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T = mapper.map("")
    }

}