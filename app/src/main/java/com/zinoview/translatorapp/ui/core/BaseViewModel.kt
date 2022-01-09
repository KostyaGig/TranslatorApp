package com.zinoview.translatorapp.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel<T : Any>(
    private val communication: BaseCommunication<T>
) : ViewModel(), Observe<T> {

    override fun observe(owner: LifecycleOwner, observer: Observer<T>)
        = communication.observe(owner, observer)

    fun ui(block: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            block.invoke()
        }
    }
}