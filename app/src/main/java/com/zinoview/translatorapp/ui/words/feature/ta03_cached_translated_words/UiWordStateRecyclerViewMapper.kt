package com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words

import com.zinoview.translatorapp.core.words.Abstract
import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import java.lang.IllegalStateException


interface UiWordStateRecyclerViewMapper : Abstract.WordsMapper<UiWordsStateRecyclerView> {

    class Success : UiWordStateRecyclerViewMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): UiWordsStateRecyclerView
            = throw IllegalStateException("UiWordStateRecyclerViewMapper.Success not use map()")

        override fun cachedMap(
            translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
        ): UiWordsStateRecyclerView = throw IllegalStateException("UiWordStateRecyclerViewMapper.Success not use cachedMap()")

        override fun map(message: String): UiWordsStateRecyclerView
            = throw IllegalStateException("UiWordStateRecyclerViewMapper.Success not use map()")

        override fun map(cachedWords: List<CacheWord>, position: Int): UiWordsStateRecyclerView
            = UiWordsStateRecyclerView.Success(cachedWords,position)
    }

    class Base : UiWordStateRecyclerViewMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language,
        ): UiWordsStateRecyclerView
                = UiWordsStateRecyclerView.Base(translatedWord, srcWord, language,false)

        override fun cachedMap(
            translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
        ): UiWordsStateRecyclerView
            = throw IllegalStateException("UiWordStateRecyclerViewMapper.Base not use cachedMap()")

        override fun map(message: String): UiWordsStateRecyclerView
            = throw IllegalStateException("UiWordStateRecyclerViewMapper.Base not use map()")

        override fun map(cachedWords: List<CacheWord>, position: Int): UiWordsStateRecyclerView
            = throw IllegalStateException("UiWordStateRecyclerViewMapper.Base not use map()")
    }

}