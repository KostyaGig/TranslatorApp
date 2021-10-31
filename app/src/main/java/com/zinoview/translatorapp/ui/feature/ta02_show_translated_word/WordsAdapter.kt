package com.zinoview.translatorapp.ui.feature.ta02_show_translated_word

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.Show
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextViewImpl
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.UiWordsStateRecyclerView

interface WordsAdapter : Show<List<UiWordsStateRecyclerView>> {

    class Base : WordsAdapter, RecyclerView.Adapter<Base.WordsViewHolder>()  {

        private val words = ArrayList<UiWordsStateRecyclerView>()

        //todo use diffutilcallback
        override fun show(arg: List<UiWordsStateRecyclerView>) {
            this.words.clear()
            words.addAll(arg)
            notifyDataSetChanged()
        }

        override fun getItemViewType(position: Int): Int {
            return when(words[position]) {
                is UiWordsStateRecyclerView.Progress -> 1
                else -> 2
            }
        }

        class WordsViewHolder(view: View) : RecyclerView.ViewHolder(view), Show<UiWordsStateRecyclerView> {

            private val translatedWordTextView = view.findViewById<WordTextViewImpl>(R.id.translated_word_tv)
            private val srcWordTextView = view.findViewById<WordTextViewImpl>(R.id.src_word_tv)

            override fun show(arg: UiWordsStateRecyclerView) {
                val views = Pair(translatedWordTextView,srcWordTextView)
                arg.show(views)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
            val view = when(viewType) {
                1 -> LayoutInflater.from(parent.context).inflate(R.layout.progress_item,parent,false)
                else -> LayoutInflater.from(parent.context).inflate(R.layout.word_item,parent,false)

            }
            return WordsViewHolder(view)
        }

        override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
            val word = words[position]
            holder.show(word)
        }

        override fun getItemCount(): Int
            = words.size
    }

}

