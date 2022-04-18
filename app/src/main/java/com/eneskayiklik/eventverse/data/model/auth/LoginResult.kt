package com.eneskayiklik.eventverse.data.model.auth

data class LoginResult(
    val emailError: String = "",
    val passwordError: String = "",
    val isSuccess: Boolean = false
)
