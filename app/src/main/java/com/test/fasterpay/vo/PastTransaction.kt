package com.test.fasterpay.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
class PastTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val walletId: Long,
    val day: String,
    transactionId: Long,
    currency: Currency,
    fee: Double,
    totalAmount: Double,
    isRefund: Boolean,
    description: String,
    warning: String?,
    source: Source
) : MoneyTransaction(transactionId, currency, fee, totalAmount, isRefund, description, warning, source) {
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