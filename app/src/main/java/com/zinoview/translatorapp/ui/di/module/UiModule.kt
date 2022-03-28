package com.zinoview.translatorapp.ui.di.module

import com.zinoview.translatorapp.domain.auth.AuthInteractor
import com.zinoview.translatorapp.domain.users.UsersInteractor
import com.zinoview.translatorapp.domain.words.WordInteractor
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.UiAuthMapper
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.login.LoginViewModelFactory
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register.RegisterViewModelFactory
import com.zinoview.translatorapp.ui.users.feature.ta09_show_users.UserViewModelFactory
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.TranslateWordViewModelFactory
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.UiLanguageMapper
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.UiWordMapper
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.WordsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class UiModule {

    @Provides
    fun provideUiWordMapper() : UiWordMapper {
        return UiWordMapper.Base(
            UiLanguageMapper.Base()
        )
    }

    @Provides
    fun provideTranslatedWordViewModelFactory(
        wordInteractor: WordInteractor,
        uiWordMapper: UiWordMapper
    ) : TranslateWordViewModelFactory {
        return TranslateWordViewModelFactory.Base(
            wordInteractor,
            uiWordMapper
        )
    }

    @Provides
    fun provideWordsViewModelFactory(
        wordInteractor: WordInteractor,
        uiWordMapper: UiWordMapper
    ) : WordsViewModelFactory {
        return WordsViewModelFactory.Base(
            wordInteractor,
            uiWordMapper
        )
    }

    @Provides
    fun provideUiAuthMapper() : UiAuthMapper {
        return UiAuthMapper.Base()
    }

    @Provides
    fun provideRegisterViewModelFactory(
        authInteractor: AuthInteractor,
        uiAuthMapper: UiAuthMapper
    ) : RegisterViewModelFactory {
        return RegisterViewModelFactory.Base(
            authInteractor,
            uiAuthMapper
        )
    }

    @Provides
    fun provideLoginViewModelFactory(
        authInteractor: AuthInteractor,
        uiAuthMapper: UiAuthMapper
    ) : LoginViewModelFactory {
        return LoginViewModelFactory.Base(
            authInteractor,
            uiAuthMapper
        )
    }

    @Provides
    fun provideUsersViewModelFactory(
        interractor: UsersInteractor
    ) : UserViewModelFactory {
        return UserViewModelFactory.Base(
            interractor
        )
    }
}