package com.zinoview.translatorapp.ui.core


interface Communication<T> : Observe<T> {

    fun postValue(value: T)
}