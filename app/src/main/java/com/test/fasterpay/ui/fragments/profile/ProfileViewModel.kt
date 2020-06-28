package com.test.fasterpay.ui.fragments.profile

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
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
            ldUser.postValue(it.data)
        }
    }

    fun logout() {
        userRepo.logout()
    }
}