package com.test.fasterpay.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PastTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val walletId: Long,
    val day: String,
    currency: Currency,
    fee: Double,
    totalAmount: Double,
    isRefund: Boolean,
    description: String,
    warning: String?,
    source: Source
) : MoneyTransaction(currency, fee, totalAmount, isRefund, description, warning, source)