package com.eneskayiklik.eventverse.data.state.profile

import com.eneskayiklik.eventverse.core.data.model.ErrorState
import com.eneskayiklik.eventverse.util.TextFieldState

data class UpdatePasswordState(
    val isLoading: Boolean = false,
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val newPassword: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isNewPasswordVisible: Boolean = false,
    val errorDialogState: ErrorState? = null
)
