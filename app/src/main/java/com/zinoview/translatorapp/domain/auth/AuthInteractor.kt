package com.zinoview.translatorapp.domain.auth

import com.zinoview.translatorapp.data.auth.AuthRepository
import com.zinoview.translatorapp.domain.words.sync.SyncWordsInteractor


interface AuthInteractor {

    suspend fun register(userName: String, userPhone: String) : DomainAuth

    suspend fun login(userName: String, userPhone: String) : DomainAuth

    suspend fun requestAuthorize(): Boolean

    class Base(
        private val authRepository: AuthRepository,
        private val syncWordsInteractor: SyncWordsInteractor,
        private val domainRegisterMapper: DomainAuthMapper
    ) : AuthInteractor {

        override suspend fun register(userName: String, userPhone: String): DomainAuth {
            val dataRegister = authRepository.register(userName, userPhone)
            val domainRegister = dataRegister.map(domainRegisterMapper)
            domainRegister.syncWords(syncWordsInteractor)
            return domainRegister
        }

        override suspend fun login(userName: String, userPhone: String): DomainAuth {
            val dataRegister = authRepository.login(userName, userPhone)
            val domainRegister = dataRegister.map(domainRegisterMapper)
            domainRegister.syncWords(syncWordsInteractor)
            return domainRegister
        }

        override suspend fun requestAuthorize(): Boolean {
            return authRepository.requestAuthorize()
        }
    }
}