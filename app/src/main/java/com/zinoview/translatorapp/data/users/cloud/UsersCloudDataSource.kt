package com.zinoview.translatorapp.data.users.cloud

interface UsersCloudDataSource {

    suspend fun users() : List<String>

    class Base(
        private val userService: UserService
    ) : UsersCloudDataSource {

        override suspend fun users(): List<String>
            = userService.users().users()
    }
}