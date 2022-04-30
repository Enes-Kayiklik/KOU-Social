package com.eneskayiklik.eventverse.data.event.profile


sealed class VerifyAccountEvent {
    data class OnVerifyCode(val text: String): VerifyAccountEvent()
    object OnVerifyAccount: VerifyAccountEvent()
}