package com.test.fasterpay.dataaccess.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.test.basemodule.base.model.resource.UserResource
import com.test.fasterpay.dataaccess.fakenetwork.FakeWebService
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.dataaccess.storage.dao.UserDao
import com.test.fasterpay.vo.User
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class UserRepo @Inject constructor(
    private val fakeWebService: FakeWebService,
    private val userDao: UserDao
) {

    fun getLoggedInUser(): LiveData<UserResource<User>> {
        return Transformations.map(userDao.getLoggedInUserLiveData()) { UserResource.success(it) }
    }

    suspend fun login(email: String, password: String): User = withContext(Dispatchers.IO){
        val user = fakeWebService.login(CredentialsForm(email, password))
        Log.d(TAG, "login: succeed: ${user.email}")
        authenticateUser(user)
        user
    }


    private suspend fun authenticateUser(user: User) {
        user.isLoggedUser = true
        userDao.addUser(user)
    }

    suspend fun signUp(user: User, password: String): User = withContext(Dispatchers.IO){
        val loggedUser = fakeWebService.signUp(CredentialsForm(user.email, password), user)
        Log.d(TAG, "signUp: succeed: ${loggedUser.email}")
        authenticateUser(loggedUser)
        loggedUser
    }

    suspend fun logout() = withContext(Dispatchers.IO){
        val user = userDao.getLoggedInUserSync()
        user.isLoggedUser = false
        userDao.addUser(user)
        Log.d(TAG, "signUp: logout: ${user.email}")
    }

    companion object {
        private const val TAG = "UserRepo"
    }
}