package com.zinoview.translatorapp.data.users.cloud

import com.google.gson.annotations.SerializedName

interface CloudUsers {

    fun users() : List<String>

    class Base(
        @SerializedName("users")
        private val users: List<String>
    ) : CloudUsers {

        override fun users(): List<String> = users
    }
}