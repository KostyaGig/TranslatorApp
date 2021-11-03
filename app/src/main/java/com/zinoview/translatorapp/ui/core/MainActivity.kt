package com.zinoview.translatorapp.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.core.nav.Navigator
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, WordsFragment())
            .commit()

        navigator = Navigator.Base()

        navigator.observe(this) { navigatorModel ->
            navigatorModel.navigate(this)
        }
    }

    override fun navigateTo(fragment: BaseFragment) {
        navigator.openFragment(fragment)
    }

    override fun exit() = super.onBackPressed()

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments[0] as BaseFragment
        fragment.navigateToBack()
    }
}