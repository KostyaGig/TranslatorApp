package com.zinoview.translatorapp.data.words

import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.data.words.cache.DataBaseOperationLanguage
import com.zinoview.translatorapp.data.words.cache.db.CacheWord
import com.zinoview.translatorapp.data.words.cache.db.WordDao

data class DataLanguage(
    private val fromLanguage: String,
    private val toLanguage: String
) : DataBaseOperationLanguage {

    override fun <T> map(mapper: Language.LanguageMapper<T>): T
        = mapper.map(fromLanguage,toLanguage)

    override suspend fun saveToDb(dao: WordDao,translatedWord: String, srcWord: String) {
        val cacheWord = CacheWord.Base(
            srcWord,translatedWord,fromLanguage, toLanguage
        )
        dao.insert(cacheWord)
    }

}