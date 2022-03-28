package com.eneskayiklik.eventverse.feature_profile.data.state

import com.eneskayiklik.eventverse.core.util.Settings

data class ProfileState(
    val userId: String = ""
) {
    val isSelf = userId == Settings.currentUser.userId
}
