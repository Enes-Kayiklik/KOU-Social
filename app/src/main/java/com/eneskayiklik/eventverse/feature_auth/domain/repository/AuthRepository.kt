package com.eneskayiklik.eventverse.feature_auth.domain.repository

import com.eneskayiklik.eventverse.feature_auth.domain.LoginResult

interface AuthRepository {
    suspend fun loginWithEmail(
        email: String,
        password: String
    ): LoginResult
}