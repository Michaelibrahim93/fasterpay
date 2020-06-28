package com.test.fasterpay.dataaccess.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.test.basemodule.base.model.resource.Resource
import com.test.fasterpay.dataaccess.storage.dao.TransactionDao
import com.test.fasterpay.vo.PastTransaction
import javax.inject.Inject

class TransactionsRepo @Inject constructor(
    private val transactionsDao: TransactionDao
) {
    fun loadTransactions(walletId: Long): LiveData<Resource<List<PastTransaction>>>{
        return Transformations.map(transactionsDao.getTransactionsByWalletId(walletId, 10)){
            Resource.success(it)
        }
    }
}