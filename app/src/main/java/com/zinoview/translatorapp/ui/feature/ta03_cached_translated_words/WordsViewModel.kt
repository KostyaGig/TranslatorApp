package com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.WordInteractor
import com.zinoview.translatorapp.ui.core.Observe
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordMapper
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.WordCommunication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface WordsViewModel : Observe<UiWordsStateRecyclerView>{

    fun words()

    class Base(
        private val wordInteractor: WordInteractor,
        private val uiWordMapper: UiWordMapper,
        private val uiWordStateRecyclerViewMapper: UiWordStateRecyclerViewMapper,
        private val communication: WordCommunication.BaseWordCommunication<UiWordsStateRecyclerView>,
        ) : WordsViewModel, ViewModel() {

        override fun words() {
            viewModelScope.launch(Dispatchers.IO) {
                communication.postValue(UiWordsStateRecyclerView.Progress)
                delay(1500)
                val domainWords = wordInteractor.words()
                val uiWords = domainWords.map(uiWordMapper)
                val uiStateWordsRecyclerView = uiWords.map(uiWordStateRecyclerViewMapper)
                communication.postValue(uiStateWordsRecyclerView)
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiWordsStateRecyclerView>) {
            communication.observe(owner,observer)
        }

    }
}