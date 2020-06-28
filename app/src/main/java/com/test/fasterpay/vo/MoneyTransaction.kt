package com.test.fasterpay.vo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
open class MoneyTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val transactionId: Long,
    val currency: Currency,
    val fee: Double,
    val totalAmount: Double,
    val isRefund: Boolean,
    val description: String,
    val warning: String? = null,
    val source: Source
) : Parcelable