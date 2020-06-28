package com.test.fasterpay.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source (
    val name: String,
    val logoUrl: String
) : Parcelable