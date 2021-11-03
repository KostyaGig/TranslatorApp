package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zinoview.translatorapp.ui.core.Observe

interface WordCommunication<T> : Observe<T> {

    fun postValue(value: T)

    open class BaseWordCommunication<T : Any> : WordCommunication<T> {

        private val liveData = MutableLiveData<T>()

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }

        override fun postValue(value: T) {
            liveData.value = value
        }

    }

}