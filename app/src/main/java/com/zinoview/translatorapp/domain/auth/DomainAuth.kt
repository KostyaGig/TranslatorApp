package com.zinoview.translatorapp.domain.auth

import com.zinoview.translatorapp.core.auth.Abstract

sealed class DomainAuth : Abstract.Register {

    class Success(
        private val message: String
    ) : DomainAuth() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map(message)
    }

    class Exist(private val message: String) : DomainAuth() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapExist(message)
    }

    class Failure(
        private val message: String
    ) : DomainAuth() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapFailure(message)
    }
}
