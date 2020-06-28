package com.test.fasterpay.ui.fragments.wallet.adapter

import com.test.fasterpay.vo.PastTransaction

data class UiTransaction(val transaction: PastTransaction) {
    var isFirst = false
}