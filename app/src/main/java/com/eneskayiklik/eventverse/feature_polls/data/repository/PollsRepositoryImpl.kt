package com.eneskayiklik.eventverse.feature_polls.data.repository

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.core.util.Settings
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class PollsRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    suspend fun votePoll(
        pollId: String,
        vote: Int
    ) {
        try {
            db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("polls")
                .collection("polls")
                .document(pollId)
                .set(
                    hashMapOf(
                        "answers" to hashMapOf(
                            Settings.currentUser.userId to vote
                        )
                    ), SetOptions.merge()
                ).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}