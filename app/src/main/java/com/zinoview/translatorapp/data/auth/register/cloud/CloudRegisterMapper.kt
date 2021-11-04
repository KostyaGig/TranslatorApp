package com.zinoview.translatorapp.data.auth.register.cloud

import com.zinoview.translatorapp.core.FactoryMapper
import java.lang.IllegalStateException

/**
 * @param src - result from server
 */
interface CloudRegisterMapper : FactoryMapper<String, CloudRegisterMapper.StateMark> {

    class Base : CloudRegisterMapper {

        override fun map(src: String): StateMark {
            return when(src) {
                SUCCESS -> StateMark.SUCCESS
                FAILURE -> StateMark.FAILURE
                EXIST -> StateMark.EXIST
                else -> throw IllegalStateException("CloudResultMapper not found $src state")
            }
        }

        private companion object {
            const val SUCCESS = "Success"
            const val FAILURE = "Failure"
            const val EXIST = "Exist"
        }
    }

    enum class StateMark{
        SUCCESS,FAILURE,EXIST
    }
}
