package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zinoview.translatorapp.domain.words.WordInteractor
import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.RecentWordsCommunication
import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.UiRecentMapper

interface TranslateWordViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val wordInteractor: WordInteractor,
        private val uiWordMapper: UiWordMapper
    ) : TranslateWordViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TranslateWordViewModel.Base(
                wordInteractor,
                uiWordMapper,
                UiWordStateMapper.Base(),
                TranslatedWordCommunication(),
                RecentWordsCommunication(),
                UiRecentMapper.Base()
            ) as T
        }

    }
}