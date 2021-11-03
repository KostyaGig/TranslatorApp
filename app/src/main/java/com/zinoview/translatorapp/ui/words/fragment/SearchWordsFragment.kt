package com.zinoview.translatorapp.ui.words.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.MainActivity
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.SearchEditTextImpl
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordProgressBarImpl
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextViewImpl
import com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words.RecentWordTextViewImpl
import com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words.RecentWordsAdapter
import com.zinoview.translatorapp.ui.feature.ta04_recent_entered_words.TempRecentWords
import com.zinoview.translatorapp.ui.feature.ta05_favorite_words.view.ItemViewImpl

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
        val recentQueryTextView = view.findViewById<RecentWordTextViewImpl>(R.id.recent_query_tv)

        val rootWordTextViewView = view.findViewById<ItemViewImpl>(R.id.root_word_tv)

        val adapter = RecentWordsAdapter.Base(object : RecentWordsAdapter.ItemClickListener<String> {
            override fun onItemClick(item: String) {
                wordField.setText(item)
                wordField.setSelection(wordField.length())
            }
        })

        val recentQueryRecyclerView = view.findViewById<RecyclerView>(R.id.recent_query_recycler_view)
        recentQueryRecyclerView.adapter = adapter

        viewModel.observe(this) { state ->
            state.show(wordTextView, wordProgressBar,rootWordTextViewView)
            state.changeRecentQuery(tempRecentWords)
        }

        searchWordBtn.setOnClickListener {
            val enteredWord = wordField.enteredText()
            viewModel.translateWord(enteredWord)

            recentQueryTextView.defaultState(recentQueryRecyclerView)
        }

        backBtn.setOnClickListener {
            navigateToBack()
        }

        recentQueryTextView.setOnClickListener { tv ->
            val wordTv = tv as RecentWordTextViewImpl
            wordTv.changeText()
            wordTv.changeRecyclerViewVisibility(recentQueryRecyclerView,tempRecentWords,adapter)
        }

        viewModel.observeRecentWords(this) { uiRecentWords ->
            log("view model recent words")
            uiRecentWords.map(tempRecentWords)
        }

        viewModel.recentWords()
    }

    override fun navigateToBack() {
        log("SEARCH NAV TO BACK")
        navigation.navigateTo(WordsFragment())
    }

    override fun onPause() {
        log("search fragment onPause")
        tempRecentWords.save(viewModel)
        super.onPause()
    }

    override fun onDestroy() {
        log("search fragment onDestroy")
        super.onDestroy()
    }

}