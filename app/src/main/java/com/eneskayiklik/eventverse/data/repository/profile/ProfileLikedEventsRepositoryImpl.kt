package com.eneskayiklik.eventverse.data.repository.profile

import com.eneskayiklik.eventverse.BuildConfig
import com.eneskayiklik.eventverse.data.model.create.EventDto
import com.eneskayiklik.eventverse.util.Resource
import com.eneskayiklik.eventverse.util.Settings
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProfileLikedEventsRepositoryImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    suspend fun getEvents(getNext: Boolean, userId: String) = flow {
        if (getNext.not()) return@flow
        try {
            emit(Resource.Loading())
            if (Settings.userStorage.isEmpty()) Settings.getAllUsers(db)
            val data = db.collection(BuildConfig.FIREBASE_REFERENCE)
                .document("events")
                .collection("events")
                .whereArrayContains("likedBy", userId)
                .get().await().toObjects(EventDto::class.java)
                .map { it.toEvent() }
            emit(Resource.Success(data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: ""))
        }
    }
}