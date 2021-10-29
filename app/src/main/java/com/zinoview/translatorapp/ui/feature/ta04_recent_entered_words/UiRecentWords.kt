package com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.ui.core.log

sealed class UiRecentWords : Abstract.RecentWord {

    open fun map() : List<String> = emptyList()

    class Base(
        private val recentWords: List<String>
    ) : UiRecentWords() {

        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T
                = mapper.map(recentWords)

        override fun map(): List<String> {
            recentWords.forEach {
                log("UiRecentWords -> Base: $it")
            }
            return emptyList()
        }
    }


    object Empty : UiRecentWords() {

        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T
            = mapper.map()

        override fun map(): List<String> {
            log("UiRecentWords -> Empty")
            return emptyList()
        }
    }
}