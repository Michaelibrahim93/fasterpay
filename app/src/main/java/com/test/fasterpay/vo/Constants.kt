package com.test.fasterpay.vo

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    fun databaseDateFormat(): SimpleDateFormat {
        return SimpleDateFormat(DATE_FORMAT_DATABASE, DATE_LOCALE_DATABASE)
    }

    private const val DATE_FORMAT_DATABASE = "yyyy MM dd"
    @JvmStatic
    private val DATE_LOCALE_DATABASE: Locale = Locale.ENGLISH
}