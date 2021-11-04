package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

import com.zinoview.translatorapp.core.words.Language

data class UiLanguage(
    private val fromLanguage: String,
    private val toLanguage: String
) : Language {

    override fun <T> map(mapper: Language.LanguageMapper<T>): T
        = mapper.map(fromLanguage,toLanguage)
}