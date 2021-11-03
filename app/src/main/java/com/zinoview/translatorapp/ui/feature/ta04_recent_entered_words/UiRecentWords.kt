package com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.ui.core.log

sealed class UiRecentWords : Abstract.RecentWord {

    open fun map(tempRecentWords: TempRecentWords) = Unit

    class Base(
        private val recentWords: List<String>
    ) : UiRecentWords() {

        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T {
            log("temp recent word map <T>")
            return mapper.map(recentWords)
        }

        override fun map(tempRecentWords: TempRecentWords) {
            log("temp recent map size ${recentWords.size}")
            tempRecentWords.fill(recentWords)
        }
    }


    object Empty : UiRecentWords() {

        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T {
            log("temp recent word map empty")
            return mapper.map()
        }
    }
}