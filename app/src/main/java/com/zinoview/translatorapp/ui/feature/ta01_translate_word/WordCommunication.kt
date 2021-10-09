package com.zinoview.translatorapp.ui.feature.ta01_translate_word

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zinoview.translatorapp.ui.core.Observe

interface WordCommunication<T> : Observe<T> {

    fun postValue(value: T)

    //todo remove after adding destroyed viewmodel
    fun clear()

    class Base : WordCommunication<UiWordState> {

        private val liveData = MutableLiveData<UiWordState>()

        override fun observe(owner: LifecycleOwner, observer: Observer<UiWordState>) {
            liveData.observe(owner, observer)
        }

        override fun postValue(value: UiWordState)
            = liveData.postValue(value)

        override fun clear() {
            //Mock value for last livevadata value
            liveData.value = UiWordState.Empty
        }

    }
}