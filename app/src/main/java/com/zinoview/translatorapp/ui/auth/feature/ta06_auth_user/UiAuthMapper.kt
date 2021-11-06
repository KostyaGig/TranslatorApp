package com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user

import com.zinoview.translatorapp.core.auth.Abstract

interface UiAuthMapper : Abstract.RegisterMapper<UiAuth> {

    class Base : UiAuthMapper {

        override fun map(message: String): UiAuth
            = UiAuth.Success(message)

        override fun mapExist(message: String): UiAuth
            = UiAuth.Exist(message)

        override fun mapFailure(message: String): UiAuth
            = UiAuth.Failure(message)
    }
}