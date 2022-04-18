package com.eneskayiklik.eventverse.data.model.profile

data class UpdatePasswordResult(
    val emailError: String = "",
    val passwordError: String = "",
    val otherError: String = "",
    val isSuccess: Boolean = true
)
