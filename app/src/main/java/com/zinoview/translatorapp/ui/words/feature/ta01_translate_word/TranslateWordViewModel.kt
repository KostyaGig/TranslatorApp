package com.zinoview.translatorapp.ui.words.feature.ta01_translate_word

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.words.WordInteractor
import com.zinoview.translatorapp.ui.core.BaseCommunication
import com.zinoview.translatorapp.ui.core.Observe

import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.UiRecentMapper
import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.UiRecentWords
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface TranslateWordViewModel : Observe<UiWordState> {

    fun translateWord(srcWord: String)

    fun recentWords()

    fun observeRecentWords(owner: LifecycleOwner,observer: Observer<UiRecentWords>)

    fun saveRecentQuery(recentQuery: ArrayList<String>)

    //todo remove after invoked view model to lifecycle
    fun clean()

    class Base(
        private val wordInteractor: WordInteractor,
        private val uiWordMapper: UiWordMapper,
        private val uiWordStateMapper: UiWordStateMapper,
        private val communication: BaseCommunication<UiWordState>,
        private val recentWordsCommunication: BaseCommunication<UiRecentWords>,
        private val uiRecentMapper: UiRecentMapper,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : TranslateWordViewModel, ViewModel() {

        override fun translateWord(srcWord: String) {
            communication.postValue(UiWordState.Progress)
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

        override fun recentWords() {
            viewModelScope.launch(defaultDispatcher) {
                val domainRecentWords = wordInteractor.recentQuery()
                val uiRecentWords = domainRecentWords.map(uiRecentMapper)

                withContext(Dispatchers.Main) {
                    recentWordsCommunication.postValue(uiRecentWords)
                }
            }
        }

        override fun observeRecentWords(owner: LifecycleOwner, observer: Observer<UiRecentWords>) {
            recentWordsCommunication.observe(owner,observer)
        }

        override fun saveRecentQuery(recentQuery: ArrayList<String>) {
            viewModelScope.launch(defaultDispatcher) {
                wordInteractor.saveRecentQuery(recentQuery)
            }
        }

        override fun clean() {
            communication.postValue(UiWordState.Empty)
        }
    }
}