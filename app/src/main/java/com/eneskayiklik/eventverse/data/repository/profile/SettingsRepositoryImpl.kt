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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class SettingsRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
    private val storage: FirebaseStorage = Firebase.storage,
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
                deleteUserDocument(Settings.currentUser.userId)
                user.delete().await()
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
            val batch = db.batch()
            // Delete user data
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document(uid)
                .delete()
                .await()

            // Delete photos uploaded by this user
            try {
                storage.reference
                    .child("images/$uid")
                    .listAll()
                    .await()
                    .items.forEach { it.delete() }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Delete polls created by this user
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("polls")
                .collection("polls")
                .whereEqualTo("userId", uid)
                .get()
                .await()
                .documents
                .forEach { batch.delete(it.reference) }

            // Delete posts created by this user
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("posts")
                .collection("posts")
                .whereEqualTo("userId", uid)
                .get()
                .await()
                .documents
                .forEach { batch.delete(it.reference) }

            // Delete events created by this user
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("events")
                .collection("events")
                .whereEqualTo("user", uid)
                .get()
                .await()
                .documents
                .forEach { batch.delete(it.reference) }

            batch.commit().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}