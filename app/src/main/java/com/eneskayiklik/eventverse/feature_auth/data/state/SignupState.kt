package com.eneskayiklik.eventverse.feature_auth.data.state

import com.eneskayiklik.eventverse.core.util.TextFieldState
import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.eneskayiklik.eventverse.feature_auth.domain.model.Faculty

data class SignupState(
    val email: TextFieldState = TextFieldState(),
    val fullName: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val faculties: List<Faculty> = emptyList(),
    val isFacultiesLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isFacultyPopupActive: Boolean = false,
    val selectedDepartment: Department = Department()
)
