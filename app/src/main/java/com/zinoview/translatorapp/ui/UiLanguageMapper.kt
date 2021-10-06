package com.zinoview.translatorapp.ui

import com.zinoview.translatorapp.core.Language


interface UiLanguageMapper : Language.LanguageMapper<UiLanguage> {

    class Base : UiLanguageMapper {

        override fun map(fromLanguage: String, toLanguage: String): UiLanguage
            = UiLanguage(fromLanguage, toLanguage)
    }
}