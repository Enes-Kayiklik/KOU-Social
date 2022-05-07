package com.eneskayiklik.eventverse.data.event.auth

sealed class SignupEvent {
    data class OnPassword(val password: String) : SignupEvent()
    data class OnEmail(val email: String) : SignupEvent()
    data class OnFullName(val fullName: String) : SignupEvent()
    object OnTogglePassword : SignupEvent()
    object OnRegister : SignupEvent()
    object OnLogin : SignupEvent()
    object ShowFacultyPopup : SignupEvent()
}