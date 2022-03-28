package com.zinoview.translatorapp.core

import android.app.Application
import com.zinoview.translatorapp.ui.di.component.AppComponent
import com.zinoview.translatorapp.ui.di.component.DaggerAppComponent
import com.zinoview.translatorapp.ui.di.module.AppModule

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