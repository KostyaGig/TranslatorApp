package com.zinoview.translatorapp.data.core

import android.content.SharedPreferences

interface SharedPreferencesReader<T> {

    fun read(sharedPreferences: SharedPreferences,key: String) : T
}