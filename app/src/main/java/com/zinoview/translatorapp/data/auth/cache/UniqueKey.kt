package com.zinoview.translatorapp.data.auth.cache

import android.content.SharedPreferences
import com.zinoview.translatorapp.data.core.SharedPreferencesReader

interface UniqueKey : SharedPreferencesReader<String> {

    class Base : UniqueKey {

        override fun read(sharedPreferences: SharedPreferences, key: String): String {
            return sharedPreferences.getString(key,"") ?: ""
        }

    }
}