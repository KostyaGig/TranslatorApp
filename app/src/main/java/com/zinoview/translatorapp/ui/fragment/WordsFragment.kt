package com.zinoview.translatorapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.nav.NavigatorData
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.WordsAdapter

class WordsFragment : BaseFragment(R.layout.words_fragment) {

    /// TODO: 10/8/21 move this companion object and  companion object NavigatorModel to shared class

    private companion object {
        const val DATA = "data"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val addWordButton = view.findViewById<FloatingActionButton>(R.id.add_word_btn)
        val adapter = WordsAdapter.Base()
        val wordsRecyclerView = view.findViewById<RecyclerView>(R.id.words_rec_view)
        wordsRecyclerView.adapter = adapter

        arguments?.let { bundle ->
            val navigatorData = bundle.getSerializable(DATA) as NavigatorData.Words
            navigatorData.show(adapter)
        }

        addWordButton.setOnClickListener {
            navigation.navigateTo(SearchWordsFragment())
        }

    }

    override fun navigateToBack() {
        navigation.exit()
    }
}