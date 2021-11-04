package com.zinoview.translatorapp.data.auth.core

import com.zinoview.translatorapp.data.auth.register.cloud.CloudRegister
import retrofit2.http.*

/**
 * Temp base url - http://translatorappserver.pythonanywhere.com
 */

interface AuthService {

    //todo Come up with following: how result should return our server?
    @POST("/register/{userName}/{userNumberPhone}")
    suspend fun register(@Path("userName") userName: String, @Path("userNumberPhone") userNumberPhone: String) : CloudRegister.Base
}