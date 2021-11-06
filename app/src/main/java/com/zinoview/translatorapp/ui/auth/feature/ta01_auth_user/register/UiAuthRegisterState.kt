package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.register

import android.os.Bundle
import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.ui.auth.fragment.LoginFragment
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment

sealed class UiAuthRegisterState : Abstract.Register {

    override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
        = mapper.map("")

    open fun map(navigation: Navigation,enteredWord: String) = Unit

    object Empty : UiAuthRegisterState()

    object Progress : UiAuthRegisterState() {

        override fun map(navigation: Navigation,enteredWord: String) = log("Register state Progress")
    }

    class Success(
        private val message: String
    ) : UiAuthRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.map(message)

        override fun map(navigation: Navigation,enteredWord: String) {
            val searchFragment = SearchWordsFragment().apply {
                arguments = Bundle().apply {
                    putString("entered_word",enteredWord)
                }
            }
            navigation.navigateTo(searchFragment)
        }
    }

    class Exist(private val message: String) : UiAuthRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapExist(message)

        override fun map(navigation: Navigation,enteredWord: String) {
            log("Register exist $message")
            val loginFragment = LoginFragment().apply {
                arguments = Bundle().apply {
                    putString("entered_word",enteredWord)
                }
            }
            navigation.navigateTo(loginFragment)
        }
    }

    class Failure(
        private val message: String
    ) : UiAuthRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapFailure(message)

        override fun map(navigation: Navigation,enteredWord: String) = log("Register state Failure $message")

    }

}