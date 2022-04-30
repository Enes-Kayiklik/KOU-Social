package com.eneskayiklik.eventverse.data.repository.profile

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class VerifyAccountRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {
    suspend fun verifyAccount(
        code: String,
    ) = flow {
        emit(Resource.Loading())
        val verifyRef = db.collection(BuildConfig.FIREBASE_REFERENCE)
            .document("verificationCodes")
        var value: Resource<Boolean> = Resource.Success(false)
        db.runTransaction { transaction ->
            val snapshot = transaction.get(verifyRef)

            (snapshot[code] as? Boolean)?.let {
                value = if (it.not()) {
                    transaction.update(verifyRef, code, true)
                    Resource.Success(true)
                } else Resource.Error("Unavailable code")
            } ?: run {
                value = Resource.Error("Unavailable code")
            }
            null
        }.await()
        if (value.response == true) updateUser()
        emit(value)
    }

    private suspend fun updateUser() {
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("users")
                .collection("users")
                .document(Settings.currentUser.userId)
                .update("verified", true)
                .await()
            Settings.currentUser = Settings.currentUser.copy(
                verified = true
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}