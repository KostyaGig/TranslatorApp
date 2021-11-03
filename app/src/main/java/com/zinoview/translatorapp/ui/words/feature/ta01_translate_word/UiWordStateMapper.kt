package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import java.lang.IllegalStateException


interface UiWordStateMapper : Abstract.WordsMapper<UiWordState> {

    class Base: UiWordStateMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): UiWordState = UiWordState.Base.Success(
            srcWord, translatedWord, language
        )

        override fun cachedMap(
            translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
        ): UiWordState = UiWordState.Base.TranslatedCache(
            srcWord, translatedWord, language,isFavorite
        )

        override fun map(message: String): UiWordState
            = UiWordState.Failure(message)

        override fun map(cachedWords: List<CacheWord>, position: Int): UiWordState
            = throw IllegalStateException("UiWordStateMapper.Base not use map()")
    }

}