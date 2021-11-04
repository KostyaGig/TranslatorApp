package com.zinoview.translatorapp.domain.words

import com.zinoview.translatorapp.core.words.Language


interface DomainLanguageMapper : Language.LanguageMapper<DomainLanguage> {

    class Base : DomainLanguageMapper {

        override fun map(fromLanguage: String, toLanguage: String): DomainLanguage
            = DomainLanguage(fromLanguage, toLanguage)
    }
}