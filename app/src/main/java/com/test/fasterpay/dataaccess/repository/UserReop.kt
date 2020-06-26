package com.test.fasterpay.dataaccess.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.test.fasterpay.vo.User
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class UserReop @Inject constructor() {
    private val loggedUserLiveData: LiveData<User>

    init {
        //TODO:: listen to logged in user in database
        loggedUserLiveData = MediatorLiveData()
    }

    fun isLoggedIn(): Boolean {
        return loggedUserLiveData.value != null
    }
}