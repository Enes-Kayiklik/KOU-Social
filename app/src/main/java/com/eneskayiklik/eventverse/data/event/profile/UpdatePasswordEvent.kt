package com.eneskayiklik.eventverse.data.event.profile


sealed class UpdatePasswordEvent {
    data class OnPassword(val password: String) : UpdatePasswordEvent()
    data class OnNewPassword(val password: String) : UpdatePasswordEvent()
    data class OnEmail(val email: String) : UpdatePasswordEvent()
    object OnTogglePassword : UpdatePasswordEvent()
    object OnToggleNewPassword : UpdatePasswordEvent()
    object OnReset : UpdatePasswordEvent()
}