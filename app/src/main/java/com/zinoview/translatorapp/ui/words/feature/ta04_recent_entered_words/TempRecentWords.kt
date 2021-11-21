package com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words

import com.zinoview.translatorapp.data.words.cache.core.Save
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.TranslateWordViewModel
import kotlin.collections.ArrayList

interface TempRecentWords : Save<TranslateWordViewModel> {

    fun addNewWord(translatedWord: String)

    fun fill(recentWordsFromCache: List<String>)

    fun show(adapter: RecentWordsAdapter)

    //usage for test
    fun read() : List<String>

    class Base: TempRecentWords {

        private val recentWords = ArrayList<String>()

        override fun fill(recentWordsFromCache: List<String>) {
            recentWords.clear()
            recentWords.addAll(recentWordsFromCache)
        }

        override fun save(viewModel: TranslateWordViewModel) {
            viewModel.saveRecentQuery(ArrayList(recentWords))
        }

        override fun show(adapter: RecentWordsAdapter) {
            adapter.show(ArrayList(recentWords))
        }

        override fun addNewWord(translatedWord: String) {
            if (recentWords.contains(translatedWord).not()) {
                recentWords.add(translatedWord)
                if (recentWords.size > 6) {
                    val firstRecentSixWords = recentWords.take(6)
                    recentWords.clear()
                    recentWords.add(translatedWord)
                    recentWords.addAll(firstRecentSixWords)
                } else {
                    if (recentWords.size > 1) {
                        val lastTempWord = recentWords[recentWords.size - 1]
                        recentWords.remove(lastTempWord)
                        val tempRecentWords = recentWords.take(recentWords.size)
                        recentWords.clear()
                        recentWords.add(translatedWord)
                        recentWords.addAll(tempRecentWords)
                    }
                }
            }
        }

        override fun read() = throw IllegalStateException("TempRecentWords.Base not use read()")
    }

    class Test : TempRecentWords {

        private val data = ArrayList<String>()

        override fun addNewWord(translatedWord: String) {
            if (data.contains(translatedWord).not()) {
                data.add(translatedWord)
                if (data.size > 6) {
                    val firstRecentSixWords = data.take(6)
                    data.clear()
                    data.add(translatedWord)
                    data.addAll(firstRecentSixWords)
                } else {
                    if (data.size > 1) {
                        val lastTempWord = data[data.size - 1]
                        data.remove(lastTempWord)
                        val tempRecentWords = data.take(data.size)
                        data.clear()
                        data.add(translatedWord)
                        data.addAll(tempRecentWords)
                    }
                }
            }
        }

        override fun fill(recentWordsFromCache: List<String>)
                = throw IllegalStateException("TempRecentWords.Test not use fill()")

        override fun show(adapter: RecentWordsAdapter)
                = throw IllegalStateException("TempRecentWords.Test not use show()")

        override fun save(save: TranslateWordViewModel)
                = throw IllegalStateException("TempRecentWords.Test not use save()")

        override fun read() : List<String> = data
    }

}