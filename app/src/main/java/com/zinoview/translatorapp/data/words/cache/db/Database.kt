package com.zinoview.translatorapp.data.words.cache.db

import com.zinoview.translatorapp.data.words.cache.DataBaseOperationLanguage
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


interface Database<T,S> {

    fun objects() : T

    suspend fun insertObject(data: S)

    suspend fun updateObject(translatedWord: String) : CacheWord

    suspend fun containsObject(translatedWord: String) : Boolean

    suspend fun isFavoriteObject(translatedWord: String): Boolean


    interface Room : Database<List<CacheWord>, Triple<String, String, DataBaseOperationLanguage>> {

        class Base(
            private val dao: WordDao
        ) : Room {

            override fun objects(): List<CacheWord> {
                return dao.words()
            }

            override suspend fun insertObject(data: Triple<String, String, DataBaseOperationLanguage>) {
                val translatedWord = data.first
                val srcWord = data.second
                val language = data.third
                language.saveToDb(dao,translatedWord,srcWord)
            }

            override suspend fun updateObject(translatedWord: String) : CacheWord {
                return updatedObject(translatedWord)
            }

            private suspend fun updatedObject(translatedWord: String) : CacheWord {
                return suspendCoroutine { continuation ->
                         val cacheWord = dao.word(translatedWord)
                         val updatedCacheWord = cacheWord.first().update()
                    dao.update(updatedCacheWord)
                         continuation.resume(updatedCacheWord)
                     }
                }

            override suspend fun containsObject(translatedWord: String): Boolean {
                return dao.word(translatedWord).isNotEmpty()
            }

            override suspend fun isFavoriteObject(translatedWord: String): Boolean {
                return dao.word(translatedWord).first().favorite().isFavorite()
            }
        }



    }

}