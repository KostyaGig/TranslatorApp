package com.zinoview.translatorapp.ui.words.feature.ta02_show_translated_word

import com.zinoview.translatorapp.ui.core.Same
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.UiWordsStateRecyclerView

interface UiWordsSame : Same<UiWordsStateRecyclerView> {

    fun same(srcWord: String,isFavorite: Boolean) : Boolean
}