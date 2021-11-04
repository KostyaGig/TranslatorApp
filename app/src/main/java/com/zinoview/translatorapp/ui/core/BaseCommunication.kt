package com.zinoview.translatorapp.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

abstract class BaseCommunication<T : Any> : Communication<T> {

    private val liveData = MutableLiveData<T>()

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        liveData.observe(owner, observer)
    }

    override fun postValue(value: T) {
        liveData.value = value
    }

}