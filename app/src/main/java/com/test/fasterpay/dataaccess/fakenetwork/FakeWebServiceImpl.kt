package com.test.fasterpay.dataaccess.fakenetwork

import android.app.Application
import android.util.Log
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.dataaccess.fakenetwork.models.FakeServiceError
import com.test.fasterpay.dataaccess.storage.dao.CredentialsDao
import com.test.fasterpay.dataaccess.storage.dao.TransactionDao
import com.test.fasterpay.dataaccess.storage.dao.UserDao
import com.test.fasterpay.dataaccess.storage.dao.WalletDao
import com.test.fasterpay.util.CurrencyCalculator
import com.test.fasterpay.vo.*
import javax.inject.Inject
import kotlin.random.Random

class FakeWebServiceImpl @Inject constructor(
    private val application: Application,
    private val userDao: UserDao,
    private val credentialsDao: CredentialsDao,
    private val walletDao: WalletDao,
    private val transactionDao: TransactionDao
) : FakeWebService{
    override suspend fun login(credentialsForm: CredentialsForm): User {
        val dbCredentials = verifyCredentials(credentialsForm)
        return findUserByEmail(dbCredentials.email) { FakeServiceError.userNotFound(application) }
    }

    override suspend fun signUp(credentialsForm: CredentialsForm, user: User): User {
        registerCredentials(credentialsForm) //it can adding credentials failed
        userDao.addUser(user)
        Log.d(TAG, "signUp: user added")
        val registeredUser = findUserByEmail(credentialsForm.email) { FakeServiceError.registrationFailed(application) }
        walletDao.addWallet(
            Wallet(
                currency = RandomSelector.selectCurrency(),
                balance = Random.nextDouble(100.0, 500.0),
                ownerId = registeredUser.id
            ))
       return registeredUser
    }

    override suspend fun addTransaction(transaction: MoneyTransaction, walletId: Long): PastTransaction {
        val pastTransaction = PastTransaction.generateTransaction(transaction, walletId, Constants.databaseDateFormat())
        //add transactions
        val userTransactionCount = transactionDao.countItems(walletId, transaction.transactionId)
        if (userTransactionCount > 0)
            throw FakeServiceError.sameTransaction(application)

        //reduce transaction amount from wallet balance
        val wallet = walletDao.getWalletSync(walletId)
        wallet.balance -= CurrencyCalculator.toCurrencyValue(pastTransaction.totalAmount, pastTransaction.currency, wallet.currency)
        walletDao.addWallet(wallet)

        transactionDao.addTransaction(pastTransaction)

        //return past transaction
        return pastTransaction
    }

    //helping functions
    private suspend inline fun findUserByEmail(email: String, generateError: () -> FakeServiceError): User {
        val dbUser = userDao.getUserByEmail(email)
        Log.d(TAG, "findUserByEmail: ${dbUser?.email}")
        if (dbUser == null)
            throw generateError()
        return dbUser
    }

    private suspend fun registerCredentials(credentialsForm: CredentialsForm) {
        try {
            credentialsDao.addCredentials(credentialsForm)
            Log.d(TAG, "registerCredentials: registration done")
        } catch (t: Throwable) {
            Log.w(TAG, "registerCredentials: failed", t)
            throw FakeServiceError.alreadyRegistered(application)
        }
    }

    private suspend fun verifyCredentials(credentialsForm: CredentialsForm): CredentialsForm {
        val dbCredentials = credentialsDao.getCredentialsByEmail(credentialsForm.email)
        Log.d(TAG, "verifyCredentials: ${dbCredentials?.email}")
        if (dbCredentials == null)
            throw FakeServiceError.wrongEmail(application)
        if (dbCredentials.password != credentialsForm.password)
            throw FakeServiceError.wrongPassword(application)

        return dbCredentials
    }

    companion object {
        private const val TAG = "FakeWebServiceImpl"
    }
}