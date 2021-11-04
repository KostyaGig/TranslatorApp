package com.zinoview.translatorapp.data.auth.register

import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.ui.core.log

sealed class DataRegister : Abstract.Register {

    //todo save unique key to shared pref
//    open fun save

    class Success(
        private val message: String,
        private val uniqueKey: String
    ) : DataRegister() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T {
            log("Data register Success map unique key -> $uniqueKey")
            return mapper.map(message)
        }
    }

    class Exist(private val message: String) : DataRegister() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T {
            log("Data register Exist map $message")
            return mapper.mapExist(message)
        }
    }

    class Failure(
        private val message: String
    ) : DataRegister() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T {
            log("Data register Failure map $message")
            return mapper.mapFailure(message)
        }

    }
}
