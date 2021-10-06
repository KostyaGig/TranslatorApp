package com.zinoview.translatorapp.ui

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.domain.DomainWord

sealed class UiWord : Abstract.Word {

    class Success(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: Language
    ) : UiWord() {

        override fun <T> map(mapper: Abstract.WordMapper<T>): T
            = mapper.map(translatedWord, srcWord, language)
    }

    class Failure(
        private val message: String,
    ) : UiWord() {

        override fun <T> map(mapper: Abstract.WordMapper<T>): T
            = mapper.map(message)
    }

}