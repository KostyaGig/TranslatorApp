package com.zinoview.translatorapp.ui.core.nav

import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.SelectItem


interface Navigation : Exit, SelectItem {

    fun navigateTo(fragment: BaseFragment)
}