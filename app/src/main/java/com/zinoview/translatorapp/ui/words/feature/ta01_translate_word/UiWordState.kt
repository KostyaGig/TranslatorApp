package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

import com.zinoview.translatorapp.core.words.Abstract
import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.view.WordProgressBar
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.view.WordTextView
import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.TempRecentWords
import com.zinoview.translatorapp.ui.words.feature.ta05_favorite_words.view.ItemView


sealed class UiWordState
    : Abstract.Words,
    Same<UiWordState>,
    Compare<String> {

    override fun same(item: UiWordState): Boolean = false
    override fun compare(arg1: String, arg2: String): Boolean = false

    override fun <T> map(mapper: Abstract.WordsMapper<T>): T
        =  throw IllegalStateException("UiWords state not use map()")


    open fun show(wordTv: WordTextView, wordPb: WordProgressBar,view: ItemView) = Unit

    open fun changeRecentQuery(tempRecentWords: TempRecentWords) = Unit

    object Empty : UiWordState()

    object Progress : UiWordState() {

        override fun show(wordTv: WordTextView, wordPb: WordProgressBar, view: ItemView) {
            wordTv.hide()
            wordPb.show()
        }
    }

    open class Base(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: Language,
    ) : UiWordState() {

        override fun show(wordTv: WordTextView, wordPb: WordProgressBar,view: ItemView) {
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

        class Success(
            private val srcWord: String,
            translatedWord: String,
            language: Language
        ) : Base(srcWord, translatedWord, language) {

            override fun show(wordTv: WordTextView, wordPb: WordProgressBar, view: ItemView) {
                super.show(wordTv, wordPb, view)
                log("Show Success,src $srcWord")
                view.changeBackground(false)

            }
        }

        class TranslatedCache(
            private val srcWord: String,
            translatedWord: String,
            language: Language,
            private val isFavorite: Boolean
        ) : Base(srcWord, translatedWord, language) {

            override fun show(wordTv: WordTextView, wordPb: WordProgressBar, view: ItemView) {
                log("Show TranslatedCache,src $srcWord isFavorite $isFavorite")
                super.show(wordTv, wordPb, view)
                view.changeBackground(isFavorite)
            }
        }

    }

    class Failure(private val message: String) : UiWordState() {

        override fun show(wordTv: WordTextView, wordPb: WordProgressBar,view: ItemView) {
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

