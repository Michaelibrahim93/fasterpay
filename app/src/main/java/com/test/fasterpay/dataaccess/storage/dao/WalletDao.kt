package com.test.fasterpay.dataaccess.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.fasterpay.vo.Wallet

@Dao
interface WalletDao {
    @Query("select * from Wallet where ownerId = :userId")
    fun getWalletByUserId(userId: Long): LiveData<Wallet>

    @Query("select * from Wallet where id = :id")
    suspend fun getWalletSync(id: Long): Wallet

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWallet(wallet: Wallet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWalletList(list: List<Wallet>)
}