package com.test.fasterpay.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.fasterpay.vo.MoneyTransaction
import io.reactivex.Observable

@Dao
interface TransactionDao {
    @Query("select * from MoneyTransaction where walletId = :walletId")
    fun getTransactionsByWalletId(walletId: String): LiveData<List<MoneyTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransaction(transaction: MoneyTransaction): Observable<Void>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTransactionList(list: List<MoneyTransaction>): Observable<Void>
}