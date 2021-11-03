package com.zinoview.translatorapp.domain.words

import com.zinoview.translatorapp.core.Abstract

interface DomainRecentMapper : Abstract.RecentWordsMapper<DomainRecentWords> {

    class Base : DomainRecentMapper {

        override fun map(): DomainRecentWords
            = DomainRecentWords.Empty

        override fun map(recentWords: List<String>): DomainRecentWords
            = DomainRecentWords.Base(recentWords)
    }
}