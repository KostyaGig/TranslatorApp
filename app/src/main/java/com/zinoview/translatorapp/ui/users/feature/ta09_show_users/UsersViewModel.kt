package com.zinoview.translatorapp.ui.users.feature.ta09_show_users

import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.users.UsersInteractor
import com.zinoview.translatorapp.ui.core.BaseViewModel
import com.zinoview.translatorapp.ui.core.Observe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface UsersViewModel : Observe<List<UiStateUsers>> {

    fun users()

    class Base(
        private val usersInteractor: UsersInteractor,
        private val domainToUiStateUsersMapper: DomainToUiStateUsersMapper,
        private val uiStateUsersCommunication: UiStateUsersCommunication,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : UsersViewModel, BaseViewModel<List<UiStateUsers>>(uiStateUsersCommunication) {

        override fun users() {
            uiStateUsersCommunication.postValue(listOf(UiStateUsers.Progress))
            viewModelScope.launch(defaultDispatcher) {
                val domainUsers = usersInteractor.users()
                val uiStateUsers = domainUsers.map(domainToUiStateUsersMapper)
                ui { uiStateUsersCommunication.postValue(uiStateUsers) }
            }
        }


    }
}