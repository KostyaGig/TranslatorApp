package com.zinoview.translatorapp.domain.users

import com.zinoview.translatorapp.core.users.Abstract

sealed class DomainUsers : Abstract.Users {

    abstract class Success : DomainUsers(){

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
    ) : DomainUsers() {

        override fun <T> map(mapper: Abstract.UsersMapper<T>): T = mapper.map(message)
    }
}