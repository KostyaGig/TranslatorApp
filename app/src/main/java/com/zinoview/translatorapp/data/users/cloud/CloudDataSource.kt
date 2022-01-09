package com.zinoview.translatorapp.data.users.cloud

interface CloudDataSource {

    suspend fun users() : List<String>

    class Base(
        private val userService: UserService
    ) : CloudDataSource {

        override suspend fun users(): List<String>
            = userService.users().users()
    }
}