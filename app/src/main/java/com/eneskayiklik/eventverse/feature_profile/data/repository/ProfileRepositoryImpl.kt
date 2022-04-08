package com.eneskayiklik.eventverse.feature_profile.data.repository

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.feature_auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import java.lang.NullPointerException

class ProfileRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
) {

    suspend fun getUser(userId: String) = flow {
        try {
            emit(Resource.Loading())
            val user = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document(userId)
                .get()
                .await()
                .toObject(User::class.java)
            emit(Resource.Success(user ?: throw NullPointerException("Null User")))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }

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
}