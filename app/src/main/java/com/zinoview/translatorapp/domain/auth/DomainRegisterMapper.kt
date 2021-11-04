package com.zinoview.translatorapp.domain.auth

import com.zinoview.translatorapp.core.auth.Abstract

interface DomainRegisterMapper : Abstract.RegisterMapper<DomainRegister> {

    class Base : DomainRegisterMapper {

        override fun map(message: String): DomainRegister
            = DomainRegister.Success(message)

        override fun mapExist(message: String): DomainRegister
            = DomainRegister.Exist(message)

        override fun mapFailure(message: String): DomainRegister
            = DomainRegister.Failure(message)
    }
}