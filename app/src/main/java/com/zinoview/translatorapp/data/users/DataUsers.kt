package com.zinoview.translatorapp.data.users

import com.zinoview.translatorapp.core.users.Abstract

sealed class DataUsers : Abstract.Users {

    abstract class Success : DataUsers(){

        class Cloud(
            private val users: List<String>
        ) : Success() {

            override fun <T> map(mapper: Abstract.UsersMapper<T>): T
                = mapper.map(users)
        }

        class Cache() : Success() {
            override fun <T> map(mapper: Abstract.UsersMapper<T>): T
                = mapper.map("")

        }

    }

    class Failure(
        private val message: String
    ) : DataUsers() {

        override fun <T> map(mapper: Abstract.UsersMapper<T>): T = mapper.map(message)
    }
}