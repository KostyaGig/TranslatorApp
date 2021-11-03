package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.ui.core.log
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


interface Database<T,S> {

    fun objects() : T

    suspend fun insertObject(data: S)

    suspend fun updateObject(translatedWord: String,isFavorite: Boolean) : CacheWord

    interface Room : Database<List<CacheWord>,Triple<String,String,DataBaseOperationLanguage>> {

        class Base(
            private val roomProvider: RoomProvider
        ) : Room {

            override fun objects(): List<CacheWord> {
                return roomProvider.provide().words()
            }

            override suspend fun insertObject(data: Triple<String, String, DataBaseOperationLanguage>) {
                val translatedWord = data.first
                val srcWord = data.second
                val language = data.third
                language.saveToDb(roomProvider,translatedWord,srcWord)
            }

            override suspend fun updateObject(translatedWord: String,isFavorite: Boolean) : CacheWord {
                return updatedObject(isFavorite, translatedWord)
            }

            private suspend fun updatedObject(isFavorite: Boolean,translatedWord: String) : CacheWord {
                return suspendCoroutine { continuation ->
                         val cacheWord = roomProvider.provide().word(translatedWord)
                            log("words size ${roomProvider.provide().words().size}")
                        if (cacheWord.isEmpty()) {
                            log("cache word null")
                        } else {
                            val updatedCacheWord = cacheWord[0].update(isFavorite)
                            roomProvider.provide().update(updatedCacheWord)
                            continuation.resume(updatedCacheWord)
                        }
                     }
                }
        }

    }

}