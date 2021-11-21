package com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.words.DomainWords
import com.zinoview.translatorapp.domain.words.WordInteractor
import com.zinoview.translatorapp.ui.core.BaseCommunication
import com.zinoview.translatorapp.ui.core.Observe

import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.UiWordMapper
import kotlinx.coroutines.*

interface WordsViewModel : Observe<UiWordsStateRecyclerView> {

    fun words()

    fun updateWord(srcWord: String)

    class Base(
        private val wordInteractor: WordInteractor,
        private val uiWordMapper: UiWordMapper,
        private val uiWordStateRecyclerViewMapper: UiWordStateRecyclerViewMapper,
        private val communication: BaseCommunication<UiWordsStateRecyclerView>,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
        ) : WordsViewModel, ViewModel() {

        override fun words() {
            communication.postValue(UiWordsStateRecyclerView.Progress)
            viewModelScope.launch(defaultDispatcher) {
                val domainWords = wordInteractor.words()
                makeAction(domainWords)
            }
        }

        override fun updateWord(srcWord: String) {
            viewModelScope.launch(defaultDispatcher) {
                val domainWords = wordInteractor.updateWord(srcWord)
                makeAction(domainWords)
            }
        }

        private suspend fun makeAction(domainWords: DomainWords) {
            val uiUpdatedWord = domainWords.map(uiWordMapper)
            val uiStateWordsRecyclerView = uiUpdatedWord.map(uiWordStateRecyclerViewMapper)

            withContext(Dispatchers.Main) {
                communication.postValue(uiStateWordsRecyclerView)
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiWordsStateRecyclerView>) {
            communication.observe(owner,observer)
        }

    }
}