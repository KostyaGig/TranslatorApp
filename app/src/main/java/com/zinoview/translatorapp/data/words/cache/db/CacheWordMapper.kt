package com.zinoview.translatorapp.data.words.cache.db

import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.UiWordsStateRecyclerView

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