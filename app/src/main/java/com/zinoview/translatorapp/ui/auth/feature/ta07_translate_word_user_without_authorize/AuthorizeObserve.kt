package com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register.UiAuthRegisterState
import com.zinoview.translatorapp.ui.core.Observe

interface AuthorizeObserve<T> : Observe<UiAuthRegisterState> {

    fun observeAuthorize(owner: LifecycleOwner, observer: Observer<AuthState>)
}