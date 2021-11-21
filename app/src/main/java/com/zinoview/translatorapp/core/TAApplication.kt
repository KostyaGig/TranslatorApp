package com.zinoview.translatorapp.core

import android.app.Application
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.login.LoginViewModel
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register.RegisterViewModel
import com.zinoview.translatorapp.ui.di.component.AppComponent
import com.zinoview.translatorapp.ui.di.component.DaggerAppComponent
import com.zinoview.translatorapp.ui.di.module.AppModule
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.TranslateWordViewModel
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.WordsViewModel

class TAApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent =
            DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()

    }
}