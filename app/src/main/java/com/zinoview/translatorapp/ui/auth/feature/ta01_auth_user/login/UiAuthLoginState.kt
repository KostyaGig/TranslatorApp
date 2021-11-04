package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.login

import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment

sealed class UiAuthLoginState : Abstract.Register {

    override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map("")

    open fun map(navigation: Navigation) = Unit

    object Empty : UiAuthLoginState()

    object Progress : UiAuthLoginState() {
        override fun map(navigation: Navigation) = log("Login state Progress")
    }

    class Success(
        private val message: String
    ) : UiAuthLoginState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.map(message)

        override fun map(navigation: Navigation) = navigation.navigateTo(SearchWordsFragment())

    }

    class Failure(
        private val message: String
    ) : UiAuthLoginState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapFailure(message)

        override fun map(navigation: Navigation) = log("Login state Failure $message")


    }

}