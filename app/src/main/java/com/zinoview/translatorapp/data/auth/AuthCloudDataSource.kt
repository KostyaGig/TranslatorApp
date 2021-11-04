package com.zinoview.translatorapp.data.auth


interface AuthCloudDataSource {

    suspend fun register(userName: String,userPhone: String) : CloudAuth

    suspend fun login(userName: String,userPhone: String) : CloudAuth

    class Base(
        private val authService: AuthService
    ) : AuthCloudDataSource {

        override suspend fun register(userName: String, userPhone: String): CloudAuth {
            return authService.register(userName,userPhone)
        }

        override suspend fun login(userName: String, userPhone: String): CloudAuth {
            return authService.login(userName,userPhone)
        }
    }
}