package com.zinoview.translatorapp.data.cache.shared_prefs

import android.content.SharedPreferences
import com.zinoview.translatorapp.ui.core.log

interface SharedPreferencesReader  {

    fun read(sharedPreferences: SharedPreferences,key: String) : List<String>

    class Base(
        private val setToListMapper: SetToListMapper<String>
    ) : SharedPreferencesReader {

        override fun read(sharedPreferences: SharedPreferences,key: String): List<String> {
            val set = sharedPreferences.getStringSet(key, emptySet())
            log("Read $set")
            return setToListMapper.map(set!!)
        }
    }
}