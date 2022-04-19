package com.eneskayiklik.eventverse.data.repository.polls

import android.util.Log
import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.model.poll.PollDto
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class PollsRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    private var lastSnapshot: QuerySnapshot? = null
    suspend fun getPolls(getNext: Boolean, isRefreshing: Boolean = false) = flow {
        if (getNext.not()) return@flow
        // Refresh page
        if (isRefreshing) lastSnapshot = null
        try {
            emit(Resource.Loading())
            if (Settings.userStorage.isEmpty()) Settings.getAllUsers(db)
            val snapshot = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("polls")
                .collection("polls")
                .orderBy("createdAt")
                .startAfter(lastSnapshot)
                .limit(25)
                .get().await()
            lastSnapshot = snapshot
            val data = snapshot.toObjects(PollDto::class.java)
                .map { it.toPoll() }
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
            Log.e("TAG", "getPolls: $e", )
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