package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.core.Language

interface SaveLanguage : Language {

    fun saveToDb(realmProvider: RealmProvider,translatedWord: String,srcWord: String)

    object Test : SaveLanguage {

        private val list = ArrayList< Pair<String,String> >()

        override fun saveToDb(
            realmProvider: RealmProvider,
            translatedWord: String,
            srcWord: String
        ) {
            list.add(Pair(translatedWord,srcWord))
        }

        fun read() : List< Pair<String,String> >
            = list

        override fun <T> map(mapper: Language.LanguageMapper<T>): T = mapper.map("","")
    }
}