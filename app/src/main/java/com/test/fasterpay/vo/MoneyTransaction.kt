package com.test.fasterpay.vo


open class MoneyTransaction(
    val currency: Currency,
    val fee: Double, //currency amount for not changing
    val totalAmount: Double,
    val isRefund: Boolean,
    val description: String,
    val warning: String? = null,
    val source: Source
)