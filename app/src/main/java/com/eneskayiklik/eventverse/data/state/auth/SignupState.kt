package com.eneskayiklik.eventverse.data.state.auth

import com.eneskayiklik.eventverse.core.data.model.ErrorState
import com.eneskayiklik.eventverse.data.model.auth.Department
import com.eneskayiklik.eventverse.data.model.auth.Faculty
import com.eneskayiklik.eventverse.util.TextFieldState

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
