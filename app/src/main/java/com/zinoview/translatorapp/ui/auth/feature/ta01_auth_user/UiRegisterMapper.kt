package com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user

import com.zinoview.translatorapp.core.auth.Abstract

interface UiRegisterMapper : Abstract.RegisterMapper<UiRegister> {

    class Base : UiRegisterMapper {

        override fun map(message: String): UiRegister
            = UiRegister.Success(message)

        override fun mapExist(message: String): UiRegister
            = UiRegister.Exist(message)

        override fun mapFailure(message: String): UiRegister
            = UiRegister.Failure(message)
    }
}