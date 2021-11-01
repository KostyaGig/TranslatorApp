package com.zinoview.translatorapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.MainActivity
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.WordsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordsFragment : BaseFragment(R.layout.words_fragment) {

    private val viewModel by lazy {
        val activity = (requireActivity() as MainActivity)
        val application = activity.application as TAApplication
        application.wordsViewModel
    }

    //todo разобраться с обновлением и получением слова на одном и том же потоке,что и вставка слова в бд

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val addWordButton = view.findViewById<FloatingActionButton>(R.id.add_word_btn)
        val adapter = WordsAdapter.Base(object : WordsAdapter.WordsAdapterItemClickListener {

            override fun itemClick(position: Int, srcWord: String, isFavorite: Boolean) {
                viewModel.updateWord(srcWord,isFavorite,position)
            }
        })
        val wordsRecyclerView = view.findViewById<RecyclerView>(R.id.words_rec_view)
        wordsRecyclerView.adapter = adapter

        addWordButton.setOnClickListener {
            navigation.navigateTo(SearchWordsFragment())
        }

        viewModel.observe(this) { state ->
            state.uiShow(adapter)
        }

        viewModel.words()
    }

    override fun navigateToBack() {
        navigation.exit()
    }

}