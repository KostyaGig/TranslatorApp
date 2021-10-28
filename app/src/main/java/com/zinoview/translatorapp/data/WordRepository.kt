package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.data.cache.CacheDataSource
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.data.cloud.CloudDataSource
import com.zinoview.translatorapp.data.cloud.CloudResultMapper
import com.zinoview.translatorapp.data.cloud.CloudWord

interface WordRepository<T> {

    suspend fun translatedWord(srcWord: String) : T

    suspend fun words() : DataWords

    class Base(
        private val cacheDataSource: CacheDataSource<List<CacheWord>>,
        private val cloudDataSource: CloudDataSource<CloudWord>,
        private val cloudResultMapper: CloudResultMapper,
        private val exceptionMapper: ExceptionMapper
    ) : WordRepository<DataWords> {

        override suspend fun translatedWord(srcWord: String): DataWords {
            return try {
                val cloudTranslatedWord = cloudDataSource.translatedWord(srcWord)
                val dataWord = cloudTranslatedWord.map(cloudResultMapper)
                cacheDataSource.saveWord(dataWord)
                return dataWord
            } catch (e: Exception) {
                val errorMessage = exceptionMapper.map(e)
                DataWords.Failure(errorMessage)
            }
        }

        override suspend fun words(): DataWords {
            val cachedWords = cacheDataSource.words()
            return DataWords.Cache(cachedWords)
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

        }
    }

}