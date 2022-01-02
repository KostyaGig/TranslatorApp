package com.zinoview.translatorapp.data.words.sync

import com.google.gson.Gson

interface Json {

    fun json(any: Any) : String

    class Base(
        private val gson: Gson
    ) : Json {

        override fun json(any: Any) : String
            = gson.toJson(any)
    }
}