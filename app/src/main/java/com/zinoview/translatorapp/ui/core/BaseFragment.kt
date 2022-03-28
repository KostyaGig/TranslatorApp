package com.zinoview.translatorapp.ui.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.zinoview.translatorapp.core.ResourceProvider
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.auth.fragment.LoginFragment
import com.zinoview.translatorapp.ui.auth.fragment.RegisterFragment
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.users.feature.ta09_show_users.UsersFragment
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment
import com.zinoview.translatorapp.ui.words.fragment.WordsFragment
import java.lang.IllegalArgumentException

abstract class BaseFragment(@LayoutRes id: Int) : Fragment(id) {

    protected val navigation by lazy {
        requireActivity() as Navigation
    }

    protected fun inject(fragment: BaseFragment) {
        val application = requireActivity().application as TAApplication
        val appComponent = application.appComponent
        when (fragment) {
            is WordsFragment -> appComponent.inject(fragment)
            is SearchWordsFragment -> appComponent.inject(fragment)
            is RegisterFragment -> appComponent.inject(fragment)
            is LoginFragment -> appComponent.inject(fragment)
            is UsersFragment -> appComponent.inject(fragment)
            else -> throw IllegalArgumentException("BaseFragment.inject(), fragment ${fragment.javaClass} not have component inject()")
        }
    }

    protected val bottomNavigationActivity by lazy {
        requireActivity() as BottomNavigationActivity
    }

    protected val resourceProvider by lazy {
        ResourceProvider.Base(requireContext())
    }

    abstract fun navigateToBack()
}