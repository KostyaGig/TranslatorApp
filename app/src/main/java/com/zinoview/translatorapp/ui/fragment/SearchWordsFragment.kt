package com.zinoview.translatorapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.MainActivity
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.SearchEditTextImpl
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordProgressBarImpl
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextViewImpl
import com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words.TempRecentWords
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchWordsFragment : BaseFragment(R.layout.search_words_fragment){

    private val viewModel by lazy {
        val activity = (requireActivity() as MainActivity)
        val application = activity.application as TAApplication
        application.translatedWordViewModel
    }

    private val tempRecentWords = TempRecentWords.Base()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log("onViewCreated")

        val wordField = view.findViewById<SearchEditTextImpl>(R.id.word_field)
        val searchWordBtn = view.findViewById<Button>(R.id.search_word_btn)
        val backBtn = view.findViewById<Button>(R.id.back_btn)
        val wordTextView = view.findViewById<WordTextViewImpl>(R.id.word_tv)
        val wordProgressBar = view.findViewById<WordProgressBarImpl>(R.id.word_pb)

        viewModel.observe(this) { state ->
            state.show(wordTextView, wordProgressBar)
        }

        searchWordBtn.setOnClickListener {
            val enteredWord = wordField.enteredText()
            viewModel.translateWord(enteredWord)
        }

        backBtn.setOnClickListener {
            navigation.navigateTo(WordsFragment())
        }

        viewModel.observeRecentWords(this) { uiRecentWords ->
            uiRecentWords.map()
        }

        viewModel.recentWords()

        CoroutineScope(Dispatchers.IO).launch {
//            for (i in 0..100) {
//                tempRecentWords.addNewWord("Word $i")
//                if (i == 5) {
//                    tempRecentWords.addNewWord("Word 5")
//                }
//                tempRecentWords.readFirstSeven()
//            }
//            for (i in 0..10) {
//                delay(1000)
//                tempRecentWords.addNewWord("Word $i")
//                tempRecentWords.read()
//            }
            tempRecentWords.addNewWord("string 1")
            tempRecentWords.addNewWord("string 2")
            tempRecentWords.addNewWord("string 3")
            tempRecentWords.addNewWord("string 4")
            tempRecentWords.addNewWord("string 5")
            tempRecentWords.addNewWord("string 6")
            tempRecentWords.addNewWord("string 7")
            tempRecentWords.read()
            tempRecentWords.addNewWord("string 8")
            tempRecentWords.addNewWord("string 9")
            tempRecentWords.addNewWord("string 10")
            tempRecentWords.addNewWord("string 11")
            tempRecentWords.addNewWord("string 12")
            tempRecentWords.addNewWord("string 13")
            tempRecentWords.addNewWord("string 14")
            tempRecentWords.read()
        }
    }

    override fun navigateToBack() {
        navigation.navigateTo(WordsFragment())
    }



}