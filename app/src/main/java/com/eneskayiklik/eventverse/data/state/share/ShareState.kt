package com.eneskayiklik.eventverse.data.state.share

import com.eneskayiklik.eventverse.util.TextFieldState

data class ShareState(
    val isLoading: Boolean = false,
    val titleState: TextFieldState = TextFieldState(),
    val bodyState: TextFieldState = TextFieldState()
)
