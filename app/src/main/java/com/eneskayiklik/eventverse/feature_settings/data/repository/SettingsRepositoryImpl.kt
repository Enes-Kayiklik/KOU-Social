package com.eneskayiklik.eventverse.feature_settings.data.repository

import com.eneskayiklik.eventverse.core.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class SettingsRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth
) {

    suspend fun logOut() = flow {
        try {
            emit(Resource.Loading())
            auth.signOut()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }

    suspend fun deleteAccount() = flow {
        try {
            emit(Resource.Loading())
            auth.currentUser?.delete()?.await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }
}