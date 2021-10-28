package com.zinoview.translatorapp.ui.core.nav

import com.zinoview.translatorapp.ui.core.BaseFragment


interface Navigation {

    fun navigateTo(fragment: BaseFragment)

    fun exit()
}