package com.zinoview.translatorapp.ui.feature.ta01_translate_word

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextView
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.WordsAdapter
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.UiWordStateRecyclerViewMapper

sealed class UiWords : Abstract.Words, UiShow<Pair<WordTextView,WordTextView>,WordsAdapter> {

    override fun show(arg: Pair<WordTextView, WordTextView>) = Unit

    override fun uiShow(arg: WordsAdapter) = Unit

    class Success(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: Language
    ) : UiWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(translatedWord, srcWord, language)

        override fun show(arg: Pair<WordTextView, WordTextView>) {
            arg.first.text(translatedWord)
            arg.second.text(srcWord)
        }
    }

    data class Cache(
        private val words: List<CacheWord>
    ) : UiWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(words)

        override fun uiShow(arg: WordsAdapter) {
            val uiWordStateRecyclerViewMapper = UiWordStateRecyclerViewMapper.Base()
            val uiWordStateRecycler = words.map { cachedWord ->
                cachedWord.map(uiWordStateRecyclerViewMapper)
            }
            arg.show(uiWordStateRecycler)
        }
    }

    class Failure(
        private val message: String,
    ) : UiWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(message)
    }

}