package com.eneskayiklik.eventverse.feature_auth.data.model

import com.eneskayiklik.eventverse.core.model.ErrorState

data class IntroState(
    val isGoogleLoading: Boolean = false,
    val errorDialogState: ErrorState? = null
)
