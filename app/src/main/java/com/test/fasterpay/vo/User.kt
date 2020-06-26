package com.test.fasterpay.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String = "",
    var imageUrl: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var isLoggedUser: Boolean = false
)