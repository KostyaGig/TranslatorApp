package com.zinoview.translatorapp.data.words.cloud

import com.zinoview.translatorapp.data.core.cloud.CloudAbstractResponse
import retrofit2.http.*

/**
 * Base url - http://translatorappserver.pythonanywhere.com/
 * */

interface WordService {

    @GET("/translate/{word}")
    suspend fun translatedWord(@Path("word") srcWord: String) : CloudWord.Base

    @GET("/translateUniqueKey/{srcWord}/{userUniqueKey}")
    suspend fun translatedWordWithAuthorized(@Path("srcWord") srcWord: String,@Path("userUniqueKey") userUniqueKey: String) : CloudWord.Base

    @FormUrlEncoded
    @POST("/syncWords/{userUniqueKey}")
    suspend fun syncWords(
        @Field("wordsJson")
        jsonWords: String,
        @Path("userUniqueKey") userUniqueKey: String
    ) : CloudAbstractResponse.Base
}