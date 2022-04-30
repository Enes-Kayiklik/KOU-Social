package com.eneskayiklik.eventverse.data.state.profile

import com.eneskayiklik.eventverse.core.data.model.ErrorState
import com.eneskayiklik.eventverse.data.model.auth.Department
import com.eneskayiklik.eventverse.data.model.auth.SocialAccount
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.util.TextFieldState
import com.eneskayiklik.eventverse.data.model.auth.User
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

data class SettingsState(
    val user: User = Settings.currentUser.toUser(),
    val fullName: TextFieldState = TextFieldState(user.fullName),
    val profilePic: String = user.profilePic,
    val cropperImage: String = "",
    val birthDate: LocalDate? = user.birthDate?.toDate()?.toInstant()
        ?.atZone(ZoneId.systemDefault())?.toLocalDate(),
    val department: Department = user.department,
    val instagram: TextFieldState = TextFieldState(user.socialAccounts.instagram),
    val github: TextFieldState = TextFieldState(user.socialAccounts.github),
    val linkedIn: TextFieldState = TextFieldState(user.socialAccounts.linkedIn),
    val twitter: TextFieldState = TextFieldState(user.socialAccounts.twitter),
    val isLoading: Boolean = false,
    val isDepartmentPopupVisible: Boolean = false,
    val isProfilePicUpdated: Boolean = false,
    val errorDialogState: ErrorState? = null
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
        ) else null,
        socialLogin = user.socialLogin,
        verified = user.verified
    )
}
