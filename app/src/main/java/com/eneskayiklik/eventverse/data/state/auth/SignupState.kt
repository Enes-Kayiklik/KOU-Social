package com.eneskayiklik.eventverse.data.state.auth

import com.eneskayiklik.eventverse.core.data.model.ErrorState
import com.eneskayiklik.eventverse.util.TextFieldState

data class SignupState(
    val email: TextFieldState = TextFieldState(),
    val fullName: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isFacultyPopupActive: Boolean = false,
    val dialogState: ErrorState? = null
)
