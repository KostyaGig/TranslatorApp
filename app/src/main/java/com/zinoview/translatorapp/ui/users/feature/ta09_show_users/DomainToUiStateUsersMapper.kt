package com.zinoview.translatorapp.ui.users.feature.ta09_show_users

import com.zinoview.translatorapp.core.users.Abstract

interface DomainToUiStateUsersMapper : Abstract.UsersMapper<List<UiStateUsers>> {

    class Base : DomainToUiStateUsersMapper {

        override fun map(users: List<String>): List<UiStateUsers>
                =  users.map { name -> UiStateUsers.Success.Cloud(name) }

        override fun map(message: String): List<UiStateUsers> = listOf(UiStateUsers.Failure(message))

    }

}