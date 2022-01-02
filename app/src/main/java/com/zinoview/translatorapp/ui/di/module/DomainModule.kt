package com.zinoview.translatorapp.ui.di.module

import com.zinoview.translatorapp.data.auth.AuthRepository
import com.zinoview.translatorapp.data.words.DataWords
import com.zinoview.translatorapp.data.words.WordRepository
import com.zinoview.translatorapp.data.words.sync.SyncWordsRepository
import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.domain.auth.DomainAuthMapper
import com.zinoview.translatorapp.domain.words.DomainLanguageMapper
import com.zinoview.translatorapp.domain.words.DomainRecentMapper
import com.zinoview.translatorapp.domain.words.DomainWordMapper
import com.zinoview.translatorapp.domain.words.WordInteractor
import com.zinoview.translatorapp.domain.words.sync.SyncWordsInteractor
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideWordInteractor(
        repository: WordRepository<DataWords>
    ) : WordInteractor {
        return WordInteractor.Base(
            repository,
            DomainWordMapper.Base(
                DomainLanguageMapper.Base()
            ),
            DomainRecentMapper.Base()
        )
    }

    @Provides
    fun provideAuthInteractor(
        syncWordsRepository: SyncWordsRepository,
        authRepository: AuthRepository
    ) : AuthInteractor {
        return AuthInteractor.Base(
            authRepository,
            SyncWordsInteractor.Base(
                syncWordsRepository
            ),
            DomainAuthMapper.Base()
        )
    }
}