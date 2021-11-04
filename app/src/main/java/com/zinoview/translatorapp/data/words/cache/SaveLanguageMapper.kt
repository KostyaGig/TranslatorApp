package com.zinoview.translatorapp.data.words.cache

import com.zinoview.translatorapp.core.words.Language
import com.zinoview.translatorapp.data.words.DataLanguage

interface SaveLanguageMapper : Language.LanguageMapper<DataLanguage> {

    class Base : SaveLanguageMapper {

        override fun map(fromLanguage: String, toLanguage: String): DataLanguage
            = DataLanguage(fromLanguage, toLanguage)
    }
}