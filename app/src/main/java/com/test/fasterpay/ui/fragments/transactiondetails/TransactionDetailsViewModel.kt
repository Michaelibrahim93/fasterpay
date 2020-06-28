package com.test.fasterpay.ui.fragments.transactiondetails

import android.app.Application
import android.os.Bundle
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import com.test.fasterpay.dataaccess.repository.TransactionsRepo
import com.test.fasterpay.dataaccess.repository.UserRepo
import com.test.fasterpay.dataaccess.repository.WalletRepo
import com.test.fasterpay.ui.fragments.base.FasterPayBaseViewModel
import com.test.fasterpay.ui.fragments.transactiondetails.model.UiTransactionDetails
import com.test.fasterpay.util.CurrencyCalculator
import com.test.fasterpay.vo.MoneyTransaction
import com.test.fasterpay.vo.Wallet

class TransactionDetailsViewModel @ViewModelInject constructor(
    application: Application,
    private val walletRepo: WalletRepo,
    private val userRepo: UserRepo,
    private val transactionsRepo: TransactionsRepo
) : FasterPayBaseViewModel(application) {
    private lateinit var moneyTransaction: MoneyTransaction
    val ldUiTransactionDetails = MediatorLiveData<UiTransactionDetails>()



    private fun initMediatorLiveData() {
        val ldUser = userRepo.getLoggedInUser()
        ldUiTransactionDetails.addSource(ldUser) {
            ldUiTransactionDetails.removeSource(ldUser)
            ldUiTransactionDetails.addSource(walletRepo.walletLiveData(ldUser.value?.data?.id?:0)){
                ldUiTransactionDetails.value = createUiTransaction(it, moneyTransaction)
            }
        }
    }

    private fun createUiTransaction(wallet: Wallet, transition: MoneyTransaction): UiTransactionDetails? {
        val amount = CurrencyCalculator.toCurrencyValue(transition.totalAmount, transition.currency, wallet.currency)
        return UiTransactionDetails(moneyTransaction, wallet, amount,amount <= wallet.balance)
    }

    fun pay() {
        val observable = transactionsRepo.addTransaction(moneyTransaction
            , ldUiTransactionDetails.value!!.wallet.id)
            .doOnNext { addAction(TransactionDetailsFragment.ACTION_SHOW_CONGRATS) }
            .doOnError { handleNetworkError(it) }

        runObservable(observable)
    }

    fun extractData(arguments: Bundle?) {
        if (arguments == null) return
        moneyTransaction = arguments.getParcelable(TransactionDetailsFragment.KEY_TRANSACTION)!!
        initMediatorLiveData()
    }
}