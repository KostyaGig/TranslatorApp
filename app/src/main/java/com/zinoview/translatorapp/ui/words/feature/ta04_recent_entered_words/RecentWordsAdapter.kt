package com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.Show
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextViewImpl

interface RecentWordsAdapter :
    com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.Show<List<String>> {


    class Base(
        private val itemClickListener: ItemClickListener<String>
    ): RecentWordsAdapter, RecyclerView.Adapter<Base.RecentWordsViewHolder>() {

        private val recentWords = ArrayList<String>()

        //todo use diffutil
        override fun show(arg: List<String>) {
            this.recentWords.clear()
            this.recentWords.addAll(arg)
            notifyDataSetChanged()
        }

        class RecentWordsViewHolder(
            view: View,
            private val itemClickListener: ItemClickListener<String>
        ) : RecyclerView.ViewHolder(view) {

            private val recentWordTv = view.findViewById<WordTextViewImpl>(R.id.recent_word_tv)

            fun bind(recentWord: String) {
                recentWordTv.text = recentWord

                itemView.setOnClickListener {
                    itemClickListener.onItemClick(recentWord)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentWordsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_word,parent,false)
            return RecentWordsViewHolder(view,itemClickListener)
        }

        override fun onBindViewHolder(holder: RecentWordsViewHolder, position: Int) {
            val recentWord = recentWords[position]
            holder.bind(recentWord)
        }

        override fun getItemCount(): Int
            = recentWords.size
    }

    interface ItemClickListener<T> {

        fun onItemClick(item: T)
    }
}