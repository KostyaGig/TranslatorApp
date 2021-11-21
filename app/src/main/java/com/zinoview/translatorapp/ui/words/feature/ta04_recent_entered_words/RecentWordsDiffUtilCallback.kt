package com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words

import androidx.recyclerview.widget.DiffUtil
import com.zinoview.translatorapp.ui.words.feature.ta03_cached_translated_words.UiWordsStateRecyclerView

class RecentWordsDiffUtilCallback(
    private val oldList: List<String>,
    private val newList: List<String>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = true

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == (newList[newItemPosition])
    }

}