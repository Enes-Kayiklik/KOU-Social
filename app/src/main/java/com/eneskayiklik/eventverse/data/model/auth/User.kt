package com.eneskayiklik.eventverse.data.model.auth

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
    val socialLogin: Boolean = false,
    val verified: Boolean = false
) {
    fun toAppUser() = AppUser(
        userId = userId,
        department = department,
        email = email,
        fullName = fullName,
        birthDate = birthDate?.toDate(),
        profilePic = profilePic,
        socialAccounts = socialAccounts,
        socialLogin = socialLogin,
        verified = verified
    )

    fun toPostUser() = PostUser(
        userId = userId,
        department = department.departmentName,
        email = email,
        fullName = fullName,
        profilePic = profilePic,
        verified = verified
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
    val verified: Boolean = false,
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
        socialAccounts = socialAccounts,
        verified = verified
    )
}

data class PostUser(
    val userId: String = "",
    val email: String = "",
    val fullName: String = "",
    val profilePic: String = "",
    val department: String = "",
    val verified: Boolean = false
)

data class SocialAccount(
    val instagram: String = "",
    val twitter: String = "",
    val linkedIn: String = "",
    val github: String = ""
)

data class Faculty(
    val departments: List<Department> = emptyList(),
    val name: String = ""
)

data class Department(
    val departmentName: String = "",
    val departmentUrl: String = "",
    val baseUrl: String = ""
)