package com.test.fasterpay.ui.fragments.signup

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.test.fasterpay.R
import com.test.fasterpay.databinding.FragmentSignUpBinding
import com.test.fasterpay.ui.fragments.base.BindingBaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class SignUpFragment : BindingBaseFragment<SignUpViewModel, FragmentSignUpBinding>()
    , SignUpCallbacks {

    override val layoutRes: Int
        get() = R.layout.fragment_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataBinding()
    }

    private fun initDataBinding() {
        binding.callbacks = this
        binding.email = viewModel.ldEmail
        binding.password = viewModel.ldPassword
        binding.firstName = viewModel.ldFirstName
        binding.lastName = viewModel.ldLastName
        binding.phoneNumber = viewModel.ldPhoneNumber
        binding.birthDate = viewModel.ldBirthDate
        binding.policyAccepted = viewModel.ldAcceptPolicy
        binding.formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }

    override fun clickedLogin() {
        activity?.onBackPressed()
    }

    override fun clickedSignUp() {
        viewModel.signUp()
    }

    override fun clickedBirthDate() {
        context?.let {
            DatePickerDialog(it,
                { _, year, month, day ->  viewModel.updateBirthDate(year, month, day)},
                1993, 1, 1)
                .show()
        }
    }
}