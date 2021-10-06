package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.data.cloud.CloudDataSource
import com.zinoview.translatorapp.data.cloud.CloudResultMapper
import com.zinoview.translatorapp.data.cloud.CloudWord

interface WordRepository<T> {

    suspend fun translatedWord(srcWord: String) : T

    class Base(
        private val cloudDataSource: CloudDataSource<CloudWord>,
        private val cloudResultMapper: CloudResultMapper,
        private val exceptionMapper: ExceptionMapper
    ) : WordRepository<DataWord> {

        override suspend fun translatedWord(srcWord: String): DataWord {
            return try {
                val cloudTranslatedWord = cloudDataSource.translatedWord(srcWord)
                return cloudTranslatedWord.map(cloudResultMapper)
            } catch (e: Exception) {
                val errorMessage = exceptionMapper.map(e)
                DataWord.Failure(errorMessage)
            }
        }

    }

    class Test(
        private val cloudDataSource: CloudDataSource<DataWord>
    ) : WordRepository<DataWord> {

        override suspend fun translatedWord(srcWord: String): DataWord
            = cloudDataSource.translatedWord(srcWord)
    }
}