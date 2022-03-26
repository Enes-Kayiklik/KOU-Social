package com.eneskayiklik.eventverse.feature_share.data.state

import com.eneskayiklik.eventverse.core.util.TextFieldState

data class ShareState(
    val isLoading: Boolean = false,
    val titleState: TextFieldState = TextFieldState(),
    val bodyState: TextFieldState = TextFieldState()
)
