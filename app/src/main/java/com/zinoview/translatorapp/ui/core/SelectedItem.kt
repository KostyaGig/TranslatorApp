package com.zinoview.translatorapp.ui.core

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.FactoryMapper
import com.zinoview.translatorapp.ui.auth.fragment.LoginFragment
import com.zinoview.translatorapp.ui.auth.fragment.RegisterFragment
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment
import com.zinoview.translatorapp.ui.words.fragment.WordsFragment
import java.lang.IllegalArgumentException

interface SelectedItem {

    fun show(bottomNavigationView: BottomNavigationView,fragment: BaseFragment)

    class Base(
        private val fragmentClassMapper: FragmentClassMapper
    ) : SelectedItem {

        override fun show(bottomNavigationView: BottomNavigationView,fragment: BaseFragment) {
            val itemId = fragmentClassMapper.map(fragment)
            log("itemid $itemId")
            bottomNavigationView.selectedItemId = R.id.authorize_item
        }
    }

    interface FragmentClassMapper : FactoryMapper<BaseFragment, Int> {

        class Base : FragmentClassMapper

            override fun map(src: BaseFragment): Int {
                return when(src) {
                    is RegisterFragment,is LoginFragment -> R.id.authorize_item
                    is WordsFragment, is SearchWordsFragment -> R.id.translate_item
                    else -> throw IllegalArgumentException("FragmentClassMapper.Base -> map() not handled argument: $src")
                }
            }

    }
}