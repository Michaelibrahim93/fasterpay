package com.test.fasterpay.ui.fragments.transactiondetails.model

import com.test.fasterpay.vo.MoneyTransaction
import com.test.fasterpay.vo.Wallet

class UiTransactionDetails (
    val transaction: MoneyTransaction,
    val wallet: Wallet,
    val amountInWalletCurrency: Double,
    val canBuy: Boolean
)
