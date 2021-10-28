package com.zinoview.translatorapp.data.cache


interface Database<T,S> {

    fun objects() : T

    fun insertObject(data: S)

    interface Realm : Database<List<CacheWord>,Triple<String,String,SaveLanguage>> {

        class Base(
            private val realmProvider: RealmProvider,
        ) : Realm {

            override fun objects(): List<CacheWord> {
                realmProvider.provide().use { realm ->
                    val words = realm.where(CacheWord::class.java).findAll() ?: emptyList()
                    return realm.copyFromRealm(words)
                }
            }

            override fun insertObject(data: Triple<String, String, SaveLanguage>) {
                val translatedWord = data.first
                val srcWord = data.second
                val language = data.third
                if (objectNotExist(translatedWord)) {
                    language.saveToDb(realmProvider,translatedWord,srcWord)
                }

            }

            private fun objectNotExist(objectKey: String) : Boolean {
                val objectById = realmProvider.provide().use { realm ->
                    realm.where(CacheWord::class.java).equalTo(OBJECT_KEY,objectKey).findFirst()
                }
                return objectById == null
            }

            private companion object{
                const val OBJECT_KEY = "translatedWord"
            }

        }
    }

}