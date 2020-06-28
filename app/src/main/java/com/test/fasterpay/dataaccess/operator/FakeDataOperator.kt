package com.test.fasterpay.dataaccess.operator

import android.content.SharedPreferences
import android.util.Log
import com.test.basemodule.utils.DateUtils
import com.test.fasterpay.dataaccess.fakenetwork.models.CredentialsForm
import com.test.fasterpay.dataaccess.storage.dao.*
import com.test.fasterpay.util.ExecutorsModule
import com.test.fasterpay.vo.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList
import kotlin.random.Random

class FakeDataOperator @Inject constructor(
    private val credentialsDao: CredentialsDao,
    private val userDao: UserDao,
    private val walletDao: WalletDao,
    private val transactionDao: TransactionDao,
    private val futureTransactionDao: FutureTransactionDao,
    private val sharedPreferences: SharedPreferences,
    @Named(ExecutorsModule.EXECUTOR_DISK)
    private val diskExecutor: Executor
) {
    private lateinit var credentialsList: ArrayList<CredentialsForm>
    private lateinit var usersList: ArrayList<User>
    private lateinit var walletList: ArrayList<Wallet>
    private lateinit var pastTransactions: ArrayList<PastTransaction>
    private lateinit var futureTransactions: ArrayList<MoneyTransaction>

    fun insertTestDataIfNeeded() {
        if (!sharedPreferences.getBoolean(KEY_IS_FIRST_TIME, true)) return

        diskExecutor.execute {
            createTestLists()
            insertToDatabase()

            sharedPreferences
                .edit()
                .putBoolean(KEY_IS_FIRST_TIME, false)
                .apply()
        }
    }

    private fun insertToDatabase() {
        credentialsDao.addCredentialsList(credentialsList)
            .doOnComplete { Log.d(TAG, "insertToDatabase: credentialsDao") }
            .subscribe()

        userDao.addUsers(usersList)
            .doOnComplete { Log.d(TAG, "insertToDatabase: userDao") }
            .subscribe()

        walletDao.addWalletList(walletList)
            .doOnComplete { Log.d(TAG, "insertToDatabase: walletDao") }
            .subscribe()

        transactionDao.addTransactionList(pastTransactions)
            .doOnComplete { Log.d(TAG, "insertToDatabase: addTransactionList") }
            .subscribe()

        futureTransactionDao.addFutureTransactionList(futureTransactions)
            .doOnComplete { Log.d(TAG, "insertToDatabase: addFutureTransactionList") }
            .subscribe()
    }

    private fun createTestLists() {
        val databaseDateFormat = Constants.databaseDateFormat()

        credentialsList = ArrayList(5)
        usersList = ArrayList(5)
        walletList = ArrayList(5)
        pastTransactions = ArrayList(100)
        futureTransactions = ArrayList(5)

        for(i in 1..5) {
            credentialsList.add(generateFakeCredentials(i))
            usersList.add(generateFakeUser(i))
            walletList.add(generateWallet(i))
        }
        for (i in 1..100)
            pastTransactions.add(generateFakeTransaction(i, databaseDateFormat))

        for (i in 101..105)
            futureTransactions.add(generateFakeFutureTransaction(i))
    }

    private fun generateFakeFutureTransaction(i: Int): MoneyTransaction {
        return MoneyTransaction(
            transactionId = i.toLong(),
            currency = RandomSelector.selectCurrency(),
            fee = Random.nextDouble(0.5, 5.0),
            totalAmount = Random.nextDouble(7.0, 50.0),
            isRefund = false,
            description = "Transaction $i",
            warning = null,
            source = RandomSelector.nextSource()
        )
    }

    private fun generateFakeTransaction(i: Int, databaseDateFormat: SimpleDateFormat): PastTransaction {
        val transType = Random.nextInt(5)

        return PastTransaction(
            transactionId = i.toLong(),
            walletId = Random.nextLong(1, 5),
            day = databaseDateFormat.format(fakeDate()),
            currency = RandomSelector.selectCurrency(),
            fee = Random.nextDouble(0.5, 5.0),
            totalAmount = Random.nextDouble(7.0, 50.0),
            isRefund = transType == 1,
            description = "Transaction $i",
            warning = if(transType == 2) "Warning" else null,
            source = RandomSelector.nextSource()
        )
    }

    private fun fakeDate(): Date {
        return DateUtils.generateDate(
            2019,
                Random.nextInt(7, 8),
                Random.nextInt(10, 15)
        )
    }

    private fun generateFakeCredentials(i: Int): CredentialsForm {
        return CredentialsForm(
            email = "test$i@test.com",
            password = "Test1234"
        )
    }

    private fun generateFakeUser(i: Int): User {
        return User(
            id = i.toLong(),
            email = "test$i@test.com",
            firstName = "First$i",
            lastName = "Last$i",
            imageUrl = RandomSelector.getRandomImage(),
            phoneNumber = "123456789",
            birthDate = "1993 01 01"
        )
    }

    private fun generateWallet(i: Int): Wallet {
        return Wallet(
            id = i.toLong(),
            ownerId = i.toLong(),
            currency = RandomSelector.selectCurrency(),
            balance = Random.nextDouble(100.0, 700.0)
        )
    }

    companion object {
        private const val TAG = "FakeDataOperator"
        private const val KEY_IS_FIRST_TIME = "FakeDataOperator"
    }
}