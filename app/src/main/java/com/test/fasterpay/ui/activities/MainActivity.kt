package com.test.fasterpay.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.test.basemodule.base.model.resource.UserResource
import com.test.basemodule.base.model.resource.UserStatus
import com.test.fasterpay.R
import com.test.fasterpay.vo.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initObservers()
    }

    private fun initObservers() {
        viewModel.ldUser.observe(this, Observer { onUserUpdated(it) })
    }

    private fun onUserUpdated(userResource: UserResource<User>) {
        if (userResource.status == UserStatus.LOGGED_OUT)
            navigateToLogin()
    }

    private fun navigateToLogin() {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopUpTo(R.id.main_nav_graph, true)
            .build()
        findNavController(R.id.nav_host_fragment).navigate(R.id.signInFragment, null, navOptions)
    }
}