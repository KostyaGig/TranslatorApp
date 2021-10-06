package com.zinoview.translatorapp.domain

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.DataWord

sealed class DomainWord : Abstract.Word {

    class Success(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: Language
    ) : DomainWord() {

        override fun <T> map(mapper: Abstract.WordMapper<T>): T
            = mapper.map(translatedWord, srcWord, language)
    }

    class Failure(
        private val message: String,
    ) : DomainWord() {

        override fun <T> map(mapper: Abstract.WordMapper<T>): T
            = mapper.map(message)
    }

}