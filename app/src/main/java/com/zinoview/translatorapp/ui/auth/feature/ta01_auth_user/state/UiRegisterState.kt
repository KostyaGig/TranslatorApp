package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.state

import com.zinoview.translatorapp.core.auth.Abstract
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.view.WordProgressBar

sealed class UiRegisterState : Abstract.Register {

    override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
        = mapper.map("")

    open fun mapResult(progress: WordProgressBar) = Unit

    object Progress : UiRegisterState() {

        override fun mapResult(progress: WordProgressBar) {
            progress.show()
        }
    }

    class Success(
        private val message: String
    ) : UiRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
                = mapper.map(message)

        override fun mapResult(progress: WordProgressBar) {
            progress.hide()
        }
    }

    class Exist(private val message: String) : UiRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapExist(message)

        override fun mapResult(progress: WordProgressBar) {
            progress.hide()
        }
    }

    class Failure(
        private val message: String
    ) : UiRegisterState() {

        override fun <T> map(mapper: Abstract.RegisterMapper<T>): T
            = mapper.mapFailure(message)

        override fun mapResult(progress: WordProgressBar) {
            progress.hide()
        }
    }

}