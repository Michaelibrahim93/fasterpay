package com.test.fasterpay.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MoneyTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val currency: Currency,
    val fee: Double, //currency amount for not changing
    val totalAmount: Double,
    val isRefund: Boolean,
    val valueInCurrency: Double,
    val description: String,
    val warning: String? = null,
    val source: Source,
    val walletId: Long
)