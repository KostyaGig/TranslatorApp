package com.zinoview.translatorapp.data.auth

import retrofit2.http.*

/**
 * Temp base url - http://translatorappserver.pythonanywhere.com
 */

interface AuthService {

    //todo Come up with following: how result should return our server?
    @POST("/register/{userName}/{userNumberPhone}")
    suspend fun register(@Path("userName") userName: String, @Path("userNumberPhone") userNumberPhone: String) : CloudAuth.Base

    @POST("/login/{userName}/{userNumberPhone}")
    suspend fun login(@Path("userName") userName: String, @Path("userNumberPhone") userNumberPhone: String) : CloudAuth.Base
}