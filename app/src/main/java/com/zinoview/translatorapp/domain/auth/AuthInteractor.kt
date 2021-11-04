package com.zinoview.translatorapp.domain.auth

import com.zinoview.translatorapp.data.auth.AuthRepository


interface AuthInteractor {

    suspend fun register(userName: String, userPhone: String) : DomainRegister

    class Base(
        private val authRepository: AuthRepository,
        private val domainRegisterMapper: DomainRegisterMapper
    ) : AuthInteractor {

        override suspend fun register(userName: String, userPhone: String): DomainRegister {
            val dataRegister = authRepository.register(userName, userPhone)
            return dataRegister.map(domainRegisterMapper)
        }
    }
}