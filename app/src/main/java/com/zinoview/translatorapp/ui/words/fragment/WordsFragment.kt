package com.zinoview.translatorapp.ui.words.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.TranslateWordViewModelFactory
import com.zinoview.translatorapp.ui.words.feature.ta02_show_translated_word.WordsAdapter
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.WordsViewModel
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.WordsViewModelFactory
import javax.inject.Inject

class WordsFragment : BaseFragment(R.layout.words_fragment) {

    @Inject
    lateinit var wordsViewModelFactory: WordsViewModelFactory

    private val wordsViewModel: WordsViewModel by viewModels {
        wordsViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = WordsAdapter.Base(object : WordsAdapter.WordsAdapterItemClickListener {

            override fun itemClick(translatedWord: String) {
                wordsViewModel.updateWord(translatedWord)
            }
        })

        val wordsRecyclerView = view.findViewById<RecyclerView>(R.id.words_rec_view)
        wordsRecyclerView.adapter = adapter

        wordsViewModel.observe(this) { state ->
            state.uiShow(adapter)
        }

        wordsViewModel.words()
    }

    override fun navigateToBack() {
        navigation.exit()
    }

}