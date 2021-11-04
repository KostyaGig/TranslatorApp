package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.register

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.UiAuthMapper
import com.zinoview.translatorapp.ui.core.BaseCommunication
import com.zinoview.translatorapp.ui.core.Observe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface RegisterViewModel : Observe<UiAuthRegisterState> {

    fun register(userName: String, userPhone: String)

    fun clean()

    class Base(
        private val authInteractor: AuthInteractor,
        private val uiAuthMapper: UiAuthMapper,
        private val uiAuthRegisterStateMapper: UiAuthRegisterStateMapper,
        private val registerCommunication: BaseCommunication<UiAuthRegisterState>,
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

    }
}