package com.eneskayiklik.eventverse.util.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(): String = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault()).format(this)