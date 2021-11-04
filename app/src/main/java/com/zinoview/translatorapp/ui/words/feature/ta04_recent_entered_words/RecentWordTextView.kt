package com.zinoview.translatorapp.ui.words.feature.ta04_recent_entered_words

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface RecentWordTextView {

    fun changeRecyclerViewVisibility(
        recyclerView: RecyclerView,
        tempRecentWords: TempRecentWords,
        adapter: RecentWordsAdapter
    )

    fun changeText()

    fun defaultState(recentQueryRecyclerView: RecyclerView)
}

class RecentWordTextViewImpl : RecentWordTextView, androidx.appcompat.widget.AppCompatTextView {

    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion


    override fun changeText() {
        val currentText = this.text
        if (currentText == SHOW_RECENT_QUERY) {
            this.text = HIDE_RECENT_QUERY
        } else {
            this.text = SHOW_RECENT_QUERY
        }
    }

    override fun changeRecyclerViewVisibility(
        recyclerView: RecyclerView,
        tempRecentWords: TempRecentWords,
        adapter: RecentWordsAdapter
    ) {
        val currentText = this.text
        if (currentText == HIDE_RECENT_QUERY) {
            tempRecentWords.show(adapter)
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
        }
    }

    override fun defaultState(recentQueryRecyclerView: RecyclerView) {
        recentQueryRecyclerView.visibility = View.GONE
        this.text = SHOW_RECENT_QUERY
    }

    private companion object {
        private const val SHOW_RECENT_QUERY = "Show recent query"
        private const val HIDE_RECENT_QUERY = "Hide recent query"
    }

}