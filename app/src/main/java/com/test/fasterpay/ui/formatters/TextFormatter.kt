@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.test.fasterpay.ui.formatters

import android.view.View
import androidx.core.content.ContextCompat
import com.test.fasterpay.R
import com.test.fasterpay.ui.fragments.transactiondetails.model.UiTransactionDetails
import com.test.fasterpay.vo.MoneyTransaction
import com.test.fasterpay.vo.PastTransaction
import com.test.fasterpay.vo.User
import com.test.fasterpay.vo.Wallet
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

object TextFormatter {
    @JvmStatic
    fun formatDate(simpleDateFormat: SimpleDateFormat, date: Date?): String {
        return if (date == null) ""
        else simpleDateFormat.format(date)
    }

    @JvmStatic
    fun formatDate(fromFormat: SimpleDateFormat, toFormat: SimpleDateFormat, dateStr: String?): String {
        return if (dateStr == null) ""
        else toFormat.format(fromFormat.parse(dateStr))
    }

    @JvmStatic
    fun formatPaymentText(view: View, transaction: PastTransaction): String {
        return if (transaction.isRefund)
            view.context!!.getString(R.string.returned_from, transaction.source.name)
        else
            view.context!!.getString(R.string.paid_to, transaction.source.name)
    }

    @JvmStatic
    fun formatPaymentValue(transaction: MoneyTransaction): String {
        val stringBuilder = StringBuilder()
        if (!transaction.isRefund)
            stringBuilder.append("- ")
        stringBuilder.append("${transaction.currency.symbol} ")
        stringBuilder.append(String.format("%.2f", transaction.totalAmount))
        if (!transaction.warning.isNullOrEmpty())
            stringBuilder.append("\n${transaction.warning}")
        return stringBuilder.toString()
    }

    @JvmStatic
    fun formatPaymentTextColor(view: View, transaction: PastTransaction): Int {
        return ContextCompat.getColor(
            view.context,
            if (!transaction.warning.isNullOrEmpty()) R.color.app_red
            else if (transaction.isRefund) R.color.app_green
            else R.color.black
        )
    }

    @JvmStatic
    fun formatWalletBalance(wallet: Wallet?): String {
        return if (wallet == null) ""
        else "${wallet.currency.symbol}  ${String.format("%.2f", wallet.balance)}"
    }

    @JvmStatic
    fun formatUserName(user: User?): String {
        return if (user == null) ""
        else "${user.firstName} ${user.lastName}"
    }

    @JvmStatic
    fun formatTotalAmount(uiTransactionDetails: UiTransactionDetails?): String {
        if (uiTransactionDetails == null) return ""
        return "${uiTransactionDetails.transaction.currency.symbol}  ${String.format("%.2f", uiTransactionDetails.transaction.totalAmount)}"
    }

    @JvmStatic
    fun formatSubtotal(uiTransactionDetails: UiTransactionDetails?): String {
        if (uiTransactionDetails == null) return ""
        val amount = uiTransactionDetails.transaction.totalAmount - uiTransactionDetails.transaction.fee
        return "${uiTransactionDetails.transaction.currency.symbol}  ${String.format("%.2f", amount)}"
    }

    @JvmStatic
    fun formatFee(uiTransactionDetails: UiTransactionDetails?): String {
        if (uiTransactionDetails == null) return ""
        return "${uiTransactionDetails.transaction.currency.symbol}  ${String.format("%.2f", uiTransactionDetails.transaction.fee)}"
    }

    @JvmStatic
    fun formatPayText(view: View, uiTransactionDetails: UiTransactionDetails?): String {
        if (uiTransactionDetails == null) return ""
        return "${view.context.getString(R.string.pay)} ${uiTransactionDetails.wallet.currency.symbol}  ${String.format("%.2f", uiTransactionDetails.amountInWalletCurrency)}"
    }
}