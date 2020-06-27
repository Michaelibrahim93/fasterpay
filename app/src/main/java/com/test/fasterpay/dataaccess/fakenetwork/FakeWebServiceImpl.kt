package com.test.fasterpay.dataaccess.fakenetwork

import android.app.Application
import android.util.Log
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.dataaccess.fakenetwork.models.FakeServiceError
import com.test.fasterpay.dataaccess.storage.dao.CredentialsDao
import com.test.fasterpay.dataaccess.storage.dao.UserDao
import com.test.fasterpay.vo.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class FakeWebServiceImpl @Inject constructor(
    private val application: Application,
    private val userDao: UserDao,
    private val credentialsDao: CredentialsDao
) : FakeWebService{
    override fun login(credentialsForm: CredentialsForm): Observable<User> {
        return verifyCredentials(credentialsForm)
            .flatMap {
                findUserByEmail(credentialsForm.email) { FakeServiceError.userNotFound(application) }
            }
    }

    override fun signUp(credentialsForm: CredentialsForm, user: User): Observable<User> {
        return registerCredentials(credentialsForm)
            .toObservable<Any>()
            .flatMap{
                userDao.addUser(user).subscribe()
                findUserByEmail(credentialsForm.email) { FakeServiceError.registrationFailed(application) }
            }
    }

    //helping functions
    private inline fun findUserByEmail(email: String, crossinline generateError: () -> FakeServiceError): Observable<User> {
        return userDao.getUserByEmail(email)
            .onErrorResumeNext (
                Function<Throwable, Observable<User>> {
                    Observable.error<User>(
                        if (it is FakeServiceError) it
                        else generateError()
                    )
                }
            )
    }

    private fun registerCredentials(credentialsForm: CredentialsForm): Completable {
        return credentialsDao.addCredentials(credentialsForm)
            .onErrorResumeNext (
                Function<Throwable, Completable> {
                    Completable.error(FakeServiceError.alreadyRegistered(application))
                }
            )
    }

    private fun verifyCredentials(credentialsForm: CredentialsForm): Observable<CredentialsForm> {
        return credentialsDao.getCredentialsByEmail(credentialsForm.email).toObservable()
            .doOnNext {
                if (it.password != credentialsForm.password)
                    throw FakeServiceError.wrongPassword(application)
            }
            .doOnError { Log.d(TAG, "verifyCredentials: $it") }
            .onErrorResumeNext (
                Function<Throwable, Observable<CredentialsForm>> {
                    Log.d(TAG, "verifyCredentials: Function $it")
                    Observable.error<CredentialsForm>(FakeServiceError.wrongEmail(application))
                }
            )
    }

    companion object {
        private const val TAG = "FakeWebServiceImpl"
    }
}