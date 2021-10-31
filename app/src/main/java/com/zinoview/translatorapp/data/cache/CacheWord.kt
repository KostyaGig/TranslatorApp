package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.core.Language
import com.zinoview.translatorapp.data.DataLanguage
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.jetbrains.annotations.NotNull

open class CacheWord : RealmObject(), Abstract.Words {

    @PrimaryKey
    var translatedWord: String = ""
    var srcWord: String = ""
    var fromLanguage: String = ""
    var toLanguage: String = ""
    var isFavorite: Boolean = false

    override fun <T> map(mapper: Abstract.WordsMapper<T>): T
        = mapper.map(translatedWord, srcWord, DataLanguage(fromLanguage,toLanguage))
}