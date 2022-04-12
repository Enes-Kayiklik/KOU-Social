package com.eneskayiklik.eventverse.feature_settings.data.state

import com.eneskayiklik.eventverse.core.util.TextFieldState

data class UpdatePasswordState(
    val isLoading: Boolean = false,
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val newPassword: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isNewPasswordVisible: Boolean = false,
)
