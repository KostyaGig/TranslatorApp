package com.zinoview.translatorapp.domain.words

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.words.cache.db.CacheWord

interface DomainWordMapper : Abstract.WordsMapper<DomainWords> {

    class Base(
        private val domainLanguageMapper: DomainLanguageMapper
    ) : DomainWordMapper {

        override fun map(
            translatedWord: String,
            srcWord: String,
            language: Language
        ): DomainWords = DomainWords.Base.Success(
            srcWord, translatedWord, language.map(domainLanguageMapper)
        )

        override fun cachedMap(
            translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
        ): DomainWords = DomainWords.Base.TranslatedCache(
            srcWord, translatedWord, language.map(domainLanguageMapper), isFavorite
        )

        override fun map(message: String): DomainWords
            = DomainWords.Failure(message)

        override fun map(cachedWords: List<CacheWord>, position: Int): DomainWords
            = DomainWords.Cache(cachedWords, position)
    }

}