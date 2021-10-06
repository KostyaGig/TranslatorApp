package com.zinoview.translatorapp.domain

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language

interface DomainWordMapper : Abstract.WordMapper<DomainWord> {

    class Base(
        private val domainLanguageMapper: DomainLanguageMapper
    ) : DomainWordMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): DomainWord = DomainWord.Success(
            srcWord, translatedWord, language.map(domainLanguageMapper)
        )

        override fun map(message: String): DomainWord
            = DomainWord.Failure(message)
    }

}