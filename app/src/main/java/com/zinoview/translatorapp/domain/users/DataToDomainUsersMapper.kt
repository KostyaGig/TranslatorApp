package com.zinoview.translatorapp.domain.users

import com.zinoview.translatorapp.core.users.Abstract

interface DataToDomainUsersMapper : Abstract.UsersMapper<DomainUsers> {

    class Base : DataToDomainUsersMapper {

        override fun map(users: List<String>): DomainUsers
            = DomainUsers.Success.Cloud(users)

        override fun map(message: String): DomainUsers = DomainUsers.Failure(message)


    }
}