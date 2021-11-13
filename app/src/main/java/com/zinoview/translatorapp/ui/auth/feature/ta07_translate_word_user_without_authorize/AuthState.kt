package com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize

import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.core.BottomNavigationActivity

sealed class AuthState {

    abstract fun handleAuth(activity: BottomNavigationActivity)

    object Auth : AuthState() {

        override fun handleAuth(activity: BottomNavigationActivity)
            = activity.inflateBottomNavigationMenu(R.menu.authentificated_main_menu)

    }

    object NotAuth : AuthState() {

        override fun handleAuth(activity: BottomNavigationActivity)
            = activity.inflateBottomNavigationMenu(R.menu.main_menu)
    }
}