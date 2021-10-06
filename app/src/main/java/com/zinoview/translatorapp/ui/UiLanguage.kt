package com.zinoview.translatorapp.ui

import com.zinoview.translatorapp.core.Language

data class UiLanguage(
    private val fromLanguage: String,
    private val toLanguage: String
) : Language {

    override fun <T> map(mapper: Language.LanguageMapper<T>): T
        = mapper.map(fromLanguage,toLanguage)
}