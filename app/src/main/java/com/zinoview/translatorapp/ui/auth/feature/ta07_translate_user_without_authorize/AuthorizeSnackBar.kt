package com.zinoview.translatorapp.ui.auth.feature.ta07_translate_user_without_authorize

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

interface AuthorizeSnackBar {

    fun show(text: String)

    class Base(
        view: View
    ) : AuthorizeSnackBar {

        private var weakView = WeakReference(view)

        override fun show(text: String)
            = Snackbar.make(weakView.get()!!,text,Snackbar.LENGTH_SHORT).show()

    }
}