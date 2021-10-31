package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.core.Language

interface DataBaseOperationLanguage : Language {

    fun saveToDb(realmProvider: RealmProvider,translatedWord: String,srcWord: String)

    fun updateWord(realmProvider: RealmProvider,cacheWord: CacheWord,isFavorite: Boolean) : CacheWord

    object Test : DataBaseOperationLanguage {

        private val list = ArrayList< Pair<String,String> >()

        override fun saveToDb(
            realmProvider: RealmProvider,
            translatedWord: String,
            srcWord: String
        ) {
            list.add(Pair(translatedWord,srcWord))
        }

        //todo write test for this method
        override fun updateWord(
            realmProvider: RealmProvider,
            cacheWord: CacheWord,
            isFavorite: Boolean
        ) {

        }

        fun read() : List< Pair<String,String> >
            = list

        override fun <T> map(mapper: Language.LanguageMapper<T>): T = mapper.map("","")
    }
}