package com.test.fasterpay.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.fasterpay.vo.PastTransaction
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TransactionDao {

    @Query("SELECT COUNT(id) FROM PastTransaction where walletId = :walletId AND transactionId = :transactionId")
    fun countItems(walletId: Long, transactionId: Long): Single<Int>

    @Query("select * from PastTransaction where walletId = :walletId ORDER BY day DESC LIMIT :limit")
    fun getTransactionsByWalletId(walletId: Long, limit: Int): LiveData<List<PastTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransaction(transaction: PastTransaction): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransactionList(list: List<PastTransaction>): Completable
}