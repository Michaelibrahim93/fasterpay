package com.test.fasterpay.vo

import android.annotation.SuppressLint
import androidx.room.Entity
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ParcelCreator")
@Entity
class PastTransaction(
    val walletId: Long,
    val day: String,
    id: Long = 0,
    transactionId: Long,
    currency: Currency,
    fee: Double,
    totalAmount: Double,
    isRefund: Boolean,
    description: String,
    warning: String?,
    source: Source
) : MoneyTransaction(id, transactionId, currency, fee, totalAmount, isRefund, description, warning, source) {
    companion object {
        fun generateTransaction(transaction: MoneyTransaction, walletId: Long,
                                databaseDateFormat: SimpleDateFormat): PastTransaction {
            return PastTransaction(
                walletId = walletId,
                day = databaseDateFormat.format(Date()),
                transactionId = transaction.transactionId,
                currency = transaction.currency,
                fee = transaction.fee,
                totalAmount = transaction.totalAmount,
                isRefund = transaction.isRefund,
                description = transaction.description,
                warning = transaction.warning,
                source = transaction.source
            )
        }

    }
}