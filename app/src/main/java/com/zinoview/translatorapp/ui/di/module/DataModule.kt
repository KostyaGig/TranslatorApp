package com.zinoview.translatorapp.ui.di.module

import com.google.gson.Gson
import com.zinoview.translatorapp.core.ResourceProvider
import com.zinoview.translatorapp.data.auth.AuthCloudDataSource
import com.zinoview.translatorapp.data.auth.AuthRepository
import com.zinoview.translatorapp.data.auth.CloudAuthMapper
import com.zinoview.translatorapp.data.auth.cache.AuthSharedPreferences
import com.zinoview.translatorapp.data.words.DataWords
import com.zinoview.translatorapp.data.words.ExceptionMapper
import com.zinoview.translatorapp.data.words.WordRepository
import com.zinoview.translatorapp.data.words.cache.CacheDataSource
import com.zinoview.translatorapp.data.words.cache.DataLanguageMapper
import com.zinoview.translatorapp.data.words.cache.TranslatedCacheDataWordsMapper
import com.zinoview.translatorapp.data.words.cache.TranslatedCacheNotFavoriteDataWordsMapper
import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import com.zinoview.translatorapp.data.words.cache.shared_prefs.TranslatorSharedPreferences
import com.zinoview.translatorapp.data.words.cloud.CloudDataSource
import com.zinoview.translatorapp.data.words.cloud.CloudResultMapper
import com.zinoview.translatorapp.data.words.cloud.CloudWord
import com.zinoview.translatorapp.data.words.sync.CacheWordToSyncWordMapper
import com.zinoview.translatorapp.data.words.sync.Json
import com.zinoview.translatorapp.data.words.sync.SyncWordsRepository
import com.zinoview.translatorapp.data.words.sync.cloud.SyncWordsCloudDataSource
import dagger.Module
import dagger.Provides

@Module(includes = [CacheModule::class, NetworkModule::class])
class DataModule {

    @Provides
    fun provideDataLanguageMapper() : DataLanguageMapper {
        return DataLanguageMapper.Base()
    }

    @Provides
    fun provideWordRepository(
        resourceProvider: ResourceProvider,
        cacheDataSource: CacheDataSource<CacheWord>,
        cloudDataSource: CloudDataSource<CloudWord>,
        translatorSharedPreferences: TranslatorSharedPreferences,
        authSharedPreferences: AuthSharedPreferences,
        dataLanguageMapper: DataLanguageMapper
    ) : WordRepository<DataWords> {
        return WordRepository.Base(
            cacheDataSource,
            cloudDataSource,
            CloudResultMapper.Base(),
            ExceptionMapper.Base(resourceProvider),
            translatorSharedPreferences,
            authSharedPreferences,
            TranslatedCacheDataWordsMapper.Base(
                dataLanguageMapper
            ),
            TranslatedCacheNotFavoriteDataWordsMapper.Base(
                dataLanguageMapper
            )
        )
    }


    @Provides
    fun provideAuthRepository(
        resourceProvider: ResourceProvider,
        authCloudDataSource: AuthCloudDataSource,
        authSharedPreferences: AuthSharedPreferences
    ) : AuthRepository {
        return AuthRepository.Base(
            authCloudDataSource,
            CloudAuthMapper.Base(),
            com.zinoview.translatorapp.data.auth.ExceptionMapper.Base(
                resourceProvider
            ),
            authSharedPreferences
        )
    }

    @Provides
    fun provideSyncWordsRepository(
        cloudDataSource: SyncWordsCloudDataSource,
        gson: Gson,
        cacheDataSource: CacheDataSource<CacheWord>,
        authSharedPreferences: AuthSharedPreferences
    ) : SyncWordsRepository {
        return SyncWordsRepository.Base(
            cloudDataSource,
            Json.Base(
                gson
            ),
            CacheWordToSyncWordMapper.Base(),
            authSharedPreferences,
            cacheDataSource
        )
    }
}