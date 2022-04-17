package com.eneskayiklik.eventverse.feature_polls.data.repository

import android.util.Log
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.core.util.Settings
import com.eneskayiklik.eventverse.feature_polls.data.model.Poll
import com.eneskayiklik.eventverse.feature_share.data.model.ShareResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CreatePollRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    suspend fun createPoll(title: String, options: List<String>): ShareResult {
        val poll = Poll(
            title = title,
            options = options,
            fromUser = Settings.currentUser.toPostUser()
        )

        return try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("polls")
                .collection("polls")
                .add(poll)
                .await()
            ShareResult(isSuccess = true)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "createPoll: $e", )
            ShareResult(error = e.message ?: "")
        }
    }
}