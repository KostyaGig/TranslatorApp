package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user

import com.zinoview.translatorapp.core.auth.Abstract


sealed class UiRegister : Abstract.Register {

    class Success(
        private val message: String
    ) : UiRegister() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.map(message)
    }

    class Exist(private val message: String) : UiRegister() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.mapExist(message)
    }

    class Failure(
        private val message: String
    ) : UiRegister() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.mapFailure(message)
    }
}