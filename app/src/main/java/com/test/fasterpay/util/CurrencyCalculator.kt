package com.test.fasterpay.util

import com.test.fasterpay.vo.Currency

object CurrencyCalculator {
    fun toCurrencyValue(amount: Double, fromCurrency: Currency, toCurrency: Currency): Double {
        return (amount * fromCurrency.pointsValue) / toCurrency.pointsValue
    }
}