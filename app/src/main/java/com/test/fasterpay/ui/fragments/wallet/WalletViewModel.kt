package com.test.fasterpay.ui.fragments.wallet

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import com.test.fasterpay.dataaccess.repository.UserRepo
import com.test.fasterpay.dataaccess.repository.WalletRepo
import com.test.fasterpay.ui.fragments.base.FasterPayBaseViewModel
import com.test.fasterpay.vo.Wallet

class WalletViewModel @ViewModelInject constructor(
    application: Application,
    private val walletRepo: WalletRepo,
    private val userRepo: UserRepo
) : FasterPayBaseViewModel(application) {

    val ldWallet = MediatorLiveData<Wallet>()

    init {
        ldWallet.addSource(userRepo.getLoggedInUser()) { user ->
            ldWallet.addSource(walletRepo.walletLiveData(user.data?.id?:0)) {
                ldWallet.postValue(it)
            }
        }
    }
}