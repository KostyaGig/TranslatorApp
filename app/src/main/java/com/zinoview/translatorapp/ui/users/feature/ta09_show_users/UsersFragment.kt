package com.zinoview.translatorapp.ui.users.feature.ta09_show_users

import android.os.Bundle
import android.view.View
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.core.BaseFragment

class UsersFragment : BaseFragment(R.layout.users_fragment) {

    // todo inject viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        val usersAdapter = UsersAdapter.Base()
//        viewModel.users()
//        viewModel().observe(this) {
//            usersAdapter.update()
//        }
    }

    override fun navigateToBack() = bottomNavigationActivity.selectItem(R.id.words_item)
}