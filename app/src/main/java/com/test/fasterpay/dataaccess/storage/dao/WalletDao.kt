package com.test.fasterpay.dataaccess.storage.dao

import androidx.room.*
import com.test.fasterpay.vo.Wallet
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface WalletDao {
    @Query("select * from Wallet where ownerId = :walletId")
    fun getWalletByUserId(walletId: String): Observable<Wallet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWallet(transaction: Wallet): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWalletList(list: List<Wallet>): Completable
}