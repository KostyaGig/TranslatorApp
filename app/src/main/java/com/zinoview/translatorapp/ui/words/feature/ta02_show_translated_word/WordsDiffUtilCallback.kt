package com.zinoview.translatorapp.ui.words.feature.ta02_show_translated_word

import androidx.recyclerview.widget.DiffUtil
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.UiWordsStateRecyclerView

class WordsDiffUtilCallback(
    private val oldList: List<UiWordsStateRecyclerView>,
    private val newList: List<UiWordsStateRecyclerView>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = true

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].same(newList[newItemPosition])
    }

}