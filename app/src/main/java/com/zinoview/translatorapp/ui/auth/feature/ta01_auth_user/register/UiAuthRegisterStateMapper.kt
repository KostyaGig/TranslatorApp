package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.register

import com.zinoview.translatorapp.core.auth.Abstract

interface UiAuthRegisterStateMapper : Abstract.RegisterMapper<UiAuthRegisterState> {

    class Base : UiAuthRegisterStateMapper {

        override fun map(message: String): UiAuthRegisterState
            = UiAuthRegisterState.Success(message)

        override fun mapExist(message: String): UiAuthRegisterState
            = UiAuthRegisterState.Exist(message)

        override fun mapFailure(message: String): UiAuthRegisterState
            = UiAuthRegisterState.Failure(message)
    }
}