package com.zinoview.translatorapp.ui.users.feature.ta09_show_users

import android.view.View
import android.widget.TextView

sealed class UiStateUsers {

    open fun bind(view: TextView) = Unit

    object Progress : UiStateUsers()

    abstract class Success : UiStateUsers(){

        class Cloud(
            private val userName:String
        ) : Success() {

            override fun bind(view: TextView) {
                view.text = userName
            }
        }

        class Cache : Success()

    }

    class Failure(
        private val message: String
    ) : UiStateUsers() {

        override fun bind(view: TextView) {
            view.text = message
        }
    }
}