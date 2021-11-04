package com.zinoview.translatorapp.core

import android.app.Application
import com.zinoview.translatorapp.data.auth.AuthRepository
import com.zinoview.translatorapp.data.auth.core.AuthCloudDataSource
import com.zinoview.translatorapp.data.auth.core.AuthService
import com.zinoview.translatorapp.data.auth.register.cloud.CloudRegisterMapper
import com.zinoview.translatorapp.data.words.ExceptionMapper
import com.zinoview.translatorapp.data.words.WordRepository
import com.zinoview.translatorapp.data.words.cache.db.Database
import com.zinoview.translatorapp.data.words.cache.db.RoomProvider
import com.zinoview.translatorapp.data.words.cache.shared_prefs.SharedPreferencesReader
import com.zinoview.translatorapp.data.words.cache.shared_prefs.TranslatorSharedPreferences
import com.zinoview.translatorapp.data.words.cloud.CloudDataSource
import com.zinoview.translatorapp.data.words.cloud.CloudResultMapper
import com.zinoview.translatorapp.data.words.cloud.WordService
import com.zinoview.translatorapp.data.words.cache.CacheDataSource
import com.zinoview.translatorapp.data.words.cache.DataLanguageMapper
import com.zinoview.translatorapp.data.words.cache.TranslatedCacheDataWordsMapper
import com.zinoview.translatorapp.data.words.cache.TranslatedCacheNotFavoriteDataWordsMapper
import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.domain.auth.DomainRegisterMapper
import com.zinoview.translatorapp.domain.words.DomainLanguageMapper
import com.zinoview.translatorapp.domain.words.DomainRecentMapper
import com.zinoview.translatorapp.domain.words.DomainWordMapper
import com.zinoview.translatorapp.domain.words.WordInteractor
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.AuthViewModel
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.RegisterCommunication
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.UiRegisterMapper
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.state.UiRegisterStateMapper
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.TranslateWordViewModel
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.UiLanguageMapper
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.UiWordMapper
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.UiWordStateMapper
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.RecyclerViewWordCommunication
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.UiWordStateRecyclerViewMapper
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.WordsViewModel
import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.RecentWordsCommunication
import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.UiRecentMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TAApplication : Application() {

    lateinit var translatedWordViewModel: TranslateWordViewModel
    lateinit var wordsViewModel: WordsViewModel

    lateinit var authViewModel: AuthViewModel

    private companion object {
        //http://effeegre.pythonanywhere.com - older server link
        private const val BASE_URL = "http://translatorappserver.pythonanywhere.com"

        private const val TEMP_AUTH_BASE_URL = "http://translatorappserver.pythonanywhere.com"
    }

    override fun onCreate() {
        super.onCreate()

        val client =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val cloudDataSource = CloudDataSource.Base(
            retrofit.create(WordService::class.java)
        )

        val cacheDataSource = CacheDataSource.Base(
            Database.Room.Base(
                RoomProvider.Base(this)
            )
        )

        val resourceProvider = ResourceProvider.Base(this)

        val dataLanguageMapper = DataLanguageMapper.Base()
        val wordRepository = WordRepository.Base(
            cacheDataSource,
            cloudDataSource,
            CloudResultMapper.Base(),
            ExceptionMapper.Base(resourceProvider),
            TranslatorSharedPreferences.Base(
                this,
                SharedPreferencesReader.Base(),
            ),
            TranslatedCacheDataWordsMapper.Base(dataLanguageMapper),
            TranslatedCacheNotFavoriteDataWordsMapper.Base(dataLanguageMapper)
        )
        val wordInteractor = WordInteractor.Base(
            wordRepository, DomainWordMapper.Base(
                DomainLanguageMapper.Base()
            ),
            DomainRecentMapper.Base()
        )

        val uiWordMapper = UiWordMapper.Base(
                UiLanguageMapper.Base()
        )
        translatedWordViewModel = TranslateWordViewModel.Base(
            wordInteractor,
            uiWordMapper,
            UiWordStateMapper.Base(),
            com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.TranslatedWordCommunication(),
            RecentWordsCommunication(),
            UiRecentMapper.Base()
        )

        wordsViewModel = WordsViewModel.Base(
            wordInteractor,
            uiWordMapper,
            UiWordStateRecyclerViewMapper.Success(),
            RecyclerViewWordCommunication()
        )

        val authRetrofit = Retrofit.Builder()
            .baseUrl(TEMP_AUTH_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authService = authRetrofit.create(AuthService::class.java)

        val authRepository = AuthRepository.Base(
            AuthCloudDataSource.Base(
                authService
            ),
            CloudRegisterMapper.Base(),
            com.zinoview.translatorapp.data.auth.core.ExceptionMapper.Base(resourceProvider)
        )

        val authInteractor = AuthInteractor.Base(
            authRepository,DomainRegisterMapper.Base()
        )

        authViewModel = AuthViewModel.Base(
            authInteractor,
            UiRegisterMapper.Base(),
            UiRegisterStateMapper.Base(),
            RegisterCommunication()
        )
    }
}