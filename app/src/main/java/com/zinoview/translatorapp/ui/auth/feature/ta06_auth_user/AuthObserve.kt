package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zinoview.translatorapp.ui.core.Observe

interface AuthObserve<T> : Observe<T> {

    fun observeLogin(owner: LifecycleOwner, observer: Observer<T>)
}