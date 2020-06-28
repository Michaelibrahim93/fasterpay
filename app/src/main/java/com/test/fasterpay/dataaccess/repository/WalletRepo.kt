package com.test.fasterpay.dataaccess.repository

import androidx.lifecycle.LiveData
import com.test.fasterpay.dataaccess.storage.dao.WalletDao
import com.test.fasterpay.vo.Wallet
import io.reactivex.Observable
import javax.inject.Inject

class WalletRepo @Inject constructor(private val walletDao: WalletDao) {
    fun walletLiveData(userId: Long): LiveData<Wallet> {
        return walletDao.getWalletByUserId(userId)
    }
}