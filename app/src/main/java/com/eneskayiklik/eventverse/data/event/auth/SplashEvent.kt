package com.eneskayiklik.eventverse.data.event.auth

sealed class SplashEvent {
    data class OnNavigate(val route: String) : SplashEvent()
    object ShowVerifyPopup : SplashEvent()
}