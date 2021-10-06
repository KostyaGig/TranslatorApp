package com.zinoview.translatorapp.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface WordCommunication<T> {

    fun observe(owner: LifecycleOwner,observer: Observer<T>)

    fun postValue(value: T)

    class Base : WordCommunication<UiWordState> {

        private val liveData = MutableLiveData<UiWordState>()

        override fun observe(owner: LifecycleOwner, observer: Observer<UiWordState>)
            = liveData.observe(owner, observer)

        override fun postValue(value: UiWordState)
            = liveData.postValue(value)

    }
}