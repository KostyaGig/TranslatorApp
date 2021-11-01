package com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.data.cache.CacheWordMapper
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.*
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextView
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.WordsAdapter


sealed class UiWordsStateRecyclerView
    : Abstract.Words,
    UiShow<Pair<WordTextView, WordTextView>,WordsAdapter> {

    override fun show(arg: Pair<WordTextView, WordTextView>) = Unit

    override fun uiShow(arg: WordsAdapter) = Unit

    open fun itemClick(position: Int,wordsAdapterItemClickListener: WordsAdapter.WordsAdapterItemClickListener)
        = Unit

    object Empty : UiWordsStateRecyclerView()

    override fun <T> map(mapper: Abstract.WordsMapper<T>): T
        =  throw IllegalStateException("UiWords state not use map")


    object Progress : UiWordsStateRecyclerView() {

        override fun uiShow(arg: WordsAdapter)
            = arg.show(listOf(this), -1)

    }

    class Success(
        private val words: List<CacheWord>,
        private val position: Int
    ) : UiWordsStateRecyclerView() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(words,position)

        override fun uiShow(arg: WordsAdapter) {
            val cacheWordMapper = CacheWordMapper.Base()
            val uiWordsStateRecyclerView = words.map { cachedWord ->
                log("uishow -> map(), thread ${Thread.currentThread().name}")
                cachedWord.map(cacheWordMapper)
            }
            arg.show(uiWordsStateRecyclerView,position)
        }

    }

    class Base(
        private val translatedWord: String,
        private val srcWord: String,
        private val language: Language,
        private val isFavorite: Boolean
    ) : UiWordsStateRecyclerView() {

        override fun show(arg: Pair<WordTextView, WordTextView>) {
            arg.first.text("$translatedWord isFavorite $isFavorite")
            arg.second.text(srcWord)
        }

        override fun itemClick(position: Int,wordsAdapterItemClickListener: WordsAdapter.WordsAdapterItemClickListener)
            = wordsAdapterItemClickListener.itemClick(position,srcWord,!isFavorite)
    }

}