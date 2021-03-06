package com.zinoview.translatorapp.data.words.cache.db

interface Favorite {

    fun isFavorite() : Boolean

    class Base(
        private val favorite: Boolean
    ) : Favorite {

        override fun isFavorite() = favorite
    }
}