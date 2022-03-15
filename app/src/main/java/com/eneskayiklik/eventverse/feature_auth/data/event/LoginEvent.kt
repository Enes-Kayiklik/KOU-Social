package com.eneskayiklik.eventverse.feature_auth.data.event

sealed class LoginEvent {
    data class OnNavigate(val route: String) : LoginEvent()
    object ShowVerifyPopup : LoginEvent()
    object ShowErrorPopup : LoginEvent()
    object PasswordError : LoginEvent()
    object UserError : LoginEvent()
    data class OnPassword(val password: String) : LoginEvent()
    data class OnEmail(val email: String) : LoginEvent()
    object OnTogglePassword : LoginEvent()
    object OnLogin : LoginEvent()
}