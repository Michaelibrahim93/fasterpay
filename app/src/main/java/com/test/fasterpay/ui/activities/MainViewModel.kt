package com.test.fasterpay.ui.activities

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import com.test.basemodule.base.model.resource.Resource
import com.test.basemodule.base.model.resource.UserResource
import com.test.basemodule.base.viewmodel.BaseViewModel
import com.test.fasterpay.dataaccess.repository.UserRepo
import com.test.fasterpay.vo.User


class MainViewModel @ViewModelInject constructor(
    application: Application,
    userRepo: UserRepo
) : BaseViewModel(application) {
    val ldUser = MediatorLiveData<UserResource<User>>()

    init {
        ldUser.addSource(userRepo.getLoggedInUser()) {
            ldUser.value = if (ldUser.value?.data != null && it.data == null) UserResource.loggedOut()
                else it
        }
    }
}