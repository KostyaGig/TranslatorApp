package com.zinoview.translatorapp.ui.di.module

import android.content.Context
import com.google.gson.Gson
import com.zinoview.translatorapp.data.auth.AuthCloudDataSource
import com.zinoview.translatorapp.data.auth.AuthService
import com.zinoview.translatorapp.data.core.cloud.NetworkConnection
import com.zinoview.translatorapp.data.users.cloud.UserService
import com.zinoview.translatorapp.data.users.cloud.UsersCloudDataSource
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
    fun provideNetworkConnection(context: Context) : NetworkConnection {
        return NetworkConnection.Base(context)
    }

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

    @Provides
    fun provideUsersService(retrofit: Retrofit) : UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun provideUsersCloudDataSource(usersService: UserService) : UsersCloudDataSource {
        return UsersCloudDataSource.Base(
            usersService,
        )
    }

    private companion object {
        private const val BASE_URL = "https://uehuf.pythonanywhere.com"
    }

}