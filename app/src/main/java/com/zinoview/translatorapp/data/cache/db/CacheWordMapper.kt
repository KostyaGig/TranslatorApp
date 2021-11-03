package com.zinoview.translatorapp.data.cache.db

import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.UiWordsStateRecyclerView

interface CacheWordMapper {

    fun map(translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
    ) : UiWordsStateRecyclerView

    class Base : CacheWordMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
        ): UiWordsStateRecyclerView
            = UiWordsStateRecyclerView.Base(
                translatedWord, srcWord, language, isFavorite
            )

    }
}