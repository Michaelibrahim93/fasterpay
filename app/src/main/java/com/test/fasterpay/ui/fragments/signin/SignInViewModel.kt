package com.test.fasterpay.ui.fragments.signin

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.test.fasterpay.R
import com.test.fasterpay.dataaccess.repository.UserRepo
import com.test.fasterpay.ui.fragments.base.FasterPayBaseFragment
import com.test.fasterpay.ui.fragments.base.FasterPayBaseViewModel
import com.test.fasterpay.util.AuthValidator
import java.lang.StringBuilder

class SignInViewModel @ViewModelInject constructor(application: Application, private val userRepo: UserRepo)
    : FasterPayBaseViewModel(application) {

    val ldEmail = MutableLiveData<String>()
    val ldPassword = MutableLiveData<String>()

    fun login() = launchDataLoad {
        if (!validateData()) return@launchDataLoad

        userRepo.login(ldEmail.value!!, ldPassword.value!!)
        addAction(FasterPayBaseFragment.ACTION_TO_HOME, null, false)
    }

    private fun validateData(): Boolean {
         val stringBuilder = StringBuilder()
        if (!AuthValidator.isValidEmail(ldEmail.value))
            stringBuilder.append(getContext().getString(R.string.not_valid_email)).append("\n")
        if (ldPassword.value.isNullOrBlank())
            stringBuilder.append(getContext().getString(R.string.empty_password))

        if (stringBuilder.isNotEmpty())
            sendError(stringBuilder.trim().toString())

        return stringBuilder.isEmpty()
    }
}