package com.eneskayiklik.eventverse.feature_auth.data.repository

import com.eneskayiklik.eventverse.feature_auth.domain.LoginResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.feature_auth.domain.model.Faculty
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class SignupRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {
    suspend fun signupWithEmail(email: String, password: String): LoginResult {
        return LoginResult()
    }

    suspend fun getFaculties() = flow {
        emit(Resource.Loading())
        val data = try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("faculties")
                .collection("faculties")
                .get()
                .await()
                .toObjects(Faculty::class.java)
        } catch (e: Exception) {
            null
        }
        if (data != null) emit(Resource.Success(data)) else emit(Resource.Error(""))
    }
}