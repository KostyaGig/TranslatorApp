package com.zinoview.translatorapp.data.words.cache

import com.zinoview.translatorapp.core.words.Abstract
import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.data.words.DataWords
import com.zinoview.translatorapp.data.words.cache.db.CacheWord

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
