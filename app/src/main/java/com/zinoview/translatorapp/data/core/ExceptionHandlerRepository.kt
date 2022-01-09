package com.zinoview.translatorapp.data.core

import com.zinoview.translatorapp.data.words.ExceptionMapper
import java.lang.Exception

abstract class ExceptionHandlerRepository(
    private val exceptionMapper: ExceptionMapper
) {

    fun errorMessage(exception: Exception) = exceptionMapper.map(exception)
}