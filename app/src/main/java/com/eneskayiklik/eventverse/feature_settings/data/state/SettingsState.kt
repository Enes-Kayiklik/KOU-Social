package com.eneskayiklik.eventverse.feature_settings.data.state

import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.core.util.TextFieldState
import com.eneskayiklik.eventverse.feature_auth.data.model.User
import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.eneskayiklik.eventverse.feature_auth.domain.model.SocialAccount
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

data class SettingsState(
    val user: User = Settings.currentUser.toUser(),
    val fullName: TextFieldState = TextFieldState(user.fullName),
    val profilePic: String = user.profilePic,
    val birthDate: LocalDate? = user.birthDate?.toDate()?.toInstant()
        ?.atZone(ZoneId.systemDefault())?.toLocalDate(),
    val department: Department = user.department,
    val instagram: TextFieldState = TextFieldState(user.socialAccounts.instagram),
    val github: TextFieldState = TextFieldState(user.socialAccounts.github),
    val linkedIn: TextFieldState = TextFieldState(user.socialAccounts.linkedIn),
    val twitter: TextFieldState = TextFieldState(user.socialAccounts.twitter),
    val isLoading: Boolean = false,
    val isDepartmentPopupVisible: Boolean = false,
    val isProfilePicUpdated: Boolean = false
) {
    val formattedBirthdate: String = if (birthDate != null) birthDate.format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    ) else ""

    fun getFieldsAsUser() = User(
        userId = user.userId,
        department = department,
        socialAccounts = SocialAccount(
            instagram = instagram.text,
            github = github.text,
            linkedIn = linkedIn.text,
            twitter = twitter.text
        ), fullName = fullName.text,
        profilePic = profilePic,
        email = user.email,
        birthDate = if (birthDate != null) Timestamp(
            Date.from(
                birthDate
                    .atStartOfDay(
                        ZoneId.of("Turkey")
                    ).toInstant()
            )
        ) else null
    )
}
