package com.test.fasterpay.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.fasterpay.vo.Wallet
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface WalletDao {
    @Query("select * from Wallet where ownerId = :userId")
    fun getWalletByUserId(userId: Long): LiveData<Wallet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWallet(wallet: Wallet): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWalletList(list: List<Wallet>): Completable
}