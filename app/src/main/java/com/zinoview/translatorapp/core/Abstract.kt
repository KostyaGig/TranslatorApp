package com.zinoview.translatorapp.core

import com.zinoview.translatorapp.data.cache.CacheWord

interface Abstract {

    interface Words {

        fun <T> map(mapper: WordsMapper<T>) : T
    }

    interface WordsMapper<T> : Mapper {

        //success
        fun map(translatedWord: String,srcWord: String,language: Language) : T

        //failure
        fun map(message: String) : T

        //cached
        fun map(cachedWords: List<CacheWord>) : T
    }

    interface FactoryMapper<S,R> : Mapper {
        fun map(src: S) : R
    }

    interface Mapper
}