package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.state

import com.zinoview.translatorapp.core.auth.Abstract

interface UiRegisterStateMapper : Abstract.RegisterMapper<UiRegisterState> {

    class Base : UiRegisterStateMapper {

        override fun map(message: String): UiRegisterState
            = UiRegisterState.Success(message)

        override fun mapExist(message: String): UiRegisterState
            = UiRegisterState.Exist(message)

        override fun mapFailure(message: String): UiRegisterState
            = UiRegisterState.Failure(message)
    }
}