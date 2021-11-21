package com.zinoview.translatorapp.ui.di.module

import android.content.Context
import androidx.room.Room
import com.zinoview.translatorapp.data.words.cache.CacheDataSource
import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import com.zinoview.translatorapp.data.words.cache.db.WordDao
import com.zinoview.translatorapp.data.words.cache.db.WordsDatabase
import dagger.Module
import dagger.Provides

@Module(includes = [WordsCacheModule::class, AuthCacheModule::class])
class CacheModule {

    private companion object {
        private const val DATABASE_NAME = "words_db"
    }

    @Provides
    fun provideWordsDatabase(context: Context) : WordsDatabase {
        return Room.databaseBuilder(
            context,
            WordsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideWordDao(wordsDatabase: WordsDatabase) : WordDao {
        return wordsDatabase.dao()
    }

    @Provides
    fun provideCacheDataSource(dao: WordDao) : CacheDataSource<CacheWord> {
        return CacheDataSource.Base(
            com.zinoview.translatorapp.data.words.cache.db.Database.Room.Base(
                dao
            )
        )
    }
}