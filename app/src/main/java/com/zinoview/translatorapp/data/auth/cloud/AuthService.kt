package com.zinoview.translatorapp.data.auth.cloud

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Temp base url - http://127.0.0.1:5000
 */

interface AuthService {

    //todo Come up with following: how result should return our server?
    @POST("/register")
    suspend fun register(@Body userName: String, @Body userNumberPhone: String) : String
}