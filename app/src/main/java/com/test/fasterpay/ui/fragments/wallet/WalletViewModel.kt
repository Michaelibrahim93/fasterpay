package com.test.fasterpay.ui.fragments.wallet

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import com.test.basemodule.base.model.resource.Resource
import com.test.fasterpay.dataaccess.repository.TransactionsRepo
import com.test.fasterpay.dataaccess.repository.UserRepo
import com.test.fasterpay.dataaccess.repository.WalletRepo
import com.test.fasterpay.ui.fragments.base.FasterPayBaseViewModel
import com.test.fasterpay.ui.fragments.wallet.adapter.UiTransaction
import com.test.fasterpay.vo.PastTransaction
import com.test.fasterpay.vo.Wallet

class WalletViewModel @ViewModelInject constructor(
    application: Application,
    private val walletRepo: WalletRepo,
    private val userRepo: UserRepo,
    private val transactionsRepo: TransactionsRepo
) : FasterPayBaseViewModel(application) {

    val ldWallet = MediatorLiveData<Wallet>()
    val ldTransactions = MediatorLiveData<List<UiTransaction>>()

    init {
        initWalletLiveData()
        initTransactionsLiveData()
    }

    private fun initWalletLiveData() {
        val userLiveData = userRepo.getLoggedInUser()
        ldWallet.addSource(userLiveData) { user ->
            ldWallet.removeSource(userLiveData)
            ldWallet.addSource(walletRepo.walletLiveData(user.data?.id?:0)) {
                ldWallet.postValue(it)
            }
        }
    }

    private fun initTransactionsLiveData() {
        ldTransactions.addSource(ldWallet) { wallet ->
            ldTransactions.removeSource(ldWallet)
            ldTransactions.addSource(transactionsRepo.loadTransactions(wallet.id)){ listOfTransactionsRes ->
                ldTransactions.postValue(mapTransActions(listOfTransactionsRes))
            }
        }
    }

    private fun mapTransActions(resource: Resource<List<PastTransaction>>): List<UiTransaction> {
        var lastDate = ""
        return resource.data?.map {
            val itr = UiTransaction(it, lastDate != it.day)
            lastDate = it.day
            itr
        }?:ArrayList()
    }
}