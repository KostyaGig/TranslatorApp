package com.zinoview.translatorapp.core

interface Abstract {

    interface Word {

        fun <T> map(mapper: WordMapper<T>) : T
    }

    interface WordMapper<T> : Mapper {

        //success
        fun map(translatedWord: String,srcWord: String,language: Language) : T

        //failure
        fun map(message: String) : T
    }

    interface FactoryMapper<S,R> : Mapper {
        fun map(src: S) : R
    }

    interface Mapper
}