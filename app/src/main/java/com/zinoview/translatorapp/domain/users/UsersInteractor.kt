package com.zinoview.translatorapp.domain.users

import com.zinoview.translatorapp.data.users.UsersRepository

interface UsersInteractor {

    suspend fun users() : DomainUsers

    class Base(
        private val dataToDomainUsersMapper: DataToDomainUsersMapper,
        private val repository: UsersRepository
    ) : UsersInteractor {

        override suspend fun users(): DomainUsers
            = repository.users().map(dataToDomainUsersMapper)
    }
}