package com.test.fasterpay.dataaccess.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.test.basemodule.base.model.resource.UserResource
import com.test.fasterpay.dataaccess.fakenetwork.FakeWebService
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.dataaccess.storage.dao.UserDao
import com.test.fasterpay.vo.User
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityRetainedScoped
class UserRepo @Inject constructor(private val fakeWebService: FakeWebService, private val userDao: UserDao) {

    fun getLoggedInUser(): LiveData<UserResource<User>> {
        return Transformations.map(userDao.getLoggedInUserLiveData()) { UserResource.success(it) }
    }

    fun login(email: String, password: String): Observable<User> {
        return fakeWebService.login(CredentialsForm(email, password))
            .subscribeOn(Schedulers.io())
            .doOnNext { authenticateUser(it) }
    }

    private fun authenticateUser(user: User) {
        user.isLoggedUser = true
        userDao.addUser(user)
            .doOnComplete { Log.d(TAG, "authenticateUser: ${user.email}") }
            .subscribe()
    }

    fun signUp(user: User, password: String): Observable<User> {
        return fakeWebService.signUp(CredentialsForm(user.email, password), user)
            .subscribeOn(Schedulers.io())
            .doOnNext { authenticateUser(it) }
    }
    companion object {
        private const val TAG = "UserRepo"
    }
}