package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.login

import com.zinoview.translatorapp.core.auth.Abstract

interface UiAuthLoginStateMapper : Abstract.RegisterMapper<UiAuthLoginState> {

    class Base : UiAuthLoginStateMapper {

        override fun map(message: String): UiAuthLoginState
            = UiAuthLoginState.Success(message)

        override fun mapExist(message: String): UiAuthLoginState
            = UiAuthLoginState.Progress

        override fun mapFailure(message: String): UiAuthLoginState
            = UiAuthLoginState.Failure(message)
    }
}