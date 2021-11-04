package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zinoview.translatorapp.ui.core.Observe

interface AuthObserve<T,E> : Observe<T> {

    fun observeLogin(owner: LifecycleOwner, observer: Observer<E>)
}