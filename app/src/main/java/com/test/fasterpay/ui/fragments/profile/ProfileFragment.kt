package com.test.fasterpay.ui.fragments.profile

import android.os.Bundle
import android.view.View
import com.test.fasterpay.R
import com.test.fasterpay.databinding.FragmentProfileBinding
import com.test.fasterpay.ui.fragments.base.BindingBaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BindingBaseFragment<ProfileViewModel, FragmentProfileBinding>(), ProfileCallbacks {
    override val layoutRes: Int
        get() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding.callbacks = this
        binding.user = viewModel.ldUser
    }

    override fun clickedNotifications() {
        comingSoon()
    }

    override fun clickedHelp() {
        comingSoon()
    }

    override fun clickedLogout() {
        viewModel.logout()
    }

}