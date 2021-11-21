package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.UiAuthMapper

interface LoginViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val authInteractor: AuthInteractor,
        private val uiAuthMapper: UiAuthMapper
    ) : LoginViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel.Base(
                authInteractor,
                uiAuthMapper,
                UiAuthLoginStateMapper.Base(),
                LoginCommunication()
            ) as T
        }
    }
}