package com.test.fasterpay.util

import android.util.Patterns
import java.util.*
import java.util.regex.Pattern

object AuthValidator {
    @JvmStatic
    fun isValidEmail(value: String?): Boolean {
        return !value.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

    fun isValidPhoneNumber(value: String?): Boolean {
        return !value.isNullOrBlank() && value.length >= 8
    }

    fun isValidPassword(value: String?): Boolean {
        return !value.isNullOrBlank()
                && value.length >= 8
                && value.toUpperCase(Locale.ROOT) != value
                && value.toLowerCase(Locale.ROOT) != value
                && Pattern.compile(".*\\d.*").matcher(value).matches()
    }
}