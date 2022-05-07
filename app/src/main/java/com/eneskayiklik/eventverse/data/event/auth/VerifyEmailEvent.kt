package com.eneskayiklik.eventverse.data.event.auth

sealed class VerifyEmailEvent {
    object OnContinue : VerifyEmailEvent()
    object OnResend : VerifyEmailEvent()
}