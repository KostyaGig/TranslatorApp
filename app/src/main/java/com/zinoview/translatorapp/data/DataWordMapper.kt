package com.zinoview.translatorapp.data

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.cache.CacheWord
import com.zinoview.translatorapp.data.cache.SaveLanguageMapper

//todo remove not usage mapper
interface DataWordMapper : Abstract.WordsMapper<DataWords> {

    class Base(
        private val saveLanguageMapper: SaveLanguageMapper
    ) : DataWordMapper {

        override fun map(translatedWord: String, srcWord: String, language: Language): DataWords
            = DataWords.Success(srcWord, translatedWord, language.map(saveLanguageMapper))

        override fun map(message: String): DataWords
            = DataWords.Failure(message)

        override fun map(cachedWords: List<CacheWord>,position: Int): DataWords
            = DataWords.Cache(cachedWords)
    }
}