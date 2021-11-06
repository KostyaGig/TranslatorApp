package com.zinoview.translatorapp.domain.words

import com.zinoview.translatorapp.data.words.DataWords
import com.zinoview.translatorapp.data.words.WordRepository


interface WordInteractor {

    suspend fun translatedWord(srcWord: String) : DomainWords

    suspend fun words() : DomainWords

    suspend fun updateWord(srcWord: String,position: Int) : DomainWords

    suspend fun recentQuery() : DomainRecentWords

    suspend fun saveRecentQuery(recentQuery: ArrayList<String>)

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

        override suspend fun updateWord(srcWord: String,position: Int) : DomainWords {
            val dataWords = repository.updateWord(srcWord,position)
            return dataWords.map(domainWordMapper)
        }

        override suspend fun recentQuery(): DomainRecentWords {
            val dataRecentWords = repository.recentQuery()
            return dataRecentWords.map(domainRecentMapper)
        }

        override suspend fun saveRecentQuery(recentQuery: ArrayList<String>) {
            repository.saveRecentQuery(recentQuery)
        }

    }
}