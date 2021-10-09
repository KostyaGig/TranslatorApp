package com.zinoview.translatorapp.ui.feature.ta01_translate_word

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language


interface UiWordMapper : Abstract.WordMapper<UiWord> {

    class Base(
        private val uiLanguageMapper: UiLanguageMapper
    ) : UiWordMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): UiWord = UiWord.Success(
            srcWord, translatedWord, language.map(uiLanguageMapper)
        )

        override fun map(message: String): UiWord
            = UiWord.Failure(message)

    }

}