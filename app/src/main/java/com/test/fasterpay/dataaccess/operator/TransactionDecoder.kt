package com.test.fasterpay.dataaccess.operator

import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.test.fasterpay.dataaccess.repository.TransactionsRepo
import com.test.fasterpay.vo.MoneyTransaction
import com.test.fasterpay.vo.TransactionQRModel
import javax.inject.Inject

class TransactionDecoder @Inject constructor(private val transactionsRepo: TransactionsRepo){
    @WorkerThread
    suspend fun decodeTransaction(jsonObject: String): MoneyTransaction {
        val qrModel = Gson().fromJson(jsonObject, TransactionQRModel::class.java)
        return transactionsRepo.getTransactionSync(qrModel.transactionId)!!
    }
}