package com.zinoview.translatorapp.data.words.sync

import com.zinoview.translatorapp.data.words.cache.CacheDataSource
import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import com.zinoview.translatorapp.data.words.sync.cloud.SyncWordsCloudDataSource

interface SyncWordsRepository {

    suspend fun sync()

    class Base(
        private val cloudDataSource: SyncWordsCloudDataSource,
        private val json: Json,
        private val cacheDataSource: CacheDataSource<CacheWord>
    ) : SyncWordsRepository {

        override suspend fun sync() {
            val cacheWords = cacheDataSource.words()
            val jsonWords = json.json(cacheWords)
            cloudDataSource.sync(jsonWords)
        }

    }
}