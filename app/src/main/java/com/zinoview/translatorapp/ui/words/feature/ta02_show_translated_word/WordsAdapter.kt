package com.zinoview.translatorapp.ui.words.feature.ta02_show_translated_word

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.Show
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.WordTextViewImpl
import com.zinoview.translatorapp.ui.feature.ta03_cached_translated_words.UiWordsStateRecyclerView
import com.zinoview.translatorapp.ui.feature.ta05_favorite_words.view.ItemViewImpl

interface WordsAdapter : com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.Show.WordsAdapterShow<List<UiWordsStateRecyclerView>> {


    class Base(
        private val adapterItemClickListener: WordsAdapterItemClickListener
    ) : WordsAdapter, RecyclerView.Adapter<Base.WordsViewHolder>()  {

        private val words = ArrayList<UiWordsStateRecyclerView>()

        //todo use diffutilcallback
        override fun show(items: List<UiWordsStateRecyclerView>,position: Int) {
                if (position < 0) {
                    this@Base.words.clear()
                    words.addAll(items)
                    notifyDataSetChanged()
                } else {
                    val updatedItem = items[0]
                    updateItemByPosition(updatedItem,position)
                }
        }

        private fun updateItemByPosition(item: UiWordsStateRecyclerView,position: Int) {
            this.words[position] = item
            notifyItemChanged(position)
        }

        override fun getItemViewType(position: Int): Int {
            return when(words[position]) {
                is UiWordsStateRecyclerView.Progress -> 1
                else -> 2
            }
        }

        abstract class WordsViewHolder(view: View) : RecyclerView.ViewHolder(view),
            com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.Show<UiWordsStateRecyclerView> {

            override fun show(arg: UiWordsStateRecyclerView) {}

            class Base(view: View,private val itemClickListener: WordsAdapterItemClickListener) : WordsViewHolder(view) {

                private val translatedWordTextView = view.findViewById<WordTextViewImpl>(R.id.translated_word_tv)
                private val srcWordTextView = view.findViewById<WordTextViewImpl>(R.id.src_word_tv)
                private val rootItemView = view.findViewById<ItemViewImpl>(R.id.item_view)
                private val changeFavoriteBtn = view.findViewById<Button>(R.id.change_favorite_btn)

                override fun show(arg: UiWordsStateRecyclerView) {
                    super.show(arg)

                    val views = Triple(translatedWordTextView,srcWordTextView,rootItemView)
                    arg.show(views)

                    changeFavoriteBtn.setOnClickListener {
                        arg.itemClick(adapterPosition,itemClickListener)
                    }
                }
            }

            class Progress(view: View) : WordsViewHolder(view)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
            return when(viewType) {
                1 -> WordsViewHolder.Progress(LayoutInflater.from(parent.context).inflate(R.layout.progress_item,parent,false))
                else -> WordsViewHolder.Base(LayoutInflater.from(parent.context).inflate(R.layout.word_item,parent,false),adapterItemClickListener)

            }

        }

        override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
            val word = words[position]
            holder.show(word)
        }

        override fun getItemCount(): Int
            = words.size
    }

    interface WordsAdapterItemClickListener {

        fun itemClick(position: Int,translatedWord: String)
    }
}

