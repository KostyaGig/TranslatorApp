package com.zinoview.translatorapp.domain

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.ui.core.log

sealed class DomainRecentWords : Abstract.RecentWord {


    class Base(
        private val recentWords: List<String>
    ) : DomainRecentWords() {

        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T
            = mapper.map(recentWords)
    }


    object Empty : DomainRecentWords() {

        override fun <T> map(mapper: Abstract.RecentWordsMapper<T>): T
            = mapper.map()
    }
}
