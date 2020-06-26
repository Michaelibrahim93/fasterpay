package com.test.basemodule.utils

import java.util.*

object CollectionsUtils {
    @JvmStatic
    fun <T> toList(arr: Array<T>): MutableList<T> {
        val list = ArrayList<T>(arr.size)
        for (o in arr) list.add(o)
        return list
    }
}