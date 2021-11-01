package com.zinoview.translatorapp.data.cache

import io.realm.Realm
import io.realm.RealmConfiguration
import java.lang.IllegalStateException

interface RealmProvider  {

    fun provide() : Realm

    class Base : RealmProvider {

        override fun provide(): Realm

        {
            val config = RealmConfiguration.Builder().allowQueriesOnUiThread(true).build()
            return Realm.getInstance(config)
        }
    }

    class Test : RealmProvider {

        override fun provide(): Realm
            = throw IllegalStateException("RealmProvider.Test not provide realm instance!")
    }
}