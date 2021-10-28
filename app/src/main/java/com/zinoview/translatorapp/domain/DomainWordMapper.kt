package com.zinoview.translatorapp.domain

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.DataWords
import com.zinoview.translatorapp.data.cache.CacheWord

interface DomainWordMapper : Abstract.WordsMapper<DomainWords> {

    class Base(
        private val domainLanguageMapper: DomainLanguageMapper
    ) : DomainWordMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): DomainWords = DomainWords.Success(
            srcWord, translatedWord, language.map(domainLanguageMapper)
        )

        override fun map(message: String): DomainWords
            = DomainWords.Failure(message)

        override fun map(cachedWords: List<CacheWord>): DomainWords
            = DomainWords.Cache(cachedWords)
    }

}