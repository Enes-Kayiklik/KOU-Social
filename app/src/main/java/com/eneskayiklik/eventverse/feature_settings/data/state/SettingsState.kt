package com.eneskayiklik.eventverse.feature_settings.data.state

import com.eneskayiklik.eventverse.feature_auth.data.model.User

data class SettingsState(
    val user: User? = null,
    val isLoading: Boolean = false
)
