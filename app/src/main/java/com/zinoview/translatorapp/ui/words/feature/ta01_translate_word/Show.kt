package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

interface Show<T> {

    fun show(arg: T)

    interface WordsAdapterShow<T> {

        fun show(items: T,position: Int)
    }
}

interface UiShow<A,B> : com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.Show<A> {

    fun uiShow(arg: B)
}