package com.zinoview.translatorapp.data.words.cache.shared_prefs

import android.content.SharedPreferences
import java.util.ArrayList

interface SharedPreferencesReader  {

    fun read(sharedPreferences: SharedPreferences,key: String) : List<String>

    class Base : SharedPreferencesReader {

        override fun read(sharedPreferences: SharedPreferences,key: String): List<String> {
            val list = ObjectSerializer.deserialize(
                sharedPreferences.getString(
                    key,
                    ObjectSerializer.serialize(ArrayList<String>())
                )
            )
            return list as ArrayList<String>
        }
    }
}