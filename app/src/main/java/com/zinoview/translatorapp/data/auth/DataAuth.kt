package com.zinoview.translatorapp.data.auth

import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.data.auth.cache.AuthSharedPreferences

sealed class DataAuth : Abstract.Register {

    open fun saveUniqueKey(authSharedPreferences: AuthSharedPreferences) = Unit

    class Success(
        private val message: String,
        private val uniqueKey: String
    ) : DataAuth() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map(message)

        override fun saveUniqueKey(authSharedPreferences: AuthSharedPreferences)
            = authSharedPreferences.save(uniqueKey)
    }

    //usage this state only for register
    class Exist(private val message: String) : DataAuth() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapExist(message)
    }

    class Failure(
        private val message: String
    ) : DataAuth() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapFailure(message)
    }
}
