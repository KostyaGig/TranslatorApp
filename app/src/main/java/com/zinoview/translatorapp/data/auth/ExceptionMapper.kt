package com.zinoview.translatorapp.data.auth

import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.FactoryMapper
import com.zinoview.translatorapp.core.ResourceProvider
import com.zinoview.translatorapp.ui.core.info
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
                is SocketTimeoutException -> resourceProvider.string(R.string.timeout_error)
                else -> throw IllegalArgumentException("data -> REGISTER ExceptionMapper not found ${src.info()}")
            }
    }

}

