package com.eneskayiklik.eventverse.data.state.auth

import com.eneskayiklik.eventverse.core.model.ErrorState

data class IntroState(
    val isGoogleLoading: Boolean = false,
    val errorDialogState: ErrorState? = null
)
