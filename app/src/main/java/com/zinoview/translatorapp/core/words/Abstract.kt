package com.zinoview.translatorapp.core.words

import com.zinoview.translatorapp.data.words.cache.db.CacheWord

interface Abstract {

    interface Words {

        fun <T> map(mapper: WordsMapper<T>) : T
    }

    interface WordsMapper<T> : Mapper {

        //success
        fun map(translatedWord: String,srcWord: String,language: Language) : T

        //translated cached
        fun cachedMap(translatedWord: String, srcWord: String, language: Language, isFavorite: Boolean) : T

        //failure
        fun map(message: String) : T

        //cached
        fun map(cachedWords: List<CacheWord>, position: Int) : T
    }

    interface RecentWord {

        fun <T> map(mapper: RecentWordsMapper<T>) : T
    }

    interface RecentWordsMapper<T> {

        fun map(recentWords: List<String>) : T

        fun map() : T
    }

    interface Mapper
}