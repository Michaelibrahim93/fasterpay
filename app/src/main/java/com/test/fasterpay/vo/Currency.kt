package com.test.fasterpay.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Currency(
    val pointsValue: Double,
    val symbol: String
) : Parcelable