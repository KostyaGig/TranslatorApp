package com.zinoview.translatorapp.ui.auth.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register.RegisterViewModel
import com.zinoview.translatorapp.ui.auth.feature.ta06_auth_user.register.RegisterViewModelFactory
import com.zinoview.translatorapp.ui.auth.feature.ta07_translate_word_user_without_authorize.AuthSnackBar
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.view.SearchEditTextImpl
import com.zinoview.translatorapp.ui.words.fragment.WordsFragment
import javax.inject.Inject

class RegisterFragment : BaseFragment(R.layout.auth_fagment) {

    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory

    private val registerViewModel: RegisterViewModel by viewModels {
        registerViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
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

    override fun navigateToBack() {
        navigation.navigateTo(WordsFragment())
        navigation.selectItem(R.id.words_item)
    }
}