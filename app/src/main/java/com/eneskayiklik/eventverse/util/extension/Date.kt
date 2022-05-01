package com.eneskayiklik.eventverse.util.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(pattern: String? = null): String = SimpleDateFormat(pattern ?: "dd MMM HH:mm", Locale.getDefault()).format(this)