package com.zinoview.translatorapp.data.words.cache

import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.data.words.cache.db.WordDao

interface DataBaseOperationLanguage : Language {

    suspend fun saveToDb(dao: WordDao, translatedWord: String, srcWord: String)

    interface Test {
        suspend fun saveToDb(translatedWord: String, srcWord: String)

        object Base : Test {

            private val list = ArrayList<Pair<String,String> >()

            override suspend fun saveToDb(
                translatedWord: String,
                srcWord: String
            ) {
                list.add(Pair(translatedWord,srcWord))
            }

            fun read() : List< Pair<String,String> >
                    = list
        }
    }

}