package com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord
import java.lang.IllegalStateException


interface UiWordStateRecyclerViewMapper : Abstract.WordsMapper<UiWordsStateRecyclerView> {

    class Success : UiWordStateRecyclerViewMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): UiWordsStateRecyclerView
            = throw IllegalStateException("UiWordStateRecyclerViewMapper.Success not use map()")

        override fun map(message: String): UiWordsStateRecyclerView
            = throw IllegalStateException("UiWordStateRecyclerViewMapper.Success not use map()")

        override fun map(cachedWords: List<CacheWord>): UiWordsStateRecyclerView
            = UiWordsStateRecyclerView.Success(cachedWords)
    }

    class Base : UiWordStateRecyclerViewMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): UiWordsStateRecyclerView
                = UiWordsStateRecyclerView.Base(translatedWord, srcWord, language)

        override fun map(message: String): UiWordsStateRecyclerView
            = throw IllegalStateException("UiWordStateRecyclerViewMapper.Base not use map()")

        override fun map(cachedWords: List<CacheWord>): UiWordsStateRecyclerView
            = throw IllegalStateException("UiWordStateRecyclerViewMapper.Base not use map()")
    }

}