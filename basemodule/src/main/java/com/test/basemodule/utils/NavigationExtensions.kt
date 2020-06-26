package com.test.basemodule.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openBrowser(url: String) {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    this.startActivity(i)
}