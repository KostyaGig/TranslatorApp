package com.zinoview.translatorapp.domain.auth

import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.domain.words.sync.SyncWordsInteractor
import com.zinoview.translatorapp.ui.core.log

sealed class DomainAuth : Abstract.Register {

    open suspend fun syncWords(syncWordsInteractor: SyncWordsInteractor) = Unit

    class Success(
        private val message: String
    ) : DomainAuth() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map(message)

        override suspend fun syncWords(syncWordsInteractor: SyncWordsInteractor) {
            log("Success sync words")
            syncWordsInteractor.sync()
        }
    }

    class Exist(private val message: String) : DomainAuth() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapExist(message)

        override suspend fun syncWords(syncWordsInteractor: SyncWordsInteractor) {
            log("Failure sync words: Exist account")
        }
    }

    class Failure(
        private val message: String
    ) : DomainAuth() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapFailure(message)
        override suspend fun syncWords(syncWordsInteractor: SyncWordsInteractor) {
            log("Failure sync words: $message")
        }
    }
}
