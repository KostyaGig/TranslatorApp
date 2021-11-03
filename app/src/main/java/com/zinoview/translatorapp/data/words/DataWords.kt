package com.zinoview.translatorapp.data.words

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.data.words.cache.db.Database
import com.zinoview.translatorapp.data.words.cache.DataBaseOperationLanguage
import com.zinoview.translatorapp.data.words.cache.db.CacheWord

sealed class DataWords : Abstract.Words {

    open suspend fun save(room: Database.Room) = Unit

    open suspend fun contains(room: Database.Room) : Boolean = false

    open suspend fun isFavorite(database: Database.Room): Boolean = false

    open class Base(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: DataBaseOperationLanguage
    ) : DataWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T {
            return mapper.map(translatedWord, srcWord, language)
        }

        class Success(
            private val srcWord: String,
            private val translatedWord: String,
            private val language: DataBaseOperationLanguage
        ) : Base(srcWord, translatedWord, language) {

            override suspend fun save(room: Database.Room) {
                room.insertObject(
                    Triple(translatedWord, srcWord, language)
                )
            }

            override suspend fun contains(room: Database.Room) : Boolean {
                return room.containsObject(translatedWord)
            }

            override suspend fun isFavorite(database: Database.Room): Boolean {
                return database.isFavoriteObject(translatedWord)
            }
        }

        class TranslatedCache(
            private val srcWord: String,
            private val translatedWord: String,
            private val language: DataBaseOperationLanguage,
            private val isFavorite: Boolean
        ) : Base(srcWord, translatedWord, language) {

            override fun <T> map(mapper: Abstract.WordsMapper<T>): T {
                return mapper.cachedMap(translatedWord, srcWord, language,isFavorite)
            }
        }
    }

    data class Cache(
        private val words: List<CacheWord>,
        private val position: Int = -1) : DataWords() {

            override fun <T> map(mapper: Abstract.WordsMapper<T>): T
                = mapper.map(words, position)
        }

    data class Failure(
        private val message: String,
    ) : DataWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T = mapper.map(message)
    }

    data class Test(
        val srcWord: String,
        val translatedWord: String,
        val isFavorite: Boolean = false
    ) : DataWords() {

            override fun <T> map(mapper: Abstract.WordsMapper<T>): T = mapper.map("")
        }

}