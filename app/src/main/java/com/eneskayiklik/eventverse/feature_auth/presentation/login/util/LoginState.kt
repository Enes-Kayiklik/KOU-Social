package com.eneskayiklik.eventverse.feature_auth.presentation.login.util

sealed class LoginState {
    data class OnPassword(val password: String) : LoginState()
    data class OnEmail(val email: String) : LoginState()
    data class OnFullname(val fullname: String) : LoginState()
    object OnTogglePassword : LoginState()
    object OnNavigateRegister : LoginState()
    object OnRegister : LoginState()
    object OnLogin : LoginState()
    object OnGoogle : LoginState()
}
