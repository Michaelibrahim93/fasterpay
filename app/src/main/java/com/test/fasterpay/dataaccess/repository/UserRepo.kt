package com.test.fasterpay.dataaccess.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.test.fasterpay.dataaccess.fakenetwork.FakeWebService
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.vo.User
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityRetainedScoped
class UserRepo @Inject constructor(private val fakeWebService: FakeWebService) {
    private val loggedUserLiveData: LiveData<User>

    init {
        //TODO:: listen to logged in user in database
        loggedUserLiveData = MediatorLiveData()
    }

    fun isLoggedIn(): Boolean {
        return loggedUserLiveData.value != null
    }

    fun login(email: String, password: String): Observable<User> {
        return fakeWebService.login(CredentialsForm(email, password))
            .subscribeOn(Schedulers.io())
    }
}