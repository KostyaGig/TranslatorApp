package com.zinoview.translatorapp.core

import java.lang.IllegalStateException

interface Language {

    fun <T> map(mapper: LanguageMapper<T>) : T

    interface LanguageMapper<T> {

        fun map(fromLanguage: String, toLanguage: String) : T
    }

    class Test() : Language {

        override fun <T> map(mapper: LanguageMapper<T>): T
            = throw IllegalStateException("Language.Test not use map()")

        override fun equals(other: Any?): Boolean {
            return true
        }

        override fun hashCode(): Int {
            return 100
        }
    }
}