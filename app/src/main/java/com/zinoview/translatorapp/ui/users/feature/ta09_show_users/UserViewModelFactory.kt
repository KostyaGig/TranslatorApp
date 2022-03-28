package com.zinoview.translatorapp.ui.users.feature.ta09_show_users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zinoview.translatorapp.domain.users.UsersInteractor

interface UserViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val interactor: UsersInteractor
    ) : UserViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return UsersViewModel.Base(
                interactor,
                DomainToUiStateUsersMapper.Base(),
                UiStateUsersCommunication()
            ) as T
        }

    }
}