package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

import com.zinoview.translatorapp.core.words.Language


interface UiLanguageMapper : Language.LanguageMapper<UiLanguage> {

    class Base : UiLanguageMapper {

        override fun map(fromLanguage: String, toLanguage: String): UiLanguage
            = UiLanguage(fromLanguage, toLanguage)
    }
}