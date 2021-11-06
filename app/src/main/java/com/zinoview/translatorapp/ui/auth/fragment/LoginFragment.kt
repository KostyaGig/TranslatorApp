package com.zinoview.translatorapp.ui.auth.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_user_without_authorize.AuthorizeSnackBar
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.MainActivity
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.view.SearchEditTextImpl

class LoginFragment : BaseFragment(R.layout.auth_fagment) {

    private val loginViewModel by lazy {
        val activity = (requireActivity() as MainActivity)
        val application = activity.application as TAApplication
        application.loginViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val fieldUserName = view.findViewById<SearchEditTextImpl>(R.id.field_user_name)
        val fieldUserPhone = view.findViewById<SearchEditTextImpl>(R.id.field_user_phone)
        val loginBtn = view.findViewById<Button>(R.id.authorize_btn)
        val authView = view.findViewById<LinearLayout>(R.id.auth_view)
        authView.visibility = View.VISIBLE

        loginBtn.text = resourceProvider.string(R.string.login_text)

        val authorizeSnackBar = AuthorizeSnackBar.Base(authView)

        loginBtn.setOnClickListener {
            val userName = fieldUserName.enteredText()
            val userPhone = fieldUserPhone.enteredText()

            loginViewModel.login(userName, userPhone)
        }

        loginViewModel.observe(this) { uiAuthLoginState ->
            uiAuthLoginState.map(navigation,authorizeSnackBar)
        }
    }

    override fun navigateToBack()
        = navigation.navigateTo(RegisterFragment())

    override fun onDestroy() {
        loginViewModel.clean()
        super.onDestroy()
    }
}