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
import com.test.fasterpay.vo.Wallet
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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

    fun addTransaction(moneyTransaction: MoneyTransaction, walletId: Long): Observable<PastTransaction> {
        return fakeWebService.addTransaction(moneyTransaction, walletId)
            .subscribeOn(Schedulers.io())
    }

    @WorkerThread
    fun getTransactionSync(transactionId: Long): MoneyTransaction? {
        return futureTransactionDao.getPreTransactionSync(transactionId)
    }
}