package com.zinoview.translatorapp.ui.core.nav

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zinoview.translatorapp.R


interface NavigatorModel {

     fun navigate(activity: AppCompatActivity) = Unit

    object Empty : NavigatorModel

    class ConcreteFragment(
        private val fragment: Fragment
    ) : NavigatorModel {

        override fun navigate(activity: AppCompatActivity) {
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container,fragment)
                .commit()
        }

    }

}