package com.zinoview.translatorapp.domain

import com.zinoview.translatorapp.data.DataWords
import com.zinoview.translatorapp.data.WordRepository


interface WordInteractor {

    suspend fun translatedWord(srcWord: String) : DomainWords

    suspend fun words() : DomainWords

    suspend fun recentQuerys() : DomainRecentWords

    suspend fun saveRecentQuerys(recentQuerys: List<String>)

    class Base(
        private val repository: WordRepository<DataWords>,
        private val domainWordMapper: DomainWordMapper,
        private val domainRecentMapper: DomainRecentMapper
    ) : WordInteractor {

        override suspend fun translatedWord(srcWord: String): DomainWords {
            val dataTranslatedWord = repository.translatedWord(srcWord)
            return dataTranslatedWord.map(domainWordMapper)
        }

        override suspend fun words(): DomainWords {
            val dataWords = repository.words()
            return dataWords.map(domainWordMapper)
        }

        override suspend fun recentQuerys(): DomainRecentWords {
            val dataRecentWords = repository.recentQuerys()
            return dataRecentWords.map(domainRecentMapper)
        }

        override suspend fun saveRecentQuerys(recentQuerys: List<String>) {
            repository.saveRecentQuerys(recentQuerys)
        }
    }
}