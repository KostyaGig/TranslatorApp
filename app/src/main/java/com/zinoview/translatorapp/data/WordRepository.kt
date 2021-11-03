package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.data.cache.CacheDataSource
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.data.cache.CacheWordMapper
import com.zinoview.translatorapp.data.cache.shared_prefs.TranslatorSharedPreferences
import com.zinoview.translatorapp.data.cloud.CloudDataSource
import com.zinoview.translatorapp.data.cloud.CloudResultMapper
import com.zinoview.translatorapp.data.cloud.CloudWord
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.UiWordsStateRecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface WordRepository<T> {

    suspend fun translatedWord(srcWord: String) : T

    suspend fun words() : DataWords

    suspend fun recentQuery() : DataRecentWords

    suspend fun updateWord(srcWord: String,isFavorite: Boolean,position: Int) : DataWords

    //todo make cache recentQuery in translatedWord(srcWord: String) and delete method below
    suspend fun saveRecentQuery(recentQuery: List<String>)


    class Base(
        private val cacheDataSource: CacheDataSource<List<CacheWord>>,
        private val cloudDataSource: CloudDataSource<CloudWord>,
        private val cloudResultMapper: CloudResultMapper,
        private val exceptionMapper: ExceptionMapper,
        private val translatorSharedPreferences: TranslatorSharedPreferences
    ) : WordRepository<DataWords> {

        override suspend fun translatedWord(srcWord: String): DataWords {
            return try {
                val cloudTranslatedWord = cloudDataSource.translatedWord(srcWord)
                val dataWord = cloudTranslatedWord.map(cloudResultMapper)
                cacheDataSource.saveWord(dataWord)
                dataWord
            } catch (e: Exception) {
                val errorMessage = exceptionMapper.map(e)
                DataWords.Failure(errorMessage)
            }
        }

        override suspend fun words(): DataWords {
            val cachedWords = cacheDataSource.words()
            return DataWords.Cache(cachedWords)
        }

        override suspend fun updateWord(srcWord: String, isFavorite: Boolean,position: Int) : DataWords {
            val updatedWord = cacheDataSource.updateWord(srcWord, isFavorite)
            return DataWords.Cache(listOf(updatedWord),position)
        }

        override suspend fun recentQuery(): DataRecentWords {
            val recentWords = translatorSharedPreferences.read()
            return if (recentWords.isEmpty()) {
                DataRecentWords.Empty
            } else {
                DataRecentWords.Base(recentWords)
            }
        }

        override suspend fun saveRecentQuery(recentQuery: List<String>) {
            translatorSharedPreferences.save(recentQuery)
        }
    }

    interface TestRepository<T> : WordRepository<DataWords> {

        suspend fun pairWords() : T

        class Test(
            private val cloudDataSource: CloudDataSource<DataWords>,
            private val cacheDataSource: CacheDataSource<List<Pair<String,String>>>
        ) : TestRepository<List<Pair<String,String>>> {

            override suspend fun translatedWord(srcWord: String): DataWords {
                val word = cloudDataSource.translatedWord(srcWord)
                if((word is DataWords.Failure).not()) {
                    cacheDataSource.saveWord(word)
                }
                return word
            }

            override suspend fun words(): DataWords
                = throw IllegalStateException("WordRepository.TestRepository.Test not use words()")

            override suspend fun pairWords(): List<Pair<String,String>>
                = cacheDataSource.words()

            override suspend fun recentQuery(): DataRecentWords
                = throw IllegalStateException("WordRepository.TestRepository.Test not use recentWords()")

            override suspend fun saveRecentQuery(recentQuery: List<String>)
                    = throw IllegalStateException("WordRepository.TestRepository.Test not use saveRecentQuery()")

            override suspend fun updateWord(srcWord: String, isFavorite: Boolean,position: Int)
                = throw IllegalStateException("WordRepository.TestRepository.Test not use updateWord()")
        }
    }

}