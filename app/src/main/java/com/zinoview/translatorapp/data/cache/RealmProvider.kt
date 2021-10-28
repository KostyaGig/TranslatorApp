package com.zinoview.translatorapp.data.cache

import io.realm.Realm
import java.lang.IllegalStateException

interface RealmProvider  {

    fun provide() : Realm

    class Base : RealmProvider {

        override fun provide(): Realm
            = Realm.getDefaultInstance()
    }

    class Test : RealmProvider {

        override fun provide(): Realm
            = throw IllegalStateException("RealmProvider.Test not provide realm instance!")
    }
}