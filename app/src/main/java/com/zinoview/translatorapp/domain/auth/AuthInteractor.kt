package com.zinoview.translatorapp.domain.auth

import com.zinoview.translatorapp.data.auth.AuthRepository


interface AuthInteractor {

    suspend fun register(userName: String, userPhone: String) : DomainAuth

    suspend fun login(userName: String, userPhone: String) : DomainAuth

    class Base(
        private val authRepository: AuthRepository,
        private val domainRegisterMapper: DomainAuthMapper
    ) : AuthInteractor {

        override suspend fun register(userName: String, userPhone: String): DomainAuth {
            val dataRegister = authRepository.register(userName, userPhone)
            return dataRegister.map(domainRegisterMapper)
        }

        override suspend fun login(userName: String, userPhone: String): DomainAuth {
            val dataRegister = authRepository.login(userName, userPhone)
            return dataRegister.map(domainRegisterMapper)
        }
    }
}