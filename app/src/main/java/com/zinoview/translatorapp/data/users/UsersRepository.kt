package com.zinoview.translatorapp.data.users

import com.zinoview.translatorapp.data.core.ExceptionHandlerRepository
import com.zinoview.translatorapp.data.core.cloud.NetworkConnection
import com.zinoview.translatorapp.data.users.cloud.CloudDataSource
import com.zinoview.translatorapp.data.words.ExceptionMapper
import java.lang.Exception

interface UsersRepository {

    suspend fun users() : DataUsers

    class Base(
        private val networkConnection: NetworkConnection,
        private val cloudToDataUsersMapper: CloudToDataUsersMapper,
        private val cloudDataSource: CloudDataSource,
        exceptionMapper: ExceptionMapper
    ) : UsersRepository, ExceptionHandlerRepository(exceptionMapper) {

        override suspend fun users(): DataUsers {
            return try {
                return if (networkConnection.isNotAvailable()) {
                    val cloudUsers = cloudDataSource.users()
                    // save users to cache
                    DataUsers.Success.Cloud(cloudUsers)
                } else {
                    // read from cache
                    DataUsers.Success.Cache()
                }
            } catch (e: Exception) {
                DataUsers.Failure(errorMessage(e))
            }
        }

        // todo unit test
    }
}