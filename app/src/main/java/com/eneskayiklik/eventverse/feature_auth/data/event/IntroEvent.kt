package com.eneskayiklik.eventverse.feature_auth.data.event

sealed class IntroEvent {
    object OnRegister : IntroEvent()
    object OnLogin : IntroEvent()
}