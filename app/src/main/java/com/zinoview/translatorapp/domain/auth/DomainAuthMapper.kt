package com.zinoview.translatorapp.domain.auth

import com.zinoview.translatorapp.core.auth.Abstract

interface DomainAuthMapper : Abstract.RegisterMapper<DomainAuth> {

    class Base : DomainAuthMapper {

        override fun map(message: String): DomainAuth
            = DomainAuth.Success(message)

        override fun mapExist(message: String): DomainAuth
            = DomainAuth.Exist(message)

        override fun mapFailure(message: String): DomainAuth
            = DomainAuth.Failure(message)
    }
}