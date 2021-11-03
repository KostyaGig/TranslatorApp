package com.zinoview.translatorapp.data.words.cache

import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.words.DataLanguage

interface DataLanguageMapper : Language.LanguageMapper<DataLanguage> {

    class Base : DataLanguageMapper {

        override fun map(fromLanguage: String, toLanguage: String): DataLanguage
            = DataLanguage(fromLanguage, toLanguage)
    }
}