package com.test.fasterpay.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wallet(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val currency: Currency,
    var balance: Double,
    val ownerId: Long
)