package com.eneskayiklik.eventverse.feature_auth.presentation.login.util

data class LoginData(
    var email: String = "",
    var password: String = "",
    var isPasswordShowing: Boolean = false
)
