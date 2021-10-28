package com.zinoview.translatorapp.domain

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord

sealed class DomainWords : Abstract.Words {

    class Success(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: Language
    ) : DomainWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(translatedWord, srcWord, language)
    }

    data class Cache(
        private val words: List<CacheWord>
    ) : DomainWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(words)
    }

    class Failure(
        private val message: String,
    ) : DomainWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(message)
    }

}