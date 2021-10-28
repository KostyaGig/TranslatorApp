package com.zinoview.translatorapp.ui.feature.ta01_translate_word

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord


interface UiWordMapper : Abstract.WordsMapper<UiWords> {

    class Base(
        private val uiLanguageMapper: UiLanguageMapper
    ) : UiWordMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): UiWords = UiWords.Success(
            srcWord, translatedWord, language.map(uiLanguageMapper)
        )

        override fun map(message: String): UiWords
            = UiWords.Failure(message)

        override fun map(cachedWords: List<CacheWord>): UiWords
            = UiWords.Cache(cachedWords)

    }

}