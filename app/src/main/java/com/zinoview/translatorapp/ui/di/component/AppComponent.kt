package com.zinoview.translatorapp.ui.di.component

import com.zinoview.translatorapp.ui.auth.fragment.LoginFragment
import com.zinoview.translatorapp.ui.auth.fragment.RegisterFragment
import com.zinoview.translatorapp.ui.core.MainActivity
import com.zinoview.translatorapp.ui.di.module.AppModule
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment
import com.zinoview.translatorapp.ui.words.fragment.WordsFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: WordsFragment)
    fun inject(fragment: SearchWordsFragment)
    fun inject(fragment: RegisterFragment)
    fun inject(fragment: LoginFragment)
}