package com.eneskayiklik.eventverse.feature_auth.data.repository

import com.eneskayiklik.eventverse.feature_auth.domain.LoginResult
import com.eneskayiklik.eventverse.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    override suspend fun loginWithEmail(email: String, password: String): LoginResult {
        delay(3000L)
        return LoginResult(isSuccess = true)
    }
}