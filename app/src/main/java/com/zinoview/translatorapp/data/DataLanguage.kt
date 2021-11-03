package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.data.cache.RoomProvider
import com.zinoview.translatorapp.data.cache.DataBaseOperationLanguage

data class DataLanguage(
    private val fromLanguage: String,
    private val toLanguage: String
) : DataBaseOperationLanguage {

    override fun <T> map(mapper: Language.LanguageMapper<T>): T
        = mapper.map(fromLanguage,toLanguage)

    override suspend fun saveToDb(roomProvider: RoomProvider, translatedWord: String, srcWord: String) {
        val cacheWord = CacheWord(
            srcWord,translatedWord,fromLanguage, toLanguage
        )
        roomProvider.provide().insert(cacheWord)
    }

    //todo remove later
//    override fun updateWord(realmProvider: RealmProvider,cacheWord: CacheWord) {
//        realmProvider.provide().use { realm ->
//                realm.executeTransaction { rm ->
//                rm.copyToRealmOrUpdate(cacheWord)
//            }
//        }
//    }
}