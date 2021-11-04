package com.zinoview.translatorapp.data.auth.core

import com.zinoview.translatorapp.data.auth.register.cloud.CloudRegister

interface AuthCloudDataSource {

    suspend fun register(userName: String,userPhone: String) : CloudRegister

    class Base(
        private val authService: AuthService
    ) : AuthCloudDataSource {

        override suspend fun register(userName: String, userPhone: String): CloudRegister {
            return authService.register(userName,userPhone)
        }
    }
}