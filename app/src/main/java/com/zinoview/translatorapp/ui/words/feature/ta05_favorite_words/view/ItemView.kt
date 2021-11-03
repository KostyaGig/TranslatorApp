package com.zinoview.translatorapp.ui.words.feature.ta05_favorite_words.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

interface ItemView {

    fun changeBackground(isFavorite: Boolean)
}

class ItemViewImpl @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    attrStyle: Int = 0
): LinearLayout(context,attributeSet,attrStyle), ItemView {

    override fun changeBackground(isFavorite: Boolean) {
        if(isFavorite) {
            this.setBackgroundColor(resources.getColor(android.R.color.holo_orange_dark))
        } else {
            this.setBackgroundColor(resources.getColor(android.R.color.white))
        }
    }

}