package com.zinoview.translatorapp.core

import android.app.Application
import com.zinoview.translatorapp.data.DataWords
import com.zinoview.translatorapp.data.ExceptionMapper
import com.zinoview.translatorapp.data.WordRepository
import com.zinoview.translatorapp.data.cache.*
import com.zinoview.translatorapp.data.cache.shared_prefs.ListToSetMapper
import com.zinoview.translatorapp.data.cache.shared_prefs.SetToListMapper
import com.zinoview.translatorapp.data.cache.shared_prefs.SharedPreferencesReader
import com.zinoview.translatorapp.data.cache.shared_prefs.TranslatorSharedPreferences
import com.zinoview.translatorapp.data.cloud.CloudDataSource
import com.zinoview.translatorapp.data.cloud.CloudResultMapper
import com.zinoview.translatorapp.data.cloud.WordService
import com.zinoview.translatorapp.domain.*
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.*
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.RecyclerViewWordCommunication
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.UiWordStateRecyclerViewMapper
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.WordsViewModel
import com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words.RecentWordsCommunication
import com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words.UiRecentMapper
import io.realm.Realm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TAApplication : Application() {

    lateinit var translatedWordViewModel: TranslateWordViewModel
    lateinit var wordsViewModel: WordsViewModel

    //todo remove later
    lateinit var wordRepository: WordRepository<DataWords>
    private companion object {
        const val BASE_URL = "http://effeegre.pythonanywhere.com"
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
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
            Database.Realm.Base(
                RealmProvider.Base()
            )
        )

        val resourceProvider = ResourceProvider.Base(this)

        wordRepository = WordRepository.Base(
            cacheDataSource,
            cloudDataSource,
            CloudResultMapper.Base(),
            ExceptionMapper.Base(resourceProvider),
            TranslatorSharedPreferences.Base(
                this,
                SharedPreferencesReader.Base(
                    SetToListMapper.String()
                ),
                ListToSetMapper.String()
            )
        )
        val wordInteractor = WordInteractor.Base(
            wordRepository,DomainWordMapper.Base(
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
            TranslatedWordCommunication(),
            RecentWordsCommunication(),
            UiRecentMapper.Base()
        )

        wordsViewModel = WordsViewModel.Base(
            wordInteractor,
            uiWordMapper,
            UiWordStateRecyclerViewMapper.Success(),
            RecyclerViewWordCommunication()
        )

    }
}