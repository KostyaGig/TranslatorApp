package com.zinoview.translatorapp.data.words.sync.cloud

import com.zinoview.translatorapp.data.words.cloud.WordService

interface SyncWordsCloudDataSource {

    suspend fun sync(jsonWords: String) : CloudSyncWords

    class Base(
        private val wordsService: WordService
    ) : SyncWordsCloudDataSource {

        override suspend fun sync(jsonWords: String): CloudSyncWords
            = wordsService.syncWords(jsonWords)
    }

}