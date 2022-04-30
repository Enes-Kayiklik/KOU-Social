package com.eneskayiklik.eventverse.data.state.profile

import com.eneskayiklik.eventverse.util.TextFieldState

data class VerifyAccountState(
    val isLoading: Boolean = false,
    val verifyCode: TextFieldState = TextFieldState(),
) {
    val canVerify = verifyCode.text.length == 6
}
