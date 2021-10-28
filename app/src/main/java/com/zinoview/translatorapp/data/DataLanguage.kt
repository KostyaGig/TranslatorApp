package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.data.cache.RealmProvider
import com.zinoview.translatorapp.data.cache.SaveLanguage
import io.realm.RealmObject

data class DataLanguage(
    private val fromLanguage: String,
    private val toLanguage: String
) : SaveLanguage {

    override fun <T> map(mapper: Language.LanguageMapper<T>): T
        = mapper.map(fromLanguage,toLanguage)

    override fun saveToDb(realmProvider: RealmProvider, translatedWord: String, srcWord: String) {
        realmProvider.provide().use { realm ->
            realm.executeTransaction { rm ->
                val cacheWord = rm.createObject(CacheWord::class.java,translatedWord)
                cacheWord.srcWord = srcWord
                cacheWord.fromLanguage = fromLanguage
                cacheWord.toLanguage = toLanguage
            }
        }
    }
}