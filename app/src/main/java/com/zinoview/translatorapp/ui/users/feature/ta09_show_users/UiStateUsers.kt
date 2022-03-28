package com.zinoview.translatorapp.ui.users.feature.ta09_show_users

import android.view.View
import android.widget.TextView
import com.zinoview.translatorapp.ui.core.Toolbar

sealed class UiStateUsers {

    open fun bind(view: TextView) = Unit

    open fun showSourceData(toolbar: Toolbar) = Unit

    object Progress : UiStateUsers()

    abstract class Success(
        private val srcName: String,
        private val userName: String
    ) : UiStateUsers(){

        override fun showSourceData(toolbar: Toolbar)
            = toolbar.changeTitle(srcName)

        override fun bind(view: TextView) {
            view.text = userName
        }

        class Cloud(
            srcName: String,
            userName:String
        ) : Success(srcName,userName)

        class Cache(
            srcName: String,
            userName:String
        ) : Success(srcName,userName)

    }

    class Failure(
        private val message: String
    ) : UiStateUsers() {

        override fun bind(view: TextView) {
            view.text = message
        }
    }
}