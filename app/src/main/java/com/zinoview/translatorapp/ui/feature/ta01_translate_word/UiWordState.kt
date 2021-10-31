package com.zinoview.translatorapp.ui.feature.ta01_translate_word

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordProgressBar
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextView
import com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words.TempRecentWords

sealed class UiWordState
    : Abstract.Words,
    Same<UiWordState>,
    Compare<String> {

    override fun same(item: UiWordState): Boolean = false
    override fun compare(arg1: String, arg2: String): Boolean = false

    override fun <T> map(mapper: Abstract.WordsMapper<T>): T
        =  throw IllegalStateException("UiWords state not use map()")


    open fun show(wordTv: WordTextView, wordPb: WordProgressBar) = Unit

    open fun changeRecentQuery(tempRecentWords: TempRecentWords) = Unit

    object Empty : UiWordState()

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

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(translatedWord, srcWord, language)

        override fun same(item: UiWordState): Boolean {
            return item.compare(srcWord,translatedWord)
        }

        override fun compare(arg1: String,arg2: String) : Boolean {
            return this.srcWord == arg1 && this.translatedWord == arg2
        }

        override fun changeRecentQuery(tempRecentWords: TempRecentWords)
            = tempRecentWords.addNewWord(srcWord)
    }

    class Failure(private val message: String) : UiWordState() {

        override fun show(wordTv: WordTextView, wordPb: WordProgressBar) {
            wordTv.text(message)
            wordTv.show()
            wordPb.hide()
        }

    }

    data class Test(
        private val translatedWord: String,
        private val srcWord: String
    ) : UiWordState() {

        override fun same(item: UiWordState): Boolean {
            return item.compare(srcWord,translatedWord)
        }

        override fun compare(arg1: String, arg2: String): Boolean {
            return this.srcWord == arg1 && this.translatedWord == arg2
        }
    }
}

