package com.zinoview.translatorapp.data.cloud

import com.zinoview.translatorapp.data.DataWords

interface CloudDataSource<T> {

    suspend fun translatedWord(srcWord: String) : T

    class Base(
        private val wordService: WordService
    ) : CloudDataSource<CloudWord> {

        override suspend fun translatedWord(srcWord: String): CloudWord
            = wordService.translatedWord(srcWord)
    }

    class Test : CloudDataSource<DataWords> {

        private var count = 1

        override suspend fun translatedWord(srcWord: String): DataWords {
            val result: DataWords
            result = if (count % 2 == 0) {
                count++
                val word = DataWords.Test("Мышь","Mouse")
                word
            } else {
                count++
                DataWords.Failure("Test error")
            }
            if (count == 2) {
                count = 0
            }
            return result
        }


    }
}