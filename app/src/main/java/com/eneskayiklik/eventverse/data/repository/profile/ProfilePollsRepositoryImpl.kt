package com.eneskayiklik.eventverse.data.repository.profile

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.model.poll.PollDto
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProfilePollsRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    // private var lastCreatedAt: Timestamp? = null
    suspend fun getPolls(getNext: Boolean, userId: String) = flow {
        if (getNext.not()) return@flow
        try {
            emit(Resource.Loading())
            if (Settings.userStorage.isEmpty()) Settings.getAllUsers(db)
            val data = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("polls")
                .collection("polls")
                .whereEqualTo("userId", userId)
                // We can't combine two different compare function see: https://firebase.google.com/docs/firestore/query-data/queries#kotlin+ktx_3
                //.whereLessThan("createdAt", lastCreatedAt ?: Timestamp.now())
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get().await().toObjects(PollDto::class.java)
                .map { it.toPoll() }
            //lastCreatedAt = data.last().createdAt
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }

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