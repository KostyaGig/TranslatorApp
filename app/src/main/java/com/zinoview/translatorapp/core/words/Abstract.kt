package com.zinoview.translatorapp.core.words

import com.zinoview.translatorapp.data.words.cache.db.CacheWord

interface Abstract {

    interface Words {

        fun <T> map(mapper: WordsMapper<T>) : T
    }

    interface WordsMapper<T> : FailureMapper<T> {

        //success
        fun map(translatedWord: String,srcWord: String,language: Language) : T

        //translated cached
        fun cachedMap(translatedWord: String, srcWord: String, language: Language, isFavorite: Boolean) : T

        //cached
        fun map(cachedWords: List<CacheWord>, position: Int) : T
    }

    interface RecentWord {

        fun <T> map(mapper: RecentWordsMapper<T>) : T
    }

    interface RecentWordsMapper<T> : SuccessMapper<T>{

        fun map(recentWords: List<String>) : T

    }

    interface Response {

        fun <T> map(mapper: ResponseMapper<T>) : T
    }

    interface ResponseMapper<T> : SuccessMapper<T>, FailureMapper<T>

    interface SuccessMapper<T> : Mapper{

        fun map() : T
    }

    interface FailureMapper<T> : Mapper {

        fun map(message: String) : T
    }

    interface Mapper
}