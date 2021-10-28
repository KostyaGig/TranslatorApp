package com.zinoview.translatorapp.ui.core.nav

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.Observe

import java.lang.IllegalStateException

interface Navigator : Observe<NavigatorModel>  {

    fun openFragment(fragment: BaseFragment)

    class Base : Navigator{

        private val navigatorLiveData = MutableLiveData<NavigatorModel>(NavigatorModel.Empty)

        override fun openFragment(fragment: BaseFragment) {
            navigatorLiveData.value = NavigatorModel.ConcreteFragment(fragment)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<NavigatorModel>) {
            navigatorLiveData.observe(owner, observer)
        }
    }

    object Empty : Navigator {
        override fun openFragment(fragment: BaseFragment)
            =  throw IllegalStateException("Navigator.Empty not use openFragment()")

        override fun observe(owner: LifecycleOwner, observer: Observer<NavigatorModel>)
            =  throw IllegalStateException("Navigator.Empty not use observe()")
    }
}