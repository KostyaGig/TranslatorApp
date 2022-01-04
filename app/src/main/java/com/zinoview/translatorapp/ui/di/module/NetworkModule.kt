package com.zinoview.translatorapp.ui.di.module

import com.google.gson.Gson
import com.zinoview.translatorapp.data.auth.AuthCloudDataSource
import com.zinoview.translatorapp.data.auth.AuthService
import com.zinoview.translatorapp.data.words.cloud.CloudDataSource
import com.zinoview.translatorapp.data.words.cloud.CloudWord
import com.zinoview.translatorapp.data.words.cloud.WordService
import com.zinoview.translatorapp.data.words.sync.cloud.CloudAbstractResponseToCloudSyncWordsMapper
import com.zinoview.translatorapp.data.words.sync.cloud.SyncWordsCloudDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    }

    @Provides
    fun provideGsonFactory() = GsonConverterFactory.create()

    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideRetrofit(client: OkHttpClient,gsonConverterFactory: GsonConverterFactory) : Retrofit {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideWordService(retrofit: Retrofit) : WordService {
        return retrofit.create(WordService::class.java)
    }

    @Provides
    fun provideWordsCloudDataSource(wordService: WordService) : CloudDataSource<CloudWord> {
        return CloudDataSource.Base(
            wordService
        )
    }

    @Provides
    fun provideAuthService(retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun provideAuthCloudDataSource(authService: AuthService) : AuthCloudDataSource {
        return AuthCloudDataSource.Base(
            authService
        )
    }

    @Provides
    fun provideSyncWordsCloudDataSource(wordService: WordService) : SyncWordsCloudDataSource {
        return SyncWordsCloudDataSource.Base(
            wordService,
            CloudAbstractResponseToCloudSyncWordsMapper.Base()
        )
    }

    private companion object {
        private const val BASE_URL = "https://uehuf.pythonanywhere.com"
    }

}