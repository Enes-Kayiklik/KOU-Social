package com.eneskayiklik.eventverse.data.repository.profile

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.data.model.profile.UpdatePasswordResult
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class SettingsRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
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

    suspend fun deleteAccount(idToken: String) = flow {
        emit(Resource.Loading())
        val user = auth.currentUser
        if (user == null) emit(
            Resource.Error(
                "", data = UpdatePasswordResult(
                    isSuccess = false, otherError = "Unknown Error"
                )
            )
        ) else {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                // User must be re authenticate before deleting
                user.reauthenticate(credential).await()
                user.delete().await()
                deleteUserDocument(Settings.currentUser.userId)
                emit(Resource.Success(UpdatePasswordResult()))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        "", data = UpdatePasswordResult(
                            isSuccess = false, otherError = "Unknown Error"
                        )
                    )
                )
            }
        }
    }

    private suspend fun deleteUserDocument(uid: String) {
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document(uid)
                .delete()
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}