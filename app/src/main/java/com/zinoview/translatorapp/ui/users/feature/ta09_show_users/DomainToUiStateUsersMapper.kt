package com.zinoview.translatorapp.ui.users.feature.ta09_show_users

import com.zinoview.translatorapp.core.users.Abstract

interface DomainToUiStateUsersMapper : Abstract.UsersMapper<List<UiStateUsers>> {

    class Base : DomainToUiStateUsersMapper {

        override fun map(users: List<String>): List<UiStateUsers>
            =  users.map { name -> UiStateUsers.Success.Cloud(CLOUD_SOURCE_TITLE,name) }

        override fun mapCache(users: List<String>): List<UiStateUsers>
            = users.map { name -> UiStateUsers.Success.Cache(CACHE_SOURCE_TITLE,name) }

        override fun map(message: String): List<UiStateUsers> = listOf(UiStateUsers.Failure(message))

        private companion object {
            private const val CLOUD_SOURCE_TITLE = "Users (Read from cloud)"
            private const val CACHE_SOURCE_TITLE = "Users (Read from cache)"
        }
    }

}