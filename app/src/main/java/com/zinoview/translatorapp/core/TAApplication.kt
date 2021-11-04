package com.zinoview.translatorapp.core

import android.app.Application
import com.zinoview.translatorapp.data.auth.AuthRepository
import com.zinoview.translatorapp.data.auth.AuthCloudDataSource
import com.zinoview.translatorapp.data.auth.AuthService
import com.zinoview.translatorapp.data.auth.CloudAuthMapper
import com.zinoview.translatorapp.data.auth.cache.AuthSharedPreferences
import com.zinoview.translatorapp.data.auth.cache.UniqueKey
import com.zinoview.translatorapp.data.words.ExceptionMapper
import com.zinoview.translatorapp.data.words.WordRepository
import com.zinoview.translatorapp.data.words.cache.db.Database
import com.zinoview.translatorapp.data.words.cache.db.RoomProvider
import com.zinoview.translatorapp.data.words.cache.shared_prefs.RecentWords
import com.zinoview.translatorapp.data.words.cache.shared_prefs.TranslatorSharedPreferences
import com.zinoview.translatorapp.data.words.cloud.CloudDataSource
import com.zinoview.translatorapp.data.words.cloud.CloudResultMapper
import com.zinoview.translatorapp.data.words.cloud.WordService
import com.zinoview.translatorapp.data.words.cache.CacheDataSource
import com.zinoview.translatorapp.data.words.cache.DataLanguageMapper
import com.zinoview.translatorapp.data.words.cache.TranslatedCacheDataWordsMapper
import com.zinoview.translatorapp.data.words.cache.TranslatedCacheNotFavoriteDataWordsMapper
import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.domain.auth.DomainAuthMapper
import com.zinoview.translatorapp.domain.words.DomainLanguageMapper
import com.zinoview.translatorapp.domain.words.DomainRecentMapper
import com.zinoview.translatorapp.domain.words.DomainWordMapper
import com.zinoview.translatorapp.domain.words.WordInteractor
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.register.RegisterViewModel
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.login.LoginCommunication
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.register.RegisterCommunication
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.UiAuthMapper
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.login.LoginViewModel
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.login.UiAuthLoginStateMapper
import com.zinoview.translatorapp.ui.auth.feature.ta01_auth_user.register.UiAuthRegisterStateMapper
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

    lateinit var registerViewModel: RegisterViewModel
    lateinit var loginViewModel: LoginViewModel

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

        val authSharedPreferences = AuthSharedPreferences.Base(this,UniqueKey.Base())

        val dataLanguageMapper = DataLanguageMapper.Base()
        val wordRepository = WordRepository.Base(
            cacheDataSource,
            cloudDataSource,
            CloudResultMapper.Base(),
            ExceptionMapper.Base(resourceProvider),
            TranslatorSharedPreferences.Base(
                this,
                RecentWords.Base(),
            ),
            authSharedPreferences,
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
            CloudAuthMapper.Base(),
            com.zinoview.translatorapp.data.auth.ExceptionMapper.Base(resourceProvider),
            authSharedPreferences
        )

        val authInteractor = AuthInteractor.Base(
            authRepository,DomainAuthMapper.Base()
        )

        registerViewModel = RegisterViewModel.Base(
            authInteractor,
            UiAuthMapper.Base(),
            UiAuthRegisterStateMapper.Base(),
            RegisterCommunication()
        )

        loginViewModel = LoginViewModel.Base(
            authInteractor,
            UiAuthMapper.Base(),
            UiAuthLoginStateMapper.Base(),
            LoginCommunication()
        )

    }
}