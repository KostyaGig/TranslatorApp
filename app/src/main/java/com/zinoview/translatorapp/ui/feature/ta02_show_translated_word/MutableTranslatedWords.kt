package com.zinoview.translatorapp.ui.feature.ta02_show_translated_word

import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWord
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordState

interface MutableTranslatedWords<T>{

    fun add(word: T)

    fun asImmutable(mapper: ImmutableMapper<TranslatedWords<UiWord>>) : TranslatedWords<UiWord>

    object Empty : MutableTranslatedWords<UiWordState> {
        override fun add(word: UiWordState) = throw IllegalStateException("TranslatedWords.Empty not use add()")

        override fun asImmutable(mapper: ImmutableMapper<TranslatedWords<UiWord>>) : TranslatedWords<UiWord>
            = TranslatedWords.Empty
    }

    class Base(
        private val uniqueWords: UniqueWords<UiWordState>
    ) : MutableTranslatedWords<UiWordState> {

        override fun add(word: UiWordState) {
            uniqueWords.addItem(word)
        }

        override fun asImmutable(mapper: ImmutableMapper<TranslatedWords<UiWord>>): TranslatedWords<UiWord>
            = mapper.map(uniqueWords.uniques())

    }
}
