package com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zinoview.translatorapp.domain.words.WordInteractor
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.UiWordMapper

interface WordsViewModelFactory : ViewModelProvider.Factory {

    class Base(
        private val wordInteractor: WordInteractor,
        private val uiWordMapper: UiWordMapper
    ) : WordsViewModelFactory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WordsViewModel.Base(
                wordInteractor,
                uiWordMapper,
                UiWordStateRecyclerViewMapper.Success(),
                RecyclerViewWordCommunication()
            ) as T
        }
    }
}