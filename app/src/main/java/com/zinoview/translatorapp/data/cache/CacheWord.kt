package com.zinoview.translatorapp.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.data.DataLanguage
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.UiWordsStateRecyclerView
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@Entity(tableName = "words_table")
open class CacheWord(
    @ColumnInfo(name = "src")
    @androidx.room.PrimaryKey()
    val src: String,
    @ColumnInfo(name = "translated")
    val translated: String,
    @ColumnInfo(name = "fromLanguage")
    val fromLanguage: String,
    @ColumnInfo(name = "toLanguage")
    val toLanguage: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) : Abstract.Words {

    override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(translated, src, DataLanguage(fromLanguage,toLanguage))

    //todo move to interface
    fun map(mapper: CacheWordMapper)
        = mapper.map(translated,src,DataLanguage(fromLanguage,toLanguage),isFavorite)

    //todo move to interface
    fun update(isFavorite: Boolean)
        = CacheWord(
            src, translated, fromLanguage, toLanguage,isFavorite
        )

}