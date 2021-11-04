package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.UiAuthMapper
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.register.UiAuthRegisterState
import com.zinoview.translatorapp.ui.core.BaseCommunication
import com.zinoview.translatorapp.ui.core.Observe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface LoginViewModel : Observe<UiAuthLoginState> {

    fun login(userName: String, userPhone: String)

    fun clean()

    class Base(
        private val authInteractor: AuthInteractor,
        private val uiAuthMapper: UiAuthMapper,
        private val uiAuthLoginStateMapper: UiAuthLoginStateMapper,
        private val loginCommunication: BaseCommunication<UiAuthLoginState>,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : LoginViewModel, ViewModel() {


        override fun login(userName: String, userPhone: String) {
            loginCommunication.postValue(UiAuthLoginState.Progress)
            viewModelScope.launch(defaultDispatcher) {
                val domainAuth = authInteractor.login(userName,userPhone)
                val uiAuth = domainAuth.map(uiAuthMapper)
                val uiAuthState = uiAuth.map(uiAuthLoginStateMapper)

                withContext(Dispatchers.Main) {
                    loginCommunication.postValue(uiAuthState)
                }
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiAuthLoginState>) {
            loginCommunication.observe(owner, observer)
        }

        override fun clean() {
            loginCommunication.postValue(UiAuthLoginState.Empty)
        }
    }
}