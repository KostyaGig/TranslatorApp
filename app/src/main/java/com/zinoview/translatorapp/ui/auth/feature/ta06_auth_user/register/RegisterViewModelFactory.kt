package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.UiAuthMapper

interface RegisterViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val authInteractor: AuthInteractor,
        private val uiAuthMapper: UiAuthMapper
    ) : RegisterViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegisterViewModel.Base(
                authInteractor,
                uiAuthMapper,
                UiAuthRegisterStateMapper.Base(),
                RegisterCommunication(),
                AuthorizeCommunication()
            ) as T
        }
    }
}