package com.zinoview.translatorapp.data.cloud

import com.zinoview.translatorapp.core.Abstract
import java.lang.IllegalStateException

/**
 * @param src - result from server
 */
interface CloudResultMapper : Abstract.FactoryMapper<String, CloudResultMapper.StateMark> {

    class Base : CloudResultMapper {

        override fun map(src: String): StateMark {
            return when(src) {
                SUCCESS -> StateMark.SUCCESS
                FAILURE-> StateMark.FAILURE
                else -> throw IllegalStateException("CloudResultMapper not found $src state")
            }
        }

        private companion object {
            const val SUCCESS = "Success"
            const val FAILURE = "Failure"

        }
    }

    enum class StateMark{
        SUCCESS,FAILURE
    }
}
