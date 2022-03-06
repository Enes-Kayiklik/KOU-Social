package com.eneskayiklik.eventverse.feature_auth.data.event

import com.eneskayiklik.eventverse.feature_auth.domain.model.Department

sealed class SignupEvent {
    data class OnPassword(val password: String) : SignupEvent()
    data class OnEmail(val email: String) : SignupEvent()
    data class OnFullName(val fullName: String) : SignupEvent()
    object OnTogglePassword : SignupEvent()
    object OnRegister : SignupEvent()
    object OnLogin : SignupEvent()
    object ShowFacultyPopup : SignupEvent()
    data class SelectDepartment(val department: Department) : SignupEvent()
    object OnGoogle : SignupEvent()
}