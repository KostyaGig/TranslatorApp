package com.zinoview.translatorapp.data.auth.cache

import android.content.Context

interface AuthSharedPreferences {

    fun save(uniqueKey: String)

    fun read() : String

    fun userIsAuthorized() : Boolean

    class Base(
        context: Context,
        private val reader: UniqueKey
    ): AuthSharedPreferences {

        private val sharedPreferences = context.getSharedPreferences(UNIQUE_KEY_PREFERENCES, Context.MODE_PRIVATE)

        override fun save(uniqueKey: String) {
            sharedPreferences.edit().putString(UNIQUE_USER_ID_KEY,uniqueKey).apply()
        }

        override fun read(): String
            = reader.read(sharedPreferences,UNIQUE_USER_ID_KEY)

        override fun userIsAuthorized(): Boolean
            = read().isNotEmpty()

        private companion object {
            private const val UNIQUE_KEY_PREFERENCES = "unique_key_preferences"
            private const val UNIQUE_USER_ID_KEY = "unique_key"
        }
    }
}