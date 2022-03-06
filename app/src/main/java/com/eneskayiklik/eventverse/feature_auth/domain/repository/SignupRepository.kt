package com.eneskayiklik.eventverse.feature_auth.domain.repository

import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.feature_auth.domain.LoginResult
import kotlinx.coroutines.flow.Flow

interface SignupRepository<T> {
    suspend fun signupWithEmail(
        email: String,
        password: String
    ): LoginResult

    suspend fun getFaculties(): Flow<T>
}