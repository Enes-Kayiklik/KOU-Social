package com.eneskayiklik.eventverse.feature_settings.data.repository

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.core.util.Resource
import com.eneskayiklik.eventverse.feature_settings.data.model.UpdatePasswordResult
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class DeleteAccountRepositoryImpl(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
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
                user.delete().await()
                deleteUserDocument(user.uid)
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