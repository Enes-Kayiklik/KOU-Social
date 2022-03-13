package com.eneskayiklik.eventverse.feature_auth.data.model

import com.eneskayiklik.eventverse.feature_auth.domain.model.Department
import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val userId: String = "",
    val department: Department = Department(),
    val email: String = "",
    val fullName: String = ""
)
