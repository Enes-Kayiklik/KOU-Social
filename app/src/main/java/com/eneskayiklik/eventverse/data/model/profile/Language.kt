package com.eneskayiklik.eventverse.data.model.profile

enum class Language(val value: String = "", val key: String = "") {
    ENGLISH("English", "en"),
    TURKISH("Türkçe", "tr"),
    UNDEFINED
}

fun Int.toLanguage() = when (this) {
    0 -> Language.ENGLISH
    1 -> Language.TURKISH
    else -> Language.UNDEFINED
}

val selectableLanguages = listOf(
    Language.ENGLISH,
    Language.TURKISH
)