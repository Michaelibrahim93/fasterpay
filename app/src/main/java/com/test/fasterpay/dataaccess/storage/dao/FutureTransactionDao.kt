package com.test.fasterpay.dataaccess.storage.dao

import androidx.room.*
import com.test.fasterpay.vo.MoneyTransaction
import io.reactivex.Completable

@Dao
interface FutureTransactionDao {

    @Query("select * from MoneyTransaction where transactionId = :transactionId")
    fun getPreTransactionSync(transactionId: Long): MoneyTransaction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFutureTransactionList(list: List<MoneyTransaction>): Completable
}