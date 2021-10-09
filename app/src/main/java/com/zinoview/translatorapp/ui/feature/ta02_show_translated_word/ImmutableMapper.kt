package com.zinoview.translatorapp.ui.feature.ta02_show_translated_word

import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWord
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordMapper
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordState

interface ImmutableMapper<T> {

    fun map(list: MutableList<UiWordState>) : T

    class Base(
        private val uiWordMapper: UiWordMapper
    ) : ImmutableMapper<TranslatedWords<UiWord>> {

        override fun map(list: MutableList<UiWordState>): TranslatedWords<UiWord> {
            val uiWords = list.map { uiWordState -> uiWordState.map(uiWordMapper)  }
            return TranslatedWords.Base(uiWords)
        }

    }
}