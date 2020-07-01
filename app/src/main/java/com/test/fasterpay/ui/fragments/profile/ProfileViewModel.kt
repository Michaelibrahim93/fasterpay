package com.test.fasterpay.ui.fragments.profile

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.test.fasterpay.dataaccess.repository.UserRepo
import com.test.fasterpay.ui.fragments.base.FasterPayBaseViewModel
import com.test.fasterpay.vo.User

class ProfileViewModel @ViewModelInject constructor(
    application: Application,
    private val userRepo: UserRepo
) : FasterPayBaseViewModel(application) {

    val ldUser = MediatorLiveData<User>()

    init {
        ldUser.addSource(userRepo.getLoggedInUser()) {
            ldUser.value = it.data
        }
    }

    fun logout() = launchDataLoad(false) {
        userRepo.logout()
    }
}