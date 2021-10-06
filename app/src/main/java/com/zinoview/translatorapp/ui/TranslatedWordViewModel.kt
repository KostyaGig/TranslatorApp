package com.zinoview.translatorapp.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.WordInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface TranslatedWordViewModel {

    fun observe(owner: LifecycleOwner,observer: Observer<UiWordState>)

    fun translatedWord(srcWord: String)

    class Base(
        private val wordInteractor: WordInteractor,
        private val uiWordMapper: UiWordMapper,
        private val uiWordStateMapper: UiWordStateMapper,
        private val communication: WordCommunication<UiWordState>,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : TranslatedWordViewModel, ViewModel() {

        override fun translatedWord(srcWord: String) {
            viewModelScope.launch(defaultDispatcher) {
                val domainTranslatedWord = wordInteractor.translatedWord(srcWord)
                val uiTranslatedWord = domainTranslatedWord.map(uiWordMapper)
                val uiStateTranslatedWord = uiTranslatedWord.map(uiWordStateMapper)

                withContext(Dispatchers.Main) {
                    communication.postValue(uiStateTranslatedWord)
                }
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiWordState>)
            = communication.observe(owner,observer)
    }
}