package com.zinoview.translatorapp.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.core.nav.Navigation
import com.zinoview.translatorapp.ui.core.nav.Navigator
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiLanguageMapper
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordMapper
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordState
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.ImmutableMapper
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.MutableTranslatedWords
import com.zinoview.translatorapp.ui.fragment.SearchWordsFragment
import com.zinoview.translatorapp.ui.fragment.WordsFragment

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
            .replace(R.id.main_fragment_container,WordsFragment())
            .commit()

        navigator = Navigator.Base(ImmutableMapper.Base(
            UiWordMapper.Base(
                UiLanguageMapper.Base()
            )
        ))

        navigator.observe(this) { navigatorModel ->
            navigatorModel.navigate(this)
        }
    }

    override fun navigateTo(fragment: BaseFragment, data: MutableTranslatedWords<UiWordState>) {
        navigator.openFragment(fragment,data)
    }

    override fun exit() = super.onBackPressed()

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments[0] as BaseFragment
        fragment.navigateToBack()
    }
}