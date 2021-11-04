package com.zinoview.translatorapp.data.auth

interface AuthRepository {

    suspend fun register(userName: String, userPhone: String) : DataAuth

    suspend fun login(userName: String, userPhone: String) : DataAuth

    class Base(
        private val cloudDataSource: AuthCloudDataSource,
        private val cloudAuthMapper: CloudAuthMapper,
        private val exceptionMapper: ExceptionMapper,
    ) : AuthRepository {

        override suspend fun register(userName: String, userPhone: String): DataAuth {
            return try {
                val cloudRegister = cloudDataSource.register(userName, userPhone)
                return cloudRegister.map(cloudAuthMapper)
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
}