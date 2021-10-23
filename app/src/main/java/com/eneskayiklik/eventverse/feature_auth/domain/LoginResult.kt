package com.eneskayiklik.eventverse.feature_auth.domain

data class LoginResult(
    val emailError: String = "",
    val passwordError: String = "",
    val isSuccess: Boolean = false
)
