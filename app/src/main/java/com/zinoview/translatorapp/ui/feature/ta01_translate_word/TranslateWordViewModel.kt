package com.zinoview.translatorapp.ui.feature.ta01_translate_word

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.WordInteractor
import com.zinoview.translatorapp.ui.core.Observe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface TranslateWordViewModel : Observe<UiWordState> {

    fun translateWord(srcWord: String)

    //todo remove after adding owner for viewModel
    fun clear()
    class Base(
        private val wordInteractor: WordInteractor,
        private val uiWordMapper: UiWordMapper,
        private val uiWordStateMapper: UiWordStateMapper,
        private val communication: WordCommunication<UiWordState>,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : TranslateWordViewModel, ViewModel() {

        override fun translateWord(srcWord: String) {
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

        override fun clear() {
            communication.clear()
        }
    }
}