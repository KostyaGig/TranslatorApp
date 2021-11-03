package com.zinoview.translatorapp.data.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CacheWord.Base::class], version = 1, exportSchema = false)
abstract class WordsDatabase : RoomDatabase() {

    abstract fun dao() : WordDao

    companion object {
        private var instance: WordsDatabase? = null

        @Synchronized
        fun database(context: Context) : WordsDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordsDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return instance!!
        }

        private const val DATABASE_NAME = "words_db"
    }
}