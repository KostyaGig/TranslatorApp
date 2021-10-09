package com.zinoview.translatorapp.ui.core.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWord
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.TranslatedWords

interface NavigatorModel {

     fun navigate(activity: AppCompatActivity) = Unit

    object Empty : NavigatorModel

    class ConcreteFragment(
        private val fragment: Fragment,
        private val data: NavigatorData<TranslatedWords<UiWord>>
    ) : NavigatorModel {

        override fun navigate(activity: AppCompatActivity) {
            val data = Bundle().apply {
                putSerializable(DATA,data)
            }
            fragment.arguments = data
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container,fragment)
                .commit()
        }

        private companion object {
            const val DATA = "data"
        }
    }

}