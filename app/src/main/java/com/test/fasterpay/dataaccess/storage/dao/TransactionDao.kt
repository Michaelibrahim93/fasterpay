package com.test.fasterpay.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.fasterpay.vo.PastTransaction
import io.reactivex.Completable

@Dao
interface TransactionDao {
    @Query("select * from PastTransaction where walletId = :walletId ORDER BY day DESC LIMIT :limit")
    fun getTransactionsByWalletId(walletId: Long, limit: Int): LiveData<List<PastTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransaction(transaction: PastTransaction): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransactionList(list: List<PastTransaction>): Completable
}