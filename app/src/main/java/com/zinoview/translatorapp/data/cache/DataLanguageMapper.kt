package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.DataLanguage

interface DataLanguageMapper : Language.LanguageMapper<DataLanguage> {

    class Base : DataLanguageMapper {

        override fun map(fromLanguage: String, toLanguage: String): DataLanguage
            = DataLanguage(fromLanguage, toLanguage)
    }
}