package com.zinoview.translatorapp.data.users

import com.zinoview.translatorapp.core.users.Abstract

interface CloudToDataUsersMapper : Abstract.UsersMapper<DataUsers> {

    class Base : CloudToDataUsersMapper {

        override fun map(users: List<String>): DataUsers
            = DataUsers.Success.Cloud(users)

        override fun map(message: String): DataUsers = DataUsers.Failure(message)

        override fun mapCache(users: List<String>): DataUsers
            = DataUsers.Success.Cache(emptyList())
    }
}