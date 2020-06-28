package com.test.fasterpay.ui.fragments.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.test.basemodule.base.model.VMNotification
import com.test.fasterpay.R
import com.test.fasterpay.ui.fragments.base.FasterPayBaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : FasterPayBaseFragment<SplashViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loggedUser.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onViewCreated: ${it.data?.email}")
        })
    }

    override fun doAction(vmNotification: VMNotification) {
        super.doAction(vmNotification)
        when(vmNotification.getAction()) {
            ACTION_TO_LOGIN -> view?.findNavController()?.navigate(R.id.action_splash_login)
        }
    }

    companion object {
        internal const val ACTION_TO_LOGIN = "ACTION_TO_LOGIN"

        private const val TAG = "SplashFragment"
    }
}