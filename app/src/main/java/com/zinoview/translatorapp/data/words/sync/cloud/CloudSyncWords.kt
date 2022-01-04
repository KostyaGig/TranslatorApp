package com.zinoview.translatorapp.data.words.sync.cloud

import com.zinoview.translatorapp.core.words.Abstract

interface CloudSyncWords : Abstract.Response {


    object Success : CloudSyncWords {

        override fun <T> map(mapper: Abstract.ResponseMapper<T>): T
            = mapper.map()
    }

    class Failure(
        private val message: String
    ) : CloudSyncWords {

        override fun <T> map(mapper: Abstract.ResponseMapper<T>): T
            = mapper.map(message)
    }

}