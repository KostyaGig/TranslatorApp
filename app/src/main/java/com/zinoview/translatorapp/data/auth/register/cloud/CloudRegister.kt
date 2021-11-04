package com.zinoview.translatorapp.data.auth.register.cloud

import com.google.gson.annotations.SerializedName
import com.zinoview.translatorapp.data.auth.register.DataRegister

interface CloudRegister {

    fun map(mapper: CloudRegisterMapper) : DataRegister

    class Base(
        @SerializedName("message")
        private val message: String = "",
        @SerializedName("mark")
        private val mark: String = "",
        @SerializedName("uniqueKey")
        private val uniqueKey: String = ""
    ) : CloudRegister {

        override fun map(mapper: CloudRegisterMapper): DataRegister {
            val stateWord = mapper.map(mark)
            return when(stateWord) {
               CloudRegisterMapper.StateMark.SUCCESS -> DataRegister.Success(
                    message,uniqueKey
                )
                CloudRegisterMapper.StateMark.EXIST -> DataRegister.Exist(
                    message
                )
                CloudRegisterMapper.StateMark.FAILURE -> DataRegister.Failure(
                    message
                )
            }
        }

    }
}
