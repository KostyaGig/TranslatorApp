package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.ui.core.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


interface Database<T,S> {

    fun objects() : T

    suspend fun insertObject(data: S)

    suspend fun updateObject(translatedWord: String,isFavorite: Boolean) : CacheWord

    interface Realm : Database<List<CacheWord>,Triple<String,String,DataBaseOperationLanguage>> {

        class Base(
            private val realmProvider: RealmProvider,
        ) : Realm {

            override fun objects(): List<CacheWord> {
                realmProvider.provide().use { realm ->
                    val words = realm.where(CacheWord::class.java).findAll() ?: emptyList()
                    return realm.copyFromRealm(words)
                }
            }

            override suspend fun insertObject(data: Triple<String, String, DataBaseOperationLanguage>) {
                val translatedWord = data.first
                val srcWord = data.second
                val language = data.third
                if (objectNotExist(srcWord)) {
                    language.saveToDb(realmProvider,translatedWord,srcWord)
                }

            }

            override suspend fun updateObject(translatedWord: String,isFavorite: Boolean) : CacheWord {
                return update(isFavorite, translatedWord)
            }

            private suspend fun update(isFavorite: Boolean,translatedWord: String) : CacheWord {
                return withContext(Dispatchers.Main) {
                     suspendCoroutine { continuation ->
                            realmProvider.provide().executeTransactionAsync { rm ->
                                log("update from ${Thread.currentThread().name}")
                                val cacheWord = rm.where(CacheWord::class.java).equalTo(SRC_WORD_FIELD_NAME,translatedWord).findFirst()
                                cacheWord?.isFavorite = isFavorite
                                val updatedWord = rm.copyToRealmOrUpdate(cacheWord!!)
                                continuation.resume(updatedWord)
                            }
                     }
                }
            }


            private fun objectNotExist(objectKey: String) : Boolean {
                val objectById = realmProvider.provide().use { realm ->
                    realm.where(CacheWord::class.java).equalTo(SRC_WORD_FIELD_NAME,objectKey).findFirst()
                }
                return objectById == null
            }

            private companion object{
                private const val SRC_WORD_FIELD_NAME = "srcWord"
            }
        }

    }

}