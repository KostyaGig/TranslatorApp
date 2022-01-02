package com.zinoview.translatorapp.data.words.cloud

import com.zinoview.translatorapp.data.words.sync.cloud.CloudSyncWords
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Base url - http://translatorappserver.pythonanywhere.com/
 * */

interface WordService {

    @GET("/translate/{word}")
    suspend fun translatedWord(@Path("word") srcWord: String) : CloudWord.Base

    @GET("/translateUniqueKey/{srcWord}/{userUniqueKey}")
    suspend fun translatedWordWithAuthorized(@Path("srcWord") srcWord: String,@Path("userUniqueKey") userUniqueKey: String) : CloudWord.Base

    //todo create method on server
    suspend fun syncWords(jsonWords: String) : CloudSyncWords.Base
}