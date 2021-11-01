package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.data.cache.RealmProvider
import com.zinoview.translatorapp.data.cache.DataBaseOperationLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class DataLanguage(
    private val fromLanguage: String,
    private val toLanguage: String
) : DataBaseOperationLanguage {

    override fun <T> map(mapper: Language.LanguageMapper<T>): T
        = mapper.map(fromLanguage,toLanguage)

    override suspend fun saveToDb(realmProvider: RealmProvider, translatedWord: String, srcWord: String) {
        withContext(Dispatchers.Main) {
            realmProvider.provide().use { realm ->
                realm.executeTransactionAsync { rm ->
                    val cacheWord = rm.createObject(CacheWord::class.java,srcWord)
                    cacheWord.translatedWord = translatedWord
                    cacheWord.fromLanguage = fromLanguage
                    cacheWord.toLanguage = toLanguage
                }
            }
        }
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