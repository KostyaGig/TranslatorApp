package com.zinoview.translatorapp.data.auth

import com.zinoview.translatorapp.data.auth.core.AuthCloudDataSource
import com.zinoview.translatorapp.data.auth.core.ExceptionMapper
import com.zinoview.translatorapp.data.auth.register.cloud.CloudRegisterMapper
import com.zinoview.translatorapp.data.auth.register.DataRegister


interface AuthRepository {

    suspend fun register(userName: String, userPhone: String) : DataRegister

    class Base(
        private val cloudDataSource: AuthCloudDataSource,
        private val cloudRegisterMapper: CloudRegisterMapper,
        private val exceptionMapper: ExceptionMapper,
    ) : AuthRepository {

        override suspend fun register(userName: String, userPhone: String): DataRegister {
            return try {
                val cloudRegister = cloudDataSource.register(userName, userPhone)
                return cloudRegister.map(cloudRegisterMapper)
            } catch (e: Exception) {
                val errorMessage = exceptionMapper.map(e)
                DataRegister.Failure(errorMessage)
            }
        }
    }
}