package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.data.DataWords

interface CacheDataSource<T> {

    suspend fun saveWord(word: DataWords)

    suspend fun updateWord(translatedWord: String,isFavorite: Boolean) : CacheWord

    suspend fun words() : T

    class Base(
        private val database: Database.Realm
    ) : CacheDataSource<List<CacheWord>> {

        override suspend fun saveWord(word: DataWords) {
            word.save(database)
        }

        override suspend fun words(): List<CacheWord> {
            return database.objects()
        }

        override suspend fun updateWord(translatedWord: String, isFavorite: Boolean) : CacheWord
             = database.updateObject(translatedWord,isFavorite)
    }

    class Test : CacheDataSource<List<Pair<String,String>>> {

        private val data = ArrayList<Pair<String,String>>()

        override suspend fun saveWord(word: DataWords) {
            val testWord = word as DataWords.Test
            data.add(
                Pair(
                    testWord.translatedWord,
                    testWord.srcWord
                )
            )
        }

        override suspend fun words(): List<Pair<String,String>>
            = data

        //todo make test for this method
        override suspend fun updateWord(translatedWord: String, isFavorite: Boolean) : CacheWord {
            TODO()
        }

    }
}
