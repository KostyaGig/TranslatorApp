package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language

sealed class DataWord : Abstract.Word {

    data class Success(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: Language
    ) : DataWord() {

        override fun <T> map(mapper: Abstract.WordMapper<T>): T
            = mapper.map(translatedWord, srcWord, language)
    }

    data class Failure(
        private val message: String,
    ) : DataWord() {

        override fun <T> map(mapper: Abstract.WordMapper<T>): T
            = mapper.map(message)
    }

}