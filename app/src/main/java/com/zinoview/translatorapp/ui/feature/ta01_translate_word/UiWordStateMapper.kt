package com.zinoview.translatorapp.ui.feature.ta01_translate_word

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord
import java.lang.IllegalStateException


interface UiWordStateMapper : Abstract.WordsMapper<UiWordState> {

    class Base: UiWordStateMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): UiWordState = UiWordState.Success(
            srcWord, translatedWord, language
        )

        override fun map(message: String): UiWordState
            = UiWordState.Failure(message)

        override fun map(cachedWords: List<CacheWord>): UiWordState
            = throw IllegalStateException("UiWordStateMapper.Base not use map()")
    }

}