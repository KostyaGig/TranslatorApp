package com.zinoview.translatorapp.ui.di.module

import android.content.Context
import com.zinoview.translatorapp.data.auth.cache.AuthSharedPreferences
import com.zinoview.translatorapp.data.auth.cache.UniqueKey
import dagger.Module
import dagger.Provides

@Module
class AuthCacheModule {

    @Provides
    fun provideAuthSharedPreferences(context: Context) : AuthSharedPreferences {
        return AuthSharedPreferences.Base(
            context,
            UniqueKey.Base()
        )
    }
}