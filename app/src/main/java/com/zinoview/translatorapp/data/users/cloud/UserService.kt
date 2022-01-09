package com.zinoview.translatorapp.data.users.cloud

import retrofit2.http.GET

interface UserService {

    @GET("/users")
    suspend fun users() : CloudUsers.Base
}