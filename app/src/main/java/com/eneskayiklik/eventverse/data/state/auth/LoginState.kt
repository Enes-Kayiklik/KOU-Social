package com.eneskayiklik.eventverse.data.state.auth

import com.eneskayiklik.eventverse.core.model.ErrorState
import com.eneskayiklik.eventverse.util.TextFieldState

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val dialogState: ErrorState? = null
)
