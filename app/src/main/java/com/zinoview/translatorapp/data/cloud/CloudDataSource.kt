package com.zinoview.translatorapp.data.cloud

import com.zinoview.translatorapp.data.DataWord

interface CloudDataSource<T> {

    suspend fun translatedWord(srcWord: String) : T

    class Base(
        private val wordService: WordService
    ) : CloudDataSource<CloudWord> {

        override suspend fun translatedWord(srcWord: String): CloudWord
            = wordService.translatedWord(srcWord)
    }

    class Test : CloudDataSource<DataWord> {

        private var count = 1

        override suspend fun translatedWord(srcWord: String): DataWord {
            val result: DataWord
            result = if (count % 2 == 0) {
                count++
                DataWord.Success("Мышь","Mouse",com.zinoview.translatorapp.core.Language.Test())
            } else {
                count++
                DataWord.Failure("Test error")
            }
            if (count == 2) {
                count = 0
            }
            return result
        }


    }
}