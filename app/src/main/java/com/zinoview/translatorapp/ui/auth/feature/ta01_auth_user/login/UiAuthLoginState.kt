package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.login

import android.os.Bundle
import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment

sealed class UiAuthLoginState : Abstract.Register {

    override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map("")

    open fun map(navigation: Navigation,enteredWord: String) = Unit

    object Empty : UiAuthLoginState()

    object Progress : UiAuthLoginState() {
        override fun map(navigation: Navigation,enteredWord: String) = log("Login state Progress")
    }

    class Success(
        private val message: String
    ) : UiAuthLoginState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.map(message)

        override fun map(navigation: Navigation,enteredWord: String) {
            val searchFragment = SearchWordsFragment().apply {
                arguments = Bundle().apply {
                    putString("entered_word",enteredWord)
                }
            }
            navigation.navigateTo(SearchWordsFragment())
        }


    }

    class Failure(
        private val message: String
    ) : UiAuthLoginState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapFailure(message)

        override fun map(navigation: Navigation,enteredWord: String) = log("Login state Failure $message")


    }

}