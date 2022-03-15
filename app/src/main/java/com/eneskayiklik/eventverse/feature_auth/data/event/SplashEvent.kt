package com.eneskayiklik.eventverse.feature_auth.data.event

sealed class SplashEvent {
    data class OnNavigate(val route: String) : SplashEvent()
    object ShowVerifyPopup : SplashEvent()
}