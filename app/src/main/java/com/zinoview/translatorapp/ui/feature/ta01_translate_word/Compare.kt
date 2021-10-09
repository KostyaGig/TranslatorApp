package com.zinoview.translatorapp.ui.feature.ta01_translate_word

interface Compare<T> {

    fun compare(arg1: T,arg2: T) : Boolean
}