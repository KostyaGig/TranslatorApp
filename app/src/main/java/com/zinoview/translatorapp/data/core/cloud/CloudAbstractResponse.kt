package com.zinoview.translatorapp.data.core.cloud

import com.google.gson.annotations.SerializedName
import com.zinoview.translatorapp.core.words.Abstract

interface CloudAbstractResponse : Abstract.Response {

    class Base(
        @SerializedName("mark")
        private val mark:String = FAILURE_MARK,
        @SerializedName("message")
        private val message: String = ""
    ) : CloudAbstractResponse {

        override fun <T> map(mapper: Abstract.ResponseMapper<T>): T {
            return if (mark == FAILURE_MARK) {
                mapper.map(message)
            } else {
                mapper.map()
            }
        }

        private companion object {

            //todo move this const to shared interface and use his anywhere places when is need
            private const val FAILURE_MARK = "Failure"
        }
    }
}