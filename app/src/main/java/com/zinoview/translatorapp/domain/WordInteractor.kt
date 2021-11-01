package com.zinoview.translatorapp.domain

import com.zinoview.translatorapp.data.DataWords
import com.zinoview.translatorapp.data.WordRepository


interface WordInteractor {

    suspend fun translatedWord(srcWord: String) : DomainWords

    suspend fun words() : DomainWords

    suspend fun updateWord(srcWord: String,isFavorite: Boolean,position: Int) : DomainWords

    suspend fun recentQuery() : DomainRecentWords

    suspend fun saveRecentQuery(recentQuery: List<String>)

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

        //todo rename "updateWord" on "updatedWord" in all classes
        //todo получение обновленного слова и возврат его для дальнейшего отображение в списке
        //Возврааем DataWords.Cache(listOf(updatedCacheWord))
        //В спсике обновляем 1 элемент по position
        override suspend fun updateWord(srcWord: String, isFavorite: Boolean,position: Int) : DomainWords {
            val dataWords = repository.updateWord(srcWord, isFavorite,position)
            return dataWords.map(domainWordMapper)
        }

        override suspend fun recentQuery(): DomainRecentWords {
            val dataRecentWords = repository.recentQuery()
            return dataRecentWords.map(domainRecentMapper)
        }

        override suspend fun saveRecentQuery(recentQuery: List<String>) {
            repository.saveRecentQuery(recentQuery)
        }
    }
}