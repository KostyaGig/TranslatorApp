package com.zinoview.translatorapp.data.cache.shared_prefs

import android.content.Context
import com.zinoview.translatorapp.data.cache.core.Read
import com.zinoview.translatorapp.data.cache.core.Save
import com.zinoview.translatorapp.ui.core.log

interface TranslatorSharedPreferences : Save<ArrayList<String>>, Read<List<String>> {

    class Base(
        context: Context,
        private val sharedPreferencesReader: SharedPreferencesReader
    ) : TranslatorSharedPreferences {

        private val sharedPreferences = context.getSharedPreferences(RECENT_WORDS_PREFERENCES,Context.MODE_PRIVATE)


        override fun save(recentWords: ArrayList<String>) {
            sharedPreferences.edit().putString(RECENT_WORDS_KEY,ObjectSerializer.serialize(recentWords)).apply()
            log("Save $recentWords")
            read()
        }

        override fun read(): List<String>
            = sharedPreferencesReader.read(sharedPreferences, RECENT_WORDS_KEY)

        private companion object {
            private const val RECENT_WORDS_PREFERENCES = "recent_words"
            private const val RECENT_WORDS_KEY = "recent_words_key"
        }
    }
}