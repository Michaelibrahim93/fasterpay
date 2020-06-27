package com.test.fasterpay.util

import android.util.Patterns

object AuthValidator {
    @JvmStatic
    fun isValidEmail(string: String?): Boolean {
        return !string.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(string).matches()
    }
}