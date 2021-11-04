package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

import com.zinoview.translatorapp.core.words.Abstract
import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.data.words.cache.db.CacheWord


interface UiWordMapper : Abstract.WordsMapper<UiWords> {

    class Base(
        private val uiLanguageMapper: UiLanguageMapper
    ) : UiWordMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): UiWords = UiWords.Base.Success(
            srcWord, translatedWord, language.map(uiLanguageMapper)
        )

        override fun cachedMap(
            translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
        ): UiWords = UiWords.Base.TranslatedCache(
            srcWord, translatedWord, language.map(uiLanguageMapper),isFavorite
        )

        override fun map(message: String): UiWords
            = UiWords.Failure(message)

        override fun map(cachedWords: List<CacheWord>, position: Int): UiWords
            = UiWords.Cache(cachedWords,position)

    }

}