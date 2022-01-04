package com.zinoview.translatorapp.data.words.sync

import com.zinoview.translatorapp.core.words.Abstract
import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.data.words.cache.db.CacheWord

interface CacheWordToSyncWordMapper : Abstract.WordsMapper<SyncWord> {

    class Base : CacheWordToSyncWordMapper {

        override fun map(translatedWord: String, srcWord: String, language: Language): SyncWord
            = SyncWord.Base(srcWord,translatedWord)

        override fun map(cachedWords: List<CacheWord>, position: Int): SyncWord = SyncWord.Empty

        override fun map(message: String): SyncWord = SyncWord.Empty

        override fun cachedMap(
            translatedWord: String,
            srcWord: String,
            language: Language,
            isFavorite: Boolean
        ): SyncWord = SyncWord.Empty
    }
}