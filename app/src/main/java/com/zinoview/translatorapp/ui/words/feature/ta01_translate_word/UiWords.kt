package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

import com.zinoview.translatorapp.core.words.Abstract
import com.zinoview.translatorapp.data.words.cache.db.CacheWord

import com.zinoview.translatorapp.ui.core.view.WordTextView
import com.zinoview.translatorapp.ui.words.feature.ta02_show_translated_word.WordsAdapter
import java.lang.IllegalStateException

sealed class UiWords : Abstract.Words,
    UiShow<Pair<WordTextView, WordTextView>, WordsAdapter> {

    override fun show(arg: Pair<WordTextView, WordTextView>) = Unit

    override fun uiShow(arg: WordsAdapter) = Unit

    open class Base(
        private val srcWord: String,
        private val translatedWord: String,
        private val language: UiLanguage
    ) : UiWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T =
            mapper.map(translatedWord, srcWord, language)

        class Success(
            srcWord: String,
            translatedWord: String,
            language: UiLanguage
        ) : Base(srcWord, translatedWord, language)

        class TranslatedCache(
            private val srcWord: String,
            private val translatedWord: String,
            private val language: UiLanguage,
            private val isFavorite: Boolean
        ) : Base(srcWord, translatedWord, language) {

            override fun <T> map(mapper: Abstract.WordsMapper<T>): T
                = mapper.cachedMap(translatedWord, srcWord, language,isFavorite)
        }
    }




    data class Cache(
        private val words: List<CacheWord>,
        private val position: Int
    ) : UiWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(words,position)

        override fun uiShow(arg: WordsAdapter) {
//            log("uiwords -> uiShow()")
//            val uiWordStateRecyclerViewMapper = UiWordStateRecyclerViewMapper.Base()
//            val uiWordStateRecycler = words.map { cachedWord ->
//                cachedWord.map(uiWordStateRecyclerViewMapper)
//            }
//            arg.show(uiWordStateRecycler,position)
            throw IllegalStateException("UiWords.Cache not usage uiShow")
        }
    }

    class Failure(
        private val message: String,
    ) : UiWords() {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(message)
    }

}