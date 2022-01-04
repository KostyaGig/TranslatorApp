package com.zinoview.translatorapp.data.words.sync

import com.zinoview.translatorapp.data.auth.cache.AuthSharedPreferences
import com.zinoview.translatorapp.data.auth.cache.UniqueKey
import com.zinoview.translatorapp.data.words.cache.CacheDataSource
import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import com.zinoview.translatorapp.data.words.sync.cloud.SyncWordsCloudDataSource
import com.zinoview.translatorapp.ui.core.log

interface SyncWordsRepository {

    suspend fun sync()

    class Base(
        private val cloudDataSource: SyncWordsCloudDataSource,
        private val json: Json,
        private val cacheWordToSyncWordMapper: CacheWordToSyncWordMapper,
        private val sharedPreferences: AuthSharedPreferences,
        private val cacheDataSource: CacheDataSource<CacheWord>
    ) : SyncWordsRepository {

        override suspend fun sync() {
            val listSyncWord = cacheDataSource.words().map {
                it.map(cacheWordToSyncWordMapper)
            }

            val syncWords = SyncWords.Base(listSyncWord)


            val jsonWords = json.json(syncWords)
            cloudDataSource.sync(jsonWords,sharedPreferences.read())
        }

    }
}