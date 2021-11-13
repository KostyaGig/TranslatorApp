package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.UiAuthMapper
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize.AuthState
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize.AuthorizeObserve
import com.zinoview.translatorapp.ui.core.BaseCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RegisterViewModel : AuthorizeObserve<UiAuthRegisterState> {

    fun register(userName: String, userPhone: String)

    fun clean()

    fun requestAuthorize()

    class Base(
        private val authInteractor: AuthInteractor,
        private val uiAuthMapper: UiAuthMapper,
        private val uiAuthRegisterStateMapper: UiAuthRegisterStateMapper,
        private val registerCommunication: BaseCommunication<UiAuthRegisterState>,
        private val authorizeCommunication: BaseCommunication<AuthState>,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : RegisterViewModel, ViewModel() {

        override fun register(userName: String, userPhone: String) {
            registerCommunication.postValue(UiAuthRegisterState.Progress)
            viewModelScope.launch(defaultDispatcher) {
                val domainRegister = authInteractor.register(userName,userPhone)
                val uiRegister = domainRegister.map(uiAuthMapper)
                val uiRegisterState = uiRegister.map(uiAuthRegisterStateMapper)

                withContext(Dispatchers.Main) {
                    registerCommunication.postValue(uiRegisterState)
                }
            }
        }

        override fun clean() {
            registerCommunication.postValue(UiAuthRegisterState.Empty)
        }


        override fun observe(owner: LifecycleOwner, observer: Observer<UiAuthRegisterState>) {
            registerCommunication.observe(owner, observer)
        }

        override fun requestAuthorize() {
            viewModelScope.launch(defaultDispatcher) {
                val authorize = authInteractor.requestAuthorize()
                val authState = authorize.mapAuth()
                withContext(Dispatchers.Main) {
                    authorizeCommunication.postValue(authState)
                }
            }
        }

        private fun Boolean.mapAuth() : AuthState {
            return if (this) AuthState.Auth else AuthState.NotAuth
        }

        override fun observeAuthorize(owner: LifecycleOwner, observer: Observer<AuthState>) {
            authorizeCommunication.observe(owner, observer)
        }

    }
}