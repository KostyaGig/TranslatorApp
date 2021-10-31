package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.data.cache.Database
import com.zinoview.translatorapp.data.cache.core.Save
import com.zinoview.translatorapp.data.cache.DataBaseOperationLanguage

sealed class DataWords : Abstract.Words, Save<Database.Realm> {

    override fun save(realm: Database.Realm) = Unit

    data class Success(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: DataBaseOperationLanguage
    ) : DataWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(translatedWord, srcWord, language)

        override fun save(realm: Database.Realm) {
            realm.insertObject(
                Triple(translatedWord,srcWord,language)
            )
        }
    }

    data class Cache(
        private val words: List<CacheWord>
    ) : DataWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(words)
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