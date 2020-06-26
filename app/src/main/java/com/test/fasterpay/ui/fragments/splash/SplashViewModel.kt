package com.test.fasterpay.ui.fragments.splash

import android.app.Application
import android.os.Handler
import androidx.hilt.lifecycle.ViewModelInject
import com.test.fasterpay.dataaccess.repository.UserReop
import com.test.fasterpay.ui.fragments.base.FasterPayBaseViewModel

class SplashViewModel @ViewModelInject constructor(application: Application,val userReop: UserReop)
    : FasterPayBaseViewModel(application) {

    init {
        Handler().postDelayed( { toNextScreen() } ,3000)
    }

    private fun toNextScreen() {
        if (userReop.isLoggedIn())
            addAction(SplashFragment.ACTION_TO_HOME)
        else
            addAction(SplashFragment.ACTION_TO_LOGIN)
    }
}