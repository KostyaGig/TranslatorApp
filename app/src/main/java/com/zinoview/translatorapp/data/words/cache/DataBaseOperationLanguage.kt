package com.zinoview.translatorapp.data.words.cache

import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.data.words.cache.db.RoomProvider

interface DataBaseOperationLanguage : Language {

    suspend fun saveToDb(roomProvider: RoomProvider, translatedWord: String, srcWord: String)

    object Test : DataBaseOperationLanguage {

        private val list = ArrayList< Pair<String,String> >()

        override suspend fun saveToDb(
            roomProvider: RoomProvider,
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