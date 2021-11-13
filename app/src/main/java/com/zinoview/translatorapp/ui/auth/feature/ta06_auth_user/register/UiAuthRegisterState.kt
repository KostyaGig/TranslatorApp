package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register

import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.core.AuthHandle
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize.AuthSnackBar
import com.zinoview.translatorapp.ui.auth.fragment.LoginFragment
import com.zinoview.translatorapp.ui.core.BottomNavigationActivity
import com.zinoview.translatorapp.ui.core.nav.Navigation

sealed class UiAuthRegisterState : Abstract.Register,AuthHandle {

    override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
        = mapper.map("")

    override fun map(navigation: Navigation,snackBar: AuthSnackBar) = Unit

    override fun map(activity: BottomNavigationActivity) = Unit

    object Empty : UiAuthRegisterState()

    object Progress : UiAuthRegisterState() {

        override fun map(navigation: Navigation,snackBar: AuthSnackBar) {
            snackBar.show(LOADING_TEXT)
        }

        private const val LOADING_TEXT = "Loading..."
    }

    class Success(
        private val message: String
    ) : UiAuthRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map(message)

        override fun map(navigation: Navigation,snackBar: AuthSnackBar) {
            snackBar.show(message)
            navigation.selectItem(R.id.words_item)
        }

        override fun map(activity: BottomNavigationActivity)
            = activity.inflateBottomNavigationMenu(R.menu.authentificated_main_menu)
    }

    class Exist(private val message: String) : UiAuthRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapExist(message)

        override fun map(navigation: Navigation,snackBar: AuthSnackBar) {
            snackBar.show(message)
            navigation.navigateTo(LoginFragment())
        }
    }

    class Failure(
        private val message: String
    ) : UiAuthRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapFailure(message)

        override fun map(navigation: Navigation, snackBar: AuthSnackBar) {
            snackBar.show(message)
        }

    }

}