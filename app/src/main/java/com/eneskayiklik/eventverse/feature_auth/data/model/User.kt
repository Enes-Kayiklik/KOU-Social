package com.eneskayiklik.eventverse.feature_auth.data.model

import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.eneskayiklik.eventverse.feature_auth.domain.model.SocialAccount
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.*

data class User(
    @DocumentId
    val userId: String = "",
    val department: Department = Department(),
    val socialAccounts: SocialAccount = SocialAccount(),
    val email: String = "",
    val fullName: String = "",
    val birthDate: Timestamp? = null,
    val profilePic: String = "",
    val socialLogin: Boolean = false
) {
    fun toAppUser() = AppUser(
        userId = userId,
        department = department,
        email = email,
        fullName = fullName,
        birthDate = birthDate?.toDate(),
        profilePic = profilePic,
        socialAccounts = socialAccounts,
        socialLogin = socialLogin
    )
}

data class AppUser(
    val userId: String = "",
    val department: Department = Department(),
    val email: String = "",
    val fullName: String = "",
    val profilePic: String = "",
    val birthDate: Date? = null,
    val socialLogin: Boolean = false,
    val socialAccounts: SocialAccount = SocialAccount()
) {
    fun toPostUser() = PostUser(
        userId = userId,
        email = email,
        fullName = fullName,
        profilePic = profilePic,
        department = department.departmentName
    )

    fun toUser() = User(
        userId = userId,
        email = email,
        fullName = fullName,
        birthDate = if (birthDate != null) Timestamp(birthDate) else null,
        profilePic = profilePic,
        department = department,
        socialLogin = socialLogin,
        socialAccounts = socialAccounts
    )
}

data class PostUser(
    val userId: String = "",
    val email: String = "",
    val fullName: String = "",
    val profilePic: String = "",
    val department: String = ""
)
