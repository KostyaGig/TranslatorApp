package com.zinoview.translatorapp.data.words.cache.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.zinoview.translatorapp.core.Abstract
import com.zinoview.translatorapp.data.words.DataLanguage
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.UiWordsStateRecyclerView

interface CacheWord {

    fun map(mapper: CacheWordMapper) : UiWordsStateRecyclerView

    fun update() : CacheWord

    fun favorite() : Favorite

    @Entity(tableName = "words_table")
    data class Base(
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
    ) : CacheWord, Abstract.Words {

        override fun <T> map(mapper: Abstract.WordsMapper<T>): T
            = mapper.map(translated, src, DataLanguage(fromLanguage,toLanguage))

        override fun map(mapper: CacheWordMapper)
            = mapper.map(translated,src, DataLanguage(fromLanguage,toLanguage),isFavorite)

        override fun update()
                = Base(
                    src,
                    translated,
                    fromLanguage,
                    toLanguage,
                    isFavorite.not()
                )

        override fun favorite(): Favorite = Favorite.Base(isFavorite)
    }
}
