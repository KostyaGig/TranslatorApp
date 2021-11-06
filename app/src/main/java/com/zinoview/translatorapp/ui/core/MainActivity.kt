package com.zinoview.translatorapp.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.auth.fragment.RegisterFragment
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.core.nav.Navigator
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment
import com.zinoview.translatorapp.ui.words.fragment.WordsFragment

//todo remove
fun Any.log(message: String) {
    Log.d("TestTag",message)
}

fun Exception.info() : String {
    return "class ${this::class.java}, message ${this.message}"
}

class MainActivity : AppCompatActivity(), Navigation {

    private var navigator: Navigator = Navigator.Empty

    private val selectedItem =  SelectedItem.Base(
        SelectedItem.FragmentClassMapper.Base()
    )

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.main_nav)

        navigator = Navigator.Base()
        navigateTo(WordsFragment())

        navigator.observe(this) { navigatorModel ->
            navigatorModel.navigate(this)
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.translate_item -> navigateTo(SearchWordsFragment())
                R.id.authorize_item -> navigateTo(RegisterFragment())
            }
            return@setOnItemSelectedListener true
        }

    }

    override fun selectItem(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
    }

    override fun navigateTo(fragment: BaseFragment) {
        navigator.openFragment(fragment)
    }

    override fun exit() = super.onBackPressed()

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments[0] as BaseFragment
        selectedItem.show(bottomNavigationView,fragment)
        fragment.navigateToBack()
    }
}