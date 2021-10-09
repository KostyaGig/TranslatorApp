package com.zinoview.translatorapp.ui.feature.ta01_translate_word

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextView

sealed class UiWord : Abstract.Word, Show<Pair<WordTextView,WordTextView>> {

    override fun show(arg: Pair<WordTextView, WordTextView>) = Unit

    class Success(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: Language
    ) : UiWord() {

        override fun <T> map(mapper: Abstract.WordMapper<T>): T
            = mapper.map(translatedWord, srcWord, language)

        override fun show(arg: Pair<WordTextView, WordTextView>) {
            arg.first.text(translatedWord)
            arg.second.text(srcWord)
        }
    }

    class Failure(
        private val message: String,
    ) : UiWord() {

        override fun <T> map(mapper: Abstract.WordMapper<T>): T
            = mapper.map(message)
    }

}