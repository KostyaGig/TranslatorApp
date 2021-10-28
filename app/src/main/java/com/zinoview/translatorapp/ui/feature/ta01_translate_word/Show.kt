package com.zinoview.translatorapp.ui.feature.ta01_translate_word

interface Show<T> {

    fun show(arg: T)
}

interface UiShow<A,B> : Show<A> {

    fun uiShow(arg: B)
}