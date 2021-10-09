package com.zinoview.translatorapp.ui.core.nav

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.Observe
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWord
import com.zinoview.translatorapp.ui.feature.ta01_translate_word.UiWordState
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.ImmutableMapper
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.MutableTranslatedWords
import com.zinoview.translatorapp.ui.feature.ta02_show_translated_word.TranslatedWords
import java.lang.IllegalStateException

interface Navigator : Observe<NavigatorModel>  {

    fun openFragment(fragment: BaseFragment,data: MutableTranslatedWords<UiWordState> = MutableTranslatedWords.Empty)

    class Base(
        private val immutableMapper: ImmutableMapper<TranslatedWords<UiWord>>
    ) : Navigator{

        private val navigatorLiveData = MutableLiveData<NavigatorModel>(NavigatorModel.Empty)

        override fun openFragment(fragment: BaseFragment, data: MutableTranslatedWords<UiWordState>) {
            val navigatorData = NavigatorData.Words(data.asImmutable(immutableMapper))
            navigatorLiveData.value = NavigatorModel.ConcreteFragment(fragment, navigatorData)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<NavigatorModel>) {
            navigatorLiveData.observe(owner, observer)
        }
    }

    object Empty : Navigator {
        override fun openFragment(fragment: BaseFragment, data: MutableTranslatedWords<UiWordState>)
            =  throw IllegalStateException("Navigator.Empty not use openFragment()")

        override fun observe(owner: LifecycleOwner, observer: Observer<NavigatorModel>)
            =  throw IllegalStateException("Navigator.Empty not use observe()")
    }
}