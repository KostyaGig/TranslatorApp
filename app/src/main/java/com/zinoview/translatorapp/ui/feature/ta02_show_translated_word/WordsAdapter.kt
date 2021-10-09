package com.zinoview.translatorapp.ui.feature.ta02_show_translated_word

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.Show
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWord
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextViewImpl

interface WordsAdapter : Show<List<UiWord>> {

    class Base : WordsAdapter, RecyclerView.Adapter<Base.WordsViewHolder>()  {

        private val words = ArrayList<UiWord>()

        override fun show(arg: List<UiWord>) {
            this.words.clear()
            words.addAll(arg)
            notifyDataSetChanged()
        }

        class WordsViewHolder(view: View) : RecyclerView.ViewHolder(view), Show<UiWord> {

            private val translatedWordTextView = view.findViewById<WordTextViewImpl>(R.id.translated_word_tv)
            private val srcWordTextView = view.findViewById<WordTextViewImpl>(R.id.src_word_tv)

            override fun show(arg: UiWord) {
                val views = Pair(translatedWordTextView,srcWordTextView)
                arg.show(views)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.word_item,parent,false)
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

