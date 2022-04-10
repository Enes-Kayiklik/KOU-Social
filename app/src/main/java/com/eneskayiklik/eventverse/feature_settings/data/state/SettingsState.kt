package com.eneskayiklik.eventverse.feature_settings.data.state

import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.feature_auth.data.model.User
import java.time.format.DateTimeFormatter

data class SettingsState(
    val user: User = Settings.currentUser.toUser(),
    val isLoading: Boolean = false,
    val isDepartmentPopupVisible: Boolean = false
) {
    val formattedBirthdate: String = if (user.birtDate != null) user.birtDate.format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    ) else ""
}
