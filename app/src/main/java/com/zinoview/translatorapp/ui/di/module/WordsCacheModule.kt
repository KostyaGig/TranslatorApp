package com.zinoview.translatorapp.ui.di.module

import android.content.Context
import com.zinoview.translatorapp.data.words.cache.shared_prefs.RecentWordsReader
import com.zinoview.translatorapp.data.words.cache.shared_prefs.TranslatorSharedPreferences
import dagger.Module
import dagger.Provides

@Module
class WordsCacheModule {

    @Provides
    fun provideTranslatorSharedPreferences(context: Context) : TranslatorSharedPreferences {
        return TranslatorSharedPreferences.Base(
            context,
            RecentWordsReader.Base()
        )
    }
}