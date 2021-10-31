package com.zinoview.translatorapp.ui.feature.ta01_translate_word.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar

interface Show {

    fun show()
}

interface Hide {

    fun hide()
}

interface AbstractView : com.zinoview.translatorapp.ui.feature.ta01_translate_word.view.Show, Hide

interface SearchEditText : Hide {

    fun enteredText() : String
}

class SearchEditTextImpl : SearchEditText, androidx.appcompat.widget.AppCompatEditText {

    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion


    override fun enteredText(): String {
        return if (text.isNullOrEmpty()) EMPTY else text.toString().trim()
    }

    override fun hide() {
        setText("")
    }

    private companion object {
        const val EMPTY = "\"\""
    }

}

interface WordTextView :  AbstractView {

    fun text(text: String)
}


class WordTextViewImpl : WordTextView, androidx.appcompat.widget.AppCompatTextView {

    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //endregion

    override fun text(text: String) {
        this.text = text
    }

    override fun show() {
        this.visibility = View.VISIBLE
    }

    override fun hide() {
        this.visibility = View.GONE
    }

}

interface WordProgressBar : AbstractView {

}

class WordProgressBarImpl : WordProgressBar, ProgressBar {
    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //endregion

    override fun show() {
        this.visibility = View.VISIBLE
    }

    override fun hide() {
        this.visibility = View.GONE
    }

}
