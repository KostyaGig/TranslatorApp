package com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words

import com.zinoview.translatorapp.data.cache.core.Save
import com.zinoview.translatorapp.ui.core.log
import java.util.*
import kotlin.collections.ArrayList

//todo make test
interface TempRecentWords : Save<List<String>> {

    fun addNewWord(translatedWord: String)

    //todo remove this
    fun read()

    fun readFirstSeven()

    class Base : TempRecentWords {

        private val recentWords = ArrayList<String>()

        override fun save(save: List<String>) {
            recentWords.addAll(save)
        }

        //todo make stack from retain words
        override fun addNewWord(translatedWord: String) {
            if (recentWords.contains(translatedWord).not()) {
                recentWords.add(translatedWord)
                if (recentWords.size > 6) {
                    log("SIZE > 6")
                    val firstRecentSixWords = recentWords.take(6).reversed()
                    recentWords.clear()
                    recentWords.add(translatedWord)
                    recentWords.addAll(firstRecentSixWords)
                }
            }

        }

        override fun read() {
            recentWords.forEach {
                log("TempRecentWords read -> $it")
            }
        }

        override fun readFirstSeven() {
            if (recentWords.size > 7) {
                val firstSevenWords = recentWords.take(7)
                firstSevenWords.forEach {
                    log("TempRecentWords read first seven (> 7) -> $it")
                }
            } else {
                recentWords.forEach {
                    log("TempRecentWords read first seven (< 7) -> $it")
                }
            }
        }

    }
}