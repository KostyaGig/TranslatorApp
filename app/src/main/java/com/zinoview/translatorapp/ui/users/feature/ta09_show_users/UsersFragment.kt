package com.zinoview.translatorapp.ui.users.feature.ta09_show_users

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.Toolbar
import javax.inject.Inject

class UsersFragment : BaseFragment(R.layout.users_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
    }

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory

    private val viewModel: UsersViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val usersAdapter = UsersAdapter.Base()
        val usersRecyclerView = view.findViewById<RecyclerView>(R.id.users_rec_view)
        usersRecyclerView.adapter = usersAdapter

        val toolbar = Toolbar.Base(
            (requireActivity() as AppCompatActivity).supportActionBar!!
        )

        viewModel.users()
        viewModel.observe(this) {users ->
            usersAdapter.update(users)
            users.first().showSourceData(toolbar)
        }

    }

    override fun navigateToBack() = bottomNavigationActivity.selectItem(R.id.words_item)
}