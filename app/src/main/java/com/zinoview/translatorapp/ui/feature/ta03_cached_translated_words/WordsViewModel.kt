package com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.translatorapp.domain.WordInteractor
import com.zinoview.translatorapp.ui.core.Observe
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordMapper
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.WordCommunication
import kotlinx.coroutines.*

interface WordsViewModel : Observe<UiWordsStateRecyclerView>{

    fun words()

    fun updateWord(srcWord: String,position: Int)

    class Base(
        private val wordInteractor: WordInteractor,
        private val uiWordMapper: UiWordMapper,
        private val uiWordStateRecyclerViewMapper: UiWordStateRecyclerViewMapper,
        private val communication: WordCommunication.BaseWordCommunication<UiWordsStateRecyclerView>,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
        ) : WordsViewModel, ViewModel() {

        override fun words() {
            communication.postValue(UiWordsStateRecyclerView.Progress)
            viewModelScope.launch(defaultDispatcher) {
                delay(1500)
                val domainWords = wordInteractor.words()
                val uiWords = domainWords.map(uiWordMapper)
                val uiStateWordsRecyclerView = uiWords.map(uiWordStateRecyclerViewMapper)

                withContext(Dispatchers.Main) {
                    communication.postValue(uiStateWordsRecyclerView)
                }
            }
        }

        override fun updateWord(srcWord: String,position: Int) {
            viewModelScope.launch(defaultDispatcher) {
                val domainUpdatedWord = wordInteractor.updateWord(srcWord, position)
                val uiUpdatedWord = domainUpdatedWord.map(uiWordMapper)
                val uiStateWordsRecyclerView = uiUpdatedWord.map(uiWordStateRecyclerViewMapper)

                withContext(Dispatchers.Main) {
                    communication.postValue(uiStateWordsRecyclerView)
                }
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiWordsStateRecyclerView>) {
            communication.observe(owner,observer)
        }

    }
}