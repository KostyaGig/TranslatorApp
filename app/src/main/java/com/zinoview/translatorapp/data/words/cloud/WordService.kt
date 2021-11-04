package com.zinoview.translatorapp.data.words.cloud

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Base url - http://translatorappserver.pythonanywhere.com/
 * */

interface WordService {

    @GET("/translate/{word}")
    suspend fun translatedWord(@Path("word") srcWord: String) : CloudWord.Base
}