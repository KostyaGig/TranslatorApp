package com.zinoview.translatorapp.core.auth

interface Abstract {

    interface Register {

        fun <T> map(mapper: RegisterMapper<T>) : T

    }

    interface RegisterMapper<T> : Mapper {

        fun map(message: String) : T

        fun mapFailure(message: String) : T

        fun mapExist(message: String) : T

    }

    interface Mapper
}