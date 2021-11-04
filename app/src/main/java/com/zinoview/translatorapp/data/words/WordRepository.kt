package com.zinoview.translatorapp.data.words

import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import com.zinoview.translatorapp.data.words.cache.shared_prefs.TranslatorSharedPreferences
import com.zinoview.translatorapp.data.words.cloud.CloudDataSource
import com.zinoview.translatorapp.data.words.cloud.CloudResultMapper
import com.zinoview.translatorapp.data.words.cache.CacheDataSource
import com.zinoview.translatorapp.data.words.cache.TranslatedCacheDataWordsMapper
import com.zinoview.translatorapp.data.words.cache.TranslatedCacheNotFavoriteDataWordsMapper
import com.zinoview.translatorapp.data.words.cloud.CloudWord

interface WordRepository<T> {

    suspend fun translatedWord(srcWord: String) : T

    suspend fun words() : DataWords

    suspend fun recentQuery() : DataRecentWords

    //param isFavorite need for tests
    suspend fun updateWord(srcWord: String,position: Int,isFavorite: Boolean = false) : DataWords

    //I forced usage hard implementation for serialize here to string and back
    suspend fun saveRecentQuery(recentQuery: ArrayList<String>)

    class Base(
        private val cacheDataSource: CacheDataSource<CacheWord>,
        private val cloudDataSource: CloudDataSource<CloudWord>,
        private val cloudResultMapper: CloudResultMapper,
        private val exceptionMapper: ExceptionMapper,
        private val translatorSharedPreferences: TranslatorSharedPreferences,
        private val translatedCacheFavoriteDataWordsMapper: TranslatedCacheDataWordsMapper,
        private val translatedCacheNotFavoriteDataWordsMapper: TranslatedCacheNotFavoriteDataWordsMapper
    ) : WordRepository<DataWords> {

        override suspend fun translatedWord(srcWord: String): DataWords {
            return try {
                val cloudTranslatedWord = cloudDataSource.translatedWord(srcWord)
                val dataWord = cloudTranslatedWord.map(cloudResultMapper)
                return if (cacheDataSource.contains(dataWord)) {
                    return if (cacheDataSource.isFavorite(dataWord)) {
                        dataWord.map(translatedCacheFavoriteDataWordsMapper)
                    } else {
                        dataWord.map(translatedCacheNotFavoriteDataWordsMapper)
                    }
                } else {
                    cacheDataSource.saveWord(dataWord)
                    dataWord
                }
            } catch (e: Exception) {
                val errorMessage = exceptionMapper.map(e)
                DataWords.Failure(errorMessage)
            }
        }

        override suspend fun words(): DataWords {
            val cachedWords = cacheDataSource.words()
            return DataWords.Cache(cachedWords)
        }

        override suspend fun updateWord(srcWord: String,position: Int,isFavorite: Boolean) : DataWords {
            val updatedWord = cacheDataSource.updateWord(srcWord)
            return DataWords.Cache(listOf(updatedWord), position)
        }

        override suspend fun recentQuery(): DataRecentWords {
            val recentWords = translatorSharedPreferences.read()
            return if (recentWords.isEmpty()) {
                DataRecentWords.Empty
            } else {
                DataRecentWords.Base(recentWords)
            }
        }

        override suspend fun saveRecentQuery(recentQuery: ArrayList<String>) {
            translatorSharedPreferences.save(recentQuery)
        }
    }

    interface TestRepository<T> : WordRepository<DataWords> {

        suspend fun pairWords() : T

        class Test(
            private val cloudDataSource: CloudDataSource<DataWords>,
            private val cacheDataSource: CacheDataSource<Pair<String, String>>
        ) : TestRepository<List<Pair<String, String>>> {

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

            override suspend fun saveRecentQuery(recentQuery: ArrayList<String>)
                    = throw IllegalStateException("WordRepository.TestRepository.Test not use saveRecentQuery()")

            override suspend fun updateWord(srcWord: String,position: Int,isFavorite: Boolean) : DataWords {
                return DataWords.Test(
                    cacheDataSource.updateWord(srcWord, isFavorite).second,
                    cacheDataSource.updateWord(srcWord, isFavorite).first,
                    isFavorite
                )
            }
        }
    }

}