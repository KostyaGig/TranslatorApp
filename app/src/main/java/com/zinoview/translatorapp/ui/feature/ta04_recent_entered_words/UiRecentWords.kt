package com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words

import com.zinoview.translatorapp.core.Abstract

sealed class UiRecentWords : Abstract.RecentWord {

    open fun map(tempRecentWords: TempRecentWords) = Unit

    class Base(
        private val recentWords: List<String>
    ) : UiRecentWords() {

        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T
            = mapper.map(recentWords)

        override fun map(tempRecentWords: TempRecentWords)
            = tempRecentWords.fill(recentWords)
    }


    object Empty : UiRecentWords() {

        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T
            = mapper.map()
    }
}