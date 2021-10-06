package com.zinoview.translatorapp.domain

import com.zinoview.translatorapp.data.DataWord
import com.zinoview.translatorapp.data.WordRepository


interface WordInteractor {

    suspend fun translatedWord(srcWord: String) : DomainWord

    class Base(
        private val repository: WordRepository<DataWord>,
        private val domainWordMapper: DomainWordMapper
    ) : WordInteractor {

        override suspend fun translatedWord(srcWord: String): DomainWord {
            val dataTranslatedWord = repository.translatedWord(srcWord)
            return dataTranslatedWord.map(domainWordMapper)
        }
    }
}