package com.test.fasterpay.dataaccess.fakenetwork

import android.app.Application
import android.util.Log
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.dataaccess.fakenetwork.models.FakeServiceError
import com.test.fasterpay.dataaccess.storage.dao.CredentialsDao
import com.test.fasterpay.dataaccess.storage.dao.TransactionDao
import com.test.fasterpay.dataaccess.storage.dao.UserDao
import com.test.fasterpay.dataaccess.storage.dao.WalletDao
import com.test.fasterpay.vo.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import javax.inject.Inject
import kotlin.random.Random

class FakeWebServiceImpl @Inject constructor(
    private val application: Application,
    private val userDao: UserDao,
    private val credentialsDao: CredentialsDao,
    private val walletDao: WalletDao,
    private val transactionDao: TransactionDao
) : FakeWebService{
    override fun login(credentialsForm: CredentialsForm): Observable<User> {
        return verifyCredentials(credentialsForm)
            .flatMap {
                findUserByEmail(credentialsForm.email) { FakeServiceError.userNotFound(application) }
            }
    }

    override fun signUp(credentialsForm: CredentialsForm, user: User): Observable<User> {
        return registerCredentials(credentialsForm)
            .flatMap{
                userDao.addUser(user)
                    .doOnComplete { Log.d(TAG, "signUp: user added") }
                    .subscribe()
                findUserByEmail(credentialsForm.email) { FakeServiceError.registrationFailed(application) }
            }.doOnNext {
                walletDao.addWallet(
                    Wallet(
                        currency = RandomSelector.selectCurrency(),
                        balance = Random.nextDouble(100.0, 500.0),
                        ownerId = it.id
                    )).doOnComplete { Log.d(TAG, "signUp: wallet added") }
                    .subscribe()
            }
    }

    override fun addTransaction(transaction: MoneyTransaction, walletId: Long): Observable<PastTransaction> {
        val pastTransaction = PastTransaction.generateTransaction(transaction, walletId, Constants.databaseDateFormat())
        return transactionDao.countItems(walletId, transaction.transactionId)
            .flatMap {
                if (it > 0) throw FakeServiceError.sameTransaction(application)
                transactionDao.addTransaction(pastTransaction)
            }.toObservable()
            .map { pastTransaction }
    }

    //helping functions
    private inline fun findUserByEmail(email: String, crossinline generateError: () -> FakeServiceError): Observable<User> {
        return userDao.getUserByEmail(email)
            .doOnSuccess { Log.d(TAG, "findUserByEmail: ${it.email}") }
            .onErrorResumeNext {
                Single.error(
                    if (it is FakeServiceError) it
                    else generateError()
                )
            }.toObservable()
    }

    private fun registerCredentials(credentialsForm: CredentialsForm): Observable<Long> {
        return credentialsDao.addCredentials(credentialsForm)
            .doOnSuccess { Log.d(TAG, "registerCredentials: registration done") }
            .onErrorResumeNext {
                Log.w(TAG, "registerCredentials: onErrorResumeNext", it)
                Single.error(FakeServiceError.alreadyRegistered(application))
            }.toObservable()
    }

    private fun verifyCredentials(credentialsForm: CredentialsForm): Observable<CredentialsForm> {
        return credentialsDao.getCredentialsByEmail(credentialsForm.email).toObservable()
            .doOnNext {
                Log.d(TAG, "verifyCredentials: ${it.email}")
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