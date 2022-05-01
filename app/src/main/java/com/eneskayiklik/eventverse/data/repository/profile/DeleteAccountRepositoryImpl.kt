package com.eneskayiklik.eventverse.data.repository.profile

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Resource
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

class DeleteAccountRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
    private val storage: FirebaseStorage = Firebase.storage,
) {
    suspend fun deleteAccount(
        email: String,
        password: String,
    ) = flow {
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
                val credential = EmailAuthProvider.getCredential(email, password)
                // User must be re authenticate before deleting
                user.reauthenticate(credential).await()
                deleteUserDocument(user.uid)
                user.delete().await()
                emit(Resource.Success(UpdatePasswordResult()))
            } catch (e: FirebaseAuthInvalidUserException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        "", data = UpdatePasswordResult(
                            isSuccess = false, emailError = "The email is invalid"
                        )
                    )
                )
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        "", data = UpdatePasswordResult(
                            isSuccess = false, otherError = "The password is invalid"
                        )
                    )
                )
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