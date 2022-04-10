package com.eneskayiklik.eventverse.feature_auth.data.model

import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.eneskayiklik.eventverse.feature_auth.domain.model.SocialAccount
import com.google.firebase.firestore.DocumentId
import java.time.LocalDate

data class User(
    @DocumentId
    val userId: String = "",
    val department: Department = Department(),
    val socialAccounts: SocialAccount = SocialAccount(),
    val email: String = "",
    val fullName: String = "",
    val birtDate: LocalDate? = null,
    val profilePic: String = ""
) {
    fun toAppUser() = AppUser(
        userId = userId,
        department = department,
        email = email,
        fullName = fullName,
        birtDate = birtDate,
        profilePic = profilePic,
        socialAccounts = socialAccounts
    )
}

data class AppUser(
    val userId: String = "",
    val department: Department = Department(),
    val email: String = "",
    val fullName: String = "",
    val profilePic: String = "",
    val birtDate: LocalDate? = null,
    val socialAccounts: SocialAccount = SocialAccount()
) {
    fun toPostUser() = PostUser(
        userId = userId,
        email = email,
        fullName = fullName,
        profilePic = profilePic
    )

    fun toUser() = User(
        userId = userId,
        email = email,
        fullName = fullName,
        birtDate = birtDate,
        profilePic = profilePic,
        department = department,
        socialAccounts = socialAccounts
    )
}

data class PostUser(
    val userId: String = "",
    val email: String = "",
    val fullName: String = "",
    val profilePic: String = ""
)
