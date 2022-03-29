package com.eneskayiklik.eventverse.feature_auth.data.model

import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val userId: String = "",
    val department: Department = Department(),
    val email: String = "",
    val fullName: String = "",
    val profilePic: String = ""
) {
    fun toAppUser() = AppUser(
        userId = userId,
        department = department,
        email = email,
        fullName = fullName,
        profilePic = profilePic
    )
}

data class AppUser(
    val userId: String = "",
    val department: Department = Department(),
    val email: String = "",
    val fullName: String = "",
    val profilePic: String = ""
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
        profilePic = profilePic,
        department = department
    )
}

data class PostUser(
    val userId: String = "",
    val email: String = "",
    val fullName: String = "",
    val profilePic: String = ""
)
