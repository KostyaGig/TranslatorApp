package com.zinoview.translatorapp.ui.auth.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.zinoview.translatorapp.R
import com.zinoview.translatorapp.core.TAApplication
import com.zinoview.translatorapp.ui.core.BaseFragment
import com.zinoview.translatorapp.ui.core.MainActivity
import com.zinoview.translatorapp.ui.core.log
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.view.SearchEditTextImpl
import com.zinoview.translatorapp.ui.words.feature.ta01_translate_word.view.WordProgressBarImpl
import com.zinoview.translatorapp.ui.words.fragment.SearchWordsFragment

class RegisterFragment : BaseFragment(R.layout.register_fagment) {

    private val authViewModel by lazy {
        val activity = (requireActivity() as MainActivity)
        val application = activity.application as TAApplication
        application.authViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val fieldUserName = view.findViewById<SearchEditTextImpl>(R.id.field_user_name)
        val fieldUserPhone = view.findViewById<SearchEditTextImpl>(R.id.field_user_phone)
        val registerBtn = view.findViewById<Button>(R.id.register_btn)
        val progressBar = view.findViewById<WordProgressBarImpl>(R.id.progress_bar)

        registerBtn.setOnClickListener {
            val userName = fieldUserName.enteredText()
            val userPhone = fieldUserPhone.enteredText()

            log("register user name $userName, userPhone $userPhone")
            authViewModel.register(userName,userPhone)
        }

        authViewModel.observe(this) { registerState ->
            registerState.mapResult(progressBar)
        }
    }


    override fun navigateToBack() = navigation.navigateTo(SearchWordsFragment())
}