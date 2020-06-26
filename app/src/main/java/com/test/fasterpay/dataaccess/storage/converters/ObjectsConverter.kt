package com.test.fasterpay.dataaccess.storage.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.test.fasterpay.vo.Currency
import com.test.fasterpay.vo.Source

class ObjectsConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String {
        return Gson().toJson(currency)
    }

    @TypeConverter
    fun toCurrency(jsonString: String): Currency {
        return Gson().fromJson(jsonString, Currency::class.java)
    }

    @TypeConverter
    fun fromSource(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(jsonString: String): Source {
        return Gson().fromJson(jsonString, Source::class.java)
    }
}