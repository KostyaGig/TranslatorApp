package com.zinoview.translatorapp.core.users

interface Abstract {

    interface Users {

        fun <T> map(mapper: UsersMapper<T>) : T
    }

    interface UsersMapper<T> {

         fun map(users: List<String>) : T

         fun mapCache(users: List<String>) : T

         fun map(message: String) : T
    }
}