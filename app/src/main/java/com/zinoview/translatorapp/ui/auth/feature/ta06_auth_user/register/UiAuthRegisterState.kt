package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register

import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_user_without_authorize.AuthorizeSnackBar
import com.zinoview.translatorapp.ui.auth.fragment.LoginFragment
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.words.fragment.WordsFragment

sealed class UiAuthRegisterState : Abstract.Register {

    override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
        = mapper.map("")

    open fun map(navigation: Navigation,snackBar: AuthorizeSnackBar) = Unit

    object Empty : UiAuthRegisterState()

    object Progress : UiAuthRegisterState() {

        override fun map(navigation: Navigation,snackBar: AuthorizeSnackBar) {
            snackBar.show("Loading...")
        }
    }

    class Success(
        private val message: String
    ) : UiAuthRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map(message)

        override fun map(navigation: Navigation,snackBar: AuthorizeSnackBar) {
            snackBar.show(message)
            navigation.navigateTo(WordsFragment())
            navigation.selectItem(R.id.translate_item)
        }
    }

    class Exist(private val message: String) : UiAuthRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapExist(message)

        override fun map(navigation: Navigation,snackBar: AuthorizeSnackBar) {
            snackBar.show(message)
            navigation.navigateTo(LoginFragment())
        }
    }

    class Failure(
        private val message: String
    ) : UiAuthRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapFailure(message)

        override fun map(navigation: Navigation, snackBar: AuthorizeSnackBar) {
            snackBar.show(message)
        }

    }

}