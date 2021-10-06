package com.zinoview.translatorapp.ui

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language


interface UiWordStateMapper : Abstract.WordMapper<UiWordState> {

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
    }

}