package com.zinoview.translatorapp.data.words.cache.shared_prefs

import android.content.SharedPreferences
import com.zinoview.translatorapp.data.core.SharedPreferencesReader
import java.util.ArrayList

interface RecentWordsReader : SharedPreferencesReader<List<String>>  {

    class Base : RecentWordsReader {

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