package com.zinoview.translatorapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zinoview.translatorapp.MainActivity
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.log
import com.zinoview.translatorapp.ui.view.SearchEditText
import com.zinoview.translatorapp.ui.view.SearchEditTextImpl
import com.zinoview.translatorapp.ui.view.WordProgressBarImpl
import com.zinoview.translatorapp.ui.view.WordTextViewImpl

class SearchWordsFragment : Fragment(R.layout.search_words_fragment) {

    private val viewModel by lazy {
        val activity = (requireActivity() as MainActivity)
        val application = activity.application as TAApplication
        application.translatedWordViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val wordField = view.findViewById<SearchEditTextImpl>(R.id.word_field)
        val searchWordBtn = view.findViewById<Button>(R.id.search_word_btn)
        val wordTextView = view.findViewById<WordTextViewImpl>(R.id.word_tv)
        val wordProgressBar = view.findViewById<WordProgressBarImpl>(R.id.word_pb)

        viewModel.observe(this) { state ->
           state.show(wordTextView,wordProgressBar)
        }

        searchWordBtn.setOnClickListener {
            val enteredWord = wordField.enteredText()
            viewModel.translatedWord(enteredWord)
        }
    }
}