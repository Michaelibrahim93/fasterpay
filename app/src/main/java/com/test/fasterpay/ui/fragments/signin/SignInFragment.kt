package com.test.fasterpay.ui.fragments.signin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.test.basemodule.base.model.VMNotification
import com.test.fasterpay.R
import com.test.fasterpay.databinding.FragmentSignInBinding
import com.test.fasterpay.ui.fragments.base.BindingBaseFragment
import com.test.fasterpay.ui.fragments.base.FasterPayBaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BindingBaseFragment<SignInViewModel, FragmentSignInBinding>(), LoginCallbacks {

    override val layoutRes: Int
        get() = R.layout.fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding.callbacks = this
        binding.email = viewModel.ldEmail
        binding.password = viewModel.ldPassword
    }

    override fun clickedLogin() {
        viewModel.login()
    }

    override fun clickedSignUp() {
        view?.findNavController()?.navigate(R.id.action_login_sign_up)
    }

    override fun clickedForgotPassword() {
        comingSoon()
    }

    companion object {
        private const val TAG = "SignInFragment"
    }
}