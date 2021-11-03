package com.zinoview.translatorapp.data.words

import com.zinoview.translatorapp.core.Abstract

sealed class DataRecentWords : Abstract.RecentWord {


    class Base(
        private val recentWords: List<String>
    ) : DataRecentWords() {

        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T
            = mapper.map(recentWords)
    }


    object Empty : DataRecentWords() {
        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T
            = mapper.map()
    }
}
