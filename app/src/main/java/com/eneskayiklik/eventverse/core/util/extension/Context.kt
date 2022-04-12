package com.eneskayiklik.eventverse.core.util.extension

import android.content.Context
import android.content.Intent
import com.eneskayiklik.eventverse.BuildConfig
import java.lang.Exception


fun Context.shareAppLink() {
    try {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "My application name")
            val appLink =
                "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
            putExtra(Intent.EXTRA_TEXT, appLink)
            startActivity(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}