package com.zinoview.translatorapp.ui.feature.ta01_translate_word

interface Show<T> {

    fun show(arg: T)

    interface WordsAdapterShow<T> {

        fun show(items: T,position: Int)
    }
}

interface UiShow<A,B> : Show<A> {

    fun uiShow(arg: B)
}