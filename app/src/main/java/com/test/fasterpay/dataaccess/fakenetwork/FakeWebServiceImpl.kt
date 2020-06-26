package com.test.fasterpay.dataaccess.fakenetwork

import android.app.Application
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.dataaccess.fakenetwork.models.FakeServiceError
import com.test.fasterpay.dataaccess.storage.dao.CredentialsDao
import com.test.fasterpay.dataaccess.storage.dao.UserDao
import com.test.fasterpay.vo.User
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
            .doOnNext { userDao.addUser(user).subscribe() }
            .flatMap {
                findUserByEmail(credentialsForm.email) { FakeServiceError.registrationFailed(application) }
            }
    }

    //helping functions
    private inline fun findUserByEmail(email: String, crossinline generateError: () -> FakeServiceError): Observable<User> {
        return userDao.getUserByEmail(email)
            .onErrorResumeNext (
                Function<Throwable, Observable<User>> {
                    Observable.error<User>(generateError())
                }
            )
    }

    private fun registerCredentials(credentialsForm: CredentialsForm): Observable<Void> {
        return credentialsDao.addCredentials(credentialsForm)
            .onErrorResumeNext (
                Function<Throwable, Observable<Void>> {
                    Observable.error<Void>(FakeServiceError.alreadyRegistered(application))
                }
            )
    }

    private fun verifyCredentials(credentialsForm: CredentialsForm): Observable<CredentialsForm> {
        return credentialsDao.getCredentialsByEmail(credentialsForm.email)
            .doOnNext {
                if (it.password != credentialsForm.password)
                    throw FakeServiceError.wrongPassword(application)
            }
            .onErrorResumeNext (
                Function<Throwable, Observable<CredentialsForm>> {
                    Observable.error<CredentialsForm>(FakeServiceError.wrongEmail(application))
                }
            )
    }
}