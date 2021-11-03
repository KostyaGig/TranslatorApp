package com.zinoview.translatorapp.data.words.cache.db

import android.content.Context
import java.lang.IllegalStateException

interface RoomProvider  {

    fun provide() : WordDao

    class Base(
        private val context: Context
    ) : RoomProvider {

        override fun provide(): WordDao {
            return WordsDatabase.database(context).dao()
        }
    }

    class Test : RoomProvider {

        override fun provide(): WordDao
            = throw IllegalStateException("RealmProvider.Test not provide realm instance!")
    }
}

