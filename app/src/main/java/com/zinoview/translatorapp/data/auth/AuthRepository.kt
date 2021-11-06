package com.zinoview.translatorapp.data.auth

import com.zinoview.translatorapp.data.auth.cache.AuthSharedPreferences

interface AuthRepository {

    suspend fun register(userName: String, userPhone: String) : DataAuth

    suspend fun login(userName: String, userPhone: String) : DataAuth

    suspend fun requestAuthorize(): Boolean

    class Base(
        private val cloudDataSource: AuthCloudDataSource,
        private val cloudAuthMapper: CloudAuthMapper,
        private val exceptionMapper: ExceptionMapper,
        private val authSharedPreferences: AuthSharedPreferences
    ) : AuthRepository {

        override suspend fun register(userName: String, userPhone: String): DataAuth {
            return try {
                val cloudRegister = cloudDataSource.register(userName, userPhone)
                authorize(cloudRegister)
            } catch (e: Exception) {
                val errorMessage = exceptionMapper.map(e)
                DataAuth.Failure(errorMessage)
            }
        }

        override suspend fun login(userName: String, userPhone: String): DataAuth {
            return try {
                val cloudRegister = cloudDataSource.login(userName, userPhone)
                authorize(cloudRegister)
            } catch (e: Exception) {
                val errorMessage = exceptionMapper.map(e)
                DataAuth.Failure(errorMessage)
            }
        }

        private suspend fun authorize(cloudAuth: CloudAuth) : DataAuth {
            val dataAuth = cloudAuth.map(cloudAuthMapper)
            dataAuth.saveUniqueKey(authSharedPreferences)
            return dataAuth
        }

        override suspend fun requestAuthorize(): Boolean {
            return authSharedPreferences.userIsAuthorized()
        }
    }

    class Test(
        private val authCloudDataSource: AuthCloudDataSource,
        private val cloudAuthMapper: CloudAuthMapper
    ) : AuthRepository {

        private var isAuthorized = false

        override suspend fun register(userName: String, userPhone: String): DataAuth {
            val dataAuth = authCloudDataSource.register(userName, userPhone).map(cloudAuthMapper)
            isAuthorized = dataAuth.saveUniqueKey()
            return dataAuth
        }

        override suspend fun login(userName: String, userPhone: String): DataAuth {
            val dataAuth = authCloudDataSource.login(userName, userPhone).map(cloudAuthMapper)
            isAuthorized = dataAuth.saveUniqueKey()
            return dataAuth
        }

        override suspend fun requestAuthorize(): Boolean {
            return isAuthorized
        }

    }
}