package com.zinoview.translatorapp.ui.words.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.MainActivity
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.words.feature.ta02_show_translated_word.WordsAdapter

class WordsFragment : BaseFragment(R.layout.words_fragment) {

    private val viewModel by lazy {
        val activity = (requireActivity() as MainActivity)
        val application = activity.application as TAApplication
        application.wordsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = WordsAdapter.Base(object : WordsAdapter.WordsAdapterItemClickListener {

            override fun itemClick(position: Int, translatedWord: String) {
                viewModel.updateWord(translatedWord, position)
            }
        })

        val wordsRecyclerView = view.findViewById<RecyclerView>(R.id.words_rec_view)
        wordsRecyclerView.adapter = adapter

        viewModel.observe(this) { state ->
            state.uiShow(adapter)
        }

        viewModel.words()
    }

    override fun onPause() {
        log("words fragment onPause")
        super.onPause()
    }

    override fun onDestroy() {
        log("words fragment onDestroy")
        super.onDestroy()
    }

    override fun navigateToBack() {
        navigation.exit()
    }

}