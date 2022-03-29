package com.eneskayiklik.eventverse.feature_profile.data.state

import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.feature_auth.data.model.User

data class ProfileState(
    val userId: String = "",
    val user: User? = null,
    val isLoading: Boolean = false
) {
    val isSelf = userId == Settings.currentUser.userId
}
