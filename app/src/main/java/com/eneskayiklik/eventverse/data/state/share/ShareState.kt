package com.eneskayiklik.eventverse.data.state.share

import com.eneskayiklik.eventverse.util.TextFieldState

data class ShareState(
    val isLoading: Boolean = false,
    val selectedImage: String = "",
    val postImage: String = "",
    val bodyState: TextFieldState = TextFieldState()
)
