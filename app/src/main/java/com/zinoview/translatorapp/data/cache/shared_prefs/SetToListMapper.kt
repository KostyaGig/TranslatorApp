package com.zinoview.translatorapp.data.cache.shared_prefs

interface SetToListMapper<E> {

    fun map(set: Set<E>) : List<E>

    class String : SetToListMapper<kotlin.String> {

        override fun map(set: Set<kotlin.String>): List<kotlin.String>
             = set.toList()
    }
}