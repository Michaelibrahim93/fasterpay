package com.test.fasterpay.util

import com.google.gson.Gson
import com.test.fasterpay.vo.MoneyTransaction
import javax.inject.Inject

class TransactionDecoder @Inject constructor(){
    fun decodeTransaction(jsonObject: String): MoneyTransaction {
        return Gson().fromJson(jsonObject, MoneyTransaction::class.java)
    }
}