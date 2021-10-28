package com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.*
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextView
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.WordsAdapter

sealed class UiWordsStateRecyclerView
    : Abstract.Words,
    UiShow<Pair<WordTextView, WordTextView>,WordsAdapter> {

    override fun show(arg: Pair<WordTextView, WordTextView>) = Unit

    override fun uiShow(arg: WordsAdapter) = Unit

    open fun print() = Unit

    object Empty : UiWordsStateRecyclerView() {

        override fun print() {
            log("UiWordsStateRecyclerView.Empty")
        }
    }

    override fun <T> map(mapper: Abstract.WordsMapper<T>): T
        =  throw IllegalStateException("UiWords state not use map")


    object Progress : UiWordsStateRecyclerView() {

        override fun uiShow(arg: WordsAdapter)
            = arg.show(listOf(this))

        override fun print() {
            log("UiWordsStateRecyclerView.Progress")
        }
    }

    class Success(
        private val words: List<CacheWord>
    ) : UiWordsStateRecyclerView() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
                = mapper.map(words)

        override fun uiShow(arg: WordsAdapter) {
            val uiWordStateRecyclerViewMapper = UiWordStateRecyclerViewMapper.Base()
            val uiWords = words.map { cachedWord ->
                cachedWord.map(uiWordStateRecyclerViewMapper)
            }
            arg.show(uiWords)
        }

        override fun print() {
            log("UiWordsStateRecyclerView.Success size cache ${words.size}")
        }
    }

    class Base(
        private val translatedWord: String,
        private val srcWord: String,
        private val language: Language
    ) : UiWordsStateRecyclerView() {

        override fun show(arg: Pair<WordTextView, WordTextView>) {
            arg.first.text(translatedWord)
            arg.second.text(srcWord)
        }
    }

}