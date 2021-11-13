package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.core

import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize.AuthSnackBar
import com.zinoview.translatorapp.ui.core.BottomNavigationActivity
import com.zinoview.translatorapp.ui.core.nav.Navigation

interface AuthHandle {

    fun map(navigation: Navigation, snackBar: AuthSnackBar)

    fun map(activity: BottomNavigationActivity) = Unit
}