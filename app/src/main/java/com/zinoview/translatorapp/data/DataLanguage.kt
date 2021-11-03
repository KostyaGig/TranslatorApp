package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.db.RoomProvider
import com.zinoview.translatorapp.data.cache.DataBaseOperationLanguage
import com.zinoview.translatorapp.data.cache.db.CacheWord

data class DataLanguage(
    private val fromLanguage: String,
    private val toLanguage: String
) : DataBaseOperationLanguage {

    override fun <T> map(mapper: Language.LanguageMapper<T>): T
        = mapper.map(fromLanguage,toLanguage)

    override suspend fun saveToDb(roomProvider: RoomProvider, translatedWord: String, srcWord: String) {
        val cacheWord = CacheWord.Base(
            srcWord,translatedWord,fromLanguage, toLanguage
        )
        roomProvider.provide().insert(cacheWord)
    }

}