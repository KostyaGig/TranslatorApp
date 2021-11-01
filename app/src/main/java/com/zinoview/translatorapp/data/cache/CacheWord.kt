package com.zinoview.translatorapp.data.cache

import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.data.DataLanguage
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.UiWordsStateRecyclerView
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CacheWord : RealmObject(), Abstract.Words {


    @PrimaryKey
    var srcWord: String = ""
    var translatedWord: String = ""
    var fromLanguage: String = ""
    var toLanguage: String = ""
    var isFavorite: Boolean = false

    override fun <T> map(mapper: Abstract.WordsMapper<T>): T
        = mapper.map(translatedWord, srcWord, DataLanguage(fromLanguage,toLanguage))

    fun map(mapper: CacheWordMapper)
        = mapper.map(translatedWord,srcWord,DataLanguage(fromLanguage,toLanguage),isFavorite)

    //todo remove
    fun toUiModel() : UiWordsStateRecyclerView
        = UiWordsStateRecyclerView.Base(translatedWord,srcWord,DataLanguage(fromLanguage,toLanguage),isFavorite)
}