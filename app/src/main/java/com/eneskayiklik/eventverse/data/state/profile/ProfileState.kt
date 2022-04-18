package com.eneskayiklik.eventverse.data.state.profile

import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.data.model.auth.User

data class ProfileState(
    val userId: String = "",
    val user: User? = null,
    val isLoading: Boolean = false
) {
    val isSelf = userId == Settings.currentUser.userId
}
