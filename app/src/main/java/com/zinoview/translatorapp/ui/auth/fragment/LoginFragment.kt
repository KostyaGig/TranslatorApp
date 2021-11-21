package com.zinoview.translatorapp.ui.auth.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.login.LoginViewModel
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.login.LoginViewModelFactory
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize.AuthSnackBar
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.view.SearchEditTextImpl
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.TranslateWordViewModelFactory
import javax.inject.Inject

class LoginFragment : BaseFragment(R.layout.auth_fagment) {

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    private val loginViewModel: LoginViewModel by viewModels {
        loginViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val fieldUserName = view.findViewById<SearchEditTextImpl>(R.id.field_user_name)
        val fieldUserPhone = view.findViewById<SearchEditTextImpl>(R.id.field_user_phone)
        val loginBtn = view.findViewById<Button>(R.id.authorize_btn)
        val authView = view.findViewById<LinearLayout>(R.id.auth_view)
        authView.visibility = View.VISIBLE

        loginBtn.text = resourceProvider.string(R.string.login_text)

        val authorizeSnackBar = AuthSnackBar.Base(authView)

        loginBtn.setOnClickListener {
            val userName = fieldUserName.enteredText()
            val userPhone = fieldUserPhone.enteredText()

            loginViewModel.login(userName, userPhone)
        }

        loginViewModel.observe(this) { uiAuthLoginState ->
            uiAuthLoginState.map(navigation,authorizeSnackBar)
            uiAuthLoginState.map(bottomNavigationActivity)
        }
    }

    override fun navigateToBack()
        = navigation.navigateTo(RegisterFragment())

}