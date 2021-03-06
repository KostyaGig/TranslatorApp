package com.zinoview.translatorapp.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register.RegisterViewModel
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register.RegisterViewModelFactory
import com.zinoview.translatorapp.ui.auth.fragment.RegisterFragment
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.core.nav.Navigator
import com.zinoview.translatorapp.ui.core.nav.SingleNavigation
import com.zinoview.translatorapp.ui.users.feature.ta09_show_users.UsersFragment
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment
import com.zinoview.translatorapp.ui.words.fragment.WordsFragment
import javax.inject.Inject

//todo remove
fun Any.log(message: String) {
    Log.d("TestTag",message)
}

fun Exception.info() : String {
    return "class ${this::class.java}, message ${this.message}"
}

class MainActivity : AppCompatActivity(),
    Navigation, BottomNavigationActivity, SingleNavigation {

    private var navigator: Navigator = Navigator.Empty

    private lateinit var bottomNavigationView: BottomNavigationView

    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory

    private val registerViewModel: RegisterViewModel.Base by viewModels {
        registerViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        bottomNavigationView = findViewById(R.id.main_nav)

        navigator = Navigator.Base()
        navigateTo(WordsFragment())

        navigator.observe(this) { navigatorModel ->
            navigatorModel.navigate(this)
        }

        registerViewModel.observeAuthorize(this) { authState ->
            authState.handleAuth(this)
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.words_item -> singleNavigateTo(WordsFragment())
                R.id.translate_item -> singleNavigateTo(SearchWordsFragment())
                R.id.authorize_item -> singleNavigateTo(RegisterFragment())
                R.id.users_item -> singleNavigateTo(UsersFragment())
            }
            return@setOnItemSelectedListener true
        }

        registerViewModel.requestAuthorize()
    }


    override fun selectItem(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
    }

    override fun inflateBottomNavigationMenu(id: Int) {
        bottomNavigationView.menu.removeGroup(R.id.menu_group)
        bottomNavigationView.inflateMenu(id)
    }

    override fun navigateTo(fragment: BaseFragment) {
        navigator.openFragment(fragment)
    }

    override fun singleNavigateTo(fragment: BaseFragment) {
        val currentFragment = supportFragmentManager.fragments.first() as BaseFragment
        if (currentFragment.javaClass != fragment.javaClass) {
            navigateTo(fragment)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments.first() as BaseFragment
        fragment.navigateToBack()
    }

    override fun exit() = super.onBackPressed()

    private fun AppCompatActivity.inject() {
        val application = this.application
        if (application is TAApplication) {
            application.appComponent.inject(this@MainActivity)
        }
    }
}