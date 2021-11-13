package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.login

import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.core.AuthHandle
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize.AuthSnackBar
import com.zinoview.translatorapp.ui.core.BottomNavigationActivity
import com.zinoview.translatorapp.ui.core.nav.Navigation

sealed class UiAuthLoginState : Abstract.Register,AuthHandle {

    override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map("")

    override fun map(navigation: Navigation,snackBar: AuthSnackBar) = Unit

    override fun map(activity: BottomNavigationActivity) = Unit

    object Empty : UiAuthLoginState()

    object Progress : UiAuthLoginState() {

        override fun map(navigation: Navigation,snackBar: AuthSnackBar) {
            snackBar.show("Loading...")
        }
    }

    class Success(
        private val message: String
    ) : UiAuthLoginState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.map(message)

        override fun map(navigation: Navigation,snackBar: AuthSnackBar) {
            snackBar.show(message)
            navigation.selectItem(R.id.words_item)
        }

        override fun map(activity: BottomNavigationActivity)
            = activity.inflateBottomNavigationMenu(R.menu.authentificated_main_menu)

    }

    class Failure(
        private val message: String
    ) : UiAuthLoginState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.mapFailure(message)

        override fun map(navigation: Navigation, snackBar: AuthSnackBar) {
            snackBar.show(message)
        }

    }

}