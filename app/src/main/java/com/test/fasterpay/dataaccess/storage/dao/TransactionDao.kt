package com.test.fasterpay.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.fasterpay.vo.PastTransaction
@Dao
interface TransactionDao {

    @Query("SELECT COUNT(id) FROM PastTransaction where walletId = :walletId AND transactionId = :transactionId")
    suspend fun countItems(walletId: Long, transactionId: Long): Int

    @Query("select * from PastTransaction where walletId = :walletId ORDER BY day DESC LIMIT :limit")
    fun getTransactionsByWalletId(walletId: Long, limit: Int): LiveData<List<PastTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransaction(transaction: PastTransaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransactionList(list: List<PastTransaction>)
}