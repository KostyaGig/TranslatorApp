package com.zinoview.translatorapp.data.users

import com.zinoview.translatorapp.data.core.ExceptionHandlerRepository
import com.zinoview.translatorapp.data.core.cloud.NetworkConnection
import com.zinoview.translatorapp.data.users.cloud.UsersCloudDataSource
import com.zinoview.translatorapp.data.words.ExceptionMapper
import com.zinoview.translatorapp.ui.core.log
import java.lang.Exception

interface UsersRepository {

    suspend fun users() : DataUsers

    class Base(
        private val networkConnection: NetworkConnection,
        private val cloudToDataUsersMapper: CloudToDataUsersMapper,
        private val cloudDataSource: UsersCloudDataSource,
        exceptionMapper: ExceptionMapper
    ) : UsersRepository, ExceptionHandlerRepository(exceptionMapper) {

        override suspend fun users(): DataUsers {

            return try {
                return if (networkConnection.isAvailable()) {
//                    val cloudUsers = cloudDataSource.users()
                    // save users to cache
                    return DataUsers.Success.Cloud(
                        listOf("Bob","Wharthon","Asatryan","Zinoview")
                    )
//                    DataUsers.Success.Cloud(cloudUsers)
                } else {
                    // read from cache
                    DataUsers.Success.Cache(
                        listOf("Picasso","Jiraf","Lyho","Mino")
                    )
                }
            } catch (e: Exception) {
                DataUsers.Failure(errorMessage(e))
            }
        }

        // todo unit test
    }
}