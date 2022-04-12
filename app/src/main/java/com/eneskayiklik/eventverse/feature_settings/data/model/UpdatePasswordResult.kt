package com.eneskayiklik.eventverse.feature_settings.data.model

data class UpdatePasswordResult(
    val emailError: String = "",
    val passwordError: String = "",
    val otherError: String = "",
    val isSuccess: Boolean = true
)
