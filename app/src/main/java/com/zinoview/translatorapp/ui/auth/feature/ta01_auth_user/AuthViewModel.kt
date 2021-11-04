package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.state.UiRegisterState
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.state.UiRegisterStateMapper
import com.zinoview.translatorapp.ui.core.BaseCommunication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//todo replace second generic type
interface AuthViewModel : AuthObserve<UiRegisterState, UiRegisterState> {

    fun register(userName: String, userPhone: String)

    class Base(
        private val authInteractor: AuthInteractor,
        private val uiRegisterMapper: UiRegisterMapper,
        private val uiRegisterStateMapper: UiRegisterStateMapper,
        private val registerCommunication: BaseCommunication<UiRegisterState>,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : AuthViewModel, ViewModel() {

        override fun register(userName: String, userPhone: String) {
            registerCommunication.postValue(UiRegisterState.Progress)
            viewModelScope.launch(defaultDispatcher) {
                val domainRegister = authInteractor.register(userName,userPhone)
                val uiRegister = domainRegister.map(uiRegisterMapper)
                val uiRegisterState = uiRegister.map(uiRegisterStateMapper)

                withContext(Dispatchers.Main) {
                    registerCommunication.postValue(uiRegisterState)
                }
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiRegisterState>) {
            registerCommunication.observe(owner, observer)
        }

        override fun observeLogin(owner: LifecycleOwner, observer: Observer<UiRegisterState>) {
            TODO("Not yet implemented")
        }
    }
}