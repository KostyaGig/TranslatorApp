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

class SearchWordsFragment : BaseFragment(R.layout.search_words_fragment){

    private val viewModel by lazy {
        val activity = (requireActivity() as MainActivity)
        val application = activity.application as TAApplication
        application.translatedWordViewModel
    }


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

    }

    override fun navigateToBack() {
        navigation.navigateTo(WordsFragment())
    }

}