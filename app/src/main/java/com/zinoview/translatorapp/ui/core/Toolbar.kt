package com.zinoview.translatorapp.ui.core

import androidx.appcompat.app.ActionBar

interface Toolbar {

    fun changeTitle(title: String)

    class Base(
        private val toolbar: ActionBar
    ) : Toolbar {

        override fun changeTitle(title: String) {
            toolbar.title = title
        }

    }
}