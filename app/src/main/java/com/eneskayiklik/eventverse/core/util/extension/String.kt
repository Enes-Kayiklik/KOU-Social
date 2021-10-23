package com.eneskayiklik.eventverse.core.util.extension

import android.util.Patterns

fun String.isValidEmail(): Boolean =
    isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean =
    length >= 6