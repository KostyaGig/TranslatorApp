package com.zinoview.translatorapp.domain.words.sync

import com.zinoview.translatorapp.data.words.sync.SyncWordsRepository

interface SyncWordsInteractor {

    suspend fun sync()

    class Base(
        private val syncWordsRepository: SyncWordsRepository
    ) : SyncWordsInteractor {

        override suspend fun sync()
            = syncWordsRepository.sync()
    }

    interface Test : SyncWordsInteractor {
        fun isSync() : Boolean

        class Base : Test {

            private var successAuth = false

            override fun isSync(): Boolean
                = successAuth

            override suspend fun sync() {
                successAuth = !successAuth
            }
        }
    }

}