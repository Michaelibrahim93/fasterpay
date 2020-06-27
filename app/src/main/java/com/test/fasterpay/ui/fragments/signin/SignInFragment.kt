package com.test.fasterpay.ui.fragments.signin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.basemodule.base.model.VMNotification
import com.test.fasterpay.R
import com.test.fasterpay.databinding.FragmentSignInBinding
import com.test.fasterpay.ui.fragments.base.FasterPayBaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : FasterPayBaseFragment<SignInViewModel>(), LoginCallbacks {
    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateBinding(inflater, container, R.layout.fragment_sign_in)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.callbacks = this
        binding.email = viewModel.ldEmail
        binding.password = viewModel.ldPassword
    }

    override fun clickedLogin() {
        viewModel.login()
    }

    override fun clickedSignUp() {
        //TODO::
    }

    override fun clickedForgotPassword() {

    }

    override fun doAction(vmNotification: VMNotification) {
        super.doAction(vmNotification)
        when(vmNotification.getAction()) {
            ACTION_TO_HOME -> Log.d(TAG, "doAction: ACTION_TO_HOME")
        }
    }

    companion object {
        private const val TAG = "SignInFragment"
        internal const val ACTION_TO_HOME = "ACTION_TO_HOME"
    }
}