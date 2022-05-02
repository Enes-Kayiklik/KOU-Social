package com.eneskayiklik.eventverse.data.model.profile

import androidx.annotation.StringRes
import com.eneskayiklik.eventverse.R

enum class DarkMode(@StringRes val title: Int) {
    LIGHT(R.string.light_mode),
    DARK(R.string.night_mode),
    FOLLOW_SYSTEM(R.string.follow_system)
}

fun Int.toModeEnum() = when (this) {
    0 -> DarkMode.LIGHT
    1 -> DarkMode.DARK
    else -> DarkMode.FOLLOW_SYSTEM
}

val selectableModes = listOf(
    DarkMode.LIGHT,
    DarkMode.DARK,
    DarkMode.FOLLOW_SYSTEM
)