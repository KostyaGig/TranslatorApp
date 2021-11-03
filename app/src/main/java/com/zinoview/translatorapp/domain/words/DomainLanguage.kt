package com.zinoview.translatorapp.domain.words

import com.zinoview.translatorapp.core.Language

data class DomainLanguage(
    private val fromLanguage: String,
    private val toLanguage: String
) : Language {

    override fun <T> map(mapper: Language.LanguageMapper<T>): T
        = mapper.map(fromLanguage,toLanguage)
}