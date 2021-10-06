package com.zinoview.translatorapp.ui

import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.ui.view.WordProgressBar
import com.zinoview.translatorapp.ui.view.WordTextView

sealed class UiWordState {

    open fun show(wordTv: WordTextView,wordPb: WordProgressBar) = Unit

    object Progress : UiWordState() {

        override fun show(wordTv: WordTextView, wordPb: WordProgressBar) {
            wordTv.hide()
            wordPb.show()
        }
    }

    class Success(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: Language
    ) : UiWordState() {

        override fun show(wordTv: WordTextView, wordPb: WordProgressBar) {
            wordTv.text(translatedWord)
            wordTv.show()
            wordPb.hide()
        }
    }

    class Failure(private val message: String) : UiWordState() {

        override fun show(wordTv: WordTextView, wordPb: WordProgressBar) {
            wordTv.text(message)
            wordTv.show()
            wordPb.hide()
        }
    }
}