package com.eneskayiklik.eventverse.feature_auth.domain.model

data class User(
    val user: InnerUser = InnerUser()
)

data class InnerUser(
    val name: String = "",
    val pic: String = "",
)
