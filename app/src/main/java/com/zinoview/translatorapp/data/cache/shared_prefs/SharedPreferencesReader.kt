package com.zinoview.translatorapp.data.cache.shared_prefs

import android.content.SharedPreferences

interface SharedPreferencesReader  {

    fun read(sharedPreferences: SharedPreferences,key: String) : List<String>

    class Base(
        private val setToListMapper: SetToListMapper<String>
    ) : SharedPreferencesReader {

        override fun read(sharedPreferences: SharedPreferences,key: String): List<String> {
            val set = sharedPreferences.getStringSet(key, emptySet())
            return setToListMapper.map(set!!)
        }
    }
}