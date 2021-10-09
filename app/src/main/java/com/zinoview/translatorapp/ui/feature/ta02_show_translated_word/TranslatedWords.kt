package com.zinoview.translatorapp.ui.feature.ta02_show_translated_word

import com.zinoview.translatorapp.ui.feature.ta01_translate_word.Show
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWord

interface TranslatedWords<T> : Show<WordsAdapter> {

    class Base(
        private val words: List<UiWord>
    ) : TranslatedWords<UiWord> {

        override fun show(arg: WordsAdapter) {
            arg.show(words)
        }

    }

    object Empty : TranslatedWords<UiWord> {
        override fun show(arg: WordsAdapter) = Unit
    }

}