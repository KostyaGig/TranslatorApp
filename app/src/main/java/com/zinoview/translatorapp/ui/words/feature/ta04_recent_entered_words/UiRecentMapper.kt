package com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words

import com.zinoview.translatorapp.core.words.Abstract

interface UiRecentMapper : Abstract.RecentWordsMapper<UiRecentWords> {

    class Base : UiRecentMapper {

        override fun map(): UiRecentWords
            = UiRecentWords.Empty

        override fun map(recentWords: List<String>): UiRecentWords
            = UiRecentWords.Base(recentWords)
    }
}