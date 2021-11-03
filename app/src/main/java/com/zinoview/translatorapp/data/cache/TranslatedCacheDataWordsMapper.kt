package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.DataWords
import com.zinoview.translatorapp.data.cache.db.CacheWord

interface TranslatedCacheDataWordsMapper : Abstract.WordsMapper<DataWords> {

    class Base(
        private val dataLanguageMapper: DataLanguageMapper
    ) : TranslatedCacheDataWordsMapper {

        override fun map(translatedWord: String, srcWord: String, language: Language): DataWords
            = DataWords.Base.TranslatedCache(
                srcWord,translatedWord,language.map(dataLanguageMapper),true
            )

        override fun map(message: String): DataWords
            = DataWords.Test("","")

        override fun map(cachedWords: List<CacheWord>, position: Int): DataWords
            = DataWords.Test("","")

        override fun cachedMap(
            translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
        ): DataWords = DataWords.Test("","")
    }
}