package com.zinoview.translatorapp.ui.core.nav

import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.Show
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWord
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordState
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.TranslatedWords
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.WordsAdapter
import java.io.Serializable

interface NavigatorData<T> : Serializable, Show<WordsAdapter>  {

    fun take() : T

    class Words(
        private val words: TranslatedWords<UiWord>
    ) : NavigatorData<TranslatedWords<UiWord>> {

        override fun show(adapter: WordsAdapter) {
            words.show(adapter)
        }

        override fun take(): TranslatedWords<UiWord>
            = words
    }
}