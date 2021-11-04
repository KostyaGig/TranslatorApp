package com.zinoview.translatorapp.data.auth

import com.zinoview.translatorapp.data.auth.cache.AuthSharedPreferences
import com.zinoview.translatorapp.ui.core.log

interface AuthRepository {

    suspend fun register(userName: String, userPhone: String) : DataAuth

    suspend fun login(userName: String, userPhone: String) : DataAuth

    class Base(
        private val cloudDataSource: AuthCloudDataSource,
        private val cloudAuthMapper: CloudAuthMapper,
        private val exceptionMapper: ExceptionMapper,
        private val authSharedPreferences: AuthSharedPreferences
    ) : AuthRepository {

        override suspend fun register(userName: String, userPhone: String): DataAuth {
            return try {
                val cloudRegister = cloudDataSource.register(userName, userPhone)
                val dataAuth = cloudRegister.map(cloudAuthMapper)
                dataAuth.saveUniqueKey(authSharedPreferences)
                return dataAuth
            } catch (e: Exception) {
                val errorMessage = exceptionMapper.map(e)
                DataAuth.Failure(errorMessage)
            }
        }

        override suspend fun login(userName: String, userPhone: String): DataAuth {
            return try {
                val cloudRegister = cloudDataSource.login(userName, userPhone)
                return cloudRegister.map(cloudAuthMapper)
            } catch (e: Exception) {
                val errorMessage = exceptionMapper.map(e)
                DataAuth.Failure(errorMessage)
            }
        }
    }

    class Test(
        private val authCloudDataSource: AuthCloudDataSource,
        private val cloudAuthMapper: CloudAuthMapper
    ) : AuthRepository {

        override suspend fun register(userName: String, userPhone: String): DataAuth {
            return authCloudDataSource.register(userName, userPhone).map(cloudAuthMapper)
        }

        override suspend fun login(userName: String, userPhone: String): DataAuth {
            return authCloudDataSource.login(userName, userPhone).map(cloudAuthMapper)
        }

    }
}