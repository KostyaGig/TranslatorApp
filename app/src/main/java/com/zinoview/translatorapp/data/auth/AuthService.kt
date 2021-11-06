package com.zinoview.translatorapp.data.auth

import retrofit2.http.*

/**
 * Temp base url - http://translatorappserver.pythonanywhere.com
 */

interface AuthService {

    @GET("/register/{userName}/{userNumberPhone}")
    suspend fun register(@Path("userName") userName: String, @Path("userNumberPhone") userNumberPhone: String) : CloudAuth.Base

    @GET("/login/{userName}/{userNumberPhone}")
    suspend fun login(@Path("userName") userName: String, @Path("userNumberPhone") userNumberPhone: String) : CloudAuth.Base
}