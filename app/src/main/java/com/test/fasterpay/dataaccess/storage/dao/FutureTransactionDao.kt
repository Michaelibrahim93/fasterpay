package com.test.fasterpay.dataaccess.storage.dao

import androidx.room.*
import com.test.fasterpay.vo.MoneyTransaction

@Dao
interface FutureTransactionDao {

    @Query("select * from MoneyTransaction where transactionId = :transactionId")
    suspend fun getPreTransactionSync(transactionId: Long): MoneyTransaction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFutureTransactionList(list: List<MoneyTransaction>)
}