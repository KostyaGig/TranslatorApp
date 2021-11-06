package com.zinoview.translatorapp.ui.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.zinoview.translatorapp.ui.core.nav.Navigation

abstract class BaseFragment(@LayoutRes id: Int) : Fragment(id) {

    protected val navigation by lazy {
        requireActivity() as Navigation
    }

    abstract fun navigateToBack()

    protected companion object {

        const val ENTERED_WORD = "entered_word"
    }
}