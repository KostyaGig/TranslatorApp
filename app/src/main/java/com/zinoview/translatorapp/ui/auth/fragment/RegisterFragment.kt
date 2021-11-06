package com.zinoview.translatorapp.ui.auth.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.MainActivity
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.view.SearchEditTextImpl
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.view.WordProgressBarImpl
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment

class RegisterFragment : BaseFragment(R.layout.auth_fagment) {

    private val registerViewModel by lazy {
        val activity = (requireActivity() as MainActivity)
        val application = activity.application as TAApplication
        application.registerViewModel
    }

    private var enteredWord = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            enteredWord = it.getString(ENTERED_WORD)!!
        }

        val fieldUserName = view.findViewById<SearchEditTextImpl>(R.id.field_user_name)
        val fieldUserPhone = view.findViewById<SearchEditTextImpl>(R.id.field_user_phone)
        val registerBtn = view.findViewById<Button>(R.id.authorize_btn)
        registerBtn.text = "Register"
        val progressBar = view.findViewById<WordProgressBarImpl>(R.id.progress_bar)

        val authResultTextView = view.findViewById<TextView>(R.id.auth_result_tv)

        registerBtn.setOnClickListener {
            val userName = fieldUserName.enteredText()
            val userPhone = fieldUserPhone.enteredText()

            registerViewModel.register(userName, userPhone)
        }

        registerViewModel.observe(this) { uiAuthRegisterState ->
            uiAuthRegisterState.map(navigation,enteredWord)
        }
    }

    override fun onDestroy() {
        registerViewModel.clean()
        super.onDestroy()
    }

    override fun navigateToBack() = navigation.navigateTo(SearchWordsFragment())
}