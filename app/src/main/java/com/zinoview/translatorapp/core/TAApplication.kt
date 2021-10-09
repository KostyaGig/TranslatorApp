package com.zinoview.translatorapp.core

import android.app.Application
import com.zinoview.translatorapp.data.ExceptionMapper
import com.zinoview.translatorapp.data.WordRepository
import com.zinoview.translatorapp.data.cloud.CloudDataSource
import com.zinoview.translatorapp.data.cloud.CloudResultMapper
import com.zinoview.translatorapp.data.cloud.WordService
import com.zinoview.translatorapp.domain.DomainLanguageMapper
import com.zinoview.translatorapp.domain.DomainWordMapper
import com.zinoview.translatorapp.domain.WordInteractor
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TAApplication : Application() {

    lateinit var translatedWordViewModel: TranslateWordViewModel

    private companion object {
        const val BASE_URL = "http://effeegre.pythonanywhere.com"
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

        val resourceProvider = ResourceProvider.Base(this)

        val wordRepository = WordRepository.Base(
            cloudDataSource,CloudResultMapper.Base(),
            ExceptionMapper.Base(resourceProvider)
        )
        val wordInteractor = WordInteractor.Base(
            wordRepository,DomainWordMapper.Base(
                DomainLanguageMapper.Base()
            )
        )

        translatedWordViewModel = TranslateWordViewModel.Base(
            wordInteractor,
            UiWordMapper.Base(
                UiLanguageMapper.Base()
            ),
            UiWordStateMapper.Base(),
            WordCommunication.Base()
        )
    }
}