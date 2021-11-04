package com.zinoview.translatorapp.data.auth

import com.google.gson.annotations.SerializedName

interface CloudAuth {

    fun map(mapper: CloudAuthMapper) : DataAuth

    class Base(
        @SerializedName("message")
        private val message: String = "",
        @SerializedName("mark")
        private val mark: String = "",
        @SerializedName("uniqueKey")
        private val uniqueKey: String = ""
    ) : CloudAuth {

        override fun map(mapper: CloudAuthMapper): DataAuth {
            val stateWord = mapper.map(mark)
            return when(stateWord) {
               CloudAuthMapper.StateMark.SUCCESS -> DataAuth.Success(
                    message,uniqueKey
                )
                CloudAuthMapper.StateMark.EXIST -> DataAuth.Exist(
                    message
                )
                CloudAuthMapper.StateMark.FAILURE -> DataAuth.Failure(
                    message
                )
            }
        }

    }
}
