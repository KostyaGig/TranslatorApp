package com.zinoview.translatorapp.data.cache.shared_prefs

interface ListToSetMapper<E> {

    fun map(list: List<E>) : Set<E>

    class String : ListToSetMapper<kotlin.String> {

        override fun map(list: List<kotlin.String>): Set<kotlin.String>
             = list.toSet()
    }
}