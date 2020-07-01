package com.test.fasterpay.dataaccess.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.test.basemodule.base.model.resource.Resource
import com.test.fasterpay.dataaccess.fakenetwork.FakeWebService
import com.test.fasterpay.dataaccess.storage.dao.FutureTransactionDao
import com.test.fasterpay.dataaccess.storage.dao.TransactionDao
import com.test.fasterpay.vo.MoneyTransaction
import com.test.fasterpay.vo.PastTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionsRepo @Inject constructor(
    private val fakeWebService: FakeWebService,
    private val transactionsDao: TransactionDao,
    private val futureTransactionDao: FutureTransactionDao
) {
    fun loadTransactions(walletId: Long): LiveData<Resource<List<PastTransaction>>>{
        return Transformations.map(transactionsDao.getTransactionsByWalletId(walletId, 10)){
            Resource.success(it)
        }
    }

    suspend fun addTransaction(moneyTransaction: MoneyTransaction, walletId: Long): PastTransaction
        = withContext(Dispatchers.IO){
        fakeWebService.addTransaction(moneyTransaction, walletId)
    }

    @WorkerThread
    suspend fun getTransactionSync(transactionId: Long): MoneyTransaction? = withContext(Dispatchers.IO){
        futureTransactionDao.getPreTransactionSync(transactionId)
    }
}