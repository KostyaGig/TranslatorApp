package com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words

import com.zinoview.translatorapp.core.words.Abstract
import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import com.zinoview.translatorapp.data.words.cache.db.CacheWordMapper
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.view.WordTextView
import com.zinoview.translatorapp.ui.words.feature.ta02_show_translated_word.WordsAdapter
import com.zinoview.translatorapp.ui.words.feature.ta05_favorite_words.view.ItemView


sealed class UiWordsStateRecyclerView
    : Abstract.Words,
    com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.UiShow<Triple<WordTextView, WordTextView, ItemView>, WordsAdapter> {

    override fun show(arg: Triple<WordTextView, WordTextView,ItemView>) = Unit

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

        override fun show(arg: Triple<WordTextView, WordTextView,ItemView>) {
            arg.first.text("Translated $translatedWord")
            arg.second.text("Src: $srcWord")
            arg.third.changeBackground(isFavorite)
        }

        override fun itemClick(position: Int,wordsAdapterItemClickListener: WordsAdapter.WordsAdapterItemClickListener)
            = wordsAdapterItemClickListener.itemClick(position,translatedWord)
    }

}