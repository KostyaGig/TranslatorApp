package com.zinoview.translatorapp.ui.core.nav

import androidx.fragment.app.Fragment
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordState
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.MutableTranslatedWords

interface Navigation {
    fun navigateTo(
        fragment: BaseFragment,
        data: MutableTranslatedWords<UiWordState> = MutableTranslatedWords.Empty
    )

    fun exit()
}