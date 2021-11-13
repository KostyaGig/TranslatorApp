package com.zinoview.translatorapp.ui.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.zinoview.translatorapp.core.ResourceProvider
import com.zinoview.translatorapp.ui.core.nav.Navigation

abstract class BaseFragment(@LayoutRes id: Int) : Fragment(id) {

    protected val navigation by lazy {
        requireActivity() as Navigation
    }

    protected val bottomNavigationActivity by lazy {
        requireActivity() as BottomNavigationActivity
    }

    protected val resourceProvider by lazy {
        ResourceProvider.Base(requireContext())
    }

    abstract fun navigateToBack()


}