package com.test.fasterpay.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class MoneyTransaction(
    val transactionId: Long,
    val currency: Currency,
    val fee: Double, //currency amount for not changing
    val totalAmount: Double,
    val isRefund: Boolean,
    val description: String,
    val warning: String? = null,
    val source: Source
) : Parcelable