package com.test.fasterpay.ui.fragments.signup

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.test.basemodule.utils.DateUtils
import com.test.fasterpay.R
import com.test.fasterpay.dataaccess.repository.UserRepo
import com.test.fasterpay.ui.formatters.TextFormatter
import com.test.fasterpay.ui.fragments.base.FasterPayBaseFragment
import com.test.fasterpay.ui.fragments.base.FasterPayBaseViewModel
import com.test.fasterpay.util.AuthValidator
import com.test.fasterpay.vo.Constants
import com.test.fasterpay.vo.RandomSelector
import com.test.fasterpay.vo.User
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class SignUpViewModel
    @ViewModelInject constructor(application: Application, private val userRepo: UserRepo)
    : FasterPayBaseViewModel(application) {

    val ldEmail = MutableLiveData<String>()
    val ldPassword = MutableLiveData<String>()
    val ldFirstName = MutableLiveData<String>()
    val ldLastName = MutableLiveData<String>()
    val ldPhoneNumber = MutableLiveData<String>()
    val ldBirthDate = MutableLiveData<Date>()
    val ldAcceptPolicy = MutableLiveData<Boolean>()

    fun signUp() = launchDataLoad {
        if (!validateData()) return@launchDataLoad
        userRepo.signUp(createUserObject(), ldPassword.value!!)
        addAction(FasterPayBaseFragment.ACTION_TO_HOME)
    }

    private fun createUserObject(): User {
        val dateFormat = Constants.databaseDateFormat()
        return User(
            firstName = ldFirstName.value!!,
            lastName = ldLastName.value!!,
            imageUrl = RandomSelector.getRandomImage(),
            email = ldEmail.value!!,
            phoneNumber = ldPhoneNumber.value!!,
            birthDate = TextFormatter.formatDate(dateFormat, ldBirthDate.value)
        )
    }

    private fun validateData(): Boolean {
        val stringBuilder = StringBuilder()

        if (!AuthValidator.isValidEmail(ldEmail.value))
            stringBuilder.append(getContext().getString(R.string.not_valid_email)).append("\n")
        if (!AuthValidator.isValidPassword(ldPassword.value))
            stringBuilder.append(getContext().getString(R.string.not_valid_password)).append("\n")
        if (ldFirstName.value.isNullOrBlank())
            stringBuilder.append(getContext().getString(R.string.empty_first_name)).append("\n")
        if (ldLastName.value.isNullOrBlank())
            stringBuilder.append(getContext().getString(R.string.empty_last_name)).append("\n")
        if (!AuthValidator.isValidPhoneNumber(ldPhoneNumber.value))
            stringBuilder.append(getContext().getString(R.string.invalid_phone_number)).append("\n")
        if (ldBirthDate.value == null)
            stringBuilder.append(getContext().getString(R.string.enter_your_birth_date)).append("\n")
        if (ldAcceptPolicy.value != true)
            stringBuilder.append(getContext().getString(R.string.privacy_terms_not_accepted))

        if (stringBuilder.isNotEmpty())
            sendError(stringBuilder.trim().toString())

        return stringBuilder.isEmpty()
    }

    fun updateBirthDate(year: Int, month: Int, day: Int) {
        ldBirthDate.value = DateUtils.generateDate(year, month, day)
    }
}