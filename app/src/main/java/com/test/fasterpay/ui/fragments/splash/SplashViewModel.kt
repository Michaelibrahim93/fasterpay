package com.test.fasterpay.ui.fragments.splash

import android.app.Application
import android.os.Handler
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import com.test.basemodule.base.model.resource.UserResource
import com.test.basemodule.base.model.resource.UserStatus
import com.test.fasterpay.dataaccess.operator.FakeDataOperator
import com.test.fasterpay.dataaccess.repository.UserRepo
import com.test.fasterpay.ui.fragments.base.FasterPayBaseFragment
import com.test.fasterpay.ui.fragments.base.FasterPayBaseViewModel
import com.test.fasterpay.vo.User

class SplashViewModel @ViewModelInject constructor(
    application: Application,
    private val userReop: UserRepo,
    fakeDataOperator: FakeDataOperator
): FasterPayBaseViewModel(application) {
    val loggedUser = MediatorLiveData<UserResource<User>>()

    init {
        initMediatorLiveDate()
        Handler().postDelayed( { toNextScreen() } ,3000)

        launchDataLoad(false) {
            fakeDataOperator.insertTestDataIfNeeded()
        }
    }

    private fun initMediatorLiveDate() {
        loggedUser.addSource(userReop.getLoggedInUser()) {
            loggedUser.value = it
        }
    }

    private fun toNextScreen() {
        if (loggedUser.value?.data != null)
            addAction(FasterPayBaseFragment.ACTION_TO_HOME, null, false)
        else
            addAction(SplashFragment.ACTION_TO_LOGIN, null, false)
    }
}