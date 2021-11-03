package com.zinoview.translatorapp.domain

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.data.cache.db.CacheWord


sealed class DomainWords : Abstract.Words {

    open class Base(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: DomainLanguage
    ) : DomainWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T =
            mapper.map(translatedWord, srcWord, language)

        class Success(
            srcWord: String,
            translatedWord: String,
            language: DomainLanguage
        ) : Base(srcWord, translatedWord, language)

        class TranslatedCache(
            private val srcWord: String,
            private val translatedWord: String,
            private val language: DomainLanguage,
            private val isFavorite: Boolean
        ) : Base(srcWord, translatedWord, language) {

            override fun <T> map(mapper: Abstract.WordsMapper<T>): T
                    = mapper.cachedMap(translatedWord, srcWord, language,isFavorite)
        }
    }

    data class Cache(
        private val words: List<CacheWord>,
        private val position: Int
    ) : DomainWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(words,position)
    }

    class Failure(
        private val message: String,
    ) : DomainWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(message)
    }

}