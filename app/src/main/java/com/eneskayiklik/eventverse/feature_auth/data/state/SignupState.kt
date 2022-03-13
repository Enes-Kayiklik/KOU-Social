package com.eneskayiklik.eventverse.feature_auth.data.state

import com.eneskayiklik.eventverse.core.model.ErrorState
import com.eneskayiklik.eventverse.core.util.TextFieldState
import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.eneskayiklik.eventverse.feature_auth.domain.model.Faculty

data class SignupState(
    val email: TextFieldState = TextFieldState(),
    val fullName: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val faculties: List<Faculty> = emptyList(),
    val isFacultiesLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isFacultyPopupActive: Boolean = false,
    val selectedDepartment: Department = Department(),
    val dialogState: ErrorState? = null
)
