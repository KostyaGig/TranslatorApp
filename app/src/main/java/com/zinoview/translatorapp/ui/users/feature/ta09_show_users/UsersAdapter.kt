package com.zinoview.translatorapp.ui.users.feature.ta09_show_users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.translatorapp.R

interface UsersAdapter {

    fun update(newList: List<UiStateUsers>)

    class Base : UsersAdapter, RecyclerView.Adapter<Base.ViewHolder>() {

        private val users = ArrayList<UiStateUsers>()

        override fun update(newList: List<UiStateUsers>) {
            users.clear()
            users.addAll(newList)
            notifyDataSetChanged()
        }

        private companion object {
            private const val PROGRESS = 1
            private const val CLOUD = 2
            private const val CACHE = 3
            private const val FAILURE = 4
        }

        override fun getItemViewType(position: Int): Int {
            return when(users[position]) {
                is UiStateUsers.Progress -> PROGRESS
                is UiStateUsers.Success.Cloud -> CLOUD
                is UiStateUsers.Success.Cache -> 3
                else -> FAILURE
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return when (viewType) {
                PROGRESS -> ViewHolder.Progress(R.layout.progress_item.makeView(parent))
                CLOUD,CACHE -> ViewHolder.Base(R.layout.user_item.makeView(parent))
                else -> ViewHolder.Failure(R.layout.error_item.makeView(parent))
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(users[position])
        }

        override fun getItemCount(): Int = users.size

        abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            open fun bind(user: UiStateUsers) {}

            class Progress(view: View) : ViewHolder(view)

            class Base(view: View) : ViewHolder(view) {

                private val userNameTv = view.findViewById<TextView>(R.id.user_name_tv)

                override fun bind(user: UiStateUsers) {
                    user.bind(userNameTv)
                }
            }

            class Failure(view: View) : ViewHolder(view) {

                private val errorTv = view.findViewById<TextView>(R.id.error_tv)

                override fun bind(user: UiStateUsers) { user.bind(errorTv) }
            }
        }

        private fun Int.makeView(parent: ViewGroup)
            = LayoutInflater.from(parent.context).inflate(this,parent,false)
    }
}