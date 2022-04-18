package com.eneskayiklik.eventverse.data.event.auth

sealed class IntroEvent {
    object OnRegister : IntroEvent()
    object OnLogin : IntroEvent()
}