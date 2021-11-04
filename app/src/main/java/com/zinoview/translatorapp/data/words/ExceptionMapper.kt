package com.zinoview.translatorapp.data.words

import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.FactoryMapper
import com.zinoview.translatorapp.core.ResourceProvider
import com.zinoview.translatorapp.ui.core.info
import retrofit2.HttpException
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ExceptionMapper : FactoryMapper<Exception,String> {

    class Base(
        private val resourceProvider: ResourceProvider
    ) : ExceptionMapper {

        override fun map(src: Exception): String =
            when(src) {
                is UnknownHostException -> resourceProvider.string(R.string.no_connection_error)
                is HttpException -> resourceProvider.string(R.string.not_correctly_entered_word_error)
                is SocketTimeoutException -> resourceProvider.string(R.string.timeout_error)
                else -> throw IllegalArgumentException("data -> ExceptionMapper not found ${src.info()}")
            }
    }

    class Test : ExceptionMapper {

        override fun map(src: Exception): String {
            return when(src) {
                is UnknownHostException -> NO_CONNECTION_ERROR
                is HttpException -> NOT_CORRECTLY_ENTERED_WORD_ERROR
                is SocketTimeoutException -> TIMEOUT_ERROR
                else -> GENERIC_ERROR
            }
        }

        private companion object {
            const val NO_CONNECTION_ERROR = "No connection"
            const val NOT_CORRECTLY_ENTERED_WORD_ERROR = "Not correctly entered word"
            const val TIMEOUT_ERROR = "Long connection with server"
            const val GENERIC_ERROR = "Generic"
        }
    }

}

