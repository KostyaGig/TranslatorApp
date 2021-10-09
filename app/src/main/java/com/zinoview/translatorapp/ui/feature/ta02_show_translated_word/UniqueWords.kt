package com.zinoview.translatorapp.ui.feature.ta02_show_translated_word

import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordState

//todo write test
interface UniqueWords<T : UiWordState> {

    fun addItem(item: T)

    fun uniques() : MutableList<T>

    class Base(
        private var words: MutableList<UiWordState>
    ) : UniqueWords<UiWordState> {

        override fun addItem(item: UiWordState) {
            if (words.size <= 0) {
                val newList = ArrayList<UiWordState>()
                newList.add(item)
                words = newList
            } else {
                addUniqueItem(item)
            }
        }

        private fun addUniqueItem(item: UiWordState) {
            var isContains = false
            words.forEach { word ->
                if (word.same(item)) {
                    isContains = true
                }
            }

            words = if (isContains.not()) {
                val newList = ArrayList(words)
                newList.add(item)
                newList
            } else {
                ArrayList(words)
            }
        }

        override fun uniques(): MutableList<UiWordState>
            = words
    }

    class Test : UniqueWords<UiWordState> {

        private var actualList = ArrayList<UiWordState>()

        override fun addItem(item: UiWordState) {
            if (actualList.size <= 0) {
                val newList = ArrayList<UiWordState>()
                newList.add(item)
                actualList = newList
            } else {
                addUniqueItem(item)
            }
        }

        private fun addUniqueItem(item: UiWordState) {
            var isContains = false
            actualList.forEach { word ->
                if (word.same(item)) {
                    isContains = true
                }
            }

            actualList = if (isContains.not()) {
                val newList = ArrayList(actualList)
                newList.add(item)
                newList
            } else {
                ArrayList(actualList)
            }
        }

        override fun uniques(): MutableList<UiWordState>
            = actualList

    }
}