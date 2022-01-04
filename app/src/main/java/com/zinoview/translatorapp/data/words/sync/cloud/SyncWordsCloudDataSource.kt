package com.zinoview.translatorapp.data.words.sync.cloud

import com.zinoview.translatorapp.data.auth.cache.UniqueKey
import com.zinoview.translatorapp.data.words.cloud.WordService

interface SyncWordsCloudDataSource {

    suspend fun sync(
        jsonWords: String,
        uniqueKey: String
    ) : CloudSyncWords

    class Base(
        private val wordsService: WordService,
        private val cloudAbstractResponseToCloudSyncWordsMapper: CloudAbstractResponseToCloudSyncWordsMapper
    ) : SyncWordsCloudDataSource {

        override suspend fun sync(jsonWords: String,uniqueKey: String): CloudSyncWords
            = wordsService.syncWords(jsonWords,uniqueKey).map(cloudAbstractResponseToCloudSyncWordsMapper)
    }

}