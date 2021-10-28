package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.DataLanguage

interface SaveLanguageMapper : Language.LanguageMapper<DataLanguage> {

    class Base : SaveLanguageMapper {

        override fun map(fromLanguage: String, toLanguage: String): DataLanguage
            = DataLanguage(fromLanguage, toLanguage)
    }
}