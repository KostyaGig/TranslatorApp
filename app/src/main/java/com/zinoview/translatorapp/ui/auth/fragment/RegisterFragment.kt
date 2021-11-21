package com.zinoview.translatorapp.ui.auth.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize.AuthSnackBar
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.MainActivity
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.core.view.SearchEditTextImpl
import com.zinoview.translatorapp.ui.words.fragment.WordsFragment

class RegisterFragment : BaseFragment(R.layout.auth_fagment) {

    private val registerViewModel by lazy {
        val activity = (requireActivity() as MainActivity)
        val application = activity.application as TAApplication
        application.registerViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val fieldUserName = view.findViewById<SearchEditTextImpl>(R.id.field_user_name)
        val fieldUserPhone = view.findViewById<SearchEditTextImpl>(R.id.field_user_phone)
        val registerBtn = view.findViewById<Button>(R.id.authorize_btn)
        val authView = view.findViewById<LinearLayout>(R.id.auth_view)

        registerBtn.text = resourceProvider.string(R.string.register_text)

        val authorizeSnackBar = AuthSnackBar.Base(authView)

        registerBtn.setOnClickListener {
            val userName = fieldUserName.enteredText()
            val userPhone = fieldUserPhone.enteredText()

            registerViewModel.register(userName, userPhone)
        }

        registerViewModel.observe(this) { uiAuthRegisterState ->
            uiAuthRegisterState.map(navigation,authorizeSnackBar)
            uiAuthRegisterState.map(bottomNavigationActivity)
        }

    }

    override fun onDestroy() {
        registerViewModel.clean()
        super.onDestroy()
    }

    override fun navigateToBack() {
        navigation.navigateTo(WordsFragment())
        navigation.selectItem(R.id.words_item)
    }
}