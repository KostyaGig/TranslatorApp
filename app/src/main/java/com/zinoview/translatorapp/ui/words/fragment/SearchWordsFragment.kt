package com.zinoview.translatorapp.ui.words.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.view.SearchEditTextImpl
import com.zinoview.translatorapp.ui.core.view.WordProgressBarImpl
import com.zinoview.translatorapp.ui.core.view.WordTextViewImpl
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.TranslateWordViewModel
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.TranslateWordViewModelFactory
import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.RecentWordTextViewImpl
import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.RecentWordsAdapter
import com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words.TempRecentWords
import com.zinoview.translatorapp.ui.words.feature.ta05_favorite_words.view.ItemViewImpl
import javax.inject.Inject

class SearchWordsFragment : BaseFragment(R.layout.search_words_fragment){

    @Inject
    lateinit var translateWordViewModelFactory: TranslateWordViewModelFactory

    private val translateWordViewModel: TranslateWordViewModel by viewModels {
        translateWordViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
    }

    private val tempRecentWords = TempRecentWords.Base()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // region init view
        val wordField = view.findViewById<SearchEditTextImpl>(R.id.word_field)
        val searchWordBtn = view.findViewById<Button>(R.id.search_word_btn)
        val wordTextView = view.findViewById<WordTextViewImpl>(R.id.word_tv)
        val wordProgressBar = view.findViewById<WordProgressBarImpl>(R.id.word_pb)
        val recentQueryTextView = view.findViewById<RecentWordTextViewImpl>(R.id.recent_query_tv)

        val rootWordTextViewView = view.findViewById<ItemViewImpl>(R.id.root_word_tv)
        val recentQueryRecyclerView = view.findViewById<RecyclerView>(R.id.recent_query_recycler_view)

        //endregion

        val adapter = RecentWordsAdapter.Base(object : RecentWordsAdapter.ItemClickListener<String> {
            override fun onItemClick(item: String) {
                wordField.setText(item)
                wordField.setSelection(wordField.length())
            }
        })

        recentQueryRecyclerView.adapter = adapter

        translateWordViewModel.observe(this) { state ->
            state.show(wordTextView, wordProgressBar, rootWordTextViewView)
            state.changeRecentQuery(tempRecentWords)
        }

        searchWordBtn.setOnClickListener {
            val enteredWord = wordField.enteredText()
            translateWordViewModel.translateWord(enteredWord)
            recentQueryTextView.defaultState(recentQueryRecyclerView)
        }

        recentQueryTextView.setOnClickListener { tv ->
            val wordTv = tv as RecentWordTextViewImpl
            wordTv.changeText()
            wordTv.changeRecyclerViewVisibility(
                recentQueryRecyclerView,
                tempRecentWords,
                adapter
            )
        }

        translateWordViewModel.observeRecentWords(this) { uiRecentWords ->
            uiRecentWords.map(tempRecentWords)
        }

        translateWordViewModel.recentWords()
    }

    override fun navigateToBack() {
        navigation.navigateTo(WordsFragment())
        navigation.selectItem(R.id.words_item)
    }

    override fun onPause() {
        tempRecentWords.save(translateWordViewModel)
        super.onPause()
    }

}