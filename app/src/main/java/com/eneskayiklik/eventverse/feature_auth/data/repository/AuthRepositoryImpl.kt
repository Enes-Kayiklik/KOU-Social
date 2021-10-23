package com.eneskayiklik.eventverse.feature_auth.data.repository

import com.eneskayiklik.eventverse.feature_auth.domain.LoginResult
import com.eneskayiklik.eventverse.feature_auth.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun loginWithEmail(email: String, password: String): LoginResult {
        return LoginResult(isSuccess = true)
    }
}