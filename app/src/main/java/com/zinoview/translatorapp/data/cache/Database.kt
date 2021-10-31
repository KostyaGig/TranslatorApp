package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.data.DataLanguage


interface Database<T,S> {

    fun objects() : T

    fun insertObject(data: S)

    fun updateObject(translatedWord: String,isFavorite: Boolean)

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

            override fun insertObject(data: Triple<String, String, DataBaseOperationLanguage>) {
                val translatedWord = data.first
                val srcWord = data.second
                val language = data.third
                if (objectNotExist(translatedWord)) {
                    language.saveToDb(realmProvider,translatedWord,srcWord)
                }

            }

            //todo refactor this (прокинуть language в метод,вместо создания его каждыый раз)
            override fun updateObject(translatedWord: String,isFavorite: Boolean) {
                realmProvider.provide().use { realm ->
                    val cacheWord = realm.where(CacheWord::class.java).equalTo(TRANSLATED_WORD_FIELD_NAME,translatedWord).findFirst()
                    val language = DataLanguage(cacheWord!!.fromLanguage,cacheWord.toLanguage)
                    language.updateWord(realmProvider,cacheWord,isFavorite)
                }
            }



            private fun objectNotExist(objectKey: String) : Boolean {
                val objectById = realmProvider.provide().use { realm ->
                    realm.where(CacheWord::class.java).equalTo(TRANSLATED_WORD_FIELD_NAME,objectKey).findFirst()
                }
                return objectById == null
            }

            private companion object{
                private const val TRANSLATED_WORD_FIELD_NAME = "translatedWord"
            }
        }
    }

}