package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.login

import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_user_without_authorize.AuthorizeSnackBar
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.words.fragment.WordsFragment

sealed class UiAuthLoginState : Abstract.Register {

    override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map("")

    open fun map(navigation: Navigation,snackBar: AuthorizeSnackBar) = Unit

    object Empty : UiAuthLoginState()

    object Progress : UiAuthLoginState() {

        override fun map(navigation: Navigation,snackBar: AuthorizeSnackBar) {
            snackBar.show("Loading...")
        }
    }

    class Success(
        private val message: String
    ) : UiAuthLoginState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.map(message)

        override fun map(navigation: Navigation,snackBar: AuthorizeSnackBar) {
            snackBar.show(message)
            navigation.navigateTo(WordsFragment())
            navigation.selectItem(R.id.translate_item)
        }


    }

    class Failure(
        private val message: String
    ) : UiAuthLoginState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.mapFailure(message)

        override fun map(navigation: Navigation, snackBar: AuthorizeSnackBar) {
            snackBar.show(message)
        }

    }

}