package com.test.fasterpay.dataaccess.fakenetwork.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CredentialsForm (
    @PrimaryKey
    val email: String
    , val password: String)