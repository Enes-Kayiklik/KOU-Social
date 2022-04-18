package com.eneskayiklik.eventverse.data.repository.polls

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.util.Settings
import com.eneskayiklik.eventverse.data.model.poll.PollDto
import com.eneskayiklik.eventverse.data.model.share.ShareResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CreatePollRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    suspend fun createPoll(title: String, options: List<String>): ShareResult {
        val poll = PollDto(
            title = title,
            options = options,
            userId = Settings.currentUser.userId
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
            ShareResult(error = e.message ?: "")
        }
    }
}